package edu.umn.cs.csci3081w.project.webserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests LineIssueCommand class.
 */
class LineIssueCommandTest {

  private VisualTransitSimulator simulator;
  private WebServerSession session;
  private LineIssueCommand command;

  /**
   * Setup for test cases.
   */
  @BeforeEach
  void setup() {
    simulator = mock(VisualTransitSimulator.class);
    session = mock(WebServerSession.class);
    command = new LineIssueCommand(simulator);
  }

  /**
   * Tests with execute for LineFound.
   */
  @Test
  void testExecuteLineFoundCreatesIssue() {
    Line line1 = mock(Line.class);
    Line line2 = mock(Line.class);

    when(line1.getId()).thenReturn(10);
    when(line2.getId()).thenReturn(20);

    when(simulator.getLines()).thenReturn(List.of(line1, line2));

    JsonObject json = new JsonObject();
    json.addProperty("id", 20);

    command.execute(session, json);

    verify(line2).createIssue();
    verify(line1, never()).createIssue();
  }

  /**
   * Tests execute with NoMatchingLine.
   */
  @Test
  void testExecuteNoMatchingLineNoIssueCreated() {
    Line line1 = mock(Line.class);
    when(line1.getId()).thenReturn(1);

    when(simulator.getLines()).thenReturn(List.of(line1));

    JsonObject json = new JsonObject();
    json.addProperty("id", 999); // No match

    command.execute(session, json);

    verify(line1, never()).createIssue();
  }
}
