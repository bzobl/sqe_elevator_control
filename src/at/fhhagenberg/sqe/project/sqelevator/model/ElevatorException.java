/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

public class ElevatorException extends Exception {
	private static final long serialVersionUID = -1814329521357992615L;
	private int mElevator;
	
	public ElevatorException(int elevator) {
		mElevator = elevator;
	}
	
	@Override
	public String getMessage() {
		return "Elevator " + mElevator + " is invalid";
	}

}
