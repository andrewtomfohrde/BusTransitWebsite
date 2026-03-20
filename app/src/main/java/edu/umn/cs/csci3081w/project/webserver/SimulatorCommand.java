package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * Abstract base class for commands executed by the simulator.
 *
 * <p>Each concrete command defines how to respond to a specific
 * request from the web server by implementing the {@link #execute}
 * method.
 */
public abstract class SimulatorCommand {

  /**
   * Constructs a simulator command.
   *
   * <p>This constructor is protected because this class is intended
   * to be subclassed.
   */
  protected SimulatorCommand() {
    // Default constructor
  }

  /**
   * Executes the command using the given web server session and command data.
   *
   * @param session the current web server session
   * @param command the JSON object containing command parameters
   */
  public abstract void execute(WebServerSession session, JsonObject command);
}
