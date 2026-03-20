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
 * Tests DieselTrainColorDecorator class.
 */
public class DieselTrainColorDecoratorTest {

  /**
   * Tests set color for the diesel train.
   */
  @Test
  public void testConstructorSetsYellowColor() {
    Vehicle wrapped = mock(Vehicle.class);

    when(wrapped.getId()).thenReturn(10);
    when(wrapped.getCapacity()).thenReturn(50);
    when(wrapped.getSpeed()).thenReturn(5.0);
    when(wrapped.getPassengerLoader()).thenReturn(null);
    when(wrapped.getPassengerUnloader()).thenReturn(null);

    DieselTrainColorDecorator deco = new DieselTrainColorDecorator(wrapped, 200);

    RgbaColor c = deco.getColor();
    assertEquals(255, c.getRed());
    assertEquals(204, c.getGreen());
    assertEquals(51, c.getBlue());
    assertEquals(200, c.getAlpha());
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

    DieselTrainColorDecorator deco = new DieselTrainColorDecorator(wrapped, 180);
    deco.getColor();

    verify(wrapped, never()).getColor();
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

    Position pos = new Position(5, 7);
    when(wrapped.getPosition()).thenReturn(pos);
    when(wrapped.getName()).thenReturn("DieselTrainX");

    DieselTrainColorDecorator deco = Mockito.spy(new DieselTrainColorDecorator(wrapped, 150));

    deco.update();
    verify(wrapped).update();

    assertEquals(pos, deco.getPosition());
    assertEquals("DieselTrainX", deco.getName());
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

    DieselTrainColorDecorator deco = new DieselTrainColorDecorator(wrapped, 255);

    PrintStream out = mock(PrintStream.class);
    Passenger p = mock(Passenger.class);

    when(wrapped.loadPassenger(p)).thenReturn(3);
    when(wrapped.getCurrentCO2Emission()).thenReturn(45);
    when(wrapped.handleStop()).thenReturn(2);

    deco.report(out);
    verify(wrapped).report(out);

    assertEquals(3, deco.loadPassenger(p));
    verify(wrapped).loadPassenger(p);

    assertEquals(45, deco.getCurrentCO2Emission());
    verify(wrapped).getCurrentCO2Emission();

    assertEquals(2, deco.handleStop());
    verify(wrapped).handleStop();
  }
}
