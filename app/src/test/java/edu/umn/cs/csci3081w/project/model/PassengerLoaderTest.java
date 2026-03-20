package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerLoaderTest {

  private PassengerLoader testPassengerLoader;

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
   * Tests loadPassenger function.
   */
  @Test
  public void testLoadPassenger() {

    testPassengerLoader = new PassengerLoader();

    Passenger testPassenger = new Passenger(1, "testPassenger");

    List<Passenger> passengerList = new ArrayList<Passenger>();

    assertEquals(1, testPassengerLoader.loadPassenger(testPassenger, 1, passengerList));
    assertEquals(1, passengerList.size());
    assertTrue(testPassenger.isOnVehicle());

    assertEquals(0, testPassengerLoader.loadPassenger(testPassenger, 1, passengerList));

  }

  /**
   * Tests if the loaded passenger isn't added.
   */
  @Test
  public void testLoadPassengerDoesNotAdd() {
    PassengerLoader loader = new PassengerLoader();
    List<Passenger> passengers = new ArrayList<>();
    Passenger existing = PassengerFactory.generate(0, 3);
    passengers.add(existing);
    int maxCapacity = 1;
    Passenger newPass = PassengerFactory.generate(0, 5);
    int added = loader.loadPassenger(newPass, maxCapacity, passengers);

    // New passenger should not be added
    assertEquals(0, added);
    assertEquals(1, passengers.size());
    assertFalse(newPass.isOnVehicle());
  }
}
