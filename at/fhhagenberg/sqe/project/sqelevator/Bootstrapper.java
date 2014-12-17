/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorControl;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;

public class Bootstrapper {
	private static Logger LOG = Logger.getLogger(Bootstrapper.class); 

	public static void main(String[] args) {
		
		LOG.info("starting up");

		final int num_elevators = 3;
		final int num_floors = 5;
		
		SimpleElevatorSimulator adbt = new SimpleElevatorSimulator(num_elevators, num_floors);
		
		IElevatorConnection conn = adbt;
		LOG.info("using " + conn.getClass().getCanonicalName() + " as connection interface");
		ElevatorControl ctrl = new ElevatorControl(conn);
		
		MainView view = new MainView(ctrl, num_elevators, num_floors);
		ctrl.setView(view);
		ctrl.updateAll();

		view.setVisible(true);
		
	}
}
