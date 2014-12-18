/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observer;

public interface IElevatorSystem {

	public static final int SYSTEM_PROPERTY_CHANGED = -1;
	public static final int ELEVATOR_PROPERTY_CHANGED = -2;

	public void addObserver(Observer o);

	public int getNumberOfElevators();
	public int getNumberOfFloors();

	/**
	 * @param num Number of the elevator to get
	 * @return Elevator with number num
	 * @throws ElevatorException 
	 */
	public IElevatorModel getElevator(int num) throws ElevatorException;

	public boolean getFloorButton(int floor, boolean up) throws FloorException;

}