package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import java.util.List;

/**
 * Command that injects an issue into a specific line within the simulation.
 *
 * <p>The line to modify is identified by an ID provided in the incoming JSON command.
 */
public class LineIssueCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructs a LineIssueCommand.
   *
   * @param simulator the visual transit simulator instance
   */
  public LineIssueCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Injects an issue into the line whose ID is provided in the command.
   *
   * @param session the current web server session
   * @param command the JSON command containing the line ID
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Line> lines = simulator.getLines();
    int lineId = command.get("id").getAsInt();
    for (Line line : lines) {
      if (line.getId() == lineId) {
        line.createIssue();
      }
    }
  }
}
