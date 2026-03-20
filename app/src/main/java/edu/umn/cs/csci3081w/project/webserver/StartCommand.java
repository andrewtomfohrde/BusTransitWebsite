package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Command that starts the simulation.
 *
 * <p>This command initializes vehicle factories based on the current
 * simulation time and begins the simulation for the specified number
 * of time steps.
 */
public class StartCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructs a start command.
   *
   * @param simulator simulation object
   */
  public StartCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Starts the simulation using parameters provided in the command.
   *
   * @param session current simulation session
   * @param command the content of the start simulation command
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Integer> timeBetweenVehicles = new ArrayList<Integer>();
    int numTimeSteps = command.get("numTimeSteps").getAsInt();
    JsonArray arr = command.getAsJsonArray("timeBetweenVehicles");
    for (int i = 0; i < arr.size(); i++) {
      timeBetweenVehicles.add(arr.get(i).getAsInt());
    }
    for (int i = 0; i < timeBetweenVehicles.size(); i++) {
      System.out.println("Time between vehicles for route  " + i + ": "
          + timeBetweenVehicles.get(i));
    }
    System.out.println("Number of time steps for simulation is: " + numTimeSteps);
    System.out.println("Starting simulation");
    simulator.setVehicleFactories(getCurrentSimulationTime());
    simulator.start(timeBetweenVehicles, numTimeSteps);
  }

  /**
   * Gets the current simulation time based on the system clock.
   *
   * @return the current hour of the day (0–23)
   */
  public int getCurrentSimulationTime() {
    return LocalDateTime.now().getHour();
  }
}
