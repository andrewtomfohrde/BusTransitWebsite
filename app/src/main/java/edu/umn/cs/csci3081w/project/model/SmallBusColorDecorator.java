package edu.umn.cs.csci3081w.project.model;

/**
 * Decorator that assigns maroon color to small buses.
 */
public class SmallBusColorDecorator extends VehicleColorDecorator {

  /**
   * Constructs a color decorator for a small bus.
   *
   * @param wrapped the vehicle being decorated
   * @param alpha the alpha transparency value (0–255)
   */
  public SmallBusColorDecorator(Vehicle wrapped, int alpha) {
    super(wrapped);
    setColor(new RgbaColor(122, 0, 25, alpha)); // maroon
  }
}
