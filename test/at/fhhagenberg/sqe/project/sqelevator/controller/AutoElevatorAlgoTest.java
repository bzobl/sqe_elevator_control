package at.fhhagenberg.sqe.project.sqelevator.controller;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorConnectionTestShunt;

public class AutoElevatorAlogTest {
	
	private final int NUM_ELEVATORS = 1;
	private final int NUM_FLOORS = 5;
	private final int HEIGHT = 10;
	private final long PERIOD = 10;
	private final int CAPACITY = 1001;
	
	private IElevatorAlgorithm mAutoAlgo;
	private ElevatorConnectionTestShunt mShunt;

	@Before
	public void setUp() throws Exception {

		IElevatorStatus status = new SimpleElevatorSimulator(NUM_ELEVATORS, NUM_FLOORS);
		IElevatorSystem sys = new ElevatorSystem(status);
		mShunt = new ElevatorConnectionTestShunt(NUM_FLOORS, HEIGHT, PERIOD, CAPACITY);
		mAutoAlgo = new AutoElevatorAlgorithm(sys, mShunt);
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testSingleElevator() {
//		mShunt.CommitedDirection = sqelevator.IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
//		mShunt.Floor = 0;
//		
//		mAutoAlog.setElevatorRequest(0, 1);
//		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
//		assertEquals(1, mShunt.Target);
//	}
	@Test
	public void testSingleFloorRequest() {
		mShunt.CommitedDirection = sqelevator.IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		mShunt.Floor = 0;
	
		mAutoAlgo.setFloorRequest(1, true);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mShunt.CommitedDirection);
	}

}
