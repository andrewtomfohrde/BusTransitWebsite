package edu.umn.cs.csci3081w.project.model;

import com.google.gson.JsonObject;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a vehicle that moves along a transit line.
 */
public abstract class Vehicle implements VehicleObserver {
  private int id;
  private int capacity;
  private double speed;
  private PassengerLoader loader;
  private PassengerUnloader unloader;
  private List<Passenger> passengers;
  private String name;
  private Position position;
  private Line line;
  private double distanceRemaining;
  private Stop nextStop;
  private List<Integer> carbonEmissionHistory;
  private VehicleConcreteSubject vehicleConcreteSubject;
  private RgbaColor color;

  /**
   * Unused constructor required for subclasses.
   *
   * @param id                vehicle id
   * @param capacity          vehicle capacity
   * @param speed             vehicle speed
   * @param passengerLoader   loader strategy
   * @param passengerUnloader unloader strategy
   */
  public Vehicle(int id, int capacity, double speed, PassengerLoader passengerLoader,
                 PassengerUnloader passengerUnloader) {
  }

  /**
   * Main constructor for Vehicle.
   *
   * @param id       vehicle id
   * @param line     transit line
   * @param capacity passenger capacity
   * @param speed    movement speed
   * @param loader   loader strategy
   * @param unloader unloader strategy
   */
  public Vehicle(int id, Line line, int capacity, double speed, PassengerLoader loader,
                 PassengerUnloader unloader) {
    this.id = id;
    this.capacity = capacity;
    this.speed = speed;
    this.loader = loader;
    this.unloader = unloader;
    this.passengers = new ArrayList<Passenger>();
    this.line = line;
    this.distanceRemaining = 0;
    this.nextStop = line.getOutboundRoute().getNextStop();
    setName(line.getOutboundRoute().getName() + id);
    setPosition(new Position(nextStop.getPosition().getLongitude(),
        nextStop.getPosition().getLatitude()));
    carbonEmissionHistory = new ArrayList<Integer>();
  }

  /**
   * Returns the vehicle color.
   *
   * @return the current vehicle color
   */
  public RgbaColor getColor() {
    return color;
  }

  /**
   * Sets the vehicle color.
   *
   * @param color the color to set for this vehicle
   */
  void setColor(RgbaColor color) {
    this.color = color;
  }

  /**
   * Reports vehicle state.
   *
   * @param out the {@link PrintStream} to write the report to
   */
  public abstract void report(PrintStream out);

  /**
   * Gets current CO2 emission.
   *
   * @return current CO2 emission value
   */
  public abstract int getCurrentCO2Emission();

  /**
   * Returns vehicle ID.
   *
   * @return vehicle id
   */
  public int getId() {
    return id;
  }

  /**
   * Returns passenger capacity.
   *
   * @return passenger capacity
   */
  public int getCapacity() {
    return capacity;
  }

  /**
   * Returns speed.
   *
   * @return vehicle speed
   */
  public double getSpeed() {
    return speed;
  }

  /**
   * Returns passenger loader strategy.
   *
   * @return passenger loader
   */
  public PassengerLoader getPassengerLoader() {
    return loader;
  }

  /**
   * Returns passenger unloader strategy.
   *
   * @return passenger unloader
   */
  public PassengerUnloader getPassengerUnloader() {
    return unloader;
  }

  /**
   * Returns list of passengers.
   *
   * @return list of passengers currently on the vehicle
   */
  public List<Passenger> getPassengers() {
    return passengers;
  }

  /**
   * Returns vehicle name.
   *
   * @return vehicle name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets vehicle name.
   *
   * @param name name to assign to this vehicle
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns current position.
   *
   * @return current vehicle position
   */
  public Position getPosition() {
    return position;
  }

  /**
   * Sets position.
   *
   * @param position new position for this vehicle
   */
  public void setPosition(Position position) {
    this.position = position;
  }

  /**
   * Returns whether the trip is complete.
   *
   * @return {@code true} if both outbound and inbound routes are at the end,
   *     otherwise {@code false}
   */
  public boolean isTripComplete() {
    return line.getOutboundRoute().isAtEnd() && line.getInboundRoute().isAtEnd();
  }

  /**
   * Loads a passenger if capacity permits.
   *
   * @param newPassenger passenger to load
   * @return number of passengers loaded (0 or 1)
   */
  public int loadPassenger(Passenger newPassenger) {
    return getPassengerLoader().loadPassenger(newPassenger, getCapacity(), getPassengers());
  }

  /**
   * Moves the vehicle along its route.
   */
  public void move() {
    double speed = updateDistance();
    if (!isTripComplete() && distanceRemaining <= 0) {
      int passengersHandled = handleStop();
      if (passengersHandled >= 0) {
        distanceRemaining = 0;
      }
      toNextStop();
    }

    Route currentRoute = line.getOutboundRoute();
    if (line.getOutboundRoute().isAtEnd()) {
      if (line.getInboundRoute().isAtEnd()) {
        return;
      }
      currentRoute = line.getInboundRoute();
    }
    Stop prevStop = currentRoute.prevStop();
    Stop nextStop = currentRoute.getNextStop();
    double distanceBetween = currentRoute.getNextStopDistance();
    double ratio;
    if (distanceBetween - 0.00001 < 0) {
      ratio = 1;
    } else {
      ratio = distanceRemaining / distanceBetween;
      if (ratio < 0) {
        ratio = 0;
        distanceRemaining = 0;
      }
    }

    double newLongitude = nextStop.getPosition().getLongitude() * (1 - ratio)
        + prevStop.getPosition().getLongitude() * ratio;

    double newLatitude = nextStop.getPosition().getLatitude() * (1 - ratio)
        + prevStop.getPosition().getLatitude() * ratio;

    setPosition(new Position(newLongitude, newLatitude));
  }

  /**
   * Updates passengers, movement, and carbon emissions.
   */
  public void update() {
    for (Passenger passenger : getPassengers()) {
      passenger.pasUpdate();
    }
    if (!line.isIssueExist()) {
      move();
    }
    carbonEmissionHistory.add(0, getCurrentCO2Emission());
  }

  /**
   * Unloads passengers for current stop.
   *
   * @return number of passengers unloaded at this stop
   */
  private int unloadPassengers() {
    return getPassengerUnloader().unloadPassengers(getPassengers(), nextStop);
  }

  /**
   * Handles unloading and loading at a stop.
   *
   * @return total number of passengers unloaded and loaded
   */
  public int handleStop() {
    int passengersHandled = 0;
    passengersHandled += unloadPassengers();
    passengersHandled += nextStop.loadPassengers(this);
    if (passengersHandled != 0) {
      distanceRemaining = 0;
    }
    return passengersHandled;
  }

  /**
   * Moves the route pointer to the next stop.
   */
  private void toNextStop() {
    currentRoute().nextStop();
    if (!isTripComplete()) {
      nextStop = currentRoute().getNextStop();
      distanceRemaining += currentRoute().getNextStopDistance();
    } else {
      nextStop = null;
      distanceRemaining = 999;
    }
  }

  /**
   * Updates remaining travel distance.
   *
   * @return the distance reduced (equal to the speed), or 0 if the trip is complete
   *     or the speed is negative
   */
  private double updateDistance() {
    if (isTripComplete()) {
      return 0;
    }
    if (getSpeed() < 0) {
      return 0;
    }
    distanceRemaining -= getSpeed();
    return getSpeed();
  }

  /**
   * Returns the active route based on direction.
   *
   * @return the current active route (outbound or inbound)
   */
  private Route currentRoute() {
    if (!line.getOutboundRoute().isAtEnd()) {
      return line.getOutboundRoute();
    }
    return line.getInboundRoute();
  }

  /**
   * Returns the next stop.
   *
   * @return the next stop, or {@code null} if the trip is complete
   */
  public Stop getNextStop() {
    return nextStop;
  }

  /**
   * Returns the vehicle's line.
   *
   * @return the line this vehicle is traveling on
   */
  public Line getLine() {
    return line;
  }

  /**
   * Returns remaining distance.
   *
   * @return distance remaining to the next stop
   */
  public double getDistanceRemaining() {
    return distanceRemaining;
  }

  /**
   * Sets distanceRemaining.
   *
   * @param distanceRemaining new distanceRemaining for this vehicle.
   */
  public void setDistanceRemaining(double distanceRemaining) {
    this.distanceRemaining = distanceRemaining;
  }

  /**
   * Sends observable vehicle information to the subject.
   *
   * @return {@code true} if the trip is complete, otherwise {@code false}
   */
  public boolean provideInfo() {
    boolean tripCompleted = false;

    if (!isTripComplete()) {
      JsonObject data = new JsonObject();
      data.addProperty("command", "observedVehicle");

      String type = "";
      if (this instanceof SmallBus) {
        type = SmallBus.SMALL_BUS_VEHICLE;
      } else if (this instanceof LargeBus) {
        type = LargeBus.LARGE_BUS_VEHICLE;
      } else if (this instanceof ElectricTrain) {
        type = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      } else if (this instanceof DieselTrain) {
        type = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }

      StringBuilder carbonEmissionHistoryString = new StringBuilder();
      int length = Math.min(5, carbonEmissionHistory.size());
      if (length > 0) {
        carbonEmissionHistoryString.append(carbonEmissionHistory.get(0));
        for (int i = 1; i < length; i++) {
          carbonEmissionHistoryString.append(", ");
          carbonEmissionHistoryString.append(carbonEmissionHistory.get(i));
        }
      }

      String stringBuilder = getId()
          + System.lineSeparator()
          + "-----------------------------"
          + System.lineSeparator()
          + "* Type: " + type + System.lineSeparator()
          + String.format("* Position: (%f,%f)", getPosition().getLongitude(),
          getPosition().getLatitude()) + System.lineSeparator()
          + "* Passengers: " + getPassengers().size() + System.lineSeparator()
          + "* CO2: " + carbonEmissionHistoryString + System.lineSeparator();

      data.addProperty("text", stringBuilder);
      vehicleConcreteSubject.getSession().sendJson(data);
      return false;
    } else {
      JsonObject data = new JsonObject();
      data.addProperty("command", "observedVehicle");
      data.addProperty("text", "");
      vehicleConcreteSubject.getSession().sendJson(data);
      return true;
    }
  }

  /**
   * Registers this vehicle with a subject.
   *
   * @param vehicleConcreteSubject subject to attach this vehicle to
   */
  public void setVehicleSubject(VehicleConcreteSubject vehicleConcreteSubject) {
    this.vehicleConcreteSubject = vehicleConcreteSubject;
  }
}
