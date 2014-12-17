/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;

import com.sun.istack.internal.logging.Logger;

public class PollingTask extends TimerTask {
	
	private static Logger LOG = Logger.getLogger(PollingTask.class); 
	
	protected IElevatorStatus mConnection;
	private ElevatorSystem mElevators;
	private Timer mTimer = new Timer();
	
	protected PollingTask() {
	}

	public PollingTask(IElevatorStatus connection) {
		mConnection = connection;
	}
	
	@Override
	public void run() {
		if (mElevators == null) {
			stopPolling();
			LOG.warning("Elevator system was deleted: stopping to poll");
			return;
		}

		if (mConnection == null) {
			stopPolling();
			LOG.warning("No connection set: stopping to poll");
			return;
		}

		try {
			for (int floor = 0; floor < mElevators.NUM_FLOORS; floor++) {
				mElevators.setUpButton(floor, mConnection.getFloorButtonUp(floor));
				mElevators.setDownButton(floor, mConnection.getFloorButtonDown(floor));
			}
	
			for (int num = 0; num < mElevators.NUM_ELEVATORS; num++) {
				Elevator elevator = mElevators.Elevators[num];
				elevator.setDirection(mConnection.getCommittedDirection(num));
				elevator.setAcceleration(mConnection.getElevatorAccel(num));
				elevator.setDoorstatus(mConnection.getElevatorDoorStatus(num));
				elevator.setFloor(mConnection.getElevatorFloor(num));
				elevator.setPosition(mConnection.getElevatorPosition(num));
				elevator.setSpeed(mConnection.getElevatorSpeed(num));
				elevator.setWeight(mConnection.getElevatorWeight(num));
				elevator.setTargetFloor(mConnection.getTarget(num));

				for (int floor = 0; floor < mElevators.NUM_FLOORS; floor++) {
					elevator.setButtonStatus(floor, mConnection.getElevatorButton(num, floor));
                    elevator.setServicesFloors(floor, mConnection.getServicesFloors(num, floor));
				}
			}
		} catch (FloorException e) {
			LOG.warning("Accessed invalid floor: " + e.getMessage());
		}
		
		mElevators.pollingComplete();
	}
	
	public void setElevatorSystem(ElevatorSystem sys)
	{
		mElevators = sys;
	}
	
	public void startPolling(long period)
	{
		mTimer.scheduleAtFixedRate(this, 0, period);
	}

	public void stopPolling()
	{
		mTimer.cancel();
	}
}