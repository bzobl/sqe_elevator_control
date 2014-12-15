/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.awt.Color;
import java.rmi.RemoteException;

import javax.swing.JToggleButton;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorButtonListener.ListenerType;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.PollingTask;
import at.fhhagenberg.sqe.project.sqelevator.tests.model.ElevatorConnectionShunt;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IFloorView;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;

public class ElevatorControl {
	
	private static Logger LOG = Logger.getLogger(ElevatorControl.class);
	
	PollingTask mPollTask;
	IElevator mConnection;
	ElevatorSystem mModel;
	MainView mView;
	
	public ElevatorControl() throws RemoteException {
		final int num_elevators = 3;
		final int num_floors = 5;
		final int floor_height = 6;
		final int period = 1000;
		final int capacity = 100;

		LOG.info("creating ElevatorConnectionShunt");
		ElevatorConnectionShunt elevcon = new ElevatorConnectionShunt(num_floors, floor_height, period, capacity);
		elevcon.NUM_ELEVATORS = num_elevators;
		mConnection = elevcon;
		LOG.info("initializing PollingTask");
        mPollTask = new PollingTask();

		LOG.info("initializing ElevatorControl with Connection Shunt");
        mModel = new ElevatorSystem(mConnection, mPollTask);
        
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
	}

	public void showGui() {
		mView.setVisible(true);
	}

	/**
	 * @param mElevatorNum
	 * @param btn
	 */
	public void modeButtonClicked(int mElevatorNum, JToggleButton btn) {
		if (btn.isSelected()) {
			btn.setBackground(Color.green);
		} else {
			btn.setBackground(Color.red);
		}
		LOG.info("Mode of Elevator " + mElevatorNum + " changed to " + btn.isSelected());
		
		IElevatorView eleView = mView.getElevatorView(mElevatorNum);
		if (eleView != null) {
			eleView.getFloorView(0).setElevatorStatus(IFloorView.ELEVATOR_STATUS_OPENED);;
		}
	}

	/**
	 * @param mElevatorNum
	 * @param mFloorNum
	 * @param btn
	 */
	public void callButtonClicked(int mElevatorNum, int mFloorNum, JToggleButton btn) {
		if (btn.isSelected()) {
			btn.setBackground(Color.green);
		} else {
			btn.setBackground(Color.red);
		}
		LOG.info("Call Button of Elevator " + mElevatorNum + ", Floor " + mFloorNum + " changed to " + btn.isSelected());
		
		IElevatorView eleView = mView.getElevatorView(mElevatorNum);
		if (eleView != null) {
			for (int i = 0; i < mModel.NUM_FLOORS; i++) {
				int status = IFloorView.ELEVATOR_STATUS_OPENED;
				if (i != mFloorNum) {
					status = IFloorView.ELEVATOR_STATUS_AWAY;
				}
				eleView.getFloorView(i).setElevatorStatus(status);
			}
		}
	}

	/**
	 * @param mElevatorNum
	 * @param mFloorNum
	 * @param btn
	 */
	public void serviceButtonClicked(int mElevatorNum, int mFloorNum, JToggleButton btn) {
		if (btn.isSelected()) {
			btn.setBackground(Color.green);
		} else {
			btn.setBackground(Color.red);
		}
		LOG.info("Service Button of Elevator " + mElevatorNum + ", Floor " + mFloorNum + " changed to " + btn.isSelected());
	}

}
