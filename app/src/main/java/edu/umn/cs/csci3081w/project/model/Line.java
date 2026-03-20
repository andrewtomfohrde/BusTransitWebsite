package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.Iterator;

/**
 * Represents a transit line consisting of an outbound and inbound route.
 *
 * <p>A line may experience issues that affect vehicle behavior. Each line contains two
 * {@link Route} objects and an {@link Issue} used for indicating disruptions.
 */
public class Line {

  /** Identifier string representing a bus-type transit line. */
  public static final String BUS_LINE = "BUS_LINE";

  /** Identifier string representing a train-type transit line. */
  public static final String TRAIN_LINE = "TRAIN_LINE";

  private int id;
  private String name;
  private String type;
  private Route outboundRoute;
  private Route inboundRoute;
  private Issue issue;

  /**
   * Constructs a transit line.
   *
   * @param id            line identifier
   * @param name          descriptive name of the line
   * @param type          type of the line (e.g., BUS_LINE or TRAIN_LINE)
   * @param outboundRoute the outbound route
   * @param inboundRoute  the inbound route
   * @param issue         issue object used to track disruptions
   */
  public Line(int id, String name, String type, Route outboundRoute, Route inboundRoute,
              Issue issue) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.outboundRoute = outboundRoute;
    this.inboundRoute = inboundRoute;
    this.issue = issue;
  }

  /**
   * Reports line information and both routes.
   *
   * @param out the stream to print report output to
   */
  public void report(PrintStream out) {
    out.println("====Line Info Start====");
    out.println("ID: " + id);
    out.println("Name: " + name);
    out.println("Type: " + type);
    outboundRoute.report(out);
    inboundRoute.report(out);
    out.println("====Line Info End====");
  }

  /**
   * Creates a shallow copy of this line.
   *
   * <p>Routes are shallow copied so that stops and distances remain shared.
   *
   * @return a shallow copy of this line
   */
  public Line shallowCopy() {
    Line shallowCopy = new Line(this.id, this.name, this.type,
        outboundRoute.shallowCopy(), inboundRoute.shallowCopy(), this.issue);
    return shallowCopy;
  }

  /**
   * Updates both outbound and inbound routes and decrements issue counter if needed.
   */
  public void update() {
    outboundRoute.update();
    inboundRoute.update();
    if (this.issue != null && !this.issue.isIssueResolved()) {
      this.issue.decrementCounter();
    }
  }

  /**
   * Gets the ID of the line.
   *
   * @return line identifier
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the line.
   *
   * @return line name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the type of the line.
   *
   * @return line type
   */
  public String getType() {
    return type;
  }

  /**
   * Gets the outbound route.
   *
   * @return outbound route
   */
  public Route getOutboundRoute() {
    return this.outboundRoute;
  }

  /**
   * Gets the inbound route.
   *
   * @return inbound route
   */
  public Route getInboundRoute() {
    return this.inboundRoute;
  }

  /**
   * Checks whether an issue exists on the line.
   *
   * @return {@code true} if an issue exists and is unresolved, otherwise {@code false}
   */
  public boolean isIssueExist() {
    if (this.issue == null) {
      return false;
    }

    return !issue.isIssueResolved();
  }

  /**
   * Creates (injects) an issue on the line by resetting the issue timer.
   */
  public void createIssue() {
    this.issue.createIssue();
  }
}
