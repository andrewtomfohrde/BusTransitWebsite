package edu.umn.cs.csci3081w.project.model;

import java.util.List;
import java.util.Random;

/**
 * Passenger generator that creates passengers based on probabilistic rules.
 *
 * <p>This generator uses per-stop probabilities to determine how many
 * passengers arrive at each stop during a simulation update.
 */
public class RandomPassengerGenerator extends PassengerGenerator {

  /**
   * Indicates whether deterministic mode is enabled.
   *
   * <p>When enabled, passenger generation becomes predictable for testing.
   */
  public static boolean DETERMINISTIC = false;

  /**
   * Deterministic probability value used when deterministic mode is enabled.
   */
  public static final double DETERMINISTIC_VALUE = 0.1;

  /**
   * Constructs a random passenger generator.
   *
   * @param stops list of stops on the route
   * @param probabilities passenger generation probabilities per stop
   */
  public RandomPassengerGenerator(List<Stop> stops, List<Double> probabilities) {
    super(stops, probabilities);
  }

  /**
   * Generates passengers using per-stop probabilities.
   *
   * <p>For each stop, passengers are generated repeatedly while the
   * probability of generating another passenger remains greater than 0.01%.
   *
   * @return number of passengers generated
   */
  @Override
  public int generatePassengers() {
    int passengersAdded = 0;
    int probSize = getProbabilities().size();
    int stopSize = this.getStops().size();
    int stopIndex = this.getStops().get(0).getId();
    int lastStopIndex = this.getStops().get(this.getStops().size() - 1).getId();
    int probCount = 0;
    int stopCount = 0;
    while (probCount < probSize && stopCount < stopSize) {
      double initialGenerationProbability = getProbabilities().get(probCount);
      double currentGenerationProbability = initialGenerationProbability;
      while (currentGenerationProbability > 0.0001 && stopIndex != lastStopIndex) {
        Random rand = new Random();
        double generationValue;
        if (RandomPassengerGenerator.DETERMINISTIC) {
          generationValue = RandomPassengerGenerator.DETERMINISTIC_VALUE;
        } else {
          generationValue = rand.nextDouble();
        }
        if (generationValue < currentGenerationProbability) {
          Passenger p = PassengerFactory.generate(stopIndex, lastStopIndex);
          passengersAdded += this.getStops().get(stopCount).addPassengers(p);
        }
        currentGenerationProbability *= initialGenerationProbability;
      }
      stopIndex++;
      probCount++;
      stopCount++;
    }
    return passengersAdded;
  }
}
