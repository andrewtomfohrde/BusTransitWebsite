package edu.umn.cs.csci3081w.project.webserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for StartCommand class.
 */
class StartCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession session;
  private StartCommand command;

  /**
   * Sets up VTS and websever session.
   */
  @BeforeEach
  void setup() {
    simulator = mock(VisualTransitSimulator.class);
    session = mock(WebServerSession.class);

    command = Mockito.spy(new StartCommand(simulator));
  }

  /**
   * Tests execute start simulation.
   */
  @Test
  void testExecuteStartsSimulationWithCorrectValues() {
    // ---- Arrange ----
    JsonObject json = new JsonObject();
    json.addProperty("numTimeSteps", 15);

    JsonArray arr = new JsonArray();
    arr.add(5);
    arr.add(10);
    json.add("timeBetweenVehicles", arr);

    when(command.getCurrentSimulationTime()).thenReturn(9);

    command.execute(session, json);

    verify(simulator).setVehicleFactories(9);

    verify(simulator).start(List.of(5, 10), 15);
    
    verifyNoMoreInteractions(session);
  }
}
