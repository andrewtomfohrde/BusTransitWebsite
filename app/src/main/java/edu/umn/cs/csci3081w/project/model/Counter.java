package edu.umn.cs.csci3081w.project.model;

/**
 * Maintains counters for generating unique identifiers for simulation entities.
 *
 * <p>This class provides incrementing ID values for routes, stops, vehicles,
 * and lines within the transit simulation.
 */
public class Counter {

  private int routeIdCounter = 10;
  private int stopIdCounter = 100;
  private int smallBusIdCounter = 1000;
  private int largeBusIdCounter = 2000;
  private int electricTrainIdCounter = 3000;
  private int dieselTrainIdCounter = 4000;
  private int lineIdCounter = 10000;

  /**
   * Constructs a counter with default starting values.
   */
  public Counter() {

  }

  /**
   * Returns the next route ID and increments the counter.
   *
   * @return the next route ID
   */
  public int getRouteIdCounterAndIncrement() {
    return routeIdCounter++;
  }

  /**
   * Returns the next stop ID and increments the counter.
   *
   * @return the next stop ID
   */
  public int getStopIdCounterAndIncrement() {
    return stopIdCounter++;
  }

  /**
   * Returns the next small bus ID and increments the counter.
   *
   * @return the next small bus ID
   */
  public int getSmallBusIdCounterAndIncrement() {
    return smallBusIdCounter++;
  }

  /**
   * Returns the next large bus ID and increments the counter.
   *
   * @return the next large bus ID
   */
  public int getLargeBusIdCounterAndIncrement() {
    return largeBusIdCounter++;
  }

  /**
   * Returns the next electric train ID and increments the counter.
   *
   * @return the next electric train ID
   */
  public int getElectricTrainIdCounterAndIncrement() {
    return electricTrainIdCounter++;
  }

  /**
   * Returns the next diesel train ID and increments the counter.
   *
   * @return the next diesel train ID
   */
  public int getDieselTrainIdCounterAndIncrement() {
    return dieselTrainIdCounter++;
  }

  /**
   * Returns the next line ID and increments the counter.
   *
   * @return the next line ID
   */
  public int getLineIdCounterAndIncrement() {
    return lineIdCounter++;
  }
}
