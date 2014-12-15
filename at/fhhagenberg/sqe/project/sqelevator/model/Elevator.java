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
	
	private int mTargetFloor = 0;
	private int mDirection = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
	private int mAcceleration = 0;
	private boolean mButtonStatus[];
	private int mDoorstatus = IElevator.ELEVATOR_DOORS_OPEN;
	private int mFloor = 0;
	private int mPosition = 0;
	private int mSpeed = 0;
	private int mWeight = 0;
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

	public int getTargetFloor() {
		return mTargetFloor;
	}

	public int getDirection() {
		return mDirection;
	}

	public int getAcceleration() {
		return mAcceleration;
	}

	public boolean getButtonStatus(int floor) {
		return mButtonStatus[floor];
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

	public int getSpeed() {
		return mSpeed;
	}

	public int getWeight() {
		return mWeight;
	}

	public boolean getServicesFloors(int floor) {
		return mServicesFloors[floor];
	}

	protected void setTargetFloor(int targetFloor) {
		if (targetFloor != mTargetFloor) {
			setChanged();
			mTargetFloor = targetFloor;
		}
	}

	protected void setDirection(int direction) {
		if (mDirection != direction) {
			setChanged();
			mDirection = direction;
		}
	}

	protected void setAcceleration(int acceleration) {
		if (mAcceleration != acceleration) {
			setChanged();
			mAcceleration = acceleration;
		}
	}

	protected void setButtonStatus(int floor, boolean buttonStatus) {
		assert(floor < mButtonStatus.length);
		if (mButtonStatus[floor] != buttonStatus) {
			setChanged();
			mButtonStatus[floor] = buttonStatus;
		}
	}

	protected void setDoorstatus(int doorstatus) {
		if (mDoorstatus != doorstatus) {
			setChanged();
			mDoorstatus = doorstatus;
		}
	}

	protected void setFloor(int floor) {
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

	protected void setSpeed(int speed) {
		if (mSpeed != speed) {
			setChanged();
			mSpeed = speed;
		}
	}

	protected void setWeight(int weight) {
		if (mWeight != weight) {
			setChanged();
			mWeight = weight;
		}
	}

	protected void setServicesFloors(int floor, boolean servicesFloors) {
		assert(floor < mServicesFloors.length);
		if (mServicesFloors[floor] != servicesFloors) {
			setChanged();
			mServicesFloors[floor] = servicesFloors;
		}
	}
}
