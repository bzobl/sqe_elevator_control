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

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;
import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.PollingTask;
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
	
	PollingTask mPollTask;
	IElevatorControl mControl;
	ElevatorSystem mModel;
	IMainView mView;
	
	boolean mAuto[];

	IElevatorAlgorithm mAutoAlgo;
	IElevatorAlgorithm mManuAlgo;
	
	public ElevatorControlCenter(IElevatorControl control, IElevatorStatus status) {
		mControl = control;

        mModel = new ElevatorSystem(status);
        mModel.addObserver(this);

        mPollTask = new PollingTask(status);
		mPollTask.setElevatorSystem(mModel);
		
		mAuto = new boolean[mModel.NUM_ELEVATORS];
		
		mAutoAlgo = null;
		mManuAlgo = new ManualElevatorAlgorithm(mModel, mControl);
        
        LOG.info("Elevator control successfully initialized, starting to poll");
		mPollTask.startPolling(status.getClockTick());
	}

	/**
	 * @return whether or not any elevator is in auto mode
	 */
	private boolean isAnyElevatorAuto() {
		for (int e = 0; e < mModel.NUM_ELEVATORS; e++) {
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
		
		for (int i = 0; i < mModel.NUM_FLOORS; i++) {
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
        	for (int i = 0; i < mModel.NUM_ELEVATORS; i++) {
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
		if (arg.equals(ElevatorSystem.SYSTEM_PROPERTY_CHANGED)) {
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
		} else if (arg.equals(ElevatorSystem.ELEVATOR_PROPERTY_CHANGED)) {
			Elevator elev = (Elevator) o;
			if (mView != null) {
				updateView(elev);
			} else {
				LOG.info("Elevator updated, but no view is set");
			}
			if (mAutoAlgo != null) {
				if (mAuto[elev.NUM]) {
					updateAlgorithm(elev);
				}
			} else {
				LOG.info("Elevator updated, but no auto algo is set");
				
			}
		} else {
			LOG.warning("Unexpected class notified ElevatorControl: " + o.getClass().getCanonicalName());
		}
	}

	/**
	 * @param elev
	 */
	private void updateAlgorithm(Elevator elev) {
		assert (mAutoAlgo != null) : "Auto algorithm not set";
		assert (mAuto[elev.NUM]) : "elevator is in manual mode";

		for (int f = 0; f < mModel.NUM_FLOORS; f++) {
			try {
				if (elev.getButtonStatus(f)) {
					mAutoAlgo.setElevatorRequest(elev.NUM, f);
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

		for (int f = 0; f < mModel.NUM_FLOORS; f++) {
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

	private void updateView(Elevator elev) {
		assert (mView != null) : "update of view requested when no view was set";
		
		IElevatorView eleView = mView.getElevatorView(elev.NUM);
		if (eleView == null) {
			LOG.severe("Could not find ElevatorView for elevator " + elev.NUM);
			return;
		}

		eleView.setPosition(elev.getPosition());
		eleView.setAcceleration(elev.getAcceleration());
		eleView.setPayload(elev.getWeight());
		eleView.setSpeed(elev.getSpeed());
		
		for (int i = 0; i < mModel.NUM_FLOORS; i++) {
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

		for (int e = 0; e < mModel.NUM_ELEVATORS; e++) {
			for (int f = 0; f < mModel.NUM_ELEVATORS; f++) {
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
