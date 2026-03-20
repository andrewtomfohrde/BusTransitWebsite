package edu.umn.cs.csci3081w.project.model;

/**
 * Generation strategy used for producing trains during nighttime hours.
 *
 * <p>This strategy alternates between generating electric and diesel trains
 * based on an internal counter and availability in the storage facility.
 */
public class TrainStrategyNight implements GenerationStrategy {
  private int counter;

  /**
   * Constructs a nighttime train generation strategy.
   */
  public TrainStrategyNight() {
    this.counter = 0;
  }

  /**
   * Determines the type of train vehicle to generate based on availability.
   *
   * @param storageFacility the storage facility containing available trains
   * @return the type of vehicle to generate, or {@code null} if none available
   */
  @Override
  public String getTypeOfVehicle(StorageFacility storageFacility) {
    String typeOfVehicle = null;
    if (counter == 0) {
      if (storageFacility.getElectricTrainsNum() > 0) {
        typeOfVehicle = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      }
    } else {
      if (storageFacility.getDieselTrainsNum() > 0) {
        typeOfVehicle = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }
    }

    if (typeOfVehicle != null) {
      counter++;
      counter = counter % 2;
    }

    return typeOfVehicle;
  }

  /**
   * Gets the current internal counter value.
   *
   * @return counter value
   */
  public int getCounter() {
    return counter;
  }
}
