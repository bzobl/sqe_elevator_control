/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.project.sqelevator.IElevator;

import com.sun.istack.internal.logging.Logger;

public class ElevatorSystem extends Observable {
	
	private static Logger LOG = Logger.getLogger(ElevatorSystem.class);

	public static final int SYSTEM_PROPERTY_CHANGED = -1;
	public static final int ELEVATOR_PROPERTY_CHANGED = -2;

	public final int NUM_ELEVATORS;
	public final int NUM_FLOORS;
	public final int FLOOR_HEIGHT;

	protected IElevator ElevatorConnection;
	protected Elevator Elevators[];

	private boolean mUpButtons[];
	private boolean mDownButtons[];

	public ElevatorSystem(IElevator connection, PollingTask p_task) throws RemoteException {
        ElevatorConnection = connection;

		NUM_FLOORS = ElevatorConnection.getFloorNum();
		FLOOR_HEIGHT = ElevatorConnection.getFloorHeight();
		NUM_ELEVATORS = ElevatorConnection.getElevatorNum();

		Elevators = new Elevator[NUM_ELEVATORS];
		mUpButtons = new boolean[NUM_FLOORS];
		mDownButtons = new boolean[NUM_FLOORS];
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = ElevatorConnection.getElevatorCapacity(i);
			Elevators[i] = new Elevator(i, capacity, NUM_FLOORS);
		}

		for (int i = 0; i < NUM_FLOORS; i++) {
			mUpButtons[i] = false;
			mDownButtons[i] = false;
		}

		p_task.setElevatorSystem(this);
		p_task.startPolling(ElevatorConnection.getClockTick());
	}
	
	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		for (int e = 0; e < NUM_ELEVATORS; e++) {
			Elevators[e].addObserver(o);
		}
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

	/**
	 * @param num Number of the elevator to get
	 * @return Elevator with number num
	 * @throws ElevatorException 
	 */
	public Elevator getElevator(int num) throws ElevatorException {
		checkElevator(num);
		return Elevators[num];
	}

	public boolean getFloorButton(int floor, boolean up) throws FloorException {
		checkFloor(floor);

		if (up) {
			return mUpButtons[floor];
		} else {
			return mDownButtons[floor];
		}
	}

	public void pollingComplete() {
		// will only be triggered if this object was changed
        notifyObservers(new Integer(SYSTEM_PROPERTY_CHANGED));
		
		for (int e = 0; e < NUM_ELEVATORS; e++) {
			Elevators[e].notifyObservers(new Integer(ELEVATOR_PROPERTY_CHANGED));
		}
	}

	public void setCommitedDirection(int elevator, int direction) {
		try {
			ElevatorConnection.setCommittedDirection(elevator, direction);
		} catch (RemoteException e) {
			LOG.warning("RMI interface not connected");
		}
	}

	protected void setDownButton(int floor, boolean pressed) {
		assert(floor < mDownButtons.length);
		if (mDownButtons[floor] != pressed) {
			setChanged();
			mDownButtons[floor] = pressed;
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

	protected void setUpButton(int floor, boolean pressed) {
		assert(floor < mUpButtons.length);
		if (mUpButtons[floor] != pressed) {
			setChanged();
			mUpButtons[floor] = pressed;
		}
	}
}
