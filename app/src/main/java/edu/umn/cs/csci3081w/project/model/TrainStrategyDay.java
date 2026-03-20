package edu.umn.cs.csci3081w.project.model;

/**
 * Generation strategy used for producing trains during daytime hours.
 *
 * <p>This strategy prioritizes electric trains first and periodically
 * produces diesel trains based on an internal counter.
 */
public class TrainStrategyDay implements GenerationStrategy {
  private int counter;

  /**
   * Constructs a daytime train generation strategy.
   */
  public TrainStrategyDay() {
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
    if (counter < 3) {
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
      counter = counter % 4;
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
