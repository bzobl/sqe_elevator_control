
package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
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

		mSystem = new ElevatorSystem(mShunt, this);
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

	private Elevator checkObserverUpdate(int num, boolean changed)
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
	
	private void checkElevatorProperties(Elevator elev) {
		assertEquals(mShunt.Target, elev.getTargetFloor());
		assertEquals(mShunt.CommitedDirection, elev.getDirection());
		assertEquals(mShunt.ElevatorAccel, elev.getAcceleration());
		assertEquals(mShunt.Doorstatus, elev.getDoorstatus());
		assertEquals(mShunt.Floor, elev.getFloor());
		assertEquals(mShunt.Position, elev.getPosition());
		assertEquals(mShunt.Speed, elev.getSpeed());
		assertEquals(mShunt.Weight, elev.getWeight());

		try {
			for (int floor = 0; floor < FLOOR_NUM; floor++) {
				assertEquals(mShunt.ElevatorButton[floor], elev.getButtonStatus(floor));
				assertEquals(mShunt.ServicesFloors[floor], elev.getServicesFloors(floor));
			}
		} catch (FloorException e) {
			fail("caught Floor Exception: " + e.getMessage());
		}
	}
	
	private void checkElevator(int num, boolean observer_expected) {
		Elevator elev = checkObserverUpdate(num, observer_expected);
		if (elev != null) {
			checkElevatorProperties(elev);
		}

		try {
			elev = mSystem.getElevator(num);
			checkElevatorProperties(elev);
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}


	@Test
	public void testConstants() {
		assertEquals(1, mSystem.NUM_ELEVATORS);
		assertEquals(FLOOR_NUM, mSystem.NUM_FLOORS);
		assertEquals(FLOOR_HEIGHT, mSystem.FLOOR_HEIGHT);
		try {
			assertEquals(CAPACITY, mSystem.getElevator(0).CAPACITY);
		} catch (ElevatorException e) {
			fail("Elevator Exception thrown: " + e);
		}
	}
	
	@Test
	public void testElevatorDirection() {
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_DOWN;
		poll();
		checkElevator(0,true);

		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UP;
		poll();
		checkElevator(0, true);

		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		poll();
		checkElevator(0, true);

		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		poll();
		checkElevator(0, false);
	}

	@Test
	public void testElevatorAcceleration() {
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
	public void testElevatorButton() {
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
	public void testDoorStatus() {
		mShunt.Doorstatus = IElevator.ELEVATOR_DOORS_OPENING;
		poll();
		checkElevator(0, true);

		mShunt.Doorstatus = IElevator.ELEVATOR_DOORS_CLOSING;
		poll();
		checkElevator(0, true);

		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testFloor() {
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
	public void testPosition() {
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
	public void testSpeed() {
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
	public void testWeight() {
		mShunt.Weight = 412;
		poll();
		checkElevator(0, true);

		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testServicesFloors() {
		mShunt.ServicesFloors[0] = false;
		poll();
		checkElevator(0, true);
		
		mShunt.ServicesFloors[1] = true;
		poll();
		checkElevator(0, false);
	}
	
	@Test
	public void testTarget() {
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
	public void testSetTarget() {
		mSystem.setTarget(0, 1);
		assertEquals(1, mShunt.SetTarget);

		mSystem.setTarget(0, 2);
		assertEquals(2, mShunt.SetTarget);

		mSystem.setTarget(0, 0);
		assertEquals(0, mShunt.SetTarget);
	}
	
	@Test
	public void testSetServicesFloor() {
		mSystem.setServicesFloors(0, 1, false);
		assertFalse(mShunt.SetServicesFloor[1]);

		mSystem.setServicesFloors(0, 1, true);
		assertTrue(mShunt.SetServicesFloor[1]);

		mSystem.setServicesFloors(0, 2, true);
		assertTrue(mShunt.SetServicesFloor[2]);
	}
	
	@Test
	public void testSetCommitedDirection() {
		mSystem.setCommitedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.SetCommitedDirection);

		mSystem.setCommitedDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mShunt.SetCommitedDirection);

		mSystem.setCommitedDirection(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.SetCommitedDirection);
	}
	
	@Test
	public void testFloorButtonException() {
		try {
			mSystem.getFloorButton(4, true);
			fail("No floor exception thrown");
		} catch (FloorException e) {
			assertEquals("Floor 4 is invalid", e.getMessage());
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
	}
}
