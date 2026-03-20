package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * Represents a passenger in the transit simulation.
 *
 * <p>A passenger has a destination stop and tracks how long they have
 * waited at a stop and how long they have spent on a vehicle.
 */
public class Passenger {

  private String name;
  private int destinationStopId;
  private int waitAtStop;
  private int timeOnVehicle;

  /**
   * Constructs a passenger.
   *
   * @param destinationStopId destination stop identifier
   * @param name name of the passenger
   */
  public Passenger(int destinationStopId, String name) {
    this.name = name;
    this.destinationStopId = destinationStopId;
    this.waitAtStop = 0;
    this.timeOnVehicle = 0;
  }

  /**
   * Updates the passenger's time counters depending on whether
   * they are waiting at a stop or riding on a vehicle.
   */
  public void pasUpdate() {
    if (isOnVehicle()) {
      timeOnVehicle++;
    } else {
      waitAtStop++;
    }
  }

  /**
   * Marks the passenger as being on a vehicle.
   */
  public void setOnVehicle() {
    timeOnVehicle = 1;
  }

  /**
   * Checks whether the passenger is currently on a vehicle.
   *
   * @return {@code true} if the passenger is on a vehicle, otherwise {@code false}
   */
  public boolean isOnVehicle() {
    return timeOnVehicle > 0;
  }

  /**
   * Gets the destination stop ID for the passenger.
   *
   * @return destination stop identifier
   */
  public int getDestination() {
    return destinationStopId;
  }

  /**
   * Reports passenger statistics.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Passenger Info Start####");
    out.println("Name: " + name);
    out.println("Destination: " + destinationStopId);
    out.println("Wait at stop: " + waitAtStop);
    out.println("Time on vehicle: " + timeOnVehicle);
    out.println("####Passenger Info End####");
  }

  /**
   * Gets the amount of time the passenger has waited at a stop.
   *
   * @return time waited at stop
   */
  public int getWaitAtStop() {
    return waitAtStop;
  }

  /**
   * Gets the amount of time the passenger has spent on a vehicle.
   *
   * @return time spent on vehicle
   */
  public int getTimeOnVehicle() {
    return timeOnVehicle;
  }
}
