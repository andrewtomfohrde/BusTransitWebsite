package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusStrategyNightTest {

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
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    assertEquals(0, busStrategyNight.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(3, 1, 0, 0);
    BusStrategyNight busStrategyDay = new BusStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
    }
  }

  /**
   * Tests type of vehicle with no large buses.
   */
  @Test
  public void testGetTypeOfVehicleNoLargeBuses() {
    // Small buses are 2, large buses are 0
    StorageFacility storageFacility = new StorageFacility(2, 0, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();

    // First 3 calls -> SmallBus available
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, busStrategyNight.getTypeOfVehicle(storageFacility));
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, busStrategyNight.getTypeOfVehicle(storageFacility));
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, busStrategyNight.getTypeOfVehicle(storageFacility));

    // 4th call -> no LargeBus available, expect null
    assertNull(busStrategyNight.getTypeOfVehicle(storageFacility));
  }

  /**
   * Test for counter to wrap.
   */
  @Test
  public void testCounterWrapAround() {
    StorageFacility storageFacility = new StorageFacility(3, 1, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();

    for (int i = 0; i < 8; i++) { // two full cycles
      busStrategyNight.getTypeOfVehicle(storageFacility);
    }
    // Counter should wrap around, max value before mod = 8 % 4 = 0
    assertEquals(0, busStrategyNight.getCounter());
  }

  /**
   * Test for no buses available.
   */
  @Test
  public void testGetTypeOfVehicleNoBuses() {
    // No small or large buses in storage
    StorageFacility storageFacility = new StorageFacility(0, 0, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();

    // First 4 calls should all return null
    assertNull(busStrategyNight.getTypeOfVehicle(storageFacility));
    assertNull(busStrategyNight.getTypeOfVehicle(storageFacility));
    assertNull(busStrategyNight.getTypeOfVehicle(storageFacility));
    assertNull(busStrategyNight.getTypeOfVehicle(storageFacility));

    // Counter should still increment and wrap around even though type is null
    assertEquals(0, busStrategyNight.getCounter()); // after 4 calls, 4 % 4 = 0
  }

}
