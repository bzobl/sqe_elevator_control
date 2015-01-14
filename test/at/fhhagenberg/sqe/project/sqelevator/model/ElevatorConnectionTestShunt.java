
package at.fhhagenberg.sqe.project.sqelevator.model;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;

public final class ElevatorConnectionTestShunt implements IElevatorConnection {
	private final int FLOOR_HEIGHT;
	private final int FLOOR_NUM;
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
	public long ClockTick = 0;
	public boolean[] FloorButtonDown;
	public boolean[] FloorButtonUp;
	public boolean[] ServicesFloors;
	public boolean[] ElevatorButton;

	public boolean[] SetServicesFloor;
	public int SetTarget;
	
	public boolean IsConnected;
	public boolean Connect;
	
	public ElevatorConnectionTestShunt(int floors, int height, int capacity) {
		FLOOR_NUM = floors;
		FLOOR_HEIGHT = height;
		CAPACITY = capacity;
		
		ElevatorButton = new boolean[FLOOR_NUM];
		FloorButtonDown = new boolean[FLOOR_NUM];
		FloorButtonUp = new boolean[FLOOR_NUM];
		ServicesFloors = new boolean[FLOOR_NUM];
		SetServicesFloor = new boolean[FLOOR_NUM];
		CommitedDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		
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
		CommitedDirection = direction;
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
		return ClockTick;
	}

	@Override
	public boolean isConnected()
	{
		return IsConnected;
	}

	@Override
	public boolean connect()
	{
		Connect = true;
		return Connect;
	}

	@Override
	public String getConnectionName()
	{
		return "Shunt";
	}
}
