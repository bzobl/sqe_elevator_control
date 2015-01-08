/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorStatus;

import com.sun.istack.internal.logging.Logger;

public class ElevatorSystem extends Observable implements IElevatorSystem {
	
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ElevatorSystem.class);

	public final int NUM_ELEVATORS;
	public final int NUM_FLOORS;
	public final int FLOOR_HEIGHT;

	protected Elevator mElevators[];

	private boolean mUpButtons[];
	private boolean mDownButtons[];

	public ElevatorSystem(IElevatorStatus status) {
		NUM_ELEVATORS = status.getElevatorNum();
		NUM_FLOORS = status.getFloorNum();
		FLOOR_HEIGHT = status.getFloorHeight();

		mElevators = new Elevator[NUM_ELEVATORS];
		mUpButtons = new boolean[NUM_FLOORS];
		mDownButtons = new boolean[NUM_FLOORS];
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = status.getElevatorCapacity(i);
			mElevators[i] = new Elevator(i, capacity, NUM_FLOORS);
		}

		for (int i = 0; i < NUM_FLOORS; i++) {
			mUpButtons[i] = false;
			mDownButtons[i] = false;
		}
		
		startPolling(status);
	}

	protected void startPolling(IElevatorStatus status) {
        PollingTask pTask = new PollingTask(status);
		pTask.setElevatorSystem(this);
		pTask.startPolling(status.getClockTick());
	}
	
	/* (non-Javadoc)
	 * @see at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem#addObserver(java.util.Observer)
	 */
	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		for (int e = 0; e < NUM_ELEVATORS; e++) {
			mElevators[e].addObserver(o);
		}
	}

	@Override
	public int getNumberOfElevators() {
		return NUM_ELEVATORS;
	}
	
	@Override
	public int getNumberOfFloors() {
		return NUM_FLOORS;
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

	/* (non-Javadoc)
	 * @see at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem#getElevator(int)
	 */
	@Override
	public IElevatorModel getElevator(int num) throws ElevatorException {
		checkElevator(num);
		return mElevators[num];
	}

	/* (non-Javadoc)
	 * @see at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem#getFloorButton(int, boolean)
	 */
	@Override
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
        notifyObservers(SYSTEM_PROPERTY_CHANGED);
		
		for (int e = 0; e < NUM_ELEVATORS; e++) {
			mElevators[e].notifyObservers(ELEVATOR_PROPERTY_CHANGED);
		}
	}

	protected void setDownButton(int floor, boolean pressed) {
		assert ((floor >= 0) && (floor < mDownButtons.length));
		if (mDownButtons[floor] != pressed) {
			setChanged();
			mDownButtons[floor] = pressed;
		}
	}

	protected void setUpButton(int floor, boolean pressed) {
		assert ((floor >= 0) && floor < mUpButtons.length);
		if (mUpButtons[floor] != pressed) {
			setChanged();
			mUpButtons[floor] = pressed;
		}
	}
}
