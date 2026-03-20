package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * Represents a diesel-powered train in the transit simulation.
 *
 * <p>A diesel train extends the {@link Train} class and applies a color decorator
 * based on whether issues exist on its line.
 */
public class DieselTrain extends Train {

  /** Identifier string representing a diesel train vehicle type. */
  public static final String DIESEL_TRAIN_VEHICLE = "DIESEL_TRAIN_VEHICLE";

  /** Default speed of a diesel train in simulation units. */
  public static final double SPEED = 1;

  /** Maximum passenger capacity of a standard diesel train. */
  public static final int CAPACITY = 120;

  private Vehicle decoratedSelf;

  /**
   * Constructs a diesel train.
   *
   * @param id train identifier
   * @param line the line the train travels on
   * @param capacity maximum passenger capacity
   * @param speed speed of the train
   */
  public DieselTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);

    // determine alpha transparency
    int alpha = line.isIssueExist() ? 155 : 255;

    // wrap THIS vehicle with the decorator
    decoratedSelf = new DieselTrainColorDecorator(this, alpha);
  }

  /** {@inheritDoc}
   *
   */
  @Override
  public RgbaColor getColor() {
    return decoratedSelf.getColor();
  }

  /** {@inheritDoc}
   *
   */
  @Override
  public void report(PrintStream out) {
    out.println("####Diesel Train Info Start####");
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
    out.println("####Diesel Train Info End####");
  }

  /** {@inheritDoc}
   *
   */
  @Override
  public int getCurrentCO2Emission() {
    return ((3 * getPassengers().size()) + 6);
  }
}
