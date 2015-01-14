package at.fhhagenberg.sqe.project.sqelevator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorConnectionTestShunt;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystemTestWrapper;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorTestWrapper;

public class ManualElevatorAlgoTest
{
	private ElevatorConnectionTestShunt mElevatorConnectionShunt;
	private ElevatorSystemTestWrapper mElevatorSystem;
	
	private ManualElevatorAlgorithm mManualAlgo;
	
	@Before
	public void setUp() throws Exception
	{
		mElevatorConnectionShunt = new ElevatorConnectionTestShunt(2, 1, 1);
		mElevatorSystem = new ElevatorSystemTestWrapper(mElevatorConnectionShunt);
		mManualAlgo = new ManualElevatorAlgorithm(mElevatorSystem, mElevatorConnectionShunt);
	}

	@Test
	public void testInitState() throws Exception
	{
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mElevatorConnectionShunt.getCommittedDirection(0));
		assertEquals(0, mElevatorSystem.getElevator(0).getFloor());
	}
	
	@Test
	public void testSetRequestInCurrentFloor()
	{
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mElevatorConnectionShunt.getCommittedDirection(0));
		mManualAlgo.setElevatorRequest(0, 0);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mElevatorConnectionShunt.getCommittedDirection(0));
	}
	
	@Test
	public void testSetRequestUp()
	{
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mElevatorConnectionShunt.getCommittedDirection(0));
		mManualAlgo.setElevatorRequest(0, 1);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mElevatorConnectionShunt.getCommittedDirection(0));
	}

	@Test
	public void testSetRequestDown() throws Exception
	{
		((ElevatorTestWrapper)mElevatorSystem.getElevator(0)).setFloor(1);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mElevatorConnectionShunt.getCommittedDirection(0));
		mManualAlgo.setElevatorRequest(0, 0);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mElevatorConnectionShunt.getCommittedDirection(0));
	}
	
	@Test
	public void testElevatorException()
	{
		// must not throw an elevator exception
		mManualAlgo.setElevatorRequest(2, 0);
	}

	@Test
	public void testElevatorRequestAssertions()
	{
		try {
			mManualAlgo.setElevatorRequest(0, -1);
			fail();
		} catch (AssertionError e) {
		}

		try {
			mManualAlgo.setElevatorRequest(0, 5);
			fail();
		} catch (AssertionError e) {
		}
	}
	
	@Test
	public void testFloorRequestAssertion()
	{
		try {
			mManualAlgo.setFloorRequest(0, true);
			fail();
		} catch (AssertionError e) {
		}
	}
}
