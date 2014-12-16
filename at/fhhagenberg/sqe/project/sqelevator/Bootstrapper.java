/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import at.fhhagenberg.sqe.project.sqelevator.communication.ElevatorAdaptor;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.tests.model.ElevatorConnectionShunt;

import com.sun.istack.internal.logging.Logger;

public class Bootstrapper {
	private static Logger LOG = Logger.getLogger(Bootstrapper.class); 

	public static void main(String[] args) {
		
		LOG.info("starting up");

		final int num_elevators = 3;
		final int num_floors = 5;
		final int floor_height = 6;
		final int period = 1000;
		final int capacity = 100;

		ElevatorConnectionShunt elevcon = new ElevatorConnectionShunt(num_floors, floor_height, period, capacity);
		elevcon.NUM_ELEVATORS = num_elevators;
        elevcon.ElevatorAccel = 1;
        elevcon.Floor = 2;
        elevcon.ServicesFloors[1] = false;
        elevcon.FloorButtonUp[3] = true;
        elevcon.FloorButtonDown[1] = true;
		
		ElevatorAdaptor adbt = new ElevatorAdaptor(num_elevators, num_floors);
		
		IElevatorConnection conn = adbt;
		LOG.info("using " + conn.getClass().getCanonicalName() + " as connection interface");
		ElevatorControl ctrl = new ElevatorControl(conn);

		ctrl.showGui();
		
	}
}
