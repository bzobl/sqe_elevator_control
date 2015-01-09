/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import at.fhhagenberg.sqe.project.sqelevator.communication.ElevatorSimCommunication;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorControlCenter;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;

public class Bootstrapper {
	private final static Logger LOG = Logger.getLogger(Bootstrapper.class); 

	private static final String APPLICATION_NAME = "E2C2 - Extended Elevator Control Center";
	
	public static void main(String[] args) {
		
		// for test only
		final boolean useRemoteSimulator = false;
		
		LOG.info("starting up");
		
		IElevatorConnection sim = null;
		
		if (useRemoteSimulator)
		{
			LOG.info("use remote simulator");
			String rmiName = "rmi://192.168.1.111/ElevatorSim";
			sim = new ElevatorSimCommunication(rmiName);
		}
		else
		{
			LOG.info("use local simulator");
			int numElevators= 3;
			int numFloors = 5;
			sim = new SimpleElevatorSimulator(numElevators, numFloors);
		}
		
		assert(sim != null) : "Simulator must not be null!";
		
		ElevatorControlCenter ctrl = new ElevatorControlCenter(sim);
		MainView view = new MainView(ctrl, sim.getElevatorNum(), sim.getFloorNum(), APPLICATION_NAME);
		ctrl.setView(view);
		ctrl.updateView();
		
		view.setVisible(true);
	}
}
