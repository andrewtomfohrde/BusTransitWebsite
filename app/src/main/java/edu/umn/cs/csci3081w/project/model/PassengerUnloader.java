package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles unloading passengers from a vehicle.
 *
 * <p>This class removes passengers whose destination matches the current stop
 * while keeping all others on the vehicle.
 */
public class PassengerUnloader {

  /**
   * Constructs a passenger unloader.
   */
  public PassengerUnloader() {
    // Default constructor
  }

  /**
   * Unloads passengers whose destination matches the current stop.
   *
   * @param passengers list of passengers currently on the vehicle
   * @param currentStop the stop at which passengers may disembark
   * @return the number of passengers unloaded
   */
  public int unloadPassengers(List<Passenger> passengers, Stop currentStop) {
    int passengersUnloaded = 0;
    List<Passenger> copyList = new ArrayList<>();
    for (Passenger p : passengers) {
      if (p.getDestination() == currentStop.getId()) {
        passengersUnloaded++;
      } else {
        copyList.add(p);
      }
    }
    passengers.clear();
    passengers.addAll(copyList);
    return passengersUnloaded;
  }
}
