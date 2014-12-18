/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorModel;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IFloorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IMainView;

import com.sun.istack.internal.logging.Logger;

public class ElevatorControlCenter implements IControl, Observer {
	
	private static Logger LOG = Logger.getLogger(ElevatorControlCenter.class);
	
	private static final Map<Integer, Integer> DIRECTION_LUT = new HashMap<>();
	private static final Map<Integer, Integer> DOORSTATUS_LUT = new HashMap<>();
	
	static {
		DIRECTION_LUT.put(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, IFloorView.MOVE_STATUS_STANDING);
		DIRECTION_LUT.put(IElevator.ELEVATOR_DIRECTION_UP, IFloorView.MOVE_STATUS_UP);
		DIRECTION_LUT.put(IElevator.ELEVATOR_DIRECTION_DOWN, IFloorView.MOVE_STATUS_DOWN);

		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_CLOSED, IFloorView.ELEVATOR_STATUS_CLOSED);
		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_CLOSING, IFloorView.ELEVATOR_STATUS_CLOSING);
		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_OPEN, IFloorView.ELEVATOR_STATUS_OPENED);
		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_OPENING, IFloorView.ELEVATOR_STATUS_OPENING);
	}
	
	IElevatorControl mControl;
	IElevatorSystem mModel;
	IMainView mView;
	
	boolean mAuto[];

	IElevatorAlgorithm mAutoAlgo;
	IElevatorAlgorithm mManuAlgo;
	
	public ElevatorControlCenter(IElevatorControl control, IElevatorStatus status) {
		mControl = control;

        mModel = new ElevatorSystem(status);
        mModel.addObserver(this);

		mAuto = new boolean[mModel.getNumberOfElevators()];
		
		mAutoAlgo = null;
		mManuAlgo = new ManualElevatorAlgorithm(mModel, mControl);
        
        LOG.info("Elevator control successfully initialized, starting to poll");
	}

	/**
	 * @return whether or not any elevator is in auto mode
	 */
	private boolean isAnyElevatorAuto() {
		for (int e = 0; e < mModel.getNumberOfElevators(); e++) {
			if (mAuto[e]) return true;
		}
		return false;
	}

	/**
	 * @param elevator
	 * @param btn
	 */
	@Override
	public void changeControlMode(int elevator, boolean autoMode) {
		LOG.info("Mode button of Elevator " + elevator + " clicked");
		IElevatorView eleView = mView.getElevatorView(elevator);
		
		mAuto[elevator] = autoMode;
		
		for (int i = 0; i < mModel.getNumberOfFloors(); i++) {
			IFloorView floorView = eleView.getFloorView(i); 
			floorView.enableCallButton(!mAuto[elevator]);
		}
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	@Override
	public void setCallRequest(int elevator, int floor) {
		assert(mAuto[elevator] == false) : "Call Button cannot be pressed when in auto mode";
		LOG.info("Call Button of Elevator " + elevator + ", Floor " + floor + " clicked");
		
		mManuAlgo.setElevatorRequest(elevator, floor);
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	@Override
	public void setServicedFloor(int elevator, int floor, boolean isServiced) {
		LOG.info("Service Button of Elevator " + elevator + ", Floor " + floor + " clicked");
		mControl.setServicesFloors(elevator, floor, isServiced);
	}
	
	@Override
	public void updateView() {
        try {
        	for (int i = 0; i < mModel.getNumberOfElevators(); i++) {
				updateView(mModel.getElevator(i));
        	}
        } catch (ElevatorException e) {
        	LOG.severe(e.getMessage());
		}
		
		updateView(mModel);
	}
	
	@Override
	public void setView(IMainView view) {
		mView = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(IElevatorSystem.SYSTEM_PROPERTY_CHANGED)) {
			IElevatorSystem sys = (IElevatorSystem) o;
			if (mView != null) {
				updateView(sys);
			} else {
				LOG.info("ElevatorSystem updated, but no view is set");
			}
			if (mAutoAlgo != null) {
				if (isAnyElevatorAuto()) {
					updateAlgorithm(sys);
				}
			} else {
				LOG.info("ElevatorSystem updated, but no auto algo is set");
			}
		} else if (arg.equals(IElevatorSystem.ELEVATOR_PROPERTY_CHANGED)) {
			IElevatorModel elev = (IElevatorModel) o;
			if (mView != null) {
				updateView(elev);
			} else {
				LOG.info("Elevator updated, but no view is set");
			}
			if (mAuto[elev.getElevatorNumber()]) {
				if (mAutoAlgo != null) {
					updateAlgorithm(elev);
				} else {
					LOG.info("Elevator updated, but no auto algo is set");
				}
			}
		} else {
			LOG.warning("Unexpected class notified ElevatorControl: " + o.getClass().getCanonicalName());
		}
	}

	/**
	 * @param elev
	 */
	private void updateAlgorithm(IElevatorModel elev) {
		assert (mAutoAlgo != null) : "Auto algorithm not set";
		assert (mAuto[elev.getElevatorNumber()]) : "elevator is in manual mode";

		for (int f = 0; f < mModel.getNumberOfFloors(); f++) {
			try {
				if (elev.getButtonStatus(f)) {
					mAutoAlgo.setElevatorRequest(elev.getElevatorNumber(), f);
				}
			} catch (FloorException e) {
				LOG.severe(e.getMessage());
			}
		}
	}
	
	/**
	 * @param sys
	 */
	private void updateAlgorithm(IElevatorSystem sys) {
		assert (mAutoAlgo != null) : "Auto algorithm not set";
		assert (isAnyElevatorAuto()) : "all elevators are in manual mode";

		for (int f = 0; f < mModel.getNumberOfFloors(); f++) {
			try {
				if (sys.getFloorButton(f, true)) {
					mAutoAlgo.setFloorRequest(f, true);
				} else if (sys.getFloorButton(f, false)) {
					mAutoAlgo.setFloorRequest(f, false);
				}
			} catch (FloorException e) {
				LOG.severe(e.getMessage());
			}
		}
	}

	private void updateView(IElevatorModel elev) {
		assert (mView != null) : "update of view requested when no view was set";
		
		IElevatorView eleView = mView.getElevatorView(elev.getElevatorNumber());
		if (eleView == null) {
			LOG.severe("Could not find ElevatorView for elevator " + elev.getElevatorNumber());
			return;
		}

		eleView.setPosition(elev.getPosition());
		eleView.setAcceleration(elev.getAcceleration());
		eleView.setPayload(elev.getWeight());
		eleView.setSpeed(elev.getSpeed());
		
		for (int i = 0; i < mModel.getNumberOfFloors(); i++) {
			IFloorView floorView = eleView.getFloorView(i);
			
			int moveStatus = IFloorView.MOVE_STATUS_AWAY;
			int elevatorStatus = IFloorView.ELEVATOR_STATUS_AWAY;
			
			if (elev.getFloor() == i) {
				elevatorStatus = DOORSTATUS_LUT.get(elev.getDoorstatus());
				moveStatus = DIRECTION_LUT.get(elev.getDirection());
			} else if ((elev.getTargetFloor() == i)) {
				elevatorStatus = IFloorView.ELEVATOR_STATUS_TARGET;
			}

			try {
				if (!elev.getServicesFloors(i)) {
					elevatorStatus = IFloorView.ELEVATOR_STATUS_OUT_OF_ORDER;
					floorView.setServiceStatus(false);
				} else {
					floorView.setServiceStatus(true);
				}

				eleView.setElevatorButton(i, elev.getButtonStatus(i));
			} catch (FloorException e) {
				LOG.severe(e.getMessage());
			}

			floorView.setMoveStatus(moveStatus);
			floorView.setElevatorStatus(elevatorStatus);
		}
	}

		
	private void updateView(IElevatorSystem sys) {
		assert (mView != null) : "update of view requested when no view was set";

		for (int e = 0; e < mModel.getNumberOfElevators(); e++) {
			for (int f = 0; f < mModel.getNumberOfElevators(); f++) {
				IFloorView floorView = mView.getElevatorView(e).getFloorView(f);
				try {
					floorView.setFloorButton(IFloorView.FLOOR_BUTTON_UP, sys.getFloorButton(f, true));
					floorView.setFloorButton(IFloorView.FLOOR_BUTTON_DOWN, sys.getFloorButton(f, false));
				} catch (FloorException ex) {
					LOG.severe(ex.getMessage());
				}
			}
		}
	}
}