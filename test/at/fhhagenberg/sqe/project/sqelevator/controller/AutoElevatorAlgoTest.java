/**
 * Project: Elevator_Control_Center
 * Author:  Bernhard Raab
 *          S1310567022
 */
package at.fhhagenberg.sqe.project.sqelevator.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorConnectionTestShunt;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystemTestWrapper;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorTestWrapper;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;

public class AutoElevatorAlgoTest {
	private final int HEIGHT = 10;
	private final int CAPACITY = 1001;
	
	private IElevatorAlgorithm mAutoAlgo;
	private ElevatorConnectionTestShunt mShunt;
	private IElevatorSystem mSys;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1ElevatorRequest() throws FloorException, ElevatorException {
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setButtonStatus(1, true);
		mAutoAlgo.setElevatorRequest(0, 1);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(1, mShunt.SetTarget);
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setButtonStatus(1, false);
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(1);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(1);

		((ElevatorTestWrapper)mSys.getElevator(0)).setButtonStatus(4, true);
		mAutoAlgo.setElevatorRequest(0, 4);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(4, mShunt.SetTarget);
	}

	@Test
	public void test1ElevatorFloorRequest() throws FloorException, ElevatorException {
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
	
		mAutoAlgo.setFloorRequest(1, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(1,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(1);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(1);
		
		mAutoAlgo.setFloorRequest(3, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(3,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(3);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(3);
		
		mAutoAlgo.setFloorRequest(4, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(4,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(4);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(4);

		mAutoAlgo.setFloorRequest(1, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mShunt.CommitedDirection);
		assertEquals(1,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(1);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(1);
		
		mAutoAlgo.setFloorRequest(0, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(0);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(0);

		mAutoAlgo.setFloorRequest(4, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(4,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(4);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(4);

		mAutoAlgo.setFloorRequest(0, false);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(0);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(0);

		mAutoAlgo.setFloorRequest(4, false);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(4,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(4);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(4);
	}

	@Test
	public void test1ElevatorMovingFloorRequest() throws FloorException, ElevatorException {
		/* test floor request of moving elevator
		 */
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
		
		// request from forth floor
		mAutoAlgo.setFloorRequest(4, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(4,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(4);	// change model
		
		// simulate elevator, moving up
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(1);	
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(2);	
		
		// now request from third floor
		mAutoAlgo.setFloorRequest(3, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		// previous target should be overwritten
		assertEquals(3,  mShunt.SetTarget);
	}
	
	@Test
	public void test1ElevatorsInFloorFloorRequest() throws FloorException, ElevatorException {
		/* test floor request, when elevator is already in requested floor
		 */
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
		
		mAutoAlgo.setFloorRequest(0, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(0);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(0);
	}
	
	
	@Test
	public void test1ElevatorAlreadyInFloorFloorRequest() throws FloorException, ElevatorException {
		/* test floor request of moving elevator
		 */
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
		
		// request lowest floor
		mAutoAlgo.setFloorRequest(0, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(0);	// change model
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(4);	
		mShunt.SetTarget = 4;
		
		// request highest floor
		mAutoAlgo.setFloorRequest(4, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(4,  mShunt.SetTarget);
	}
	
	
	@Test
	public void test1ElevatorDisabledRequest() throws FloorException, ElevatorException {
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, false);
		
		try {
			mAutoAlgo.setElevatorRequest(0, 0);
			fail();
		} catch (AssertionError e) {
			
		}
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0, mShunt.SetTarget);
		
		try {
			mAutoAlgo.setElevatorRequest(0, 4);
			fail();
		} catch (AssertionError e) {
			
		}			
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0, mShunt.SetTarget);
	}
	
	
	@Test
	public void test1ElevatorDisabledFloorRequest() throws FloorException, ElevatorException {
		/* test floor request of moving elevator
		 */
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, false);
		
		// request lowest floor
		mAutoAlgo.setFloorRequest(0, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		
		mAutoAlgo.setFloorRequest(0, false);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		
		// request highest floor
		mAutoAlgo.setFloorRequest(4, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
		
		mAutoAlgo.setFloorRequest(4, false);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(0,  mShunt.SetTarget);
	}
	
	
	@Test
	public void test1ElevatorFloorNotServicedRequest() throws FloorException, ElevatorException {
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
		mShunt.setServicesFloors(0, 4, false);
		((ElevatorTestWrapper)mSys.getElevator(0)).setServicesFloors(4, false);
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setButtonStatus(1, true);
		mAutoAlgo.setElevatorRequest(0, 1);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(1, mShunt.SetTarget);
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(1);	// change model
		((ElevatorTestWrapper)mSys.getElevator(0)).setTargetFloor(1);		
		
		// floor 4 is not serviced
		mAutoAlgo.setElevatorRequest(0, 4);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(1, mShunt.SetTarget);
	}
	
	
	@Test
	public void test1ElevatorFloorNotServicedFloorRequest() throws FloorException, ElevatorException {
		/* test floor request of moving elevator
		 */
		// setup
		final int NUM_ELEVATORS = 1;
		final int NUM_FLOORS = 5;
		IElevatorConnection status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		mSys = new ElevatorSystemTestWrapper(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(mSys, mShunt);
		
		// test
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
		mAutoAlgo.enableElevator(0, true);
		mShunt.setServicesFloors(0, 2, false);
		mShunt.setServicesFloors(0, 4, false);
		((ElevatorTestWrapper)mSys.getElevator(0)).setServicesFloors(2, false);
		((ElevatorTestWrapper)mSys.getElevator(0)).setServicesFloors(4, false);
		
		// request floor 1
		mAutoAlgo.setFloorRequest(1, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(1,  mShunt.SetTarget);
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(1);	
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.SetTarget = 1;
		
		// floor 2 is not serviced
		mAutoAlgo.setFloorRequest(2, false);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(1,  mShunt.SetTarget);
		
		// request floor 3
		mAutoAlgo.setFloorRequest(3, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
		assertEquals(3,  mShunt.SetTarget);		
		
		((ElevatorTestWrapper)mSys.getElevator(0)).setFloor(3);	
		mShunt.CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.SetTarget = 3;
		
		// floor 4 is not serviced
		mAutoAlgo.setFloorRequest(4, false);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mShunt.CommitedDirection);
		assertEquals(3,  mShunt.SetTarget);
	}
}
