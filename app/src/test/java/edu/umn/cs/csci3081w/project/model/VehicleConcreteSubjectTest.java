package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.VehicleConcreteSubject;
import edu.umn.cs.csci3081w.project.webserver.FakeWebServerSession;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for VehicleConcreteSubject.
 */
public class VehicleConcreteSubjectTest {

  private Vehicle testVehicle;
  private Route testRouteIn;
  private Route testRouteOut;

  /**
   * Sets up the test environment before each test case.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    List<Stop> stopsIn = new ArrayList<>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);

    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);

    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);

    PassengerGenerator generatorIn =
        new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn =
        new Route(0, "testRouteIn", stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);

    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);

    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);

    PassengerGenerator generatorOut =
        new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut =
        new Route(1, "testRouteOut", stopsOut, distancesOut, generatorOut);

    testVehicle = new VehicleTestImpl(
        1,
        new Line(10000, "testLine", "VEHICLE_LINE",
            testRouteOut, testRouteIn, new Issue()),
        3,
        1.0,
        new PassengerLoader(),
        new PassengerUnloader()
    );
  }

  @Test
  public void testConstructor() {
    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    assertEquals(0, vcs.getObservers().size());
  }

  @Test
  public void testAttachObserver() {
    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(testVehicle);
    assertEquals(1, vcs.getObservers().size());
  }

  @Test
  public void testDetachObserver() {
    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);

    vcs.attachObserver(testVehicle);
    vcs.detachObserver(testVehicle);

    assertEquals(0, vcs.getObservers().size());
  }

  @Test
  public void testNotifyObservers() {
    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);

    vcs.attachObserver(testVehicle);

    // must update before providing info
    testVehicle.update();

    vcs.notifyObservers();

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);

    assertEquals("observedVehicle", sent.get("command").getAsString());
  }
}
