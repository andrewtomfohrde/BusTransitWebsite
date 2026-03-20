package edu.umn.cs.csci3081w.project.model;

/**
 * Nighttime bus generation strategy.
 *
 * <p>Generates small buses more frequently during nighttime hours, with an
 * internal counter used to alternate vehicle selection based on availability
 * in the {@link StorageFacility}.
 */
public class BusStrategyNight implements GenerationStrategy {
  private int counter;

  /**
   * Constructs a nighttime bus generation strategy.
   */
  public BusStrategyNight() {
    this.counter = 0;
  }

  /**
   * Selects a type of vehicle to generate based on the current counter
   * and the availability of vehicles in the storage facility.
   *
   * @param storageFacility the storage facility providing available buses
   * @return the type of vehicle to generate, or {@code null} if none are available
   */
  @Override
  public String getTypeOfVehicle(StorageFacility storageFacility) {
    String typeOfVehicle = null;
    if (counter < 3) {
      if (storageFacility.getSmallBusesNum() > 0) {
        typeOfVehicle = SmallBus.SMALL_BUS_VEHICLE;
      }
    } else {
      if (storageFacility.getLargeBusesNum() > 0) {
        typeOfVehicle = LargeBus.LARGE_BUS_VEHICLE;
      }
    }

    if (typeOfVehicle != null) {
      counter++;
      counter = counter % 4;
    }
    return typeOfVehicle;
  }

  /**
   * Gets the internal counter used by the strategy.
   *
   * @return the counter value
   */
  public int getCounter() {
    return counter;
  }
}
