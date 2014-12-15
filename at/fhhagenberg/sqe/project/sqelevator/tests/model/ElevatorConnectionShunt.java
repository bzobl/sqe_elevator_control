
package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

public final class ElevatorConnectionShunt implements IElevator {
	private final int FLOOR_HEIGHT;
	private final int FLOOR_NUM;
	private final long CLOCK_TICK;
	private final int CAPACITY;
	
	public int NUM_ELEVATORS = 1;
	
	// Self shunt variables
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
	
	public ElevatorConnectionShunt(int floors, int height, long period, int capacity) {
		FLOOR_NUM = floors;
		FLOOR_HEIGHT = height;
		CLOCK_TICK = period;
		CAPACITY = capacity;
		
		ElevatorButton = new boolean[FLOOR_NUM];
		FloorButtonDown = new boolean[FLOOR_NUM];
		FloorButtonUp = new boolean[FLOOR_NUM];
		ServicesFloors = new boolean[FLOOR_NUM];
		SetServicesFloor = new boolean[FLOOR_NUM];

		for (int i = 0; i < floors; i++) {
			FloorButtonDown[i] = false;
			FloorButtonUp[i] = false;
			ElevatorButton[i] = false;
			ServicesFloors[i] = true;
		}
	}
	
	@Override
	public int getCommittedDirection(int elevatorNumber) throws RemoteException {
		return CommitedDirection;
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) throws RemoteException {
		return ElevatorAccel;
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor)
			throws RemoteException {
		return ElevatorButton[floor];
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
		return Doorstatus;
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws RemoteException {
		return Floor;
	}

	@Override
	public int getElevatorNum() throws RemoteException {
		return NUM_ELEVATORS;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) throws RemoteException {
		return Position;
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
		return Speed;
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws RemoteException {
		return Weight;
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
		return CAPACITY;
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws RemoteException {
		return FloorButtonDown[floor];
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws RemoteException {
		return FloorButtonUp[floor];
	}

	@Override
	public int getFloorHeight() throws RemoteException {
		return FLOOR_HEIGHT;
	}

	@Override
	public int getFloorNum() throws RemoteException {
		return FLOOR_NUM;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor)
			throws RemoteException {
		return ServicesFloors[floor];
	}

	@Override
	public int getTarget(int elevatorNumber) throws RemoteException {
		return Target;
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction)
			throws RemoteException {
		SetCommitedDirection = direction;
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service)
			throws RemoteException {
		SetServicesFloor[floor] = service;
	}

	@Override
	public void setTarget(int elevatorNumber, int target)
			throws RemoteException {
		SetTarget = target;
	}

	@Override
	public long getClockTick() throws RemoteException {
		return CLOCK_TICK;
	}
}
