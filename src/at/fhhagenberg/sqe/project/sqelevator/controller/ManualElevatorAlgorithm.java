/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;

import com.sun.istack.internal.logging.Logger;

/** ManualElevatorAlgorithm
 * 
 */
public class ManualElevatorAlgorithm extends ElevatorAlgorithm {
	private static Logger LOG = Logger.getLogger(ManualElevatorAlgorithm.class);
	
	public ManualElevatorAlgorithm(IElevatorSystem sys, IElevatorControl ctrl) {
		super(sys, ctrl);
	}

	@Override
	public void setElevatorRequest(int elevator, int floor) {
		assert((floor < mModel.getNumberOfFloors()) && (floor >= 0)) : "floor number is invalid";

		mControl.setTarget(elevator, floor);

		int curFloor;
		try {
			curFloor = mModel.getElevator(elevator).getFloor();
			if (curFloor < floor) {
				mControl.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_UP);
			} else if (curFloor > floor) {
				mControl.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_DOWN);
			} else {
				mControl.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
			}
		} catch (ElevatorException e) {
			LOG.severe(e.getMessage());
			return;
		}
	}

	@Override
	public void setFloorRequest(int floor, boolean up) {
		assert(false) : "Floor request cannot be called in manual mode";
	}
}
