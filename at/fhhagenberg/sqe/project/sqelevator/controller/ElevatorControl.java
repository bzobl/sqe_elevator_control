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

import javax.swing.JButton;
import javax.swing.JToggleButton;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorButtonListener.ListenerType;
import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.PollingTask;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IFloorView;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;

public class ElevatorControl implements Observer {
	
	private static Logger LOG = Logger.getLogger(ElevatorControl.class);
	
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
	IElevatorConnection mConnection;
	ElevatorSystem mModel;
	MainView mView;
	
	boolean mAuto[];
	
	public ElevatorControl(IElevatorConnection connection) {
		mConnection = connection;

        mModel = new ElevatorSystem(mConnection);
        mModel.addObserver(this);

        mPollTask = new PollingTask(mConnection);
		mPollTask.setElevatorSystem(mModel);
        
        mView = new MainView();
		initializeView();
		
		mAuto = new boolean[mModel.NUM_ELEVATORS];
        
        LOG.info("Elevator control successfully initialized, starting to poll");
		mPollTask.startPolling(mConnection.getClockTick());
	}

	private void initializeView() {
        for (int e = 0; e < mModel.NUM_ELEVATORS; e++) {
        	IElevatorView eleView = mView.addNewElevator(mModel.NUM_FLOORS);
        	ElevatorButtonListener mbl = new ElevatorButtonListener(ListenerType.MODE_BUTTON_LISTENER, this, e, -1);
        	eleView.addModeButtonListener(mbl);
        	LOG.info("initialization of IElevatorView " + e + " almost done");
        	
        	for (int f = 0; f < mModel.NUM_FLOORS; f++) {
        		IFloorView floorView = eleView.getFloorView(f);
        		
        		ElevatorButtonListener cbl = new ElevatorButtonListener(ListenerType.CALL_BUTTON_LISTENER, this, e, f);
        		ElevatorButtonListener sbl = new ElevatorButtonListener(ListenerType.SERVICE_BUTTON_LISTENER, this, e, f);
        		
        		floorView.addCallButtonListener(cbl);
        		floorView.addServiceButtonListener(sbl);
        		floorView.enableCallButton(true);
        	}
        	
        	try {
				updateElevator(mModel.getElevator(e));
			} catch (ElevatorException ex) {
				LOG.severe(ex.getMessage());
			}
        }
        
        updateElevatorSystem(mModel);
	}

	public void showGui() {
		mView.setVisible(true);
	}

	/**
	 * @param elevator
	 * @param btn
	 */
	public void modeButtonClicked(int elevator, JToggleButton btn) {
		LOG.info("Mode button of Elevator " + elevator + " clicked");
		IElevatorView eleView = mView.getElevatorView(elevator);
		
		mAuto[elevator] = btn.isSelected();
		
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
	public void callButtonClicked(int elevator, int floor, JButton btn) {
		LOG.info("Call Button of Elevator " + elevator + ", Floor " + floor + " clicked");

		assert(mAuto[elevator] == false) : "Call Button cannot be pressed when in auto mode";
		assert((floor < mModel.NUM_FLOORS) && (floor >= 0)) : "floor number is invalid";

		mConnection.setTarget(elevator, floor);

		int curFloor;
		try {
			curFloor = mModel.getElevator(elevator).getFloor();
			if (curFloor < floor) {
				mConnection.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_UP);
			} else if (curFloor > floor) {
				mConnection.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_DOWN);
			} else {
				mConnection.setCommittedDirection(elevator, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
			}
		} catch (ElevatorException e) {
			LOG.severe(e.getMessage());
			return;
		}
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	public void serviceButtonClicked(int elevator, int floor, JToggleButton btn) {
		LOG.info("Service Button of Elevator " + elevator + ", Floor " + floor + " clicked");
		mConnection.setServicesFloors(elevator, floor, btn.isSelected());
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg.equals(ElevatorSystem.SYSTEM_PROPERTY_CHANGED)) {
			LOG.info("Got System update");
			updateElevatorSystem((ElevatorSystem)o);
		} else if (arg.equals(ElevatorSystem.ELEVATOR_PROPERTY_CHANGED)) {
			LOG.info("Got Elevator update");
			updateElevator((Elevator)o);
		} else {
			LOG.warning("Unexpected class notified ElevatorControl: " + o.getClass().getCanonicalName());
		}
	}

	/**
	 * @param elev
	 */
	private void updateElevator(Elevator elev) {
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
			
			
			try {
				if (!elev.getServicesFloors(i)) {
					elevatorStatus = IFloorView.ELEVATOR_STATUS_OUT_OF_ORDER;
					floorView.setServiceStatus(false);
				} else {
					floorView.setServiceStatus(true);
				}
			} catch (FloorException e) {
				LOG.severe(e.getMessage());
			}
			
			if (elev.getFloor() == i) {
				elevatorStatus = DOORSTATUS_LUT.get(elev.getDoorstatus());
				moveStatus = DIRECTION_LUT.get(elev.getDirection());
			} else if ((elev.getTargetFloor() == i)) {
				elevatorStatus = IFloorView.ELEVATOR_STATUS_TARGET;
			}

			floorView.setMoveStatus(moveStatus);
			floorView.setElevatorStatus(elevatorStatus);
		}
	}

	/**
	 * @param sys
	 */
	private void updateElevatorSystem(ElevatorSystem sys) {
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
