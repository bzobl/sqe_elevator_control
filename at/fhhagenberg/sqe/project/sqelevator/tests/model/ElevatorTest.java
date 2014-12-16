/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;

public class ElevatorTest implements Observer {
	private final int FLOOR_NUM = 3;
	private final int CAPACITY = 1001;

	// Class under test
	private Elevator mElev = new Elevator(0, CAPACITY, FLOOR_NUM);

	private Method getProtectedMethod(String name, Class<?>...classes) throws NoSuchMethodException, SecurityException {
		Method f = mElev.getClass().getDeclaredMethod(name, classes);
		f.setAccessible(true);
		return f;
	}

	@Override
	public void update(Observable o, Object arg) {
		assertSame(mElev, o);
	}
	
	private void notifyAndCheck() {
		mElev.notifyObservers();
	}
	
	@Test
	public void testAccerleration() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setAcceleration", int.class).invoke(mElev, 99);
		assertEquals(99, mElev.getAcceleration());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setAcceleration", int.class).invoke(mElev, 99);
		assertEquals(99, mElev.getAcceleration());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testButtonStatus() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FloorException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setButtonStatus", int.class, boolean.class).invoke(mElev, 2, true);
		assertTrue(mElev.getButtonStatus(2));
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setButtonStatus", int.class, boolean.class).invoke(mElev, 2, true);
		assertTrue(mElev.getButtonStatus(2));
		assertFalse(mElev.hasChanged());
	}
	
	@Test
	public void testDirection() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setDirection", int.class).invoke(mElev, IElevator.ELEVATOR_DIRECTION_DOWN);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mElev.getDirection());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setDirection", int.class).invoke(mElev, IElevator.ELEVATOR_DIRECTION_DOWN);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mElev.getDirection());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testDoorstatus() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setDoorstatus", int.class).invoke(mElev, IElevator.ELEVATOR_DOORS_OPENING);
		assertEquals(IElevator.ELEVATOR_DOORS_OPENING, mElev.getDoorstatus());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setDoorstatus", int.class).invoke(mElev, IElevator.ELEVATOR_DOORS_OPENING);
		assertEquals(IElevator.ELEVATOR_DOORS_OPENING, mElev.getDoorstatus());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testFloor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setFloor", int.class).invoke(mElev, 1);
		assertEquals(1, mElev.getFloor());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setFloor", int.class).invoke(mElev, 1);
		assertEquals(1, mElev.getFloor());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testPosition() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setPosition", int.class).invoke(mElev, 15);
		assertEquals(15, mElev.getPosition());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setPosition", int.class).invoke(mElev, 15);
		assertEquals(15, mElev.getPosition());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testSpeed() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setSpeed", int.class).invoke(mElev, 150);
		assertEquals(150, mElev.getSpeed());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setSpeed", int.class).invoke(mElev, 150);
		assertEquals(150, mElev.getSpeed());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testTargetFloor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setTargetFloor", int.class).invoke(mElev, 1);
		assertEquals(1, mElev.getTargetFloor());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setTargetFloor", int.class).invoke(mElev, 1);
		assertEquals(1, mElev.getTargetFloor());
		assertFalse(mElev.hasChanged());
	}

	@Test
	public void testWeight() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		assertFalse(mElev.hasChanged());
		getProtectedMethod("setWeight", int.class).invoke(mElev, 700);
		assertEquals(700, mElev.getWeight());
		assertTrue(mElev.hasChanged());

		notifyAndCheck();

		getProtectedMethod("setWeight", int.class).invoke(mElev, 700);
		assertEquals(700, mElev.getWeight());
		assertFalse(mElev.hasChanged());
	}
}
