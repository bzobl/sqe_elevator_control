/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Observable;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

import com.sun.istack.internal.logging.Logger;

public class ElevatorSystem extends Observable {
	
	private static Logger LOG = Logger.getLogger(ElevatorSystem.class);

	private static final int SYSTEM_PROPERTY_CHANGED = -1;

	private final int NUM_ELEVATORS;
	private final int NUM_FLOORS;
	private final int FLOOR_HEIGHT;

	protected IElevator ElevatorConnection;
	protected Elevator Elevators[];

	private boolean mUpButtons[];
	private boolean mDownButtons[];

	private boolean mChanged;
	
	public ElevatorSystem(IElevator connection, PollingTask p_task) throws java.rmi.RemoteException, MalformedURLException, NotBoundException {
        ElevatorConnection = connection;

		NUM_FLOORS = ElevatorConnection.getFloorNum();
		FLOOR_HEIGHT = ElevatorConnection.getFloorHeight();
		NUM_ELEVATORS = ElevatorConnection.getElevatorNum();

		Elevators = new Elevator[NUM_ELEVATORS];
		mUpButtons = new boolean[NUM_FLOORS];
		mDownButtons = new boolean[NUM_FLOORS];
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = ElevatorConnection.getElevatorCapacity(i);
			Elevators[i] = new Elevator(capacity, NUM_FLOORS);
		}

		for (int i = 0; i < NUM_FLOORS; i++) {
			mUpButtons[i] = false;
			mDownButtons[i] = false;
		}

		p_task.setElevatorSystem(this);
		p_task.startPolling(ElevatorConnection.getClockTick());
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

	private void checkElevator(int elevator) throws ElevatorException {
		if ((elevator < 0) || (elevator >= NUM_ELEVATORS)) {
			throw new ElevatorException(elevator);
		}
	}

	private void checkFloor(int floor) throws FloorException {
		if ((floor < 0) || (floor >= NUM_FLOORS)) {
			throw new FloorException(floor);
		}
	}

	public void setCommitedDirection(int elevator, int direction) {
		try {
			ElevatorConnection.setCommittedDirection(elevator, direction);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	public void setServicesFloors(int elevator, int floor, boolean enable) {
		try {
			ElevatorConnection.setServicesFloors(elevator, floor, enable);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	public void setTarget(int elevator, int target) {
		try {
			ElevatorConnection.setTarget(elevator, target);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	public int getTargetFloor(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getTargetFloor();
	}

	public int getDirection(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getDirection();
	}

	public int getAcceleration(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getAcceleration();
	}

	public boolean getButtonStatus(int elevator, int floor) throws ElevatorException, FloorException {
		checkElevator(elevator);
		checkFloor(floor);

        return Elevators[elevator].getButtonStatus(floor);
	}

	public int getDoorstatus(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getDoorstatus();
	}

	public int getFloor(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getFloor();
	}

	public boolean getFloorButton(int floor, boolean up) throws FloorException {
		checkFloor(floor);

		if (up) {
			return mUpButtons[floor];
		} else {
			return mDownButtons[floor];
		}
	}

	public int getPosition(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getPosition();
	}

	public int getSpeed(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getSpeed();
	}

	public int getWeight(int elevator) throws ElevatorException {
		checkElevator(elevator);

        return Elevators[elevator].getWeight();
	}
	
	protected void setUpButton(int floor, boolean pressed) {
		assert(floor < mUpButtons.length);
		if (mUpButtons[floor] != pressed) {
			mChanged = true;
			mUpButtons[floor] = pressed;
		}
	}

	protected void setDownButton(int floor, boolean pressed) {
		assert(floor < mDownButtons.length);
		if (mDownButtons[floor] != pressed) {
			mChanged = true;
			mDownButtons[floor] = pressed;
		}
	}
	
	public void pollingComplete() {
		if (mChanged) {
			hasChanged();
			notifyObservers(new Integer(SYSTEM_PROPERTY_CHANGED));
		}
		
		for (int e = 0; e < NUM_ELEVATORS; e++) {
			if (Elevators[e].hasChanged()) {
				hasChanged();
				notifyObservers(new Integer(e));
			}
		}
		
	}
}
