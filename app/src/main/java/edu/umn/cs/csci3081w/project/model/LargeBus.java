package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * Represents a large bus within the transit simulation.
 *
 * <p>A large bus extends the {@link Bus} class and uses a color decorator
 * to determine its display color based on whether issues exist on its line.
 */
public class LargeBus extends Bus {

  /** Identifier string representing a large bus vehicle type. */
  public static final String LARGE_BUS_VEHICLE = "LARGE_BUS_VEHICLE";

  /** Default speed of a large bus in simulation units. */
  public static final double SPEED = 0.5;

  /** Maximum passenger capacity of a large bus. */
  public static final int CAPACITY = 80;

  private Vehicle decoratedSelf;

  /**
   * Constructs a large bus.
   *
   * @param id bus identifier
   * @param line the route the bus operates on
   * @param capacity passenger capacity
   * @param speed speed of the bus
   */
  public LargeBus(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);
    int alpha = line.isIssueExist() ? 155 : 255;
    decoratedSelf = new LargeBusColorDecorator(this, alpha);
  }

  /**
   * Gets the color of the large bus as determined by its decorator.
   *
   * @return the current RGBA color
   */
  @Override
  public RgbaColor getColor() {
    return decoratedSelf.getColor();
  }

  /**
   * Reports statistics and passenger information for this large bus.
   *
   * @param out the stream to print report output to
   */
  @Override
  public void report(PrintStream out) {
    out.println("####Large Bus Info Start####");
    out.println("ID: " + getId());
    out.println("Name: " + getName());
    out.println("Speed: " + getSpeed());
    out.println("Capacity: " + getCapacity());
    out.println("Position: " + (getPosition().getLatitude() + "," + getPosition().getLongitude()));
    out.println("Distance to next stop: " + getDistanceRemaining());
    out.println("****Passengers Info Start****");
    out.println("Num of passengers: " + getPassengers().size());
    for (Passenger pass : getPassengers()) {
      pass.report(out);
    }
    out.println("****Passengers Info End****");
    out.println("####Large Bus Info End####");
  }

  /**
   * Computes the current CO₂ emission level of the large bus.
   *
   * @return the current CO₂ emission value
   */
  @Override
  public int getCurrentCO2Emission() {
    return ((2 * getPassengers().size()) + 5);
  }
}
