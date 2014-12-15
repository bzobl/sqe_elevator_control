
package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;

public class ElevatorTest {
	private final int FLOOR_NUM = 3;
	private final int CAPACITY = 1001;
	
	Elevator mElevator;
	
	@Before
	public void setUp() {
		mElevator = new Elevator(0, CAPACITY, FLOOR_NUM);
	}
	
	private Method getProtectedMethod(String name, Class<?>...classes) throws NoSuchMethodException, SecurityException {
		Method f = mElevator.getClass().getDeclaredMethod(name, classes);
		f.setAccessible(true);
		return f;
	}
	
	@Test
	public void testGetButtonStatusException()
	{
		try {
			mElevator.getButtonStatus(4);
			fail("No floor exception thrown");
		} catch (FloorException e) {
			assertEquals("Floor 4 is invalid", e.getMessage());
		}
	}

	@Test
	public void testSetButtonStatusException() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		try {
			getProtectedMethod("setButtonStatus", int.class, boolean.class).invoke(mElevator, 4, true);
			fail("No floor exception thrown");
		} catch (InvocationTargetException e) {
			assertTrue(e.getTargetException() instanceof FloorException);
			FloorException f = (FloorException) e.getTargetException();
			assertEquals("Floor 4 is invalid", f.getMessage());
		}
	}

	@Test
	public void testGetServicesFloorsException()
	{
		try {
			mElevator.getServicesFloors(4);
			fail("No floor exception thrown");
		} catch (FloorException e) {
			assertEquals("Floor 4 is invalid", e.getMessage());
		}
	}

	@Test
	public void testSetServicesFloorException() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		try {
			getProtectedMethod("setServicesFloors", int.class, boolean.class).invoke(mElevator, 4, true);
			fail("No floor exception thrown");
		} catch (InvocationTargetException e) {
			assertTrue(e.getTargetException() instanceof FloorException);
			FloorException f = (FloorException) e.getTargetException();
			assertEquals("Floor 4 is invalid", f.getMessage());
		}
	}

	@Test
	public void testSetFloorException() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		try {
			getProtectedMethod("setFloor", int.class).invoke(mElevator, 4);
			fail("No floor exception thrown");
		} catch (InvocationTargetException e) {
			assertTrue(e.getTargetException() instanceof FloorException);
			FloorException f = (FloorException) e.getTargetException();
			assertEquals("Floor 4 is invalid", f.getMessage());
		}
	}

	@Test
	public void testSetTargetFloorException() throws IllegalAccessException, IllegalArgumentException, NoSuchMethodException, SecurityException
	{
		try {
			getProtectedMethod("setTargetFloor", int.class).invoke(mElevator, 4);
			fail("No floor exception thrown");
		} catch (InvocationTargetException e) {
			assertTrue(e.getTargetException() instanceof FloorException);
			FloorException f = (FloorException) e.getTargetException();
			assertEquals("Floor 4 is invalid", f.getMessage());
		}
	}
}
