package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Command that pauses or resumes the simulation.
 *
 * <p>This command toggles the paused state of the
 * {@link VisualTransitSimulator}.
 */
public class PauseCommand extends SimulatorCommand {
  private VisualTransitSimulator visSim;

  /**
   * Constructs a pause command.
   *
   * @param visSim the visual transit simulator instance
   */
  public PauseCommand(VisualTransitSimulator visSim) {
    this.visSim = visSim;
  }

  /**
   * Tells the simulator to pause or resume the simulation.
   *
   * @param session object representing the simulation session
   * @param command object containing the original command
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    visSim.togglePause();
  }
}
