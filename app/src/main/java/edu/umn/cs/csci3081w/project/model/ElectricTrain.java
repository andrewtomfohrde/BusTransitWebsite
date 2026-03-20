package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;

/**
 * Represents an electric train in the transit simulation.
 *
 * <p>An electric train extends the {@link Train} class and uses a color decorator
 * to assign an appropriate display color based on line conditions.
 */
public class ElectricTrain extends Train {

  /** Identifier string representing the electric train vehicle type. */
  public static final String ELECTRIC_TRAIN_VEHICLE = "ELECTRIC_TRAIN_VEHICLE";

  /** Default speed of an electric train in simulation units. */
  public static final double SPEED = 1;

  /** Maximum passenger capacity of an electric train. */
  public static final int CAPACITY = 120;

  private Vehicle decoratedSelf;

  /**
   * Constructs an electric train.
   *
   * @param id train identifier
   * @param line the line the train travels on
   * @param capacity maximum passenger capacity
   * @param speed speed of the train
   */
  public ElectricTrain(int id, Line line, int capacity, double speed) {
    super(id, line, capacity, speed);

    int alpha = line.isIssueExist() ? 155 : 255;

    decoratedSelf = new ElectricTrainColorDecorator(this, alpha);
  }

  /**
   * Gets the color of the electric train as provided by the decorator.
   *
   * @return the current RGBA color
   */
  @Override
  public RgbaColor getColor() {
    return decoratedSelf.getColor();
  }

  /**
   * Reports statistics and passenger information for this electric train.
   *
   * @param out the stream to print report output to
   */
  @Override
  public void report(PrintStream out) {
    out.println("####Electric Train Info Start####");
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
    out.println("####Electric Train Info End####");
  }

  /**
   * Computes the current CO₂ emission level of the electric train.
   *
   * <p>Electric trains produce no direct CO₂ emissions.
   *
   * @return the current CO₂ emission rate (always 0)
   */
  @Override
  public int getCurrentCO2Emission() {
    return 0;
  }
}
