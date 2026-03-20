package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.FakeWebServerSession;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VehicleTest {

  private Vehicle testVehicle;
  private Route testRouteIn;
  private Route testRouteOut;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn", stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut", stopsOut, distancesOut, generatorOut);

    testVehicle = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn, new Issue()), 3, 1.0,
        new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testVehicle.getId());
    assertEquals("testRouteOut1", testVehicle.getName());
    assertEquals(3, testVehicle.getCapacity());
    assertEquals(1, testVehicle.getSpeed());
    assertEquals(testRouteOut, testVehicle.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testVehicle.getLine().getInboundRoute());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertFalse(testVehicle.isTripComplete());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertTrue(testVehicle.isTripComplete());
  }

  /**
   * Tests if loadPassenger function works properly.
   */
  @Test
  public void testLoadPassenger() {

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(1, "testPassenger3");
    Passenger testPassenger4 = new Passenger(1, "testPassenger4");

    assertEquals(1, testVehicle.loadPassenger(testPassenger1));
    assertEquals(1, testVehicle.loadPassenger(testPassenger2));
    assertEquals(1, testVehicle.loadPassenger(testPassenger3));
    assertEquals(0, testVehicle.loadPassenger(testPassenger4));
  }

  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertNull(testVehicle.getNextStop());
  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.update();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertNull(testVehicle.getNextStop());
  }

  /**
   * Test to see if observer got attached and JSON was sent.
   */
  @Test
  public void testProvideInfo() {

    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(testVehicle);

    testVehicle.update();
    testVehicle.provideInfo();

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);

    String command = sent.get("command").getAsString();
    assertEquals("observedVehicle", command);
  }

  /**
   * Testing vehicle update with a line issue.
   */
  @Test
  public void testVehicleUpdateLineIssueExist() {
    Line brokenLine = new Line(100, "broken", Line.BUS_LINE, testRouteOut,
        testRouteIn, new Issue() {
          public boolean isIssueExist() {
            return true;
          }

          @Override
          public boolean isIssueResolved() {
            return false;
          }
        });

    Vehicle vehicle = new VehicleTestImpl(10, brokenLine, 3, 1.0,
        new PassengerLoader(), new PassengerUnloader());

    vehicle.update(); // move() should not be called
    assertEquals("test stop 2", vehicle.getNextStop().getName());
  }

  /**
   * Tests ProvideInfo to make sure carbon use is correct.
   */
  @Test
  public void testProvideInfoTripCompleteWithCarbon() {
    // Move vehicle until trip complete
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();

    // Add some carbon emission history
    testVehicle.update(); // adds 0 to history
    testVehicle.update(); // adds another 0

    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(testVehicle);

    boolean complete = testVehicle.provideInfo();
    assertTrue(complete);

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);
    assertEquals("observedVehicle", sent.get("command").getAsString());
    assertEquals("", sent.get("text").getAsString());
  }

  /**
   * Tests an incomplete trip.
   */
  @Test
  public void testPartialTrip() {
    assertFalse(testVehicle.isTripComplete());
    testVehicle.move();
    assertFalse(testVehicle.isTripComplete());
  }

  /**
   * Tests CO2 Emission.
   */
  @Test
  public void testCarbonUsageHistory() {
    assertEquals(0, testVehicle.getCurrentCO2Emission());
    testVehicle.update();
    assertEquals(0, testVehicle.getCurrentCO2Emission());
    testVehicle.update();
    assertEquals(0, testVehicle.getCurrentCO2Emission());
  }

  /**
   * Test for unloadPassengers.
   */
  @Test
  public void testUnloadPassengers() {
    // Load passengers
    Passenger p1 = new Passenger(1, "p1");
    Passenger p2 = new Passenger(0, "p2");
    testVehicle.loadPassenger(p1);
    testVehicle.loadPassenger(p2);

    // Move vehicle to next stop to allow unloading
    testVehicle.move();
    testVehicle.update();  // triggers unloading

    assertTrue(testVehicle.getPassengers().isEmpty());
  }

  /**
   * Tests ProvideInfo with a small bus.
   */
  @Test
  public void testProvideInfoSmallBus() {
    Line line = new Line(1, "SmallBusLine", Line.BUS_LINE, testRouteOut, testRouteIn, new Issue());
    Vehicle smallBus = new SmallBus(101, line, 3, 1.0);

    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(smallBus);

    smallBus.update();
    smallBus.provideInfo();

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);
    assertEquals("observedVehicle", sent.get("command").getAsString());
  }

  /**
   * Tests ProvideInfo for a large bus.
   */
  @Test
  public void testProvideInfoLargeBus() {
    Line line = new Line(2, "LargeBusLine", Line.BUS_LINE, testRouteOut, testRouteIn, new Issue());
    Vehicle largeBus = new LargeBus(102, line, 5, 1.0);

    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(largeBus);

    largeBus.update();
    largeBus.provideInfo();

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);
    assertEquals("observedVehicle", sent.get("command").getAsString());
  }

  /**
   * Tests ProvideInfo with an electric train.
   */
  @Test
  public void testProvideInfoElectricTrain() {
    Line line = new Line(3, "ElectricTrainLine", Line.TRAIN_LINE, testRouteOut,
        testRouteIn, new Issue());
    Vehicle electricTrain = new ElectricTrain(201, line, 50, 2.0);

    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(electricTrain);

    electricTrain.update();
    electricTrain.provideInfo();

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);
    assertEquals("observedVehicle", sent.get("command").getAsString());
  }

  /**
   * Tests ProvideInfo with a diesel train.
   */
  @Test
  public void testProvideInfoDieselTrain() {
    Line line = new Line(4, "DieselTrainLine", Line.TRAIN_LINE, testRouteOut,
        testRouteIn, new Issue());
    Vehicle dieselTrain = new DieselTrain(202, line, 50, 2.0);

    FakeWebServerSession fake = new FakeWebServerSession();
    VehicleConcreteSubject vcs = new VehicleConcreteSubject(fake);
    vcs.attachObserver(dieselTrain);

    dieselTrain.update();
    dieselTrain.provideInfo();

    JsonObject sent = fake.getLastSent();
    assertNotNull(sent);
    assertEquals("observedVehicle", sent.get("command").getAsString());
  }

  /**
   * Tests for a vehicle with a speed of -1.
   */
  @Test
  public void testVehicleNegativeSpeed() {
    testVehicle = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn, new Issue()), 3, -1,
        new PassengerLoader(), new PassengerUnloader());
    assertEquals(-1, testVehicle.getSpeed());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertEquals(44.97358, testVehicle.getPosition().getLatitude());
    assertEquals(-93.235071, testVehicle.getPosition().getLongitude());
  }

  /**
   * Tests a vehicle ratio less than zero.
   */
  @Test
  public void testVehicleRatioLessThanZero() {

    // Vehicle speed high enough to exceed distanceRemaining
    VehicleTestImpl testVehicle = new VehicleTestImpl(1, new Line(10000,
        "testLine", "VEHICLE_LINE", testRouteOut, testRouteIn, new Issue()),
        3, 1000, // Very high speed to trigger distanceRemaining < 0
        new PassengerLoader(), new PassengerUnloader());

    // Move the vehicle; it should clamp at stop2
    testVehicle.move();

    // Position should be exactly at stop2 after move
    assertEquals(44.973580, testVehicle.getPosition().getLatitude(), 1e-6);
    assertEquals(-93.235071, testVehicle.getPosition().getLongitude(), 1e-6);
  }

  /**
   * `
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testVehicle = null;
  }


}
