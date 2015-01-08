
package at.fhhagenberg.sqe.project.sqelevator.model;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;

public final class ElevatorConnectionTestShunt implements IElevatorStatus, IElevatorControl {
	private final int FLOOR_HEIGHT;
	private final int FLOOR_NUM;
	private final long CLOCK_TICK;
	private final int CAPACITY;
	
	public int NUM_ELEVATORS = 1;
	
	// Shunt variables
	public int CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
	public int Doorstatus = IElevator.ELEVATOR_DOORS_OPEN;
	public int ElevatorAccel = 0;
	public int Floor = 0;
	public int Position = 0;
	public int Speed = 0;
	public int Target = 0;
	public int Weight = 0;
	public boolean[] FloorButtonDown;
	public boolean[] FloorButtonUp;
	public boolean[] ServicesFloors;
	public boolean[] ElevatorButton;

	public int SetCommitedDirection;
	public boolean[] SetServicesFloor;
	public int SetTarget;
	
	public ElevatorConnectionTestShunt(int floors, int height, long period, int capacity) {
		FLOOR_NUM = floors;
		FLOOR_HEIGHT = height;
		CLOCK_TICK = period;
		CAPACITY = capacity;
		
		ElevatorButton = new boolean[FLOOR_NUM];
		FloorButtonDown = new boolean[FLOOR_NUM];
		FloorButtonUp = new boolean[FLOOR_NUM];
		ServicesFloors = new boolean[FLOOR_NUM];
		SetServicesFloor = new boolean[FLOOR_NUM];
		SetCommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		
		for (int i = 0; i < floors; i++) {
			FloorButtonDown[i] = false;
			FloorButtonUp[i] = false;
			ElevatorButton[i] = false;
			ServicesFloors[i] = true;
		}
	}
	
	@Override
	public int getCommittedDirection(int elevatorNumber) {
		return CommitedDirection;
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) {
		return ElevatorAccel;
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) {
		return ElevatorButton[floor];
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) {
		return Doorstatus;
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) {
		return Floor;
	}

	@Override
	public int getElevatorNum() {
		return NUM_ELEVATORS;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) {
		return Position;
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) {
		return Speed;
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) {
		return Weight;
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) {
		return CAPACITY;
	}

	@Override
	public boolean getFloorButtonDown(int floor) {
		return FloorButtonDown[floor];
	}

	@Override
	public boolean getFloorButtonUp(int floor) {
		return FloorButtonUp[floor];
	}

	@Override
	public int getFloorHeight() {
		return FLOOR_HEIGHT;
	}

	@Override
	public int getFloorNum() {
		return FLOOR_NUM;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) {
		return ServicesFloors[floor];
	}

	@Override
	public int getTarget(int elevatorNumber) {
		return Target;
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) {
		SetCommitedDirection = direction;
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
		SetServicesFloor[floor] = service;
	}

	@Override
	public void setTarget(int elevatorNumber, int target) {
		SetTarget = target;
	}

	@Override
	public long getClockTick() {
		return CLOCK_TICK;
	}
}
