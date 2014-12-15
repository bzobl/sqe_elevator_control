
package at.fhhagenberg.sqe.project.sqelevator.tests.model;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

final class ElevatorConnectionShunt implements IElevator {
	private final int FLOOR_HEIGHT;
	private final int FLOOR_NUM;
	private final long CLOCK_TICK;
	
	// Self shunt variables
	public int CommitedDirection;
	public int ElevatorAccel;
	public boolean[] ElevatorButton;
	public int Doorstatus;
	public int Floor;
	public int Position;
	public int Speed;
	public int Weight;
	public int Capacity;
	public boolean[] FloorButtonDown;
	public boolean[] FloorButtonUp;
	public boolean[] ServicesFloors;
	public int Target;

	public int SetCommitedDirection;
	public boolean[] SetServicesFloor;
	public int SetTarget;
	
	public ElevatorConnectionShunt(int floors, int height, long period, int capacity) {
		FLOOR_NUM = floors;
		FLOOR_HEIGHT = height;
		CLOCK_TICK = period;
		Capacity = capacity;
		
		ElevatorButton = new boolean[FLOOR_NUM];
		FloorButtonDown = new boolean[FLOOR_NUM];
		FloorButtonUp = new boolean[FLOOR_NUM];
		ServicesFloors = new boolean[FLOOR_NUM];
		SetServicesFloor = new boolean[FLOOR_NUM];
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
		return 1;
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
		return Capacity;
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
