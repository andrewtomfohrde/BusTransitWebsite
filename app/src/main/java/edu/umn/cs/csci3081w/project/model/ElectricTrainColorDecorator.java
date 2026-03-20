package edu.umn.cs.csci3081w.project.model;

/**
 * Decorator that assigns a green color to electric trains.
 *
 * <p>This decorator wraps a {@link Vehicle} and applies a green {@link RgbaColor},
 * using the provided alpha value to control transparency.
 */
public class ElectricTrainColorDecorator extends VehicleColorDecorator {

  /**
   * Constructs an electric train color decorator.
   *
   * @param wrapped the vehicle being decorated
   * @param alpha the alpha transparency value (0–255)
   */
  public ElectricTrainColorDecorator(Vehicle wrapped, int alpha) {
    super(wrapped);
    setColor(new RgbaColor(60, 179, 113, alpha)); // green
  }
}
