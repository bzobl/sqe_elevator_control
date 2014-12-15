/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

import com.sun.istack.internal.logging.Logger;

public class PollingTask extends TimerTask {
	
	private static Logger LOG = Logger.getLogger(PollingTask.class); 
	
	private ElevatorSystem mElevators;
	private Timer timer;
	
	public void setElevatorSystem(ElevatorSystem sys)
	{
		mElevators = sys;
	}
	
	public void startPolling(long period)
	{
		timer.scheduleAtFixedRate(this, 0, period);
	}
	
	public void stopPolling()
	{
		timer.cancel();
	}

	@Override
	public void run() {
		if (mElevators == null) {
			stopPolling();
			LOG.info("Elevator system was deleted: stopping to poll");
			return;
		}

		IElevator conn = mElevators.ElevatorConnection;
		try {
			for (int floor = 0; floor < mElevators.NUM_FLOORS; floor++) {
				mElevators.setUpButton(floor, conn.getFloorButtonUp(floor));
				mElevators.setDownButton(floor, conn.getFloorButtonDown(floor));
			}
	
			for (int num = 0; num < mElevators.NUM_ELEVATORS; num++) {
				Elevator elevator = mElevators.Elevators[num];
				elevator.setDirection(conn.getCommittedDirection(num));
				elevator.setAcceleration(conn.getElevatorAccel(num));
				elevator.setDoorstatus(conn.getElevatorDoorStatus(num));
				elevator.setFloor(conn.getElevatorFloor(num));
				elevator.setPosition(conn.getElevatorPosition(num));
				elevator.setSpeed(conn.getElevatorSpeed(num));
				elevator.setWeight(conn.getElevatorWeight(num));
				elevator.setTargetFloor(conn.getTarget(num));

				for (int floor = 0; floor < mElevators.NUM_FLOORS; floor++) {
					elevator.setButtonStatus(floor, conn.getElevatorButton(num, floor));
                    elevator.setServicesFloors(floor, conn.getServicesFloors(num, floor));
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			LOG.warning("Elevator Connection lost");
		}
		
		mElevators.pollingComplete();
	}
}