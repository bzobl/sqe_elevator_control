/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import at.fhhagenberg.sqe.project.sqelevator.controller.ElevatorControl;

import com.sun.istack.internal.logging.Logger;

/** Bootstrapper
 * 
 */
public class Bootstrapper {
	private static Logger LOG = Logger.getLogger(Bootstrapper.class); 

	public static void main(String[] args) {
		LOG.info("starting up");

		ElevatorControl ctrl;

		try {
			ctrl = new ElevatorControl();
		} catch (RemoteException e) {
			LOG.severe("RemoteException: " + e.getMessage());
			return;
		}
		
		ctrl.showGui();
	}
}
