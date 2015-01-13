/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import javax.swing.JDialog;

import at.fhhagenberg.sqe.project.sqelevator.communication.ElevatorSimCommunication;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.communication.SimpleElevatorSimulator;
import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorControlCenter;
import at.fhhagenberg.sqe.project.sqelevator.view.ConnectingDialog;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

import com.sun.istack.internal.logging.Logger;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

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
		
		connectAndStart();
	}
	
	static public void connectAndStart()
	{
		assert(mSimulator != null) : "Simulator must not be null!";
		
		ConnectingDialog cd = new ConnectingDialog(mSimulator);
		
		// Wait for connection
		if (cd.waitForConnection() == true)
		{
			LOG.info("connected!");
		}
		else
		{
			LOG.warning("couldn't connect! Exit");
			System.exit(0);
		}
		cd.dispose();
		
		ElevatorControlCenter ctrl = new ElevatorControlCenter(mSimulator);
		MainView view = new MainView(ctrl, mSimulator.getElevatorNum(), mSimulator.getFloorNum(), APPLICATION_NAME);
		ctrl.setView(view);
		ctrl.updateView();
		
		view.setVisible(true);
	}
}
