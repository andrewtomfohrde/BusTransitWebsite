package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SmallBusTest {

  private Bus testBus;
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
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774,
        44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071,
        44.973580));
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

    testBus = new SmallBus(1, new Line(10000, "testLine", "BUS", testRouteOut,
        testRouteIn, new Issue()), 3, 1.0);
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testBus.getId());
    assertEquals("testRouteOut1", testBus.getName());
    assertEquals(3, testBus.getCapacity());
    assertEquals(1, testBus.getSpeed());
    assertEquals(testRouteOut, testBus.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testBus.getLine().getInboundRoute());
  }

  /**
   * Tests if updateDistance function works properly.
   */
  @Test
  public void testReport() {
    testBus.move();
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset);
      testBus.report(testStream);
      outputStream.flush();
      String data = outputStream.toString(charset);
      testStream.close();
      outputStream.close();
      String strToCompare = "####Small Bus Info Start####" + System.lineSeparator()
          + "ID: 1" + System.lineSeparator()
          + "Name: testRouteOut1" + System.lineSeparator()
          + "Speed: 1.0" + System.lineSeparator()
          + "Capacity: 3" + System.lineSeparator()
          + "Position: 44.97358,-93.235071" + System.lineSeparator()
          + "Distance to next stop: 0.843774422231134" + System.lineSeparator()
          + "****Passengers Info Start****" + System.lineSeparator()
          + "Num of passengers: 0" + System.lineSeparator()
          + "****Passengers Info End****" + System.lineSeparator()
          + "####Small Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test the co2 calculation for a bus.
   */
  @Test
  public void testCurrentCO2Emission() {
    assertEquals(3, testBus.getCurrentCO2Emission());
    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    testBus.loadPassenger(testPassenger1);
    assertEquals(5, testBus.getCurrentCO2Emission());
  }

  /**
   * Test for color of small bus.
   */
  @Test
  public void testColor() {
    RgbaColor c = testBus.getColor();
    assertEquals(122, c.getRed());
    assertEquals(0, c.getGreen());
    assertEquals(25, c.getBlue());
    assertEquals(255, c.getAlpha());
  }


  /**
   * Tests CO2 Emission with passengers.
   */
  @Test
  public void testGetCurrentCO2EmissionWithPassengers() {
    // Add some deterministic passengers for testing
    testBus.loadPassenger(new Passenger(1, "Goldy"));
    testBus.loadPassenger(new Passenger(2, "President"));
    assertEquals(7, testBus.getCurrentCO2Emission(),
        "CO2 emission should be 2*2 + 5 for 2 passengers");
  }

  /**
   * Tests reports info with Passengers.
   */
  @Test
  public void testReportContainsExpectedInfoWithPassengers() {
    // Load passengers
    Passenger passenger1 = new Passenger(1, "Goldy");
    Passenger passenger2 = new Passenger(2, "President");
    testBus.loadPassenger(passenger1);
    testBus.loadPassenger(passenger2);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(output);

    testBus.report(ps);

    String report = output.toString();

    // Check bus-level info
    assertTrue(report.contains("####Small Bus Info Start####"));
    assertTrue(report.contains("ID: 1"));
    assertTrue(report.contains("Name:"));
    assertTrue(report.contains("Speed:"));
    assertTrue(report.contains("Capacity:"));
    assertTrue(report.contains("Position:"));
    assertTrue(report.contains("Distance to next stop:"));
    assertTrue(report.contains("****Passengers Info Start****"));
    assertTrue(report.contains("Num of passengers: 2"));

    // Check passenger reports are included
    assertTrue(report.contains("Name: Goldy"), "Report should include passenger1 info");
    assertTrue(report.contains("Name: President"), "Report should include passenger2 info");

    assertTrue(report.contains("****Passengers Info End****"));
    assertTrue(report.contains("####Small Bus Info End####"));
  }


  /**
   * Tests getColor with no issue.
   */
  @Test
  public void testGetColorWithNoIssue() {
    assertEquals(testBus.getColor().getAlpha(), 255);
  }

  /**
   * Tests alpha value based on line issue.
   */
  @Test
  public void testGetColorAlphaWithAndWithoutIssue() {
    Issue issue = new Issue();
    issue.createIssue(); // sets counter = 10, line has an issue
    Line lineWithIssue = new Line(100, "LineWithIssue", "BUS",
        testRouteOut, testRouteIn, issue);
    SmallBus busWithIssue = new SmallBus(1, lineWithIssue, LargeBus.CAPACITY, LargeBus.SPEED);
    assertEquals(155, busWithIssue.getColor().getAlpha(),
        "Alpha should be 155 when line has an issue");

    Issue noIssue = new Issue(); // counter = 0, line has no issue
    Line lineNoIssue = new Line(101, "LineNoIssue", "BUS",
        testRouteOut, testRouteIn, noIssue);
    SmallBus busNoIssue = new SmallBus(2, lineNoIssue, LargeBus.CAPACITY, LargeBus.SPEED);
    assertEquals(255, busNoIssue.getColor().getAlpha(),
        "Alpha should be 255 when line has no issue");
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testBus = null;
  }

}
