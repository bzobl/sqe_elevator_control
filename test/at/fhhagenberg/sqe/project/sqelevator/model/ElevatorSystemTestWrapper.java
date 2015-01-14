/**
 * Project: Elevator_Control_Center
 * Author:  Bernhard Raab
 *          S1310567022
 */
package at.fhhagenberg.sqe.project.sqelevator.model;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;

/** ElevatorSystemTestWrapper
 *  This class is a wrapper of the ElevatorSystem class. 
 *  The public ElevatorSystemTestWrapper extends the package protected Elevator to get access.
 */
public class ElevatorSystemTestWrapper extends ElevatorSystem {

	public ElevatorSystemTestWrapper(IElevatorConnection connection) {
		super(connection);
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = connection.getElevatorCapacity(i);
			mElevators[i] = new ElevatorTestWrapper(i, capacity, NUM_FLOORS);
		}
	}
	
	@Override
	protected void startPolling(IElevatorConnection connection) {
	}

	protected void setDownButton(int floor, boolean pressed) {
		super.setDownButton(floor, pressed);
	}

	protected void setUpButton(int floor, boolean pressed) {
		super.setUpButton(floor, pressed);
	}
	
}
