package edu.umn.cs.csci3081w.project.model;

/**
 * Represents a geographic position using longitude and latitude.
 */
public class Position {

  private double longitude;
  private double latitude;

  /**
   * Constructs a position with the given longitude and latitude.
   *
   * @param longitude the longitude value
   * @param latitude the latitude value
   */
  public Position(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  /**
   * Gets the longitude of this position.
   *
   * @return longitude value
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Gets the latitude of this position.
   *
   * @return latitude value
   */
  public double getLatitude() {
    return latitude;
  }
}
