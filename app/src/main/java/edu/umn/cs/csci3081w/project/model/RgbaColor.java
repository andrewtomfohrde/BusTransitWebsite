package edu.umn.cs.csci3081w.project.model;

/**
 * RGBA color used to render vehicles.
 */
public class RgbaColor {
  private final int red;
  private final int green;
  private final int blue;
  private final int alpha;

  /**
   * Represents an RGBA color with red, green, blue, and alpha values.
   * @param red the red component
   * @param green the green component
   * @param blue blue the blue component
   * @param alpha alpha the alpha transparency
   */
  public RgbaColor(int red, int green, int blue, int alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  /**
   * Returns the red component.
   * @return the red value
   */
  public int getRed()   {
    return red;
  }

  /**
   * Returns the green component.
   * @return the green value
   */
  public int getGreen() {
    return green;
  }

  /**
   * Returns the blue component.
   * @return the blue value
   */
  public int getBlue()  {
    return blue;
  }

  /**
   * Returns the alpha component of the color.
   * @return the alpha value
   */
  public int getAlpha() {
    return alpha;
  }
}
