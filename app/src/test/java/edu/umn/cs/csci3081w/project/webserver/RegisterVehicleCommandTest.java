package edu.umn.cs.csci3081w.project.webserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for RegisterVehicleCommand class.
 */
class RegisterVehicleCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession session;
  private RegisterVehicleCommand command;

  /**
   * Setup for tests.
   */
  @BeforeEach
  void setup() {
    simulator = mock(VisualTransitSimulator.class);
    session = mock(WebServerSession.class);
    command = new RegisterVehicleCommand(simulator);
  }

  /**
   * Tests execute with added observer.
   */
  @Test
  void testExecuteVehicleFoundAddsObserver() {
    Vehicle v1 = mock(Vehicle.class);
    Vehicle v2 = mock(Vehicle.class);

    when(v1.getId()).thenReturn(100);
    when(v2.getId()).thenReturn(200);

    when(simulator.getActiveVehicles()).thenReturn(List.of(v1, v2));

    JsonObject json = new JsonObject();
    json.addProperty("id", 200);

    command.execute(session, json);

    verify(simulator).addObserver(v2);
  }

  /**
   * Tests execute with NoVehicleFound.
   */
  @Test
  void testExecuteNoVehicleFoundAddsNullObserver() {
    Vehicle v1 = mock(Vehicle.class);
    when(v1.getId()).thenReturn(10);

    when(simulator.getActiveVehicles()).thenReturn(List.of(v1));

    JsonObject json = new JsonObject();
    json.addProperty("id", 999);

    command.execute(session, json);

    verify(simulator).addObserver(null);
  }
}
