package edu.umn.cs.csci3081w.project.model;

/**
 * Abstract base class representing a train in the transit simulation.
 *
 * <p>A train is a type of {@link Vehicle} that uses default passenger
 * loading and unloading strategies.
 */
public abstract class Train extends Vehicle {

  /**
   * String identifier for train vehicles.
   */
  public static final String TRAIN_VEHICLE = "TRAIN_VEHICLE";

  /**
   * Constructs a train.
   *
   * @param id train identifier
   * @param line route the train operates on
   * @param capacity passenger capacity of the train
   * @param speed speed of the train
   */
  public Train(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed, new PassengerLoader(), new PassengerUnloader());
  }
}
