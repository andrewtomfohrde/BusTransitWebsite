package edu.umn.cs.csci3081w.project.model;

/**
 * Represents an issue affecting a transit line.
 *
 * <p>An issue is modeled using a countdown timer that determines whether
 * the issue is active or resolved.
 */
public class Issue {
  private int counter;

  /**
   * Constructs an issue with the initial countdown timer set to zero.
   */
  public Issue() {
    this.counter = 0;
  }

  /**
   * Gets the current counter value.
   *
   * @return the current counter
   */
  public int getCounter() {
    return counter;
  }

  /**
   * Decrements the countdown timer by one.
   */
  public void decrementCounter() {
    this.counter--;
  }

  /**
   * Injects a new issue by setting the countdown timer to 10.
   */
  public void createIssue() {
    this.counter = 10;
  }

  /**
   * Checks whether the issue is resolved.
   *
   * @return {@code true} if the issue is resolved, otherwise {@code false}
   */
  public boolean isIssueResolved() {
    return this.counter == 0;
  }
}
