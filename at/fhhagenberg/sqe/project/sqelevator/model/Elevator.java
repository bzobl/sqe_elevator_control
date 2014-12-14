/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

/** Elevator
 * 
 */
public class Elevator {
	private final int CAPACITY;
	
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
	
	public Elevator(int capacity, int floors) {
		CAPACITY = capacity;
		
		mButtonStatus = new boolean[floors];
		mServicesFloors = new boolean[floors];
	}

	/**
	 * @return the CAPACITY
	 */
	public int getCapacity() {
		return CAPACITY;
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
}
