/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;

import com.sun.istack.internal.logging.Logger;

/** ManualElevatorAlgorithm
 * 
 */
public class ManualElevatorAlgorithm implements IElevatorAlgorithm {
	private static Logger LOG = Logger.getLogger(ManualElevatorAlgorithm.class);
	
	private ElevatorSystem mModel;
	private IElevatorControl mControl;
	
	public ManualElevatorAlgorithm(ElevatorSystem sys, IElevatorControl ctrl) {
		mModel = sys;
		mControl = ctrl;
	}

	@Override
	public void setElevatorRequest(int elevator, int floor) {
		assert((floor < mModel.NUM_FLOORS) && (floor >= 0)) : "floor number is invalid";

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
		assert(false) : "Floor request cannot be called in automatic mode";
	}

	@Override
	public void setModel(ElevatorSystem model) {
		mModel = model;
	}

	@Override
	public void setControl(IElevatorControl control) {
		mControl = control;
	}

}