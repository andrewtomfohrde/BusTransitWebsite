package edu.umn.cs.csci3081w.project.model;

import java.util.List;

/**
 * Handles loading passengers onto a vehicle.
 *
 * <p>This class encapsulates the logic for adding passengers to a vehicle
 * while respecting the maximum passenger capacity.
 */
public class PassengerLoader {

  /**
   * Constructs a passenger loader.
   */
  public PassengerLoader() {
    // Default constructor
  }

  /**
   * Loads a passenger onto a vehicle if capacity permits.
   *
   * <p>This method returns the number of passengers added, which is either
   * 0 or 1 in the current implementation.
   *
   * @param newPassenger passenger to be loaded
   * @param maxPass maximum number of passengers allowed on the vehicle
   * @param passengers current list of passengers on the vehicle
   * @return the number of passengers added (0 or 1)
   */
  public int loadPassenger(Passenger newPassenger, int maxPass, List<Passenger> passengers) {
    int addedPassengers = 0;
    if (passengers.size() < maxPass) {
      passengers.add(newPassenger);
      newPassenger.setOnVehicle();
      addedPassengers = 1;
    }
    return addedPassengers;
  }
}
