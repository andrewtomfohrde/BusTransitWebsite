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
 * Tests ElectricTrainColorDecorator class.
 */
public class ElectricTrainColorDecoratorTest {

  /**
   * Tests set color for the electric train.
   */
  @Test
  public void testConstructorSetsGreenColor() {
    Vehicle wrapped = mock(Vehicle.class);

    when(wrapped.getId()).thenReturn(10);
    when(wrapped.getCapacity()).thenReturn(50);
    when(wrapped.getSpeed()).thenReturn(5.0);
    when(wrapped.getPassengerLoader()).thenReturn(null);
    when(wrapped.getPassengerUnloader()).thenReturn(null);

    ElectricTrainColorDecorator deco = new ElectricTrainColorDecorator(wrapped, 180);

    RgbaColor c = deco.getColor();
    assertEquals(60, c.getRed());
    assertEquals(179, c.getGreen());
    assertEquals(113, c.getBlue());
    assertEquals(180, c.getAlpha());
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

    ElectricTrainColorDecorator deco = new ElectricTrainColorDecorator(wrapped, 200);
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

    Position pos = new Position(8, 12);
    when(wrapped.getPosition()).thenReturn(pos);
    when(wrapped.getName()).thenReturn("ElectricTrainX");

    ElectricTrainColorDecorator deco = Mockito.spy(new ElectricTrainColorDecorator(wrapped, 150));

    deco.update();
    verify(wrapped).update();

    assertEquals(pos, deco.getPosition());
    assertEquals("ElectricTrainX", deco.getName());
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

    ElectricTrainColorDecorator deco = new ElectricTrainColorDecorator(wrapped, 255);

    PrintStream out = mock(PrintStream.class);
    Passenger p = mock(Passenger.class);

    when(wrapped.loadPassenger(p)).thenReturn(4);
    when(wrapped.getCurrentCO2Emission()).thenReturn(40);
    when(wrapped.handleStop()).thenReturn(3);

    deco.report(out);
    verify(wrapped).report(out);

    assertEquals(4, deco.loadPassenger(p));
    verify(wrapped).loadPassenger(p);

    assertEquals(40, deco.getCurrentCO2Emission());
    verify(wrapped).getCurrentCO2Emission();

    assertEquals(3, deco.handleStop());
    verify(wrapped).handleStop();
  }
}
