package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainFactoryTest {
  private StorageFacility storageFacility;
  private TrainFactory trainFactory;

  /**
   * Setup operations.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
    storageFacility = new StorageFacility(0, 0, 3, 3);
    trainFactory = new TrainFactory(storageFacility, new Counter(), 9);
  }

  /**
   * Testing the constructor.
   */
  @Test
  public void testConstructor() {
    assertTrue(trainFactory.getGenerationStrategy() instanceof TrainStrategyDay);
  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicle() {
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

    Route testRouteIn = new Route(0, "testRouteIn", stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut", stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn, new Issue());

    Vehicle vehicle1 = trainFactory.generateVehicle(line);
    assertTrue(vehicle1 instanceof ElectricTrain);
  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleElectricTrain() {
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

    Route testRouteIn = new Route(0, "testRouteIn", stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut", stopsOut, distancesOut, generatorOut);

    Train testTrain = new ElectricTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0);

    assertEquals(3, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactory.getStorageFacility().getDieselTrainsNum());
    trainFactory.returnVehicle(testTrain);
    assertEquals(4, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(3, trainFactory.getStorageFacility().getDieselTrainsNum());

  }

  /**
   * Test generating a diesel train.
   */
  @Test
  public void testGenerateDieselTrain() {
    // Force DieselTrain generation by mocking strategy
    TrainFactory factory = new TrainFactory(storageFacility, new Counter(), 20);
    factory = new TrainFactory(storageFacility, new Counter(), 20) {
      @Override
      public Vehicle generateVehicle(Line line) {
        return new DieselTrain(0, line, DieselTrain.CAPACITY, DieselTrain.SPEED);
      }
    };

    // Create real route & line objects
    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(new Stop(0, "stop1", new Position(-93.243774, 44.972392)));
    stopsIn.add(new Stop(1, "stop2", new Position(-93.235071, 44.973580)));
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, List.of(0.025, 0.3));
    Route testRouteIn = new Route(0, "testRouteIn", stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(new Stop(1, "stop2", new Position(-93.235071, 44.973580)));
    stopsOut.add(new Stop(0, "stop1", new Position(-93.243774, 44.972392)));
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, List.of(0.3, 0.025));
    Route testRouteOut = new Route(1, "testRouteOut", stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn, new Issue());

    Vehicle vehicle = factory.generateVehicle(line);
    assertTrue(vehicle instanceof DieselTrain);
  }


  /**
   * Test generating a vehicle returns null if strategy returns null.
   */
  @Test
  public void testGenerateVehicleReturnsNull() {
    StorageFacility emptyStorage = new StorageFacility(0, 0, 0, 0);
    TrainFactory factory = new TrainFactory(emptyStorage, new Counter(), 9);

    List<Stop> stops = new ArrayList<>();
    stops.add(new Stop(0, "s1", new Position(0, 0)));
    stops.add(new Stop(1, "s2", new Position(1, 1)));
    List<Double> distances = new ArrayList<>();
    distances.add(1.0);
    List<Double> probabilities = new ArrayList<>();
    probabilities.add(0.5);
    probabilities.add(0.5);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    Route inRoute = new Route(0, "in", stops, distances, generator);
    Route outRoute = new Route(1, "out", stops, distances, generator);
    Line line = new Line(0, "line", "TRAIN", outRoute, inRoute, new Issue());

    Vehicle vehicle = factory.generateVehicle(line);
    assertNull(vehicle);
  }


  /**
   * Test returning a diesel train increments storage.
   */
  @Test
  public void testReturnVehicleDieselTrain() {
    // Use a real line to avoid null outbound route
    List<Stop> stops = new ArrayList<>();
    stops.add(new Stop(0, "stop1", new Position(-93.243774, 44.972392)));
    stops.add(new Stop(1, "stop2", new Position(-93.235071, 44.973580)));
    List<Double> distances = new ArrayList<>();
    distances.add(0.843774422231134);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, List.of(0.5, 0.5));
    Route route = new Route(0, "route", stops, distances, generator);
    Line line = new Line(10000, "line", "TRAIN", route, route, new Issue());

    Train testTrain = new DieselTrain(1, line, 3, 1.0);
    int before = trainFactory.getStorageFacility().getDieselTrainsNum();
    trainFactory.returnVehicle(testTrain);
    assertEquals(before + 1, trainFactory.getStorageFacility().getDieselTrainsNum());
  }


  /**
   * Test returning a vehicle that is neither electric nor diesel does nothing.
   */
  @Test
  public void testReturnVehicleOtherType() {
    Vehicle v = mock(Vehicle.class);
    int electricBefore = trainFactory.getStorageFacility().getElectricTrainsNum();
    int dieselBefore = trainFactory.getStorageFacility().getDieselTrainsNum();
    trainFactory.returnVehicle(v);
    assertEquals(electricBefore, trainFactory.getStorageFacility().getElectricTrainsNum());
    assertEquals(dieselBefore, trainFactory.getStorageFacility().getDieselTrainsNum());
  }

  /**
   * Tests a diesel train generated during the day.
   */
  @Test
  public void testGenerateDieselTrainDay() {
    StorageFacility storage = new StorageFacility(0, 0, 3, 3); // 3 electric, 3 diesel
    Counter counter = new Counter();
    TrainFactory factory = new TrainFactory(storage, counter, 9); // day time -> TrainStrategyDay

    List<Stop> stops = new ArrayList<>();
    Stop stop1 = new Stop(0, "stop1", new Position(0, 0));
    Stop stop2 = new Stop(1, "stop2", new Position(1, 1));
    stops.add(stop1);
    stops.add(stop2);

    List<Double> distances = List.of(1.0);
    List<Double> probabilities = List.of(0.5, 0.5);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);

    Route routeOut = new Route(0, "out", stops, distances, generator);
    Route routeIn = new Route(1, "in", stops, distances, generator);
    Line line = new Line(100, "line", "TRAIN", routeOut, routeIn, new Issue());

    // Generate 3 electric trains first
    for (int i = 0; i < 3; i++) {
      Vehicle v = factory.generateVehicle(line);
      assertTrue(v instanceof ElectricTrain);
    }

    // Now the 4th vehicle should be diesel
    Vehicle v4 = factory.generateVehicle(line);
    assertTrue(v4 instanceof DieselTrain);
  }


  /**
   * Tests an electric train generated during the day.
   */
  @Test
  public void testGenerateElectricTrainDay() {
    StorageFacility storage = new StorageFacility(0, 0, 3, 0); // 3 electric, 0 diesel
    TrainFactory factory = new TrainFactory(storage, new Counter(), 9);

    List<Stop> stops = new ArrayList<>();
    stops.add(new Stop(0, "s1", new Position(0, 0)));
    stops.add(new Stop(1, "s2", new Position(1, 1)));
    List<Double> distances = new ArrayList<>();
    distances.add(1.0);
    List<Double> probabilities = new ArrayList<>();
    probabilities.add(0.5);
    probabilities.add(0.5);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    Route inRoute = new Route(0, "in", stops, distances, generator);
    Route outRoute = new Route(1, "out", stops, distances, generator);
    Line line = new Line(0, "line", "TRAIN", outRoute, inRoute, new Issue());

    Vehicle electricTrain = factory.generateVehicle(line); // counter < 3 -> electric
    assertTrue(electricTrain instanceof ElectricTrain);
  }

  @Test
  public void testGenerateElectricTrainNight() {
    StorageFacility storage = new StorageFacility(0, 0, 2, 2); // 2 electric, 2 diesel
    TrainFactory factory = new TrainFactory(storage, new Counter(), 9);

    List<Stop> stops = new ArrayList<>();
    stops.add(new Stop(0, "s1", new Position(0, 0)));
    stops.add(new Stop(1, "s2", new Position(1, 1)));
    List<Double> distances = new ArrayList<>();
    distances.add(1.0);
    List<Double> probabilities = new ArrayList<>();
    probabilities.add(0.5);
    probabilities.add(0.5);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    Route inRoute = new Route(0, "in", stops, distances, generator);
    Route outRoute = new Route(1, "out", stops, distances, generator);
    Line line = new Line(0, "line", "TRAIN", outRoute, inRoute, new Issue());

    Vehicle electricTrain = factory.generateVehicle(line); // counter==0 -> electric
    assertTrue(electricTrain instanceof ElectricTrain);
  }

  @Test
  public void testTrainStrategyDay() {
    StorageFacility storage = new StorageFacility(2, 2, 3, 3); // electric=3, diesel=3
    TrainStrategyDay dayStrategy = new TrainStrategyDay();

    // First 3 calls should return ElectricTrain if available
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, dayStrategy.getTypeOfVehicle(storage));
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, dayStrategy.getTypeOfVehicle(storage));
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, dayStrategy.getTypeOfVehicle(storage));

    // 4th call should return DieselTrain if available
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, dayStrategy.getTypeOfVehicle(storage));

    // 5th call cycles back to Electric
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, dayStrategy.getTypeOfVehicle(storage));

    // Test no vehicles available
    StorageFacility emptyStorage = new StorageFacility(2, 2, 0, 0);
    assertNull(dayStrategy.getTypeOfVehicle(emptyStorage));
  }

  @Test
  public void testTrainStrategyNight() {
    StorageFacility storage = new StorageFacility(2, 2, 3, 3); // electric=3, diesel=3
    TrainStrategyNight nightStrategy = new TrainStrategyNight();

    // First call should be Electric
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, nightStrategy.getTypeOfVehicle(storage));

    // Second call should be Diesel
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, nightStrategy.getTypeOfVehicle(storage));

    // Third call cycles back to Electric
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, nightStrategy.getTypeOfVehicle(storage));

    // Test no vehicles available
    StorageFacility emptyStorage = new StorageFacility(2, 2, 0, 0);
    assertNull(nightStrategy.getTypeOfVehicle(emptyStorage));
  }

}
