package edu.umn.cs.csci3081w.project.model;

import java.util.Random;

/**
 * Factory class responsible for generating {@link Passenger} objects.
 *
 * <p>This factory supports both random passenger generation for normal
 * simulation runs and deterministic generation for testing purposes.
 */
public class PassengerFactory {

  /** Prefixes used for randomly generating passenger names. */
  private static final String[] namePrefix;

  /** Suffixes used for randomly generating passenger names. */
  private static final String[] nameSuffix;

  /** Name stems used for randomly generating passenger names. */
  private static final String[] nameStemsArray;

  /** Random number generator used for non-deterministic behavior. */
  private static Random rand;

  /**
   * Indicates whether deterministic mode is enabled.
   *
   * <p>When enabled, passenger names and destinations follow a predictable pattern
   * to facilitate testing.
   */
  public static boolean DETERMINISTIC = false;

  /**
   * Deterministic set of passenger names used for testing.
   */
  public static final String[] DETERMINISTIC_NAMES = {
      "Goldy", "President", "Coach"
  };

  /**
   * Counter used for selecting deterministic passenger names.
   */
  public static int DETERMINISTIC_NAMES_COUNT = 0;

  /**
   * Counter used for selecting deterministic destination stops.
   */
  public static int DETERMINISTIC_DESTINATION_COUNT = 0;

  /**
   * Default constructor for PassengerFactory.
   *
   * <p>This constructor is implicitly used and exists only to satisfy
   * documentation requirements. Instances of this class are not intended
   * to be created.
   */
  public PassengerFactory() {
    // Intentionally empty
  }

  static {
    namePrefix = new String[]{
        "",
        "bel",
        "nar",
        "xan",
        "bell",
        "natr",
        "ev",
    };
    nameSuffix = new String[]{
        "", "us", "ix", "ox", "ith",
        "ath", "um", "ator", "or", "axia",
        "imus", "ais", "itur", "orex", "o",
        "y"
    };
    nameStemsArray = new String[]{
        "adur", "aes", "anim", "apoll", "imac",
        "educ", "equis", "extr", "guius", "hann",
        "equi", "amora", "hum", "iace", "ille",
        "inept", "iuv", "obe", "ocul", "orbis"
    };
    rand = new Random();
  }

  /**
   * Creates a passenger with a generated name and destination stop.
   *
   * @param currStop current stop identifier
   * @param lastStop last stop identifier
   * @return the generated passenger
   */
  public static Passenger generate(int currStop, int lastStop) {
    String newName = nameGeneration();
    int destination;
    if (PassengerFactory.DETERMINISTIC) {
      destination =
          ((PassengerFactory.DETERMINISTIC_DESTINATION_COUNT + 1) % (lastStop - currStop))
              + currStop + 1;
      PassengerFactory.DETERMINISTIC_DESTINATION_COUNT++;
    } else {
      destination = ((rand.nextInt(1000) + 1) % (lastStop - currStop)) + currStop + 1;
    }
    return new Passenger(destination, newName);
  }

  /**
   * Generates a passenger name.
   *
   * @return a generated passenger name
   */
  public static String nameGeneration() {
    String str;
    if (PassengerFactory.DETERMINISTIC) {
      int nameIndex =
          PassengerFactory.DETERMINISTIC_NAMES_COUNT
              % PassengerFactory.DETERMINISTIC_NAMES.length;
      str = PassengerFactory.DETERMINISTIC_NAMES[nameIndex];
      PassengerFactory.DETERMINISTIC_NAMES_COUNT++;
    } else {
      str = namePrefix[(rand.nextInt(1000) + 1) % 7]
          + nameStemsArray[(rand.nextInt(1000) + 1) % 20]
          + nameSuffix[(rand.nextInt(1000) + 1) % 16];
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
}
