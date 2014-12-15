
package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

	Elevator checkElevator(int num)
	{
		assertEquals(new Integer(ElevatorSystem.ELEVATOR_PROPERTY_CHANGED), mObserverArgument);
		assertTrue(mLastObservable instanceof Elevator);

		Elevator elev = (Elevator) mLastObservable;
		assertEquals(num, elev.NUM);
		
		return elev;
	}
	
	private void checkDirection(int num, int direction) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(direction, mSystem.getDirection(num));
			assertEquals(direction, elev.getDirection());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}
	
	private void checkAcceleration(int num, int accel) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(accel, mSystem.getAcceleration(num));
			assertEquals(accel, elev.getAcceleration());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}

	private void checkElevatorButton(int num, int floor, boolean pressed) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(pressed, mSystem.getButtonStatus(num, floor));
			assertEquals(pressed, elev.getButtonStatus(floor));
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		} catch (FloorException e) {
			fail("caught Floor Exception: " + e.getMessage());
		}
	}

	private void checkElevatorDoorstatus(int num, int status) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(status, mSystem.getDoorstatus(num));
			assertEquals(status, elev.getDoorstatus());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}

	private void checkElevatorFloor(int num, int floor) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(floor, mSystem.getFloor(num));
			assertEquals(floor, elev.getFloor());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}

	private void checkElevatorPosition(int num, int position) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(position, mSystem.getPosition(num));
			assertEquals(position, elev.getPosition());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}

	private void checkElevatorWeight(int num, int weight) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(weight, mSystem.getWeight(num));
			assertEquals(weight, elev.getWeight());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}

	private void checkElevatorSpeed(int num, int speed) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(speed, mSystem.getSpeed(num));
			assertEquals(speed, elev.getSpeed());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}
	
	private void checkElevatorServices(int num, int floor, boolean enabled) {
		Elevator elev = checkElevator(num);

		//TODO test serviced floors from system
		assertEquals(enabled, elev.getServicesFloors(floor));
	}

	private void checkElevatorTarget(int num, int floor) {
		Elevator elev = checkElevator(num);

		try {
			assertEquals(floor, mSystem.getTargetFloor(num));
			assertEquals(floor, elev.getTargetFloor());
		} catch (ElevatorException e) {
			fail("caught Elevator Exception: " + e.getMessage());
		}
	}

	@Test
	public void testConstants() {
		assertEquals(1, mSystem.getNumElevators());
		assertEquals(FLOOR_HEIGHT, mSystem.getFloorHeight());
		assertEquals(FLOOR_NUM, mSystem.getNumFloors());
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
		checkDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);

		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UP;
		poll();
		checkDirection(0, IElevator.ELEVATOR_DIRECTION_UP);

		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		poll();
		checkDirection(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
	}

	@Test
	public void testElevatorAcceleration() {
		mShunt.ElevatorAccel = 100;
		poll();
		checkAcceleration(0, 100);

		mShunt.ElevatorAccel = -1;
		poll();
		checkAcceleration(0, -1);
		
		mShunt.ElevatorAccel = 0;
		poll();
		checkAcceleration(0, 0);
	}
	
	@Test
	public void testElevatorButton() {
		mShunt.ElevatorButton[0] = true;
		poll();
		checkElevatorButton(0, 0, true);

		mShunt.ElevatorButton[2] = true;
		poll();
		checkElevatorButton(0, 2, true);

		mShunt.ElevatorButton[1] = true;
		poll();
		checkElevatorButton(0, 1, true);

		mShunt.ElevatorButton[0] = false;
		poll();
		checkElevatorButton(0, 0, false);
	}
	
	@Test
	public void testDoorStatus() {
		mShunt.Doorstatus = IElevator.ELEVATOR_DOORS_OPENING;
		poll();
		checkElevatorDoorstatus(0, IElevator.ELEVATOR_DOORS_OPENING);

		mShunt.Doorstatus = IElevator.ELEVATOR_DOORS_CLOSING;
		poll();
		checkElevatorDoorstatus(0, IElevator.ELEVATOR_DOORS_CLOSING);
	}
	
	@Test
	public void testFloor() {
		mShunt.Floor = 1;
		poll();
		checkElevatorFloor(0, 1);
		
		mShunt.Floor = 2;
		poll();
		checkElevatorFloor(0, 2);

		mShunt.Floor = 0;
		poll();
		checkElevatorFloor(0, 0);
	}
	
	@Test
	public void testPosition() {
		mShunt.Position = 54;
		poll();
		checkElevatorPosition(0, 54);
	}
	
	@Test
	public void testSpeed() {
		mShunt.Speed = 99;
		poll();
		checkElevatorSpeed(0, 99);
	}
	
	@Test
	public void testWeight() {
		mShunt.Weight = 412;
		poll();
		checkElevatorWeight(0, 412);
	}
	
	@Test
	public void testServicesFloors() {
		mShunt.ServicesFloors[0] = true;
		poll();
		checkElevatorServices(0, 0, true);
		
	}
	
	@Test
	public void testTarget() {
		mShunt.Target = 2;
		poll();
		checkElevatorTarget(0, 2);

		mShunt.Target = 1;
		poll();
		checkElevatorTarget(0, 1);

		mShunt.Target = 0;
		poll();
		checkElevatorTarget(0, 0);
	}
}
