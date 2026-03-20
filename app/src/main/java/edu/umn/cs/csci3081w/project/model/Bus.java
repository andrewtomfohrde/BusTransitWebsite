package edu.umn.cs.csci3081w.project.model;

/**
 * Abstract base class representing a bus vehicle.
 *
 * <p>A bus is a type of {@link Vehicle} that uses standard passenger loading
 * and unloading strategies.
 */
public abstract class Bus extends Vehicle {

  /** Identifier string used to represent a bus vehicle type. */
  public static final String BUS_VEHICLE = "BUS_VEHICLE";

  /**
   * Constructs a bus.
   *
   * @param id bus identifier
   * @param line route of inbound and outbound travel
   * @param capacity capacity of the bus
   * @param speed speed of the bus
   */
  public Bus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed, new PassengerLoader(), new PassengerUnloader());
  }
}
