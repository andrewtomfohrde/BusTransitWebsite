package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * Represents a small bus in the transit simulation.
 *
 * <p>A small bus has lower capacity than a large bus and uses a color
 * decorator to determine its display color when issues exist.
 */
public class SmallBus extends Bus {

  /**
   * String identifier for a small bus vehicle.
   */
  public static final String SMALL_BUS_VEHICLE = "SMALL_BUS_VEHICLE";

  /**
   * Speed of a small bus.
   */
  public static final double SPEED = 0.5;

  /**
   * Passenger capacity of a small bus.
   */
  public static final int CAPACITY = 20;

  private Vehicle decoratedSelf;

  /**
   * Constructs a small bus.
   *
   * @param id bus identifier
   * @param line route the bus operates on
   * @param capacity passenger capacity
   * @param speed speed of the bus
   */
  public SmallBus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
    int alpha = line.isIssueExist() ? 155 : 255;
    decoratedSelf = new SmallBusColorDecorator(this, alpha);
  }

  /**
   * Gets the display color of the small bus.
   *
   * @return RGBA color of the bus
   */
  @Override
  public RgbaColor getColor() {
    return decoratedSelf.getColor();
  }

  /**
   * Reports statistics and passenger information for the small bus.
   *
   * @param out stream for printing
   */
  @Override
  public void report(PrintStream out) {
    out.println("####Small Bus Info Start####");
    out.println("ID: " + getId());
    out.println("Name: " + getName());
    out.println("Speed: " + getSpeed());
    out.println("Capacity: " + getCapacity());
    out.println("Position: " + (getPosition().getLatitude() + ","
        + getPosition().getLongitude()));
    out.println("Distance to next stop: " + getDistanceRemaining());
    out.println("****Passengers Info Start****");
    out.println("Num of passengers: " + getPassengers().size());
    for (Passenger pass : getPassengers()) {
      pass.report(out);
    }
    out.println("****Passengers Info End****");
    out.println("####Small Bus Info End####");
  }

  /**
   * Computes the current CO₂ emission level of the small bus.
   *
   * @return current CO₂ emission value
   */
  @Override
  public int getCurrentCO2Emission() {
    return ((2 * getPassengers().size()) + 3);
  }
}
