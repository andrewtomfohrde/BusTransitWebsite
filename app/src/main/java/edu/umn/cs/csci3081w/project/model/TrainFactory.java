package edu.umn.cs.csci3081w.project.model;

/**
 * Factory responsible for creating train vehicles for the simulation.
 *
 * <p>The factory selects a {@link GenerationStrategy} based on the
 * time of day and uses it to determine whether to generate electric
 * or diesel trains.
 */
public class TrainFactory implements VehicleFactory {
  private GenerationStrategy generationStrategy;
  private Counter counter;
  private StorageFacility storageFacility;

  /**
   * Initializes the strategy depending on time of day when simulation is started.
   *
   * @param storageFacility storage facility information
   * @param counter counter information
   * @param time hour of the day
   */
  public TrainFactory(StorageFacility storageFacility, Counter counter, int time) {
    if (time >= 8 && time < 18) {
      generationStrategy = new TrainStrategyDay();
    } else {
      generationStrategy = new TrainStrategyNight();
    }
    this.storageFacility = storageFacility;
    this.counter = counter;
  }

  @Override
  public Vehicle generateVehicle(Line line) {
    String typeOfVehicle = generationStrategy.getTypeOfVehicle(storageFacility);
    Vehicle generatedVehicle = null;

    if (typeOfVehicle != null && typeOfVehicle.equals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE)) {
      generatedVehicle = new ElectricTrain(counter.getElectricTrainIdCounterAndIncrement(),
          line, ElectricTrain.CAPACITY, ElectricTrain.SPEED);
      storageFacility.decrementElectricTrainsNum();
    } else if (typeOfVehicle != null && typeOfVehicle.equals(DieselTrain.DIESEL_TRAIN_VEHICLE)) {
      generatedVehicle = new DieselTrain(counter.getDieselTrainIdCounterAndIncrement(),
          line, DieselTrain.CAPACITY, DieselTrain.SPEED);
      storageFacility.decrementDieselTrainsNum();
    }

    return generatedVehicle;
  }

  @Override
  public void returnVehicle(Vehicle vehicle) {
    if (vehicle instanceof ElectricTrain) {
      storageFacility.incrementElectricTrainsNum();
    } else if (vehicle instanceof DieselTrain) {
      storageFacility.incrementDieselTrainsNum();
    }
  }

  /**
   * Gets the storage facility used by this factory.
   *
   * @return storage facility
   */
  public StorageFacility getStorageFacility() {
    return storageFacility;
  }

  /**
   * Gets the generation strategy used by this factory.
   *
   * @return generation strategy
   */
  public GenerationStrategy getGenerationStrategy() {
    return generationStrategy;
  }
}
