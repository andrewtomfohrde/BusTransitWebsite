package edu.umn.cs.csci3081w.project.model;

/**
 * Decorator that assigns a pink color to large buses.
 *
 * <p>This decorator wraps a {@link Vehicle} and applies a pink {@link RgbaColor}
 * using the provided alpha transparency value.
 */
public class LargeBusColorDecorator extends VehicleColorDecorator {

  /**
   * Constructs a large bus color decorator.
   *
   * @param wrapped the vehicle being decorated
   * @param alpha the alpha transparency value (0–255)
   */
  public LargeBusColorDecorator(Vehicle wrapped, int alpha) {
    super(wrapped);
    setColor(new RgbaColor(239, 130, 238, alpha)); // pink
  }
}
