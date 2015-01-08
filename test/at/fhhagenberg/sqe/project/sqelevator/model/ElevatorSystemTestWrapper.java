/**
 * Project: Elevator_Control_Center
 * Author:  Bernhard Raab
 *          S1310567022
 */
package at.fhhagenberg.sqe.project.sqelevator.model;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;

/** ElevatorSystemTestWrapper
 *  This class is a wrapper of the ElevatorSystem class. 
 *  The public ElevatorSystemTestWrapper extends the package protected Elevator to get access.
 */
public class ElevatorSystemTestWrapper extends ElevatorSystem {

	public ElevatorSystemTestWrapper(IElevatorStatus status) {
		super(status);
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = status.getElevatorCapacity(i);
			mElevators[i] = new ElevatorTestWrapper(i, capacity, NUM_FLOORS);
		}
	}
	
	@Override
	protected void startPolling(IElevatorStatus status) {
	}

	protected void setDownButton(int floor, boolean pressed) {
		super.setDownButton(floor, pressed);
	}

	protected void setUpButton(int floor, boolean pressed) {
		super.setUpButton(floor, pressed);
	}
	
}
