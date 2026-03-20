package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;

/**
 * Command that initializes line information for the simulation
 * and sends it to the web client.
 */
public class InitLinesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructs a command for initializing lines.
   *
   * @param simulator the visual transit simulator instance
   */
  public InitLinesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Initializes the lines information for the simulation and sends it
   * to the current web server session.
   *
   * @param session current simulation session
   * @param command the initialize-lines command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    int numLines = simulator.getLines().size();
    JsonObject data = new JsonObject();
    data.addProperty("command", "initLines");
    data.addProperty("numLines", numLines);
    JsonArray linesArray = new JsonArray();
    for (int i = 0; i < simulator.getLines().size(); i++) {
      JsonObject s = new JsonObject();
      Line line = simulator.getLines().get(i);
      s.addProperty("id", line.getId());
      s.addProperty("name", line.getName());
      s.addProperty("type", line.getType());
      linesArray.add(s);
    }
    data.add("lines", linesArray);
    session.sendJson(data);
  }
}
