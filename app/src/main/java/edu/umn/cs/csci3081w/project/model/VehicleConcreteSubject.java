package edu.umn.cs.csci3081w.project.model;

import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Concrete implementation of {@link VehicleSubject} using the Observer pattern.
 *
 * <p>This class manages a set of {@link VehicleObserver}s and notifies them
 * during each simulation update cycle. It also maintains a reference to the
 * {@link WebServerSession} used for visualization updates.
 */
public class VehicleConcreteSubject implements VehicleSubject {

  private WebServerSession session;
  private List<VehicleObserver> observers;

  /**
   * Creates a concrete vehicle subject.
   *
   * @param session parameter used to communicate with the visualization module
   */
  public VehicleConcreteSubject(WebServerSession session) {
    this.session = session;
    observers = new ArrayList<VehicleObserver>();
  }

  /**
   * Attaches an observer to this subject.
   *
   * <p>Only one observer is retained at a time; existing observers are cleared.
   *
   * @param observer the observing vehicle
   */
  public void attachObserver(VehicleObserver observer) {
    observers.clear();
    observer.setVehicleSubject(this);
    observers.add(observer);
  }

  /**
   * Detaches an observer from this subject.
   *
   * @param observer the observer to remove
   */
  public void detachObserver(VehicleObserver observer) {
    observers.remove(observer);
  }

  /**
   * Notifies all observers of an update.
   *
   * <p>Observers that report completion are removed.
   */
  public void notifyObservers() {
    ListIterator<VehicleObserver> observersIter = observers.listIterator();
    while (observersIter.hasNext()) {
      boolean tripCompleted = observersIter.next().provideInfo();
      if (tripCompleted) {
        observersIter.remove();
      }
    }
  }

  /**
   * Returns the list of current observers.
   *
   * @return list of vehicle observers
   */
  public List<VehicleObserver> getObservers() {
    return observers;
  }

  /**
   * Returns the web server session associated with this subject.
   *
   * @return web server session
   */
  public WebServerSession getSession() {
    return session;
  }
}
