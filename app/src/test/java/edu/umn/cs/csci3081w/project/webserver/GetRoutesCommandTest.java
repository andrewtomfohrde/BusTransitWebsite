package edu.umn.cs.csci3081w.project.webserver;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.Stop;
import java.util.List;
import org.junit.jupiter.api.Test;


/**
 * Tests for GetRoutesCommand class.
 */
public class GetRoutesCommandTest {
  /**
   * Test for executing Json command.
   */
  @Test
  public void testExecuteSendsCorrectJson() {

    VisualTransitSimulator simulator = mock(VisualTransitSimulator.class);
    WebServerSession session = mock(WebServerSession.class);

    Route outbound = mock(Route.class);
    Route inbound = mock(Route.class);
    Line line = mock(Line.class);

    when(simulator.getLines()).thenReturn(List.of(line));
    when(line.getOutboundRoute()).thenReturn(outbound);
    when(line.getInboundRoute()).thenReturn(inbound);

    Stop stop = mock(Stop.class);
    Position pos = mock(Position.class);

    when(outbound.getId()).thenReturn(1);
    when(inbound.getId()).thenReturn(2);

    when(outbound.getStops()).thenReturn(List.of(stop));
    when(inbound.getStops()).thenReturn(List.of(stop));

    when(stop.getId()).thenReturn(1);
    when(stop.getPassengers()).thenReturn(List.of());
    when(stop.getPosition()).thenReturn(pos);

    when(pos.getLongitude()).thenReturn(10.0);
    when(pos.getLatitude()).thenReturn(20.0);

    doNothing().when(session).sendJson(any(JsonObject.class));

    GetRoutesCommand cmd = new GetRoutesCommand(simulator);

    cmd.execute(session, new JsonObject());

    verify(session, times(1)).sendJson(any(JsonObject.class));
  }

}
