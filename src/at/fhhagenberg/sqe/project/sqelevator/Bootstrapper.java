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
import at.fhhagenberg.sqe.project.sqelevator.view.ConnectingDialog;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;

public class Bootstrapper {
	private final static Logger LOG = Logger.getLogger(Bootstrapper.class); 

	private static final String APPLICATION_NAME = "E2C2 - Extended Elevator Control Center";
	
	private static IElevatorConnection mSimulator;
		
	public static void main(String[] args) {
		
		// for test only
		final boolean useRemoteSimulator = true;
		
		LOG.info("starting up");
		
		if (useRemoteSimulator)
		{
			LOG.info("use remote simulator");
			String rmiName = "rmi://localhost/ElevatorSim";
			mSimulator = new ElevatorSimCommunication(rmiName);
		}
		else
		{
			LOG.info("use local simulator");
			int numElevators= 3;
			int numFloors = 5;
			mSimulator = new SimpleElevatorSimulator(numElevators, numFloors);
		}		
		
		assert(mSimulator != null) : "simulator must not be null";
		
		MainView mv = new MainView(APPLICATION_NAME);
		ConnectingDialog connDlg = new ConnectingDialog();		
		
		ElevatorControlCenter ctrl = new ElevatorControlCenter(mSimulator);
		mv.setController(ctrl);
		ctrl.setView(mv);
		ctrl.setConnectingDialog(connDlg);
		
		// start
		ctrl.waitForConnection();
	}
}
