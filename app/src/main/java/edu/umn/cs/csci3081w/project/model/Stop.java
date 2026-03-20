package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a stop in a transit route.
 *
 * <p>A stop maintains a list of waiting passengers and supports
 * loading passengers onto vehicles and generating reports.
 */
public class Stop {
  private int id;
  private String name;
  private Position position;
  private List<Passenger> passengers;

  /**
   * Constructs a stop.
   *
   * @param id stop identifier
   * @param name name of the stop
   * @param position geographic position of the stop
   */
  public Stop(int id, String name, Position position) {
    this.id = id;
    this.name = name;
    this.position = position;
    passengers = new ArrayList<>();
  }

  /**
   * Loads passengers from this stop onto a vehicle.
   *
   * @param vehicle vehicle to load passengers onto
   * @return number of passengers loaded
   */
  public int loadPassengers(Vehicle vehicle) {
    int passengersAdded = 0;
    while (!passengers.isEmpty() && vehicle.loadPassenger(passengers.get(0)) > 0) {
      passengers.remove(0);
      passengersAdded++;
    }
    return passengersAdded;
  }

  /**
   * Adds a passenger to this stop.
   *
   * @param pass passenger to add
   * @return number of passengers added
   */
  public int addPassengers(Passenger pass) {
    int passengersAddedToStop = 0;
    passengers.add(pass);
    passengersAddedToStop++;
    return passengersAddedToStop;
  }

  /**
   * Updates all passengers waiting at this stop.
   */
  public void update() {
    Iterator<Passenger> passIter = passengers.iterator();
    while (passIter.hasNext()) {
      passIter.next().pasUpdate();
    }
  }

  /**
   * Reports statistics and passenger information for this stop.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("####Stop Info Start####");
    out.println("ID: " + id);
    out.println("Name: " + name);
    out.println("Position: "
        + (getPosition().getLatitude() + "," + getPosition().getLongitude()));
    out.println("****Passengers Info Start****");
    out.println("Num passengers waiting: " + passengers.size());
    Iterator<Passenger> passIter = passengers.iterator();
    while (passIter.hasNext()) {
      passIter.next().report(out);
    }
    out.println("****Passengers Info End****");
    out.println("####Stop Info End####");
  }

  /**
   * Gets the stop identifier.
   *
   * @return stop id
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the stop.
   *
   * @return stop name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the geographic position of the stop.
   *
   * @return stop position
   */
  public Position getPosition() {
    return position;
  }

  /**
   * Gets the list of passengers waiting at this stop.
   *
   * @return list of waiting passengers
   */
  public List<Passenger> getPassengers() {
    return passengers;
  }
}
