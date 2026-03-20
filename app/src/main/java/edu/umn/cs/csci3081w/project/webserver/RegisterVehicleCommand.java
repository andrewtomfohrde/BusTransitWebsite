package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.List;

/**
 * Command that registers a vehicle as an observer in the simulation.
 *
 * <p>The vehicle to register is identified by an ID provided in the
 * incoming JSON command.
 */
public class RegisterVehicleCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructs a register-vehicle command.
   *
   * @param simulator the visual transit simulator instance
   */
  public RegisterVehicleCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Registers a vehicle as an observer with the simulator.
   *
   * @param session current simulation session
   * @param command the JSON command containing the vehicle ID
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Vehicle> activeVehicles = simulator.getActiveVehicles();
    int vehicleId = command.get("id").getAsInt();
    Vehicle vehicle = null;
    for (Vehicle activeVehicle : activeVehicles) {
      if (activeVehicle.getId() == vehicleId) {
        vehicle = activeVehicle;
      }
    }
    simulator.addObserver(vehicle);
  }
}
