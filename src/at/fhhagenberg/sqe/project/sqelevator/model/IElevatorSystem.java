/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observer;

public interface IElevatorSystem {

	public abstract void addObserver(Observer o);

	/**
	 * @param num Number of the elevator to get
	 * @return Elevator with number num
	 * @throws ElevatorException 
	 */
	public IElevator getElevator(int num) throws ElevatorException;

	public boolean getFloorButton(int floor, boolean up) throws FloorException;

}