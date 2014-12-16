/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;

import com.sun.istack.internal.logging.Logger;

public class ElevatorSystem extends Observable {
	
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(ElevatorSystem.class);

	public static final int SYSTEM_PROPERTY_CHANGED = -1;
	public static final int ELEVATOR_PROPERTY_CHANGED = -2;

	public final int NUM_ELEVATORS;
	public final int NUM_FLOORS;
	public final int FLOOR_HEIGHT;

	protected Elevator Elevators[];

	private boolean mUpButtons[];
	private boolean mDownButtons[];

	public ElevatorSystem(IElevatorConnection conn) {
		NUM_ELEVATORS = conn.getElevatorNum();
		NUM_FLOORS = conn.getFloorNum();
		FLOOR_HEIGHT = conn.getFloorHeight();

		Elevators = new Elevator[NUM_ELEVATORS];
		mUpButtons = new boolean[NUM_FLOORS];
		mDownButtons = new boolean[NUM_FLOORS];
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = conn.getElevatorCapacity(i);
			Elevators[i] = new Elevator(i, capacity, NUM_FLOORS);
		}

		for (int i = 0; i < NUM_FLOORS; i++) {
			mUpButtons[i] = false;
			mDownButtons[i] = false;
		}
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

	protected void setDownButton(int floor, boolean pressed) {
		assert(floor < mDownButtons.length);
		if (mDownButtons[floor] != pressed) {
			setChanged();
			mDownButtons[floor] = pressed;
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
