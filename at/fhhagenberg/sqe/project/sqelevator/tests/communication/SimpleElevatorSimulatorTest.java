/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.tests.communication;

import static org.junit.Assert.*;

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
	
	private void moveElevatorToFloor(int elevator, int start, int floor) {
		assertEquals(start, mSim.getElevatorFloor(elevator));
		assertEquals(start, mSim.getTarget(elevator));
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, mSim.getElevatorDoorStatus(elevator));

		mSim.setTarget(elevator, floor);
		mSim.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_UP);

		assertEquals(floor, mSim.getTarget(elevator));
		
		//every call of getElevatorFloor() triggers the move action to increase the delay
		assertEquals(start, mSim.getElevatorFloor(elevator));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSING, mSim.getElevatorDoorStatus(elevator));

		assertEquals(start, mSim.getElevatorFloor(elevator));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, mSim.getElevatorDoorStatus(elevator));

		assertEquals(floor, mSim.getElevatorFloor(elevator));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, mSim.getElevatorDoorStatus(elevator));
		
		assertEquals(floor, mSim.getElevatorFloor(elevator));
		assertEquals(IElevator.ELEVATOR_DOORS_OPENING, mSim.getElevatorDoorStatus(elevator));

		assertEquals(floor, mSim.getElevatorFloor(elevator));
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, mSim.getElevatorDoorStatus(elevator));

		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mSim.getCommittedDirection(elevator));
	}
	
	@Test
	public void testMovement() {
		moveElevatorToFloor(0, 0, 3);
	}
	
	@Test
	public void testTargetOnSameFloor() {
		moveElevatorToFloor(1, 0, 0);
	}
	
	@Test
	public void testWrongCommitedDirectionUp() {
		moveElevatorToFloor(0, 0, 2);
		
		mSim.setTarget(0, 3);
		mSim.setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_DOWN);

		assertEquals(2, mSim.getElevatorFloor(0));
		assertEquals(2, mSim.getElevatorFloor(0));
		assertEquals(2, mSim.getElevatorFloor(0));
		assertEquals(2, mSim.getElevatorFloor(0));
		assertEquals(2, mSim.getElevatorFloor(0));

		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mSim.getCommittedDirection(0));
	}
	
	@Test
	public void testWrongCommitedDirectionDown() {
		moveElevatorToFloor(2, 0, 2);
		
		mSim.setTarget(2, 1);
		mSim.setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UP);

		assertEquals(2, mSim.getElevatorFloor(2));
		assertEquals(2, mSim.getElevatorFloor(2));
		assertEquals(2, mSim.getElevatorFloor(2));
		assertEquals(2, mSim.getElevatorFloor(2));
		assertEquals(2, mSim.getElevatorFloor(2));

		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, mSim.getCommittedDirection(2));
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
	
	@Test
	public void testAcceleration() {
		assertEquals(0, mSim.getElevatorAccel(0));
	}
	
	@Test
	public void testElevatorButton() {
		assertFalse(mSim.getElevatorButton(0, 1));
		assertFalse(mSim.getElevatorButton(0, 2));
		assertFalse(mSim.getElevatorButton(0, 4));
		assertFalse(mSim.getElevatorButton(1, 0));
	}
	
	@Test
	public void testSpeed() {
		assertEquals(0, mSim.getElevatorSpeed(0));
	}

	@Test
	public void testWeight() {
		assertEquals(0, mSim.getElevatorWeight(0));
	}

	@Test
	public void testCapacity() {
		assertEquals(1000, mSim.getElevatorCapacity(0));
	}

	@Test
	public void testButtonDown() {
		assertFalse(mSim.getFloorButtonDown(0));
		assertFalse(mSim.getFloorButtonDown(1));
		assertFalse(mSim.getFloorButtonDown(2));
		assertFalse(mSim.getFloorButtonDown(3));
		assertFalse(mSim.getFloorButtonDown(4));
	}

	@Test
	public void testButtonUp() {
		assertFalse(mSim.getFloorButtonUp(0));
		assertFalse(mSim.getFloorButtonUp(1));
		assertFalse(mSim.getFloorButtonUp(2));
		assertFalse(mSim.getFloorButtonUp(3));
		assertFalse(mSim.getFloorButtonUp(4));
	}
	
	@Test
	public void testSetServicesFloors() {
		assertTrue(mSim.getServicesFloors(0, 1));
		mSim.setServicesFloors(0, 1, false);
		assertFalse(mSim.getServicesFloors(0, 1));
	}

	@Test
	public void testServicesFloors() {
		assertTrue(mSim.getServicesFloors(0, 0));
		assertTrue(mSim.getServicesFloors(1, 1));
		assertTrue(mSim.getServicesFloors(2, 2));
		assertTrue(mSim.getServicesFloors(0, 3));
		assertTrue(mSim.getServicesFloors(1, 4));
	}
	
	@Test
	public void testClockTick() {
		assertEquals(SimpleElevatorSimulator.CLOCK_TICK, mSim.getClockTick());
	}
}
