package at.fhhagenberg.sqe.project.sqelevator.controller;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;

import com.sun.istack.internal.logging.Logger;

public class AutoElevatorAlgorithm extends ElevatorAlgorithm {

	private static Logger LOG = Logger.getLogger(AutoElevatorAlgorithm.class); 

	public AutoElevatorAlgorithm(IElevatorSystem sys, IElevatorControl ctrl) {
		super(sys, ctrl);
	}

	@Override
	public void setElevatorRequest(int elevator, int floor) {
		int curFloor;
		try {
			curFloor = mModel.getElevator(elevator).getFloor();
		} catch (ElevatorException e) {
			LOG.severe("AutoElevatorAlgorithm: got invalid elevator number");
			return;
		}
		assert (curFloor != floor);
		
		// searching the smallest difference from current floor to all requested floors
		int nextFloor = 0, diff = mModel.getNumberOfFloors()+1;
		for (int i = 0; i < mModel.getNumberOfFloors(); ++i) {
			try {
				if (mModel.getElevator(elevator).getButtonStatus(i) && mModel.getElevator(elevator).getServicesFloors(curFloor)) {
					if (diff > Math.abs(curFloor-i)) {
						nextFloor = i;
						diff = Math.abs(curFloor-i);
					}
				}
			} catch (FloorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid floor number");
				return;
			} catch (ElevatorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid elevator number");
				return;
			}
		}
		
		if (diff < mModel.getNumberOfFloors()+1) {
			mControl.setTarget(elevator, nextFloor);
			mControl.setCommittedDirection(elevator, getDirection(curFloor, nextFloor));
		}
		// do nothing
	}

	
	private int getDirection(int curFloor, int targetFloor) {
		int direction = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		if (targetFloor > curFloor) {
			direction = IElevator.ELEVATOR_DIRECTION_UP;	// up
		} else if (targetFloor < curFloor) {
			direction = IElevator.ELEVATOR_DIRECTION_DOWN;	// down
		}
		return direction;
	}
	
	/**
	 * very poor automatic algorithm
	 * 
	 */
	@Override
	public void setFloorRequest(int floor, boolean up) {
		// check if there is already a elevator in this floor
		for (int i = 0; i < mModel.getNumberOfElevators(); ++i) {
			try {																	// check if
				if (mEnabledElevators[i]		 									// elevator is in automatic mode
					&& mModel.getElevator(i).getServicesFloors(floor) 				// floor is serviced
					&& ((mModel.getElevator(i).getFloor() == floor) 				// elevator is in this floor
						 || (mModel.getElevator(i).getTargetFloor() == floor))) {	// elevator is moving to this floor
					// nothing to do
					return;
				}
			} catch (FloorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid floor number");
				return;
			} catch (ElevatorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid elevator number");
				return;
			} 
		}
		
		// check if there is an elevator without a job
		for (int i = 0; i < mModel.getNumberOfElevators(); ++i) {
			try {																								// check if
				if (mEnabledElevators[i] 																		// elevator is in automatic mode
					&& mModel.getElevator(i).getServicesFloors(floor)									 		// floor is serviced
					&& (mModel.getElevator(i).getDirection() == IElevator.ELEVATOR_DIRECTION_UNCOMMITTED)) {	// elevator has no job
					
					mControl.setTarget(i, floor);
					mControl.setCommittedDirection(i, getDirection(mModel.getElevator(i).getFloor(), floor));
					return;
				}
			} catch (FloorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid floor number");
				return;
			} catch (ElevatorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid elevator number");
				return;
			} 
		}
		
		// check if an elevator can pick up passengers
		for (int i = 0; i < mModel.getNumberOfElevators(); ++i) {
			try {																// check if
				if (mEnabledElevators[i] 										// elevator is in automatic mode
					&& mModel.getElevator(i).getServicesFloors(floor) 			// floor is serviced
					&& mModel.getElevator(i).getDirection() == (up ? IElevator.ELEVATOR_DIRECTION_UP : IElevator.ELEVATOR_DIRECTION_DOWN)) { 		// elevator drives in the same direction as requested
					
					int curFloor = mModel.getElevator(i).getFloor();
					
					if (up && (curFloor+1 < floor)) {
							mControl.setTarget(i, floor);
							mControl.setCommittedDirection(i, getDirection(mModel.getElevator(i).getFloor(), floor));
					} else if (!up && (curFloor-1 > floor)) {
							mControl.setTarget(i, floor);
							mControl.setCommittedDirection(i, getDirection(mModel.getElevator(i).getFloor(), floor));
					}
					
					return;
				}
			} catch (FloorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid floor number");
				return;
			} catch (ElevatorException e) {
				LOG.severe("AutoElevatorAlgorithm: got invalid elevator number");
				return;
			} 
		}		

	}

}
