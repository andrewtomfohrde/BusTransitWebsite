package edu.umn.cs.csci3081w.project.model;

/**
 * Strategy interface for determining which type of vehicle to generate.
 */
public interface GenerationStrategy {

  /**
   * Determines the type of vehicle to generate based on the current
   * state of the storage facility.
   *
   * @param storageFacility the storage facility containing available vehicles
   * @return the type of vehicle to generate, or {@code null} if none are available
   */
  String getTypeOfVehicle(StorageFacility storageFacility);
}
