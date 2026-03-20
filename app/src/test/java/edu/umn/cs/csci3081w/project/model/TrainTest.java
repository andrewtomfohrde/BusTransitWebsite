package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainTest {

  private Train testTrain;
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

    testTrain = new TrainTestImpl(1, new Line(10000, "testLine",
        Line.TRAIN_LINE, testRouteOut, testRouteIn, new Issue()), 3, 1.0);
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testTrain.getId());
    assertEquals("testRouteOut1", testTrain.getName());
    assertEquals(3, testTrain.getCapacity());
    assertEquals(1, testTrain.getSpeed());
    assertEquals(testRouteOut, testTrain.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testTrain.getLine().getInboundRoute());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertFalse(testTrain.isTripComplete());
    testTrain.move();
    testTrain.move();
    testTrain.move();
    testTrain.move();
    assertTrue(testTrain.isTripComplete());

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

    assertEquals(1, testTrain.loadPassenger(testPassenger1));
    assertEquals(1, testTrain.loadPassenger(testPassenger2));
    assertEquals(1, testTrain.loadPassenger(testPassenger3));
    assertEquals(0, testTrain.loadPassenger(testPassenger4));
  }


  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());
    testTrain.move();

    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.move();
    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.move();
    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());

    testTrain.move();
    assertNull(testTrain.getNextStop());

  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());
    testTrain.update();

    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.update();
    assertEquals("test stop 1", testTrain.getNextStop().getName());
    assertEquals(0, testTrain.getNextStop().getId());

    testTrain.update();
    assertEquals("test stop 2", testTrain.getNextStop().getName());
    assertEquals(1, testTrain.getNextStop().getId());

    testTrain.update();
    assertNull(testTrain.getNextStop());

  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testTrain = null;
  }

}
