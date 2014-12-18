/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import at.fhhagenberg.sqe.project.sqelevator.communication.ElevatorSimCommunication;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorControlCenter;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;

public class Bootstrapper {
	private static Logger LOG = Logger.getLogger(Bootstrapper.class); 

	private static final String APPLICATION_NAME = "E2C2 - Extended Elevator Control Center";
	
	public static void main(String[] args) {
		
		LOG.info("starting up");
		
		final int num_elevators = 3;
		final int num_floors = 5;
		
		//SimpleElevatorSimulator sim = new SimpleElevatorSimulator(num_elevators, num_floors);
		
		ElevatorSimCommunication sim = new ElevatorSimCommunication();
		
		final String rmiName = "rmi://10.29.17.240/ElevatorSim";
		
		
		
		if (sim.connect(rmiName) == false)
		{
			//sim = new SimpleElevatorSimulator(4, 4);
			LOG.info("simple simulator used");
		}
		
		LOG.info("using " + sim.getClass().getCanonicalName() + " as connection interface");
		ElevatorControlCenter ctrl = new ElevatorControlCenter(sim, sim);
		MainView view = new MainView(ctrl, sim.getElevatorNum(), sim.getFloorNum(), APPLICATION_NAME);
		ctrl.setView(view);
		ctrl.updateView();

		view.setVisible(true);
	}
}
