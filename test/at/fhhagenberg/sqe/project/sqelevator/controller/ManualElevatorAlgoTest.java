package at.fhhagenberg.sqe.project.sqelevator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorConnectionTestShunt;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystemTestWrapper;

public class ManualElevatorAlgoTest
{
	private ElevatorConnectionTestShunt mElevatorConnectionShunt;
	private ElevatorSystemTestWrapper mElevatorSystem;
	
	private ManualElevatorAlgorithm mManualAlgo;
	
	private void setCurrentFloor(Elevator el, int num)
	{
		try
		{
			Field field = Elevator.class.getDeclaredField("mFloor");
			field.setAccessible(true);
			field.set(el, num);
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
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
		setCurrentFloor((Elevator)mElevatorSystem.getElevator(0), 1);
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mElevatorConnectionShunt.getCommittedDirection(0));
		mManualAlgo.setElevatorRequest(0, 0);
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, mElevatorConnectionShunt.getCommittedDirection(0));
	}
	
}
