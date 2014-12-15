/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observable;

/** Elevator
 * 
 */
public class Elevator extends Observable {
	public final int CAPACITY;
	public final int NUM;
	
	protected int mTargetFloor;
	protected int mDirection;
	protected int mAcceleration;
	protected boolean mButtonStatus[];
	protected int mDoorstatus;
	protected int mFloor;
	protected int mPosition;
	protected int mSpeed;
	protected int mWeight;
	protected boolean mServicesFloors[];
	
	public Elevator(int num, int capacity, int floors) {
		NUM = num;
		CAPACITY = capacity;

		mButtonStatus = new boolean[floors];
		mServicesFloors = new boolean[floors];
	}

	/**
	 * @return the mTargetFloor
	 */
	public int getTargetFloor() {
		return mTargetFloor;
	}

	/**
	 * @return the mDirection
	 */
	public int getDirection() {
		return mDirection;
	}

	/**
	 * @return the mAcceleration
	 */
	public int getAcceleration() {
		return mAcceleration;
	}

	/**
	 * @return the mButtonStatus
	 */
	public boolean getButtonStatus(int floor) {
		return mButtonStatus[floor];
	}

	/**
	 * @return the mDoorstatus
	 */
	public int getDoorstatus() {
		return mDoorstatus;
	}

	/**
	 * @return the mFloor
	 */
	public int getFloor() {
		return mFloor;
	}

	/**
	 * @return the mPosition
	 */
	public int getPosition() {
		return mPosition;
	}

	/**
	 * @return the mSpeed
	 */
	public int getSpeed() {
		return mSpeed;
	}

	/**
	 * @return the mWeight
	 */
	public int getWeight() {
		return mWeight;
	}

	/**
	 * @return the mServicesFloors
	 */
	public boolean getServicesFloors(int floor) {
		return mServicesFloors[floor];
	}

	/**
	 * @param targetFloor the mTargetFloor to set
	 */
	protected void setTargetFloor(int targetFloor) {
		if (targetFloor != mTargetFloor) {
			setChanged();
			mTargetFloor = targetFloor;
		}
	}

	/**
	 * @param direction the mDirection to set
	 */
	protected void setDirection(int direction) {
		if (mDirection != direction) {
			setChanged();
			mDirection = direction;
		}
	}

	/**
	 * @param acceleration the mAcceleration to set
	 */
	protected void setAcceleration(int acceleration) {
		if (mAcceleration != acceleration) {
			setChanged();
			mAcceleration = acceleration;
		}
	}

	/**
	 * @param buttonStatus the mButtonStatus to set
	 */
	protected void setButtonStatus(int floor, boolean buttonStatus) {
		assert(floor < mButtonStatus.length);
		if (mButtonStatus[floor] != buttonStatus) {
			setChanged();
			mButtonStatus[floor] = buttonStatus;
		}
	}

	/**
	 * @param doorstatus the mDoorstatus to set
	 */
	protected void setDoorstatus(int doorstatus) {
		if (mDoorstatus != doorstatus) {
			setChanged();
			mDoorstatus = doorstatus;
		}
	}

	/**
	 * @param floor the mFloor to set
	 */
	protected void setFloor(int floor) {
		if (mFloor != floor) {
			setChanged();
			mFloor = floor;
		}
	}

	/**
	 * @param position the mPosition to set
	 */
	protected void setPosition(int position) {
		if (mPosition != position) {
			setChanged();
			mPosition = position;
		}
	}

	/**
	 * @param speed the mSpeed to set
	 */
	protected void setSpeed(int speed) {
		if (mSpeed != speed) {
			setChanged();
			mSpeed = speed;
		}
	}

	/**
	 * @param weight the mWeight to set
	 */
	protected void setWeight(int weight) {
		if (mWeight != weight) {
			setChanged();
			mWeight = weight;
		}
	}

	/**
	 * @param servicesFloors the mServicesFloors to set
	 */
	protected void setServicesFloors(int floor, boolean servicesFloors) {
		assert(floor < mServicesFloors.length);
		if (mServicesFloors[floor] != servicesFloors) {
			setChanged();
			mServicesFloors[floor] = servicesFloors;
		}
	}
}
