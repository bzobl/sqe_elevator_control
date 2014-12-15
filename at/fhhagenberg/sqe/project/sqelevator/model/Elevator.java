/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observable;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

/** Elevator
 *  This class is a readonly-model of the current properties of an elevator.
 *  The fields will just be updated by a polling mechanism
 *  </br>
 *  
 *  Elevator extends Observable and will notify Observers, if polling triggers
 *  it to.
 * 
 */
public class Elevator extends Observable {
	public final int NUM;
	public final int CAPACITY;
	
	private int mAcceleration = 0;
	private int mDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
	private int mDoorstatus = IElevator.ELEVATOR_DOORS_OPEN;
	private int mFloor = 0;
	private int mPosition = 0;
	private int mSpeed = 0;
	private int mTargetFloor = 0;
	private int mWeight = 0;
	private boolean mButtonStatus[];
	private boolean mServicesFloors[];
	
	public Elevator(int num, int capacity, int floors) {
		NUM = num;
		CAPACITY = capacity;

		mButtonStatus = new boolean[floors];
		mServicesFloors = new boolean[floors];
		
		for (int i = 0; i < floors; i++) {
			mButtonStatus[i] = false;
			mServicesFloors[i] = true;
		}
	}
	
	private void checkFloor(int floor) throws FloorException {
		if (floor >= mServicesFloors.length) {
			throw new FloorException(floor);
		}
	}

	public int getAcceleration() {
		return mAcceleration;
	}

	public boolean getButtonStatus(int floor) throws FloorException {
		checkFloor(floor);
		return mButtonStatus[floor];
	}

	public int getDirection() {
		return mDirection;
	}

	public int getDoorstatus() {
		return mDoorstatus;
	}

	public int getFloor() {
		return mFloor;
	}

	public int getPosition() {
		return mPosition;
	}

	public boolean getServicesFloors(int floor) throws FloorException {
		checkFloor(floor);
		return mServicesFloors[floor];
	}

	public int getSpeed() {
		return mSpeed;
	}

	public int getTargetFloor() {
		return mTargetFloor;
	}

	public int getWeight() {
		return mWeight;
	}

	protected void setAcceleration(int acceleration) {
		if (mAcceleration != acceleration) {
			setChanged();
			mAcceleration = acceleration;
		}
	}

	protected void setButtonStatus(int floor, boolean buttonStatus) throws FloorException {
		checkFloor(floor);
		if (mButtonStatus[floor] != buttonStatus) {
			setChanged();
			mButtonStatus[floor] = buttonStatus;
		}
	}

	protected void setDirection(int direction) {
		if (mDirection != direction) {
			setChanged();
			mDirection = direction;
		}
	}

	protected void setDoorstatus(int doorstatus) {
		if (mDoorstatus != doorstatus) {
			setChanged();
			mDoorstatus = doorstatus;
		}
	}

	protected void setFloor(int floor) throws FloorException {
		checkFloor(floor);
		if (mFloor != floor) {
			setChanged();
			mFloor = floor;
		}
	}

	protected void setPosition(int position) {
		if (mPosition != position) {
			setChanged();
			mPosition = position;
		}
	}

	protected void setServicesFloors(int floor, boolean servicesFloors) throws FloorException {
		checkFloor(floor);
		if (mServicesFloors[floor] != servicesFloors) {
			setChanged();
			mServicesFloors[floor] = servicesFloors;
		}
	}

	protected void setSpeed(int speed) {
		if (mSpeed != speed) {
			setChanged();
			mSpeed = speed;
		}
	}

	protected void setTargetFloor(int targetFloor) throws FloorException {
		checkFloor(targetFloor);
		if (targetFloor != mTargetFloor) {
			setChanged();
			mTargetFloor = targetFloor;
		}
	}

	protected void setWeight(int weight) {
		if (mWeight != weight) {
			setChanged();
			mWeight = weight;
		}
	}
}
