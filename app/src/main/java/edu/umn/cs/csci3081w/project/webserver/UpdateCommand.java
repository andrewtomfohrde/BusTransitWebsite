package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Command that advances the simulation by one update step.
 *
 * <p>This command triggers the simulator to update all active entities,
 * including vehicles, passengers, and routes.
 */
public class UpdateCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Creates an update command for the given simulator.
   *
   * @param simulator the simulation instance to be updated
   */
  public UpdateCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Executes a single simulation update.
   *
   * @param session current simulation session
   * @param command the update simulation command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    simulator.update();
  }
}
