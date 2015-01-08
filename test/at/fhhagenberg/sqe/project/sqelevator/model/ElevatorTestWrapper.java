/**
 * Project: Elevator_Control_Center
 * Author:  Bernhard Raab
 *          S1310567022
 */
package at.fhhagenberg.sqe.project.sqelevator.model;

/** ElevatorTestWrapper
 *  This class is a wrapper of the Elevator class. 
 *  The public ElevatorTestWrapper extends the package protected Elevator to get access.
 */
public class ElevatorTestWrapper extends Elevator {

	public ElevatorTestWrapper(int num, int capacity, int floors) {
		super(num, capacity, floors);
	}
	
	public void setAcceleration(int acceleration) {
		super.setAcceleration(acceleration);
	}

	public void setButtonStatus(int floor, boolean buttonStatus) throws FloorException {
		super.setButtonStatus(floor, buttonStatus);
	}

	public void setDirection(int direction) {
		super.setDirection(direction);
	}

	public void setDoorstatus(int doorstatus) {
		super.setDoorstatus(doorstatus);
	}

	public void setFloor(int floor) throws FloorException {
		super.setFloor(floor);
	}

	public void setPosition(int position) {
		super.setPosition(position);
	}

	public void setServicesFloors(int floor, boolean servicesFloors) throws FloorException {
		super.setServicesFloors(floor, servicesFloors);
	}

	public void setSpeed(int speed) {
		super.setSpeed(speed);
	}

	public void setTargetFloor(int targetFloor) throws FloorException {
		super.setTargetFloor(targetFloor);
	}

	public void setWeight(int weight) {
		super.setWeight(weight);
	}
}
