/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JToggleButton;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorButtonListener.ListenerType;
import at.fhhagenberg.sqe.project.sqelevator.model.Elevator;
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
	
	public ElevatorControl(IElevatorConnection connection) {
		LOG.info("creating ElevatorConnectionShunt");
		mConnection = connection;
		LOG.info("initializing PollingTask");
        mPollTask = new PollingTask();

		LOG.info("initializing ElevatorControl with Connection Shunt");
        mModel = new ElevatorSystem(mConnection);
		mPollTask.setElevatorSystem(mModel);
        mModel.addObserver(this);
        
        mView = new MainView();
        
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
        	}
        }
        
        LOG.info("Elevator control successfully initialized");
		mPollTask.startPolling(mConnection.getClockTick());

	}

	public void showGui() {
		mView.setVisible(true);
	}

	/**
	 * @param elevator
	 * @param btn
	 */
	public void modeButtonClicked(int elevator, JToggleButton btn) {
		if (btn.isSelected()) {
			btn.setBackground(Color.green);
		} else {
			btn.setBackground(Color.red);
		}
		LOG.info("Mode of Elevator " + elevator + " changed to " + btn.isSelected());
		
		IElevatorView eleView = mView.getElevatorView(elevator);
		if (eleView != null) {
			eleView.getFloorView(0).setElevatorStatus(IFloorView.ELEVATOR_STATUS_OPENED);;
		}
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	public void callButtonClicked(int elevator, int floor, JToggleButton btn) {
		if (btn.isSelected()) {
			btn.setBackground(Color.green);
		} else {
			btn.setBackground(Color.red);
		}
		LOG.info("Call Button of Elevator " + elevator + ", Floor " + floor + " changed to " + btn.isSelected());
		
		IElevatorView eleView = mView.getElevatorView(elevator);
		if (eleView != null) {
			for (int i = 0; i < mModel.NUM_FLOORS; i++) {
				int status = IFloorView.ELEVATOR_STATUS_OPENED;
				if (i != floor) {
					status = IFloorView.ELEVATOR_STATUS_AWAY;
				}
				eleView.getFloorView(i).setElevatorStatus(status);
			}
		}
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	public void serviceButtonClicked(int elevator, int floor, JToggleButton btn) {
		if (btn.isSelected()) {
			btn.setBackground(Color.green);
		} else {
			btn.setBackground(Color.red);
		}
		LOG.info("Service Button of Elevator " + elevator + ", Floor " + floor + " changed to " + btn.isSelected());
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
			
			if (elev.getFloor() == i) {
				elevatorStatus = DOORSTATUS_LUT.get(elev.getDoorstatus());
				moveStatus = DIRECTION_LUT.get(elev.getDirection());
			}
			
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
			
			if (elev.getTargetFloor() == i) {
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
