package edu.umn.cs.csci3081w.project.model;

/**
 * Represents a storage facility that tracks available vehicles.
 *
 * <p>The storage facility maintains counts of different vehicle types
 * and provides methods for incrementing and decrementing these counts
 * as vehicles are deployed and returned.
 */
public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int electricTrainsNum;
  private int dieselTrainsNum;

  /**
   * Creates an empty storage facility with zero vehicles.
   */
  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    electricTrainsNum = 0;
    dieselTrainsNum = 0;
  }

  /**
   * Creates a storage facility with specified vehicle counts.
   *
   * @param smallBusesNum number of small buses
   * @param largeBusesNum number of large buses
   * @param electricTrainsNum number of electric trains
   * @param dieselTrainsNum number of diesel trains
   */
  public StorageFacility(int smallBusesNum, int largeBusesNum,
                         int electricTrainsNum, int dieselTrainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.electricTrainsNum = electricTrainsNum;
    this.dieselTrainsNum = dieselTrainsNum;
  }

  /**
   * Gets the number of small buses available.
   *
   * @return number of small buses
   */
  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  /**
   * Gets the number of electric trains available.
   *
   * @return number of electric trains
   */
  public int getElectricTrainsNum() {
    return electricTrainsNum;
  }

  /**
   * Sets the number of small buses available.
   *
   * @param smallBusesNum number of small buses
   */
  public void setSmallBusesNum(int smallBusesNum) {
    this.smallBusesNum = smallBusesNum;
  }

  /**
   * Sets the number of electric trains available.
   *
   * @param electricTrainsNum number of electric trains
   */
  public void setElectricTrainsNum(int electricTrainsNum) {
    this.electricTrainsNum = electricTrainsNum;
  }

  /**
   * Gets the number of large buses available.
   *
   * @return number of large buses
   */
  public int getLargeBusesNum() {
    return largeBusesNum;
  }

  /**
   * Sets the number of large buses available.
   *
   * @param largeBusesNum number of large buses
   */
  public void setLargeBusesNum(int largeBusesNum) {
    this.largeBusesNum = largeBusesNum;
  }

  /**
   * Gets the number of diesel trains available.
   *
   * @return number of diesel trains
   */
  public int getDieselTrainsNum() {
    return dieselTrainsNum;
  }

  /**
   * Sets the number of diesel trains available.
   *
   * @param dieselTrainsNum number of diesel trains
   */
  public void setDieselTrainsNum(int dieselTrainsNum) {
    this.dieselTrainsNum = dieselTrainsNum;
  }

  /** Decrements the number of small buses by one. */
  public void decrementSmallBusesNum() {
    smallBusesNum--;
  }

  /** Decrements the number of large buses by one. */
  public void decrementLargeBusesNum() {
    largeBusesNum--;
  }

  /** Decrements the number of electric trains by one. */
  public void decrementElectricTrainsNum() {
    electricTrainsNum--;
  }

  /** Decrements the number of diesel trains by one. */
  public void decrementDieselTrainsNum() {
    dieselTrainsNum--;
  }

  /** Increments the number of small buses by one. */
  public void incrementSmallBusesNum() {
    smallBusesNum++;
  }

  /** Increments the number of large buses by one. */
  public void incrementLargeBusesNum() {
    largeBusesNum++;
  }

  /** Increments the number of electric trains by one. */
  public void incrementElectricTrainsNum() {
    electricTrainsNum++;
  }

  /** Increments the number of diesel trains by one. */
  public void incrementDieselTrainsNum() {
    dieselTrainsNum++;
  }
}
