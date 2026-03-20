package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * Base decorator for vehicles that modifies only their color.
 *
 * <p>This decorator wraps an existing {@link Vehicle} and delegates all
 * functional behavior to it while allowing subclasses to override the
 * color returned by {@link #getColor()}.
 */
public abstract class VehicleColorDecorator extends Vehicle {

  /** The vehicle being wrapped by this decorator. */
  protected final Vehicle wrapped;

  /**
   * Constructs a vehicle color decorator.
   *
   * @param wrapped the vehicle to be decorated
   */
  protected VehicleColorDecorator(Vehicle wrapped) {
    super(wrapped.getId(),
        wrapped.getCapacity(),
        wrapped.getSpeed(),
        wrapped.getPassengerLoader(),
        wrapped.getPassengerUnloader());
    this.wrapped = wrapped;
  }

  // delegate everything important to wrapped
  @Override
  public void update() {
    wrapped.update();
    setPosition(wrapped.getPosition());
    setName(wrapped.getName());
    // copy over any other state needed visually
  }

  @Override
  public void report(PrintStream out) {
    wrapped.report(out);
  }

  @Override
  public int loadPassenger(Passenger p) {
    return wrapped.loadPassenger(p);
  }

  @Override
  public int getCurrentCO2Emission() {
    return wrapped.getCurrentCO2Emission();
  }

  @Override
  public int handleStop() {
    return wrapped.handleStop();
  }
}
