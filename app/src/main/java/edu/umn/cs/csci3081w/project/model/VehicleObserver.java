package edu.umn.cs.csci3081w.project.model;

/**
 * Observer interface for vehicle-related updates.
 *
 * <p>Classes implementing this interface can register with a
 * {@link VehicleConcreteSubject} to receive updates or provide
 * vehicle information when requested.
 */
public interface VehicleObserver {

  /**
   * Indicates whether the observer can currently provide information.
   *
   * @return true if information is available, false otherwise
   */
  boolean provideInfo();

  /**
   * Sets the vehicle subject that this observer is attached to.
   *
   * @param vehicleConcreteSubject the subject being observed
   */
  void setVehicleSubject(VehicleConcreteSubject vehicleConcreteSubject);
}
