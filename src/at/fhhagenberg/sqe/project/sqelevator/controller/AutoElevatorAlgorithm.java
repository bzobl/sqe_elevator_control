package at.fhhagenberg.sqe.project.sqelevator.controller;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorModel;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;

import com.sun.istack.internal.logging.Logger;

public class AutoElevatorAlgorithm extends ElevatorAlgorithm {

	private static Logger LOG = Logger.getLogger(AutoElevatorAlgorithm.class); 

	public AutoElevatorAlgorithm(IElevatorSystem sys, IElevatorControl ctrl) {
		super(sys, ctrl);
	}

	private int getDirection(int curFloor, int targetFloor) {
		int direction = IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
		if (targetFloor > curFloor) {
			direction = IElevator.ELEVATOR_DIRECTION_UP;
		} else if (targetFloor < curFloor) {
			direction = IElevator.ELEVATOR_DIRECTION_DOWN;
		}
		return direction;
	}

	@Override
	public void setElevatorRequest(int elevator, int floor) {
		assert(mEnabledElevators[elevator]) : "elevator is not in auto mode";
		try {
			IElevatorModel elev = mModel.getElevator(elevator);
			int curFloor = elev.getFloor();
			int nextFloor = curFloor;
			int diff = mModel.getNumberOfFloors()+1;

			// searching the smallest difference from current floor to all requested floors
			for (int i = 0; i < mModel.getNumberOfFloors(); ++i) {
				if (elev.getButtonStatus(i)
				    && elev.getServicesFloors(i)) {
					if (diff > Math.abs(curFloor-i)) {
						nextFloor = i;
						diff = Math.abs(curFloor-i);
					}
				}
			}
			
			if ((elev.getDoorstatus() == IElevator.ELEVATOR_DOORS_OPEN)
			    || (elev.getDoorstatus() == IElevator.ELEVATOR_DOORS_CLOSED)) {
				mControl.setTarget(elevator, nextFloor);
				mControl.setCommittedDirection(elevator, getDirection(curFloor, nextFloor));
				LOG.info("elevator request: elevator " + elevator + " to floor:" + nextFloor);
			}
	
		} catch (FloorException e) {
			LOG.severe("AutoElevatorAlgorithm: got invalid floor number");
			return;
		} catch (ElevatorException e) {
			LOG.severe("AutoElevatorAlgorithm: got invalid elevator number");
			return;
		}
	}

	/**
	 * very poor automatic algorithm
	 * 
	 */
	@Override
	public void setFloorRequest(int floor, boolean up) {
        try {
        	
        	for (int e = 0; e < mModel.getNumberOfElevators(); e++) {
        		IElevatorModel elev = mModel.getElevator(e);
        		if (!mEnabledElevators[e]) {
        			LOG.info("elevator " + e + " is not in auto mode");
        			continue;
        		}
        		if (!elev.getServicesFloors(floor)) {
        			LOG.info("elevator " + e + " does not service floor " + floor);
        			continue;
        		}
                if ((elev.getDoorstatus() != IElevator.ELEVATOR_DOORS_OPEN)
                	&& (elev.getDoorstatus() != IElevator.ELEVATOR_DOORS_CLOSED)) {
        			LOG.info("elevator " + e + " now opening or closing doors");
                	continue;
                }
        		
        		if (elev.getFloor() == floor) {
					LOG.info("Request floor " + floor + " Elevator is already here");
					return;
        		}

        		if (elev.getTargetFloor() == floor) {
					LOG.info("Request floor " + floor + " Elevator is already coming");
					return;
        		}
        		
        		// elevator without job
        		if (elev.getDirection() == IElevator.ELEVATOR_DIRECTION_UNCOMMITTED) {
					mControl.setTarget(e, floor);
					mControl.setCommittedDirection(e, getDirection(elev.getFloor(), floor));
					LOG.info("Request floor " + floor + ": Elevator " + e + " has no job.");
					return;
        		}
        		
        		/*
        		// check if an elevator can pick up passengers
        		if (elev.getDirection() ==  (up ? IElevator.ELEVATOR_DIRECTION_UP : IElevator.ELEVATOR_DIRECTION_DOWN)) {
        			int curFloor = elev.getFloor();

					if (up && (curFloor+1 < floor)) {
							mControl.setTarget(e, floor);
							LOG.info("Request floor " + floor + ": Elevator " + e + " going up. new target: " + floor);
					} else if (!up && (curFloor-1 > floor)) {
							mControl.setTarget(e, floor);
							LOG.info("Request floor " + floor + ": Elevator " + e + " going down. new target: " + floor);
					}
					return;
        		}
        		*/
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
