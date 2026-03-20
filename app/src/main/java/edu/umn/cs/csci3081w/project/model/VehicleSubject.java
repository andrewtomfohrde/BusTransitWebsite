package edu.umn.cs.csci3081w.project.model;

/**
 * Subject interface in the Observer design pattern for vehicles.
 *
 * <p>Implementations of this interface manage a set of
 * {@link VehicleObserver}s and notify them of simulation updates.
 */
public interface VehicleSubject {

  /**
   * Attaches an observer to this subject.
   *
   * @param observer the observer to attach
   */
  void attachObserver(VehicleObserver observer);

  /**
   * Detaches an observer from this subject.
   *
   * @param observer the observer to detach
   */
  void detachObserver(VehicleObserver observer);

  /**
   * Notifies all attached observers of an update.
   */
  void notifyObservers();
}
