package edu.umn.cs.csci3081w.project.model;

/**
 * Factory class responsible for generating {@link Bus} vehicles.
 *
 * <p>The factory selects a {@link GenerationStrategy} based on the
 * simulation time and uses a {@link StorageFacility} to track
 * available buses.
 */
public class BusFactory implements VehicleFactory {
  private GenerationStrategy generationStrategy;
  private Counter counter;
  private StorageFacility storageFacility;

  /**
   * Constructor for bus factory that selects a strategy based on time.
   *
   * @param storageFacility storage facility information
   * @param counter counter information
   * @param time time used to select strategy
   */
  public BusFactory(StorageFacility storageFacility, Counter counter, int time) {
    if (time >= 8 && time < 18) {
      generationStrategy = new BusStrategyDay();
    } else {
      generationStrategy = new BusStrategyNight();
    }
    this.storageFacility = storageFacility;
    this.counter = counter;
  }

  /**
   * Generates a bus for the given line using the current generation strategy.
   *
   * @param line the transit line to assign the vehicle to
   * @return a newly generated vehicle, or {@code null} if none are available
   */
  @Override
  public Vehicle generateVehicle(Line line) {
    String typeOfVehicle = generationStrategy.getTypeOfVehicle(storageFacility);
    Vehicle generatedVehicle = null;
    if (typeOfVehicle != null && typeOfVehicle.equals(SmallBus.SMALL_BUS_VEHICLE)) {
      generatedVehicle = new SmallBus(counter.getSmallBusIdCounterAndIncrement(),
          line, SmallBus.CAPACITY, SmallBus.SPEED);
      storageFacility.decrementSmallBusesNum();
    } else if (typeOfVehicle != null && typeOfVehicle.equals(LargeBus.LARGE_BUS_VEHICLE)) {
      generatedVehicle = new LargeBus(counter.getLargeBusIdCounterAndIncrement(), line,
          LargeBus.CAPACITY, LargeBus.SPEED);
      storageFacility.decrementLargeBusesNum();
    }
    return generatedVehicle;
  }

  /**
   * Returns a vehicle to the storage facility.
   *
   * @param vehicle the vehicle being returned
   */
  @Override
  public void returnVehicle(Vehicle vehicle) {
    if (vehicle instanceof SmallBus) {
      storageFacility.incrementSmallBusesNum();
    } else if (vehicle instanceof LargeBus) {
      storageFacility.incrementLargeBusesNum();
    }
  }

  /**
   * Gets the storage facility.
   *
   * @return storage facility
   */
  public StorageFacility getStorageFacility() {
    return storageFacility;
  }

  /**
   * Gets the generation strategy.
   *
   * @return generation strategy
   */
  public GenerationStrategy getGenerationStrategy() {
    return generationStrategy;
  }
}
