package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * A mock implementation of {@link WebServerSession} used for testing.
 *
 * <p>This session does not send data over a network; instead, it stores
 * the most recently sent JSON object for verification in unit tests.
 */
public class FakeWebServerSession extends WebServerSession {

  private JsonObject lastSent;

  /**
   * Constructs a fake web server session.
   */
  public FakeWebServerSession() {
    super();
  }

  /**
   * Captures the sent JSON data instead of sending it to a real session.
   *
   * @param data the JSON object being "sent"
   */
  @Override
  public void sendJson(JsonObject data) {
    lastSent = data;
  }

  /**
   * Returns the last JSON object that was sent.
   *
   * @return the most recently sent JSON object, or {@code null} if none has been sent
   */
  public JsonObject getLastSent() {
    return lastSent;
  }
}
