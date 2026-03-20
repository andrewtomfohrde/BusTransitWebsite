package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.DieselTrain;
import edu.umn.cs.csci3081w.project.model.ElectricTrain;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.RgbaColor;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * Test vehicle commands.
 */
public class GetVehiclesCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession session;
  private GetVehiclesCommand command;

  /**
   * Set up mock VTS and webserver session.
   */
  @BeforeEach
  public void setUp() {
    simulator = mock(VisualTransitSimulator.class);
    session = mock(WebServerSession.class);
    command = new GetVehiclesCommand(simulator);
  }

  /**
   * Tests a single vehicle command.
   */
  @Test
  public void testExecuteSendsSingleVehicle() {
    // Arrange: a small bus
    SmallBus bus = mock(SmallBus.class);
    when(bus.getId()).thenReturn(1);
    when(bus.getPassengers()).thenReturn(new ArrayList<>());
    when(bus.getCapacity()).thenReturn(20);
    when(bus.getCurrentCO2Emission()).thenReturn(5);
    when(bus.getPosition()).thenReturn(new Position(44.0, -93.0));
    when(bus.getColor()).thenReturn(new RgbaColor(255, 0, 0, 255));

    when(simulator.getActiveVehicles()).thenReturn(List.of(bus));

    command.execute(session, new JsonObject());

    ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(captor.capture());

    JsonObject sent = captor.getValue();
    assertEquals("updateVehicles", sent.get("command").getAsString());

    JsonArray vehiclesArray = sent.getAsJsonArray("vehicles");
    assertEquals(1, vehiclesArray.size());

    JsonObject vehicleJson = vehiclesArray.get(0).getAsJsonObject();
    assertEquals(1, vehicleJson.get("id").getAsInt());
    assertEquals(20, vehicleJson.get("capacity").getAsInt());
    assertEquals(0, vehicleJson.get("numPassengers").getAsInt());
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, vehicleJson.get("type").getAsString());
    assertEquals(5.0, vehicleJson.get("co2").getAsDouble());

    JsonObject pos = vehicleJson.getAsJsonObject("position");
    assertEquals(44.0, pos.get("longitude").getAsDouble());
    assertEquals(-93.0, pos.get("latitude").getAsDouble());

    JsonObject color = vehicleJson.getAsJsonObject("color");
    assertEquals(255, color.get("r").getAsInt());
    assertEquals(0, color.get("g").getAsInt());
    assertEquals(0, color.get("b").getAsInt());
    assertEquals(255, color.get("alpha").getAsInt());
  }

  /**
   * Test multiple vehicle types with commands.
   */
  @Test
  public void testExecuteSendsMultipleVehicleTypes() {
    SmallBus bus = mock(SmallBus.class);
    LargeBus largeBus = mock(LargeBus.class);
    ElectricTrain electTrain = mock(ElectricTrain.class);
    DieselTrain diesTrain = mock(DieselTrain.class);

    List<Vehicle> vehicles = List.of(bus, largeBus, electTrain, diesTrain);
    when(simulator.getActiveVehicles()).thenReturn(vehicles);

    // Minimal stubbing for type fields
    when(bus.getId()).thenReturn(1);
    when(bus.getPassengers()).thenReturn(new ArrayList<>());
    when(bus.getCapacity()).thenReturn(10);
    when(bus.getCurrentCO2Emission()).thenReturn(1);
    when(bus.getPosition()).thenReturn(new Position(0, 0));
    when(bus.getColor()).thenReturn(new RgbaColor(0, 0, 0, 0));

    when(largeBus.getId()).thenReturn(2);
    when(largeBus.getPassengers()).thenReturn(new ArrayList<>());
    when(largeBus.getCapacity()).thenReturn(20);
    when(largeBus.getCurrentCO2Emission()).thenReturn(2);
    when(largeBus.getPosition()).thenReturn(new Position(1, 1));
    when(largeBus.getColor()).thenReturn(new RgbaColor(1, 1, 1, 1));

    when(electTrain.getId()).thenReturn(3);
    when(electTrain.getPassengers()).thenReturn(new ArrayList<>());
    when(electTrain.getCapacity()).thenReturn(30);
    when(electTrain.getCurrentCO2Emission()).thenReturn(3);
    when(electTrain.getPosition()).thenReturn(new Position(2, 2));
    when(electTrain.getColor()).thenReturn(new RgbaColor(2, 2, 2, 2));

    when(diesTrain.getId()).thenReturn(4);
    when(diesTrain.getPassengers()).thenReturn(new ArrayList<>());
    when(diesTrain.getCapacity()).thenReturn(40);
    when(diesTrain.getCurrentCO2Emission()).thenReturn(4);
    when(diesTrain.getPosition()).thenReturn(new Position(3, 3));
    when(diesTrain.getColor()).thenReturn(new RgbaColor(3, 3, 3, 3));

    command.execute(session, new JsonObject());

    ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(captor.capture());

    JsonObject sent = captor.getValue();
    JsonArray arr = sent.getAsJsonArray("vehicles");
    assertEquals(4, arr.size());

    assertEquals(SmallBus.SMALL_BUS_VEHICLE,
        arr.get(0).getAsJsonObject().get("type").getAsString());
    assertEquals(LargeBus.LARGE_BUS_VEHICLE,
        arr.get(1).getAsJsonObject().get("type").getAsString());
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE,
        arr.get(2).getAsJsonObject().get("type").getAsString());
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE,
        arr.get(3).getAsJsonObject().get("type").getAsString());
  }

  /**
   * Tests a no vehicle command.
   */
  @Test
  public void testExecuteWithNoVehicles() {
    when(simulator.getActiveVehicles()).thenReturn(new ArrayList<>());

    command.execute(session, new JsonObject());

    ArgumentCaptor<JsonObject> captor = ArgumentCaptor.forClass(JsonObject.class);
    verify(session).sendJson(captor.capture());

    JsonObject sent = captor.getValue();
    assertEquals("updateVehicles", sent.get("command").getAsString());
    assertEquals(0, sent.getAsJsonArray("vehicles").size());
  }
}
