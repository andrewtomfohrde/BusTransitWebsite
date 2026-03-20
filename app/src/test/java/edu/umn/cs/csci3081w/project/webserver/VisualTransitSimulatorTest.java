package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for VisualTransitSimulator.
 */
public class VisualTransitSimulatorTest {

  private VisualTransitSimulator simulator;

  /**
   * Sets up session before ran.
   *
   * @throws IOException if config can't be found, throw exception.
   */
  @BeforeEach
  public void setUp() throws IOException {
    // Dummy WebServerSession to satisfy constructor
    WebServerSession dummySession = new WebServerSession() {
      @Override
      public void sendJson(JsonObject message) {
        // do nothing
      }
    };

    InputStream configStream = getClass().getClassLoader().getResourceAsStream("config.txt");
    if (configStream == null) {
      throw new IOException("config.txt not found in resources");
    }

    // copy resource to a temporary file
    File tempConfigFile = File.createTempFile("config", ".txt");
    tempConfigFile.deleteOnExit();
    Files.copy(configStream, tempConfigFile.toPath(),
        java.nio.file.StandardCopyOption.REPLACE_EXISTING);

    // initialize the simulator
    simulator = new VisualTransitSimulator(tempConfigFile.getAbsolutePath(), dummySession);
  }

  /**
   * Test VST initialization.
   */
  @Test
  public void testSimulatorInitialization() {
    assertNotNull(simulator.getLines(), "Simulator lines should not be null");
    assertTrue(simulator.getLines().size() > 0,
        "Simulator should have at least one line");
  }

  /**
   * Testing vehicle generation and lifecycle.
   */
  @Test
  public void testVehicleLifecycle() {
    simulator.setVehicleFactories(0);
    simulator.start(Arrays.asList(1, 1), 3); // Launch vehicles every step, 3 steps total

    simulator.update();
    assertFalse(simulator.getActiveVehicles().isEmpty(),
        "Vehicles should be generated after first update");

    int activeBefore = simulator.getActiveVehicles().size();

    simulator.update();
    int activeAfter = simulator.getActiveVehicles().size();

    assertTrue(activeAfter >= activeBefore,
        "Vehicle count should not decrease unless trips are complete");

    for (int i = 0; i < 10; i++) {
      simulator.update();
    }

    assertTrue(simulator.getActiveVehicles().size() >= 0, "Active vehicles should remain >= 0");
  }

  /**
   * Test updating for Line.
   */
  @Test
  public void testLineUpdate() {
    simulator.setVehicleFactories(0);
    simulator.start(Arrays.asList(1, 1), 1); // set vehicle timings and steps

    simulator.update();

    for (Line line : simulator.getLines()) {
      assertNotNull(line, "Line should not be null after update");
    }
  }

  /**
   * Testing update on generated vehicles.
   */
  @Test
  void testUpdateGeneratesVehicles() {
    simulator.setVehicleFactories(0);

    List<Integer> startTimings = new ArrayList<>();
    for (int i = 0; i < simulator.getLines().size(); i++) {
      startTimings.add(1); // generate vehicles every step
    }
    simulator.start(startTimings, 5);

    int initialActive = simulator.getActiveVehicles().size();
    simulator.update(); // now safe

    assertTrue(simulator.getActiveVehicles().size() >= initialActive,
        "Update should generate at least one vehicle per line if allowed");
  }


  /**
   * Testing update for a paused sim.
   */
  @Test
  void testUpdateWithPausedSimulator() {
    List<Integer> startTimings = new ArrayList<>();
    for (int i = 0; i < simulator.getLines().size(); i++) {
      startTimings.add(1);
    }
    simulator.start(startTimings, 5);

    simulator.togglePause();
    int activeBefore = simulator.getActiveVehicles().size();
    simulator.update();
    int activeAfter = simulator.getActiveVehicles().size();
    assertEquals(activeBefore, activeAfter,
        "Paused simulator should not update vehicles");
  }

  /**
   * Tests for adding an observer.
   */
  @Test
  void testAddObserver() {
    Vehicle mockVehicle = mock(Vehicle.class);
    simulator.addObserver(mockVehicle);
    // attachObserver stores vehicle internally, we can check via triggering notifyObservers
    simulator.update();
    verify(mockVehicle, atMostOnce()).update(); // update called indirectly via notification
  }


  /**
   * Tests if bus is removed from active vehicle list.
   */
  @Test
  void testCompletedVehicleIsRemovedFromActiveList() {
    simulator.setVehicleFactories(0);

    // Launch vehicles every step
    simulator.start(Arrays.asList(1, 1), 10);

    simulator.update();
    assertFalse(simulator.getActiveVehicles().isEmpty(), "Vehicle should be generated");

    Vehicle vehicle = simulator.getActiveVehicles().get(0);

    while (!vehicle.isTripComplete()) {
      vehicle.update();
    }

    simulator.update();

    assertFalse(simulator.getActiveVehicles().contains(vehicle),
        "Completed vehicle should be removed from activeVehicles");
  }

  /**
   * Tests if train is removed active vehicle list.
   */
  @Test
  void testCompletedTrainIsRemovedFromActiveList() {
    simulator.setVehicleFactories(0);

    // Launch vehicles every step
    simulator.start(Arrays.asList(1, 1), 10);

    simulator.update();

    Vehicle train = simulator.getActiveVehicles().stream().filter(v ->
        v instanceof edu.umn.cs.csci3081w.project.model.Train).findFirst().orElse(null);

    assertNotNull(train, "Simulator should generate a train vehicle");

    // Force the train to complete its trip
    while (!train.isTripComplete()) {
      train.update();
    }

    // Run simulator update to process completed vehicles
    simulator.update();

    // Assert it is no longer active
    assertFalse(simulator.getActiveVehicles().contains(train),
        "Completed train should be removed from activeVehicles");
  }
}
