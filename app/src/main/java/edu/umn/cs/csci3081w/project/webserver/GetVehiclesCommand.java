package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.DieselTrain;
import edu.umn.cs.csci3081w.project.model.ElectricTrain;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.RgbaColor;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.List;

/**
 * Command that retrieves active vehicle information from the simulation
 * and sends it to the web client.
 */
public class GetVehiclesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructs a command for retrieving vehicles.
   *
   * @param simulator the visual transit simulator instance
   */
  public GetVehiclesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves vehicle information from the simulation and sends it
   * to the current web server session.
   *
   * @param session current simulation session
   * @param command the get-vehicles command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Vehicle> vehicles = simulator.getActiveVehicles();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateVehicles");
    JsonArray vehiclesArray = new JsonArray();
    for (int i = 0; i < vehicles.size(); i++) {
      Vehicle currVehicle = vehicles.get(i);
      JsonObject s = new JsonObject();
      s.addProperty("id", currVehicle.getId());
      s.addProperty("numPassengers", currVehicle.getPassengers().size());
      s.addProperty("capacity", currVehicle.getCapacity());
      String vehicleType = "";
      if (currVehicle instanceof SmallBus) {
        vehicleType = SmallBus.SMALL_BUS_VEHICLE;
      } else if (currVehicle instanceof LargeBus) {
        vehicleType = LargeBus.LARGE_BUS_VEHICLE;
      } else if (currVehicle instanceof ElectricTrain) {
        vehicleType = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      } else if (currVehicle instanceof DieselTrain) {
        vehicleType = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }
      s.addProperty("type", vehicleType);
      s.addProperty("co2", currVehicle.getCurrentCO2Emission());
      JsonObject positionJsonObject = new JsonObject();
      positionJsonObject.addProperty("longitude", currVehicle.getPosition().getLongitude());
      positionJsonObject.addProperty("latitude", currVehicle.getPosition().getLatitude());
      s.add("position", positionJsonObject);

      RgbaColor c = currVehicle.getColor();
      JsonObject colorJsonObject = new JsonObject();
      colorJsonObject.addProperty("r", c.getRed());
      colorJsonObject.addProperty("g", c.getGreen());
      colorJsonObject.addProperty("b", c.getBlue());
      colorJsonObject.addProperty("alpha", c.getAlpha());
      s.add("color", colorJsonObject);
      vehiclesArray.add(s);
    }
    data.add("vehicles", vehiclesArray);
    session.sendJson(data);
  }
}
