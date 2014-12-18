
package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.model.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.PollingTask;

public class ElevatorSystemTest extends PollingTask
								implements Observer {

	private final int FLOOR_HEIGHT = 6;
	private final int FLOOR_NUM = 3;
	private final long CLOCK_TICK = 1000;
	private final int CAPACITY = 1001;
	
	private Object mLastObservable;
	private Object mObserverArgument;

	// Class under test
	private ElevatorSystem mSystem;
	private ElevatorConnectionShunt mShunt;

	@Before
	public void setUp() throws RemoteException, MalformedURLException, NotBoundException {
		mShunt = new ElevatorConnectionShunt(FLOOR_NUM, FLOOR_HEIGHT, CLOCK_TICK, CAPACITY);

		mSystem = new ElevatorSystem(mShunt);
		setElevatorSystem(mSystem);
		this.mConnection = mShunt;
		mSystem.addObserver(this);
		poll();
	}

	// Observer functions
	@Override
	public void update(Observable o, Object arg) {
		mLastObservable = o;
		mObserverArgument = arg;
	}
	
	// Polling task functions
	@Override
	public void startPolling(long period) {
		
	}
	
	public void poll() {
		run();
	}

	private IElevator checkObserverUpdate(int num, boolean changed)
	{
		if (!changed) {
			assertNull(mLastObservable);
			assertNull(mObserverArgument);
			return null;
		}
		assertEquals(new Integer(ElevatorSystem.ELEVATOR_PROPERTY_CHANGED), mObserverArgument);
		assertTrue(mLastObservable instanceof Elevator);

		Elevator elev = (Elevator) mLastObservable;
		assertEquals(num, elev.NUM);
		
		mLastObservable = null;
		mObserverArgument = null;
		
		return elev;
	}
	
	private void checkElevatorProperties(IElevator elev) throws FloorException {
		assertEquals(mShunt.Target, elev.getTargetFloor());
		assertEquals(mShunt.CommitedDirection, elev.getDirection());
		assertEquals(mShunt.ElevatorAccel, elev.getAcceleration());
		assertEquals(mShunt.Doorstatus, elev.getDoorstatus());
		assertEquals(mShunt.Floor, elev.getFloor());
		assertEquals(mShunt.Position, elev.getPosition());
		assertEquals(mShunt.Speed, elev.getSpeed());
		assertEquals(mShunt.Weight, elev.getWeight());

		for (int floor = 0; floor < FLOOR_NUM; floor++) {
			assertEquals(mShunt.ElevatorButton[floor], elev.getButtonStatus(floor));
			assertEquals(mShunt.ServicesFloors[floor], elev.getServicesFloors(floor));
		}
	}
	
	private void checkElevator(int num, boolean observer_expected) throws ElevatorException, FloorException {
		IElevator elev = checkObserverUpdate(num, observer_expected);
		if (elev != null) {
			checkElevatorProperties(elev);
		}

		elev = mSystem.getElevator(num);
		checkElevatorProperties(elev);
	}

	private void checkSystem (boolean observer_expected) throws FloorException {
		if (!observer_expected) {
			assertNull(mLastObservable);
			assertNull(mObserverArgument);
		} else {
			assertEquals(new Integer(ElevatorSystem.SYSTEM_PROPERTY_CHANGED), mObserverArgument);
			assertTrue(mLastObservable instanceof ElevatorSystem);
			mLastObservable = null;
			mObserverArgument = null;
		}
		
		for (int f = 0; f < mSystem.NUM_FLOORS; f++) {
			assertEquals(mShunt.FloorButtonUp[f], mSystem.getFloorButton(f, true));
			assertEquals(mShunt.FloorButtonDown[f], mSystem.getFloorButton(f, false));
		}
	}


	@Test
	public void testConstants() throws ElevatorException {
		assertEquals(1, mSystem.NUM_ELEVATORS);
		assertEquals(FLOOR_NUM, mSystem.NUM_FLOORS);
		assertEquals(FLOOR_HEIGHT, mSystem.FLOOR_HEIGHT);
        assertEquals(CAPACITY, mSystem.getElevator(0).CAPACITY);
	}
	
	@Test
	public void testElevatorDirection() throws ElevatorException, FloorException {
		mShunt.CommitedDirection = at.fhhagenberg.sqe.project.sqelevator.IElevator.ELEVATOR_DIRECTION_DOWN;
		poll();
		checkElevator(0,true);

		mShunt.CommitedDirection = at.fhhagenberg.sqe.project.sqelevator.IElevator.ELEVATOR_DIRECTION_UP;
		poll();
		checkElevator(0, true);

		mShunt.CommitedDirection = at.fhhagenberg.sqe.project.sqelevator.IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		poll();
		checkElevator(0, true);

		mShunt.CommitedDirection = at.fhhagenberg.sqe.project.sqelevator.IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		poll();
		checkElevator(0, false);
	}

	@Test
	public void testElevatorAcceleration() throws ElevatorException, FloorException {
		mShunt.ElevatorAccel = 100;
		poll();
		checkElevator(0, true);

		mShunt.ElevatorAccel = -1;
		poll();
		checkElevator(0, true);
		
		mShunt.ElevatorAccel = 0;
		poll();
		checkElevator(0, true);

		mShunt.ElevatorAccel = 0;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testElevatorButton() throws ElevatorException, FloorException {
		mShunt.ElevatorButton[0] = true;
		poll();
		checkElevator(0, true);

		mShunt.ElevatorButton[2] = true;
		poll();
		checkElevator(0, true);

		mShunt.ElevatorButton[1] = true;
		poll();
		checkElevator(0, true);

		mShunt.ElevatorButton[0] = false;
		poll();
		checkElevator(0, true);

		mShunt.ElevatorButton[1] = true;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testDoorStatus() throws ElevatorException, FloorException {
		mShunt.Doorstatus = at.fhhagenberg.sqe.project.sqelevator.IElevator.ELEVATOR_DOORS_OPENING;
		poll();
		checkElevator(0, true);

		mShunt.Doorstatus = at.fhhagenberg.sqe.project.sqelevator.IElevator.ELEVATOR_DOORS_CLOSING;
		poll();
		checkElevator(0, true);

		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testFloor() throws ElevatorException, FloorException {
		mShunt.Floor = 1;
		poll();
		checkElevator(0, true);
		
		mShunt.Floor = 2;
		poll();
		checkElevator(0, true);

		mShunt.Floor = 0;
		poll();
		checkElevator(0, true);

		mShunt.Floor = 0;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testPosition() throws ElevatorException, FloorException {
		mShunt.Position = 54;
		poll();
		checkElevator(0, true);

		mShunt.Position = 54;
		poll();
		checkElevator(0, false);

		mShunt.Position = 53;
		poll();
		checkElevator(0, true);
	}
	
	@Test
	public void testSpeed() throws ElevatorException, FloorException {
		mShunt.Speed = 99;
		poll();
		checkElevator(0, true);

		mShunt.Speed = 98;
		poll();
		checkElevator(0, true);

		mShunt.Speed = 98;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testWeight() throws ElevatorException, FloorException {
		mShunt.Weight = 412;
		poll();
		checkElevator(0, true);

		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testServicesFloors() throws ElevatorException, FloorException {
		mShunt.ServicesFloors[0] = false;
		poll();
		checkElevator(0, true);
		
		mShunt.ServicesFloors[1] = true;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testTarget() throws ElevatorException, FloorException {
		mShunt.Target = 2;
		poll();
		checkElevator(0, true);

		mShunt.Target = 1;
		poll();
		checkElevator(0, true);

		mShunt.Target = 0;
		poll();
		checkElevator(0, true);

		mShunt.Target = 0;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testFloorButtonException() {
		try {
			mSystem.getFloorButton(4, true);
			fail("No floor exception thrown");
		} catch (FloorException e) {
			assertEquals("Floor 4 is invalid", e.getMessage());
		}

		try {
			mSystem.getFloorButton(-1, true);
			fail("No floor exception thrown");
		} catch (FloorException e) {
			assertEquals("Floor -1 is invalid", e.getMessage());
		}
	}
	
	@Test
	public void testGetElevatorException() {
		try {
			mSystem.getElevator(2);
			fail("No elevator exception thrown");
		} catch (ElevatorException e) {
			assertEquals("Elevator 2 is invalid", e.getMessage());
		}

		try {
			mSystem.getElevator(-1);
			fail("No elevator exception thrown");
		} catch (ElevatorException e) {
			assertEquals("Elevator -1 is invalid", e.getMessage());
		}
	}
	
	@Test
	public void testGetFloorButton() throws FloorException {
		
		mShunt.FloorButtonDown[1] = true;
		poll();
		checkSystem(true);

		mShunt.FloorButtonDown[1] = false;
		poll();
		checkSystem(true);

		mShunt.FloorButtonUp[1] = false;
		poll();
		checkSystem(false);

		mShunt.FloorButtonUp[0] = true;
		poll();
		checkSystem(true);
	}

	private Method getProtectedMethod(Class<?> clazz, String name, Class<?>...classes) throws NoSuchMethodException, SecurityException {
		Method f = clazz.getDeclaredMethod(name, classes);
		f.setAccessible(true);
		return f;
	}
	
	@Test
	public void testSetDownButton() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FloorException {
		getProtectedMethod(mSystem.getClass(), "setDownButton", int.class, boolean.class).invoke(mSystem, 0, true);
		assertTrue(mSystem.getFloorButton(0, false));

		getProtectedMethod(mSystem.getClass(), "setDownButton", int.class, boolean.class).invoke(mSystem, 1, false);
		assertFalse(mSystem.getFloorButton(1, false));
	}

	@Test
	public void testSetUpButton() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FloorException {
		getProtectedMethod(mSystem.getClass(), "setUpButton", int.class, boolean.class).invoke(mSystem, 0, true);
		assertTrue(mSystem.getFloorButton(0, true));

		getProtectedMethod(mSystem.getClass(), "setUpButton", int.class, boolean.class).invoke(mSystem, 1, false);
		assertFalse(mSystem.getFloorButton(1, true));
	}

}
