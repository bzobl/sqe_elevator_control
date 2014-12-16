
package at.fhhagenberg.sqe.project.sqelevator.communication;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

public class ElevatorAdaptor implements IElevatorConnection {

	private static final int FLOOR_HEIGHT = 6;
	public static final long CLOCK_TICK = 300;
	private static final int DEFAULT_CAPACITY = 1000;
	
	public static final int DELAY_CLOSING = 1;
	public static final int DELAY_CLOSED = 2;
	public static final int DELAY_MOVE = 3;
	public static final int DELAY_OPENING = 5;
	public static final int DELAY_DONE = 6;

	private final int NUM_ELEVATORS;
	private final int NUM_FLOORS;
	private final int CAPACITY[];
	
	private int delayTicks[];
	
	private int CommitedDirection[];
	private int Doorstatus[];
	private int ElevatorAccel[];
	private int Floor[];
	private int Speed[];
	private int Target[];
	private int Weight[];
	private boolean FloorButtonDown[];
	private boolean FloorButtonUp[];
	private boolean ServicesFloors[][];
	private boolean ElevatorButton[][];
	
	public ElevatorAdaptor(int elevators, int floors) {
		NUM_ELEVATORS = elevators;
        NUM_FLOORS = floors;
        CAPACITY = new int[NUM_ELEVATORS];
        for (int e = 0; e < NUM_ELEVATORS; e++) {
        	CAPACITY[e] = DEFAULT_CAPACITY;
        }
        
        delayTicks = new int[NUM_ELEVATORS];
        CommitedDirection = new int[NUM_ELEVATORS];
        Doorstatus = new int[NUM_ELEVATORS];;
        ElevatorAccel = new int[NUM_ELEVATORS];;
        Floor = new int[NUM_ELEVATORS];
        Speed = new int[NUM_ELEVATORS];
        Target = new int[NUM_ELEVATORS];
        Weight = new int[NUM_ELEVATORS];
        FloorButtonDown = new boolean[NUM_FLOORS];
        FloorButtonUp = new boolean[NUM_FLOORS];

        ServicesFloors = new boolean[NUM_ELEVATORS][NUM_FLOORS];
        ElevatorButton = new boolean[NUM_ELEVATORS][NUM_FLOORS];

        for (int e = 0; e < NUM_ELEVATORS; e++) {
        	CAPACITY[e] = DEFAULT_CAPACITY;
        	delayTicks[e] = -1;

        	CommitedDirection[e] = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
        	Doorstatus[e] = IElevator.ELEVATOR_DOORS_OPEN;
        	ElevatorAccel[e] = 0;
        	Floor[e] = 0;
        	Speed[e] = 0;
        	Target[e] = 0;
        	Weight[e] = 0;
        	for (int f = 0; f < NUM_FLOORS; f++) {
        		ServicesFloors[e][f] = true;
        		ElevatorButton[e][f] = false;
        	}
        }
        
        for (int f = 0; f < NUM_FLOORS; f++) {
        	FloorButtonUp[f] = false;
        	FloorButtonDown[f] = false;
        }
	}

	private int calcPosition(int e) {
		return Floor[e] * FLOOR_HEIGHT;
	}
	
	private void move(int elevatorNumber) {
		if (delayTicks[elevatorNumber] < 0) return;
		
		if (delayTicks[elevatorNumber] >= DELAY_DONE) {
			Doorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_OPEN;
			CommitedDirection[elevatorNumber] = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
			delayTicks[elevatorNumber] = -1;
			return;
		}
		
		delayTicks[elevatorNumber]++;

		if (delayTicks[elevatorNumber] == DELAY_MOVE) {
			if ((  ((CommitedDirection[elevatorNumber] == IElevator.ELEVATOR_DIRECTION_DOWN) && (Floor[elevatorNumber] > Target[elevatorNumber]))
			    || ((CommitedDirection[elevatorNumber] == IElevator.ELEVATOR_DIRECTION_UP)   && (Floor[elevatorNumber] < Target[elevatorNumber]))
			    || (Floor[elevatorNumber] == Target[elevatorNumber]))
			   && (ServicesFloors[elevatorNumber][Target[elevatorNumber]])){
				Floor[elevatorNumber] = Target[elevatorNumber];
			}
		}
	}

	@Override
	public int getCommittedDirection(int elevatorNumber) {
		return CommitedDirection[elevatorNumber];
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) {
		return ElevatorAccel[elevatorNumber];
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) {
		return ElevatorButton[elevatorNumber][floor];
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) {
		if (delayTicks[elevatorNumber] == DELAY_CLOSING) {
			Doorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_CLOSING;
		} else if (delayTicks[elevatorNumber] == DELAY_CLOSED) {
			Doorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_CLOSED;
		} else if (delayTicks[elevatorNumber] == DELAY_OPENING) {
			Doorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_OPENING;
		}
		return Doorstatus[elevatorNumber];
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) {
		move(elevatorNumber);
		return Floor[elevatorNumber];
	}

	@Override
	public int getElevatorNum() {
		return NUM_ELEVATORS;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber) {
		return calcPosition(elevatorNumber);
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) {
		return Speed[elevatorNumber];
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) {
		return Weight[elevatorNumber];
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) {
		return CAPACITY[elevatorNumber];
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
		return NUM_FLOORS;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) {
		return ServicesFloors[elevatorNumber][floor];
	}

	@Override
	public int getTarget(int elevatorNumber) {
		return Target[elevatorNumber];
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) {
		delayTicks[elevatorNumber] = 0;
		CommitedDirection[elevatorNumber] = direction;
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
		ServicesFloors[elevatorNumber][floor] = service;
	}

	@Override
	public void setTarget(int elevatorNumber, int target) {
		Target[elevatorNumber] = target;
	}

	@Override
	public long getClockTick() {
		return CLOCK_TICK;
	}

}
