/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

import com.sun.istack.internal.logging.Logger;

public class ElevatorSystem extends Observable {
	
	private static Logger LOG = Logger.getLogger(ElevatorSystem.class);

	private final int NUM_ELEVATORS;
	private final int NUM_FLOORS;
	private final int FLOOR_HEIGHT;

	private IElevator mElevatorConnection;
	private Elevator mElevators[];

	private Thread mPollingThread;

	protected boolean mUpButtons[];
	protected boolean mDownButtons[];
	
	public ElevatorSystem(String elevator_url) throws java.rmi.RemoteException, MalformedURLException, NotBoundException {
        mElevatorConnection = (IElevator) Naming.lookup(elevator_url);

		NUM_FLOORS = mElevatorConnection.getFloorNum();
		FLOOR_HEIGHT = mElevatorConnection.getFloorHeight();
		NUM_ELEVATORS = mElevatorConnection.getElevatorNum();

		mElevators = new Elevator[NUM_ELEVATORS];
		mUpButtons = new boolean[NUM_FLOORS];
		mDownButtons = new boolean[NUM_FLOORS];
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = mElevatorConnection.getElevatorCapacity(i);
			mElevators[i] = new Elevator(capacity, NUM_FLOORS);
		}

		for (int i = 0; i < NUM_FLOORS; i++) {
			mUpButtons[i] = false;
			mDownButtons[i] = false;
		}

		//TODO implement PollingThread oder einfach timer mit aufruf von pollfunktion, is wahrscheinlich einfacher
	}
	
	public int getFloorHeight() {
		return FLOOR_HEIGHT;
	}

	public int getNumFloors() {
		return NUM_FLOORS;
	}

	public int getNumElevators() {
		return NUM_ELEVATORS;
	}

	private void checkElevator(int elevator) throws ElevatorException
	{
		if ((elevator < 0) || (elevator >= NUM_ELEVATORS)) {
			throw new ElevatorException(elevator);
		}
	}

	private void checkFloor(int floor) throws FloorException
	{
		if ((floor < 0) || (floor >= NUM_FLOORS)) {
			throw new FloorException(floor);
		}
	}

	public void setCommitedDirection(int elevator, int direction)
	{
		try {
			mElevatorConnection.setCommittedDirection(elevator, direction);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	public void setServicesFloors(int elevator, int floor, boolean enable)
	{
		try {
			mElevatorConnection.setServicesFloors(elevator, floor, enable);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	public void setTarget(int elevator, int target)
	{
		try {
			mElevatorConnection.setTarget(elevator, target);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	public int getTargetFloor(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getTargetFloor();
	}

	public int getDirection(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getDirection();
	}

	public int getAcceleration(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getAcceleration();
	}

	public boolean getButtonStatus(int elevator, int floor) throws ElevatorException, FloorException
	{
		checkElevator(elevator);
		checkFloor(floor);

        return mElevators[elevator].getButtonStatus(floor);
	}

	public int getDoorstatus(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getDoorstatus();
	}

	public int getFloor(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getFloor();
	}

	public boolean getFloorButton(int floor, boolean up) throws FloorException
	{
		checkFloor(floor);

		if (up) {
			return mUpButtons[floor];
		} else {
			return mDownButtons[floor];
		}
	}

	public int getPosition(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getPosition();
	}

	public int getSpeed(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getSpeed();
	}

	public int getWeight(int elevator) throws ElevatorException
	{
		checkElevator(elevator);

        return mElevators[elevator].getWeight();
	}
}
