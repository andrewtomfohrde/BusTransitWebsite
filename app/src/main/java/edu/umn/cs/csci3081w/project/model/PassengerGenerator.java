package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for generating passengers at stops.
 *
 * <p>A passenger generator maintains a list of stops and corresponding
 * probabilities used to determine how many passengers are generated.
 */
public abstract class PassengerGenerator {
  private List<Stop> stops;
  private List<Double> probabilities;

  /**
   * Constructs a passenger generator.
   *
   * @param stops list of stops
   * @param probabilities list of probabilities corresponding to each stop
   */
  public PassengerGenerator(List<Stop> stops, List<Double> probabilities) {
    this.probabilities = new ArrayList<>();
    this.stops = new ArrayList<>();
    for (Stop s : stops) {
      this.stops.add(s);
    }
    for (Double probability : probabilities) {
      this.probabilities.add(probability);
    }
  }

  /**
   * Generates passengers based on the configured probabilities.
   *
   * @return the number of passengers generated
   */
  public abstract int generatePassengers();

  /**
   * Gets the list of stops associated with this generator.
   *
   * @return list of stops
   */
  public List<Stop> getStops() {
    return stops;
  }

  /**
   * Gets the list of probabilities associated with each stop.
   *
   * @return list of probabilities
   */
  public List<Double> getProbabilities() {
    return probabilities;
  }
}
