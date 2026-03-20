package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests LargeBusColorDecorator class.
 */
public class LargeBusColorDecoratorTest {
  /**
   * Tests set color for the large bus.
   */
  @Test
  public void testConstructorSetsPinkColor() {
    Vehicle wrapped = mock(Vehicle.class);

    when(wrapped.getId()).thenReturn(10);
    when(wrapped.getCapacity()).thenReturn(50);
    when(wrapped.getSpeed()).thenReturn(5.0);
    when(wrapped.getPassengerLoader()).thenReturn(null);
    when(wrapped.getPassengerUnloader()).thenReturn(null);

    LargeBusColorDecorator deco = new LargeBusColorDecorator(wrapped, 123);

    RgbaColor c = deco.getColor();
    assertEquals(239, c.getRed());
    assertEquals(130, c.getGreen());
    assertEquals(238, c.getBlue());
    assertEquals(123, c.getAlpha());
  }

  /**
   * Test to make sure getColor isn't delegating.
   */
  @Test
  public void testGetColorDoesNotDelegate() {
    Vehicle wrapped = mock(Vehicle.class);

    when(wrapped.getId()).thenReturn(10);
    when(wrapped.getCapacity()).thenReturn(50);
    when(wrapped.getSpeed()).thenReturn(5.0);
    when(wrapped.getPassengerLoader()).thenReturn(null);
    when(wrapped.getPassengerUnloader()).thenReturn(null);

    LargeBusColorDecorator deco = new LargeBusColorDecorator(wrapped, 200);
    deco.getColor();

    verify(wrapped, never()).getColor(); // decorator must NOT delegate here
  }

  /**
   * Test to make sure update will delegate.
   */
  @Test
  public void testUpdateDelegatesToWrappedAndCopiesState() {
    Vehicle wrapped = mock(Vehicle.class);

    when(wrapped.getId()).thenReturn(10);
    when(wrapped.getCapacity()).thenReturn(50);
    when(wrapped.getSpeed()).thenReturn(5.0);
    when(wrapped.getPassengerLoader()).thenReturn(null);
    when(wrapped.getPassengerUnloader()).thenReturn(null);
    Position pos = new Position(10, 20);
    when(wrapped.getPosition()).thenReturn(pos);
    when(wrapped.getName()).thenReturn("BusX");

    LargeBusColorDecorator deco = Mockito.spy(new LargeBusColorDecorator(wrapped, 100));

    deco.update();
    verify(wrapped).update();

    // Compare the SAME reference, NOT "new Position(...)"
    assertEquals(pos, deco.getPosition());
    assertEquals("BusX", deco.getName());
  }

  /**
   * Test for methods.
   */
  @Test
  public void testDelegatedMethods() {
    Vehicle wrapped = mock(Vehicle.class);

    when(wrapped.getId()).thenReturn(10);
    when(wrapped.getCapacity()).thenReturn(50);
    when(wrapped.getSpeed()).thenReturn(5.0);
    when(wrapped.getPassengerLoader()).thenReturn(null);
    when(wrapped.getPassengerUnloader()).thenReturn(null);

    LargeBusColorDecorator deco = new LargeBusColorDecorator(wrapped, 255);

    PrintStream out = mock(PrintStream.class);
    Passenger p = mock(Passenger.class);

    when(wrapped.loadPassenger(p)).thenReturn(7);
    when(wrapped.getCurrentCO2Emission()).thenReturn(123);
    when(wrapped.handleStop()).thenReturn(1);

    deco.report(out);
    verify(wrapped).report(out);

    assertEquals(7, deco.loadPassenger(p));
    verify(wrapped).loadPassenger(p);

    assertEquals(123, deco.getCurrentCO2Emission());
    verify(wrapped).getCurrentCO2Emission();

    assertEquals(1, deco.handleStop());
    verify(wrapped).handleStop();
  }
}
