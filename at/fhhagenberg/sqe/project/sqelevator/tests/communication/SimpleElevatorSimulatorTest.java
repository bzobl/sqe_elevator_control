/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.tests.communication;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;

/** SimpleElevatorSimulatorTest
 * 
 */
public class SimpleElevatorSimulatorTest {
	
	SimpleElevatorSimulator mSim = new SimpleElevatorSimulator(3, 5);

	@Test
	public void testNumElevators() {
		assertEquals(3, mSim.getElevatorNum());
	}

	@Test
	public void testNumFloors() {
		assertEquals(5, mSim.getFloorNum());
	}
	
	@Test
	public void testMovementConstants() {
		assertEquals(1, SimpleElevatorSimulator.DELAY_CLOSING);
		assertEquals(2, SimpleElevatorSimulator.DELAY_CLOSED);
		assertEquals(3, SimpleElevatorSimulator.DELAY_MOVE);
		assertEquals(4, SimpleElevatorSimulator.DELAY_OPENING);
		assertEquals(5, SimpleElevatorSimulator.DELAY_DONE);
	}
	
	@Test
	public void testMovement() {
		assertEquals(0, mSim.getElevatorFloor(0));
		assertEquals(0, mSim.getTarget(0));
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, mSim.getElevatorDoorStatus(0));
		
		mSim.setTarget(0, 3);
		mSim.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);
		
		assertEquals(3, mSim.getTarget(0));
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, mSim.getCommittedDirection(0));
		
		//every call of getElevatorFloor() triggers the move action to increase the delay
		assertEquals(0, mSim.getElevatorFloor(0));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSING, mSim.getElevatorDoorStatus(0));

		assertEquals(0, mSim.getElevatorFloor(0));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, mSim.getElevatorDoorStatus(0));

		assertEquals(3, mSim.getElevatorFloor(0));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, mSim.getElevatorDoorStatus(0));
		
		assertEquals(3, mSim.getElevatorFloor(0));
		assertEquals(IElevator.ELEVATOR_DOORS_OPENING, mSim.getElevatorDoorStatus(0));

		assertEquals(3, mSim.getElevatorFloor(0));
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, mSim.getElevatorDoorStatus(0));

		mSim.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
	}
	
	@Test
	public void testPosition() {
		assertEquals(6, mSim.getFloorHeight());

		assertEquals(0, mSim.getElevatorPosition(2));
		assertEquals(0, mSim.getElevatorFloor(2));
		
		mSim.setTarget(2, 3);
		mSim.setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UP);
		
		assertEquals(5, SimpleElevatorSimulator.DELAY_DONE);
		assertEquals(0, mSim.getElevatorFloor(2));
		assertEquals(0, mSim.getElevatorFloor(2));
		assertEquals(3, mSim.getElevatorFloor(2));
		assertEquals(3, mSim.getElevatorFloor(2));
		assertEquals(3, mSim.getElevatorFloor(2));
		
		assertEquals(18, mSim.getElevatorPosition(2));
	}

}
