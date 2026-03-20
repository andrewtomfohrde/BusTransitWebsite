package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import java.util.List;

/**
 * Command that retrieves route information from the simulation and sends it
 * to the web client.
 */
public class GetRoutesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructs a command for retrieving routes.
   *
   * @param simulator the visual transit simulator instance
   */
  public GetRoutesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves routes information from the simulation and sends it
   * to the current web server session.
   *
   * @param session current simulation session
   * @param command the get-routes command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Line> lines = simulator.getLines();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateRoutes");
    JsonArray routesArray = new JsonArray();
    for (int i = 0; i < lines.size(); i++) {
      routesArray.add(getRouteJsonObject(lines.get(i).getOutboundRoute()));
      routesArray.add(getRouteJsonObject(lines.get(i).getInboundRoute()));
    }
    data.add("routes", routesArray);
    session.sendJson(data);
  }

  /**
   * Creates a JSON representation of a route.
   *
   * @param route the route to convert
   * @return a JSON object representing the route and its stops
   */
  private JsonObject getRouteJsonObject(Route route) {
    JsonObject r = new JsonObject();
    r.addProperty("id", route.getId());
    JsonArray stopArray = new JsonArray();
    for (int j = 0; j < route.getStops().size(); j++) {
      JsonObject stopStruct = new JsonObject();
      stopStruct.addProperty("id", route.getStops().get(j).getId());
      stopStruct.addProperty("numPeople", route.getStops().get(j).getPassengers().size());
      JsonObject jsonObj = new JsonObject();
      jsonObj.addProperty("longitude",
          route.getStops().get(j).getPosition().getLongitude());
      jsonObj.addProperty("latitude",
          route.getStops().get(j).getPosition().getLatitude());
      stopStruct.add("position", jsonObj);
      stopArray.add(stopStruct);
    }
    r.add("stops", stopArray);
    return r;
  }
}
