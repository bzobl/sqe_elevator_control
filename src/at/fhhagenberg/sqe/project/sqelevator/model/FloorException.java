/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

public class FloorException extends Exception {
	
	private static final long serialVersionUID = 4918721683881015488L;
	private int mFloor;
	
	public FloorException(int floor) {
		mFloor = floor;
	}
	
	@Override
	public String getMessage() {
		return "Floor " + mFloor + " is invalid";
	}
}
