package edu.umn.cs.csci3081w.project.model;

/**
 * Daytime bus generation strategy.
 *
 * <p>Generates large buses more frequently in daytime hours, using an
 * internal counter to cycle between vehicle types depending on availability
 * in the {@link StorageFacility}.
 */
public class BusStrategyDay implements GenerationStrategy {
  private int counter;

  /**
   * Constructs a daytime bus generation strategy.
   */
  public BusStrategyDay() {
    this.counter = 0;
  }

  /**
   * Selects a type of vehicle to generate based on the current counter
   * and the availability of vehicles in the storage facility.
   *
   * @param storageFacility the storage facility providing available buses
   * @return the type of vehicle to generate, or {@code null} if none available
   */
  @Override
  public String getTypeOfVehicle(StorageFacility storageFacility) {
    String typeOfVehicle = null;
    if (counter < 2) {
      if (storageFacility.getLargeBusesNum() > 0) {
        typeOfVehicle = LargeBus.LARGE_BUS_VEHICLE;
      }
    } else {
      if (storageFacility.getSmallBusesNum() > 0) {
        typeOfVehicle = SmallBus.SMALL_BUS_VEHICLE;
      }
    }

    if (typeOfVehicle != null) {
      counter++;
      counter = counter % 3;
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
