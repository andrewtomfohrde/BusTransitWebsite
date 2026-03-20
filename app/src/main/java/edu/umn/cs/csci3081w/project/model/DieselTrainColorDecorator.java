package edu.umn.cs.csci3081w.project.model;

/**
 * Decorator that assigns a yellow color to diesel trains.
 *
 * <p>This decorator wraps a {@link Vehicle} and applies a yellow {@link RgbaColor},
 * using the provided alpha value for transparency effects.
 */
public class DieselTrainColorDecorator extends VehicleColorDecorator {

  /**
   * Constructs a diesel train color decorator.
   *
   * @param wrapped the vehicle being decorated
   * @param alpha the alpha transparency value (0–255)
   */
  public DieselTrainColorDecorator(Vehicle wrapped, int alpha) {
    super(wrapped);
    setColor(new RgbaColor(255, 204, 51, alpha)); // yellow
  }
}
