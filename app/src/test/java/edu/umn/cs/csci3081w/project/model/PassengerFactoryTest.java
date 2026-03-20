package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerFactoryTest {

  /**
   * Setup deterministic operations before each test run.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  /**
   * Tests generate function.
   */
  @Test
  public void testGenerate() {
    assertEquals(3, PassengerFactory.generate(1, 10).getDestination());

  }

  /**
   * Tests generate function.
   */
  @Test
  public void nameGeneration() {
    assertEquals("Goldy", PassengerFactory.nameGeneration());

  }

  /**
   * Tests deterministic.
   */
  @Test
  public void testGenerateDeterministicDestination() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;

    Passenger p = PassengerFactory.generate(0, 5);
    assertEquals(2, p.getDestination());
  }

  /**
   * Tests name generation.
   */
  @Test
  public void testNameGenerationDeterministic() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;

    assertEquals("Goldy", PassengerFactory.nameGeneration());
    assertEquals("President", PassengerFactory.nameGeneration());
  }

  /**
   * Tests for NonDeterministic passengers.
   */
  @Test
  public void testMultipleNonDeterministicPassengers() {
    PassengerFactory.DETERMINISTIC = false;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;

    Passenger p1 = PassengerFactory.generate(0, 5);
    Passenger p2 = PassengerFactory.generate(0, 5);
    Passenger p3 = PassengerFactory.generate(0, 5);



    // Destinations should cycle predictably
    assertEquals(p1.getDestination(), p1.getDestination()); // currStop = 0
    assertEquals(p2.getDestination(), p2.getDestination());
    assertEquals(p3.getDestination(), p3.getDestination());
  }

}
