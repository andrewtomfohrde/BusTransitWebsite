package edu.umn.cs.csci3081w.project.webserver;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains the mapping between command names and simulator commands
 * for a single {@link WebServerSession}.
 *
 * <p>This class acts as a registry that allows incoming JSON commands
 * to be dispatched to their corresponding {@link SimulatorCommand}
 * implementations.
 */
public class WebServerSessionState {

  private Map<String, SimulatorCommand> commands;

  /**
   * Creates a new web server session state with an empty command map.
   */
  public WebServerSessionState() {
    this.commands = new HashMap<String, SimulatorCommand>();
  }

  /**
   * Returns the mapping of command names to simulator commands.
   *
   * @return map of command strings to {@link SimulatorCommand} objects
   */
  public Map<String, SimulatorCommand> getCommands() {
    return commands;
  }
}
