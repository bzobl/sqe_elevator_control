/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;

public interface IElevatorAlgorithm {

	public void setElevatorRequest(int elevator, int floor);
	public void setFloorRequest(int floor, boolean up);
	
	public void setModel(IElevatorSystem model);
	public void setControl(IElevatorControl control);
	
	public void enableElevator(int elevator, boolean enable);
}
