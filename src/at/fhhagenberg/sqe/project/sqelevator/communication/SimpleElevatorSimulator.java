
package at.fhhagenberg.sqe.project.sqelevator.communication;

import sqelevator.IElevator;

public class SimpleElevatorSimulator implements IElevatorStatus, IElevatorControl {

	private static final int FLOOR_HEIGHT = 6;
	public static final long CLOCK_TICK = 300;
	private static final int DEFAULT_CAPACITY = 1000;
	
	public static final int DELAY_CLOSING = 1;
	public static final int DELAY_CLOSED = 2;
	public static final int DELAY_MOVE = 3;
	public static final int DELAY_OPENING = 4;
	public static final int DELAY_DONE = 5;

	private final int NUM_ELEVATORS;
	private final int NUM_FLOORS;
	private final int CAPACITY[];
	
	private int mDelayTicks[];
	
	private int mCommitedDirection[];
	private int mDoorstatus[];
	private int mElevatorAccel[];
	private int mFloor[];
	private int mSpeed[];
	private int mTarget[];
	private int mWeight[];
	private boolean mFloorButtonDown[];
	private boolean mFloorButtonUp[];
	private boolean mServicesFloors[][];
	private boolean mElevatorButton[][];
	
	public SimpleElevatorSimulator(int elevators, int floors) {
		NUM_ELEVATORS = elevators;
        NUM_FLOORS = floors;
        CAPACITY = new int[NUM_ELEVATORS];
        for (int e = 0; e < NUM_ELEVATORS; e++) {
        	CAPACITY[e] = DEFAULT_CAPACITY;
        }
        
        mDelayTicks = new int[NUM_ELEVATORS];
        mCommitedDirection = new int[NUM_ELEVATORS];
        mDoorstatus = new int[NUM_ELEVATORS];;
        mElevatorAccel = new int[NUM_ELEVATORS];;
        mFloor = new int[NUM_ELEVATORS];
        mSpeed = new int[NUM_ELEVATORS];
        mTarget = new int[NUM_ELEVATORS];
        mWeight = new int[NUM_ELEVATORS];
        mFloorButtonDown = new boolean[NUM_FLOORS];
        mFloorButtonUp = new boolean[NUM_FLOORS];

        mServicesFloors = new boolean[NUM_ELEVATORS][NUM_FLOORS];
        mElevatorButton = new boolean[NUM_ELEVATORS][NUM_FLOORS];

        for (int e = 0; e < NUM_ELEVATORS; e++) {
        	CAPACITY[e] = DEFAULT_CAPACITY;
        	mDelayTicks[e] = -1;

        	mCommitedDirection[e] = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
        	mDoorstatus[e] = IElevator.ELEVATOR_DOORS_OPEN;
        	mElevatorAccel[e] = 0;
        	mFloor[e] = 0;
        	mSpeed[e] = 0;
        	mTarget[e] = 0;
        	mWeight[e] = 0;
        	for (int f = 0; f < NUM_FLOORS; f++) {
        		mServicesFloors[e][f] = true;
        		mElevatorButton[e][f] = false;
        	}
        }
        
        for (int f = 0; f < NUM_FLOORS; f++) {
        	mFloorButtonUp[f] = false;
        	mFloorButtonDown[f] = false;
        }
	}

	private int calcPosition(int e) {
		return mFloor[e] * FLOOR_HEIGHT;
	}
	
	private void move(int elevatorNumber) {
		if (mDelayTicks[elevatorNumber] < 0) return;
		
		mDelayTicks[elevatorNumber]++;
		
		if (mDelayTicks[elevatorNumber] >= DELAY_DONE) {
			mDoorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_OPEN;
			mCommitedDirection[elevatorNumber] = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
			mDelayTicks[elevatorNumber] = -1;
			return;
		}

		if (mDelayTicks[elevatorNumber] == DELAY_MOVE) {
			if ((  ((mCommitedDirection[elevatorNumber] == IElevator.ELEVATOR_DIRECTION_DOWN) && (mFloor[elevatorNumber] > mTarget[elevatorNumber]))
			    || ((mCommitedDirection[elevatorNumber] == IElevator.ELEVATOR_DIRECTION_UP)   && (mFloor[elevatorNumber] < mTarget[elevatorNumber]))
			    || (mFloor[elevatorNumber] == mTarget[elevatorNumber]))
			   && (mServicesFloors[elevatorNumber][mTarget[elevatorNumber]])){
				mFloor[elevatorNumber] = mTarget[elevatorNumber];
			}
		}
	}

	@Override
	public int getCommittedDirection(int elevatorNumber) {
		return mCommitedDirection[elevatorNumber];
	}

	@Override
	public int getElevatorAccel(int elevatorNumber) {
		return mElevatorAccel[elevatorNumber];
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) {
		return mElevatorButton[elevatorNumber][floor];
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) {
		if (mDelayTicks[elevatorNumber] == DELAY_CLOSING) {
			mDoorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_CLOSING;
		} else if (mDelayTicks[elevatorNumber] == DELAY_CLOSED) {
			mDoorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_CLOSED;
		} else if (mDelayTicks[elevatorNumber] == DELAY_OPENING) {
			mDoorstatus[elevatorNumber] = IElevator.ELEVATOR_DOORS_OPENING;
		}
		return mDoorstatus[elevatorNumber];
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) {
		move(elevatorNumber);
		return mFloor[elevatorNumber];
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
		return mSpeed[elevatorNumber];
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) {
		return mWeight[elevatorNumber];
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber) {
		return CAPACITY[elevatorNumber];
	}

	@Override
	public boolean getFloorButtonDown(int floor) {
		return mFloorButtonDown[floor];
	}

	@Override
	public boolean getFloorButtonUp(int floor) {
		return mFloorButtonUp[floor];
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
		return mServicesFloors[elevatorNumber][floor];
	}

	@Override
	public int getTarget(int elevatorNumber) {
		return mTarget[elevatorNumber];
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) {
		mDelayTicks[elevatorNumber] = 0;
		mCommitedDirection[elevatorNumber] = direction;
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service) {
		mServicesFloors[elevatorNumber][floor] = service;
	}

	@Override
	public void setTarget(int elevatorNumber, int target) {
		mTarget[elevatorNumber] = target;
	}

	@Override
	public long getClockTick() {
		return CLOCK_TICK;
	}

}
