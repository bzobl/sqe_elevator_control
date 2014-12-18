/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

public interface IElevatorModel {
	
	public int getElevatorNumber();
	public int getCapacity();

	public int getAcceleration();
	public boolean getButtonStatus(int floor) throws FloorException;
	public int getDirection();
	public int getDoorstatus();
	public int getFloor();
	public int getPosition();
	public boolean getServicesFloors(int floor) throws FloorException;
	public int getSpeed();
	public int getTargetFloor();
	public int getWeight();
}