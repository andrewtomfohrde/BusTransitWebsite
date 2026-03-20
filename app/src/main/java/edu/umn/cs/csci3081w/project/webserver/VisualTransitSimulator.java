package edu.umn.cs.csci3081w.project.webserver;

import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.BusFactory;
import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.StorageFacility;
import edu.umn.cs.csci3081w.project.model.Train;
import edu.umn.cs.csci3081w.project.model.TrainFactory;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.VehicleConcreteSubject;
import edu.umn.cs.csci3081w.project.model.VehicleFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Core simulation engine for the Visual Transit Simulator.
 *
 * <p>This class manages simulation time steps, vehicle creation,
 * vehicle movement, line updates, observer notification, and
 * communication with the web server.
 */
public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private Counter counter;
  private List<Line> lines;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;
  private WebServerSession webServerSession;
  private boolean paused = false;
  private VehicleFactory busFactory;
  private VehicleFactory trainFactory;
  private VehicleConcreteSubject vehicleConcreteSubject;

  /**
   * Constructs a visual transit simulator.
   *
   * @param configFile path to the configuration file
   * @param webServerSession session used to communicate with the frontend
   */
  public VisualTransitSimulator(String configFile, WebServerSession webServerSession) {
    this.webServerSession = webServerSession;
    this.counter = new Counter();
    ConfigManager configManager = new ConfigManager();
    configManager.readConfig(counter, configFile);
    this.lines = configManager.getLines();
    this.activeVehicles = new ArrayList<>();
    this.completedTripVehicles = new ArrayList<>();
    this.vehicleStartTimings = new ArrayList<>();
    this.timeSinceLastVehicle = new ArrayList<>();
    this.storageFacility = configManager.getStorageFacility();
    if (this.storageFacility == null) {
      this.storageFacility = new StorageFacility(
          Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    vehicleConcreteSubject = new VehicleConcreteSubject(webServerSession);

    if (LOGGING) {
      System.out.println("////Simulation Lines////");
      for (Line line : lines) {
        line.report(System.out);
      }
    }
  }

  /**
   * Initializes vehicle factories based on the current simulation time.
   *
   * @param time hour of day simulation starts
   */
  public void setVehicleFactories(int time) {
    this.busFactory = new BusFactory(storageFacility, counter, time);
    this.trainFactory = new TrainFactory(storageFacility, counter, time);
  }

  /**
   * Starts the simulation.
   *
   * @param vehicleStartTimings time between vehicle launches per line
   * @param numTimeSteps number of simulation steps to run
   */
  public void start(List<Integer> vehicleStartTimings, int numTimeSteps) {
    this.vehicleStartTimings = vehicleStartTimings;
    this.numTimeSteps = numTimeSteps;
    this.timeSinceLastVehicle.clear();
    for (int i = 0; i < vehicleStartTimings.size(); i++) {
      this.timeSinceLastVehicle.add(0);
    }
    simulationTimeElapsed = 0;
  }

  /**
   * Toggles the paused state of the simulation.
   */
  public void togglePause() {
    paused = !paused;
  }

  /**
   * Advances the simulation by one time step.
   */
  public void update() {
    if (paused) {
      return;
    }

    simulationTimeElapsed++;
    if (simulationTimeElapsed > numTimeSteps) {
      return;
    }

    // Vehicle generation
    for (int i = 0; i < timeSinceLastVehicle.size(); i++) {
      Line line = lines.get(i);
      if (timeSinceLastVehicle.get(i) <= 0) {
        Vehicle generatedVehicle = null;
        if (line.getType().equals(Line.BUS_LINE) && !line.isIssueExist()) {
          generatedVehicle = busFactory.generateVehicle(line.shallowCopy());
        } else if (line.getType().equals(Line.TRAIN_LINE) && !line.isIssueExist()) {
          generatedVehicle = trainFactory.generateVehicle(line.shallowCopy());
        }

        if (generatedVehicle != null && !line.isIssueExist()) {
          activeVehicles.add(generatedVehicle);
        }
        timeSinceLastVehicle.set(i, vehicleStartTimings.get(i) - 1);
      } else if (!line.isIssueExist()) {
        timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
      }
    }

    // Update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      currVehicle.update();
      if (currVehicle.isTripComplete()) {
        Vehicle completed = activeVehicles.remove(i);
        completedTripVehicles.add(completed);
        if (completed instanceof Bus) {
          busFactory.returnVehicle(completed);
        } else if (completed instanceof Train) {
          trainFactory.returnVehicle(completed);
        }
      }
    }

    // Update lines
    for (Line line : lines) {
      line.update();
    }

    vehicleConcreteSubject.notifyObservers();
  }

  /**
   * Returns all simulation lines.
   *
   * @return list of lines
   */
  public List<Line> getLines() {
    return lines;
  }

  /**
   * Returns all currently active vehicles.
   *
   * @return list of active vehicles
   */
  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }

  /**
   * Registers a vehicle as an observer.
   *
   * @param vehicle vehicle to observe
   */
  public void addObserver(Vehicle vehicle) {
    vehicleConcreteSubject.attachObserver(vehicle);
  }
}
