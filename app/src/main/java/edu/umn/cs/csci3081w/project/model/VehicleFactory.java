package edu.umn.cs.csci3081w.project.model;

/**
 * Interface for factories that create and manage vehicles.
 */
public interface VehicleFactory {

  /**
   * Generates a vehicle for the given transit line.
   *
   * @param line the line the vehicle will operate on
   * @return the generated vehicle, or {@code null} if no vehicle can be created
   */
  Vehicle generateVehicle(Line line);

  /**
   * Returns a vehicle to the factory or its associated storage facility.
   *
   * @param vehicle the vehicle being returned
   */
  void returnVehicle(Vehicle vehicle);
}
