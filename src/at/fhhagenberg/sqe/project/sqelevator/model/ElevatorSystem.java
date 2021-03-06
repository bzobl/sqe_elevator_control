/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.model;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;

public class ElevatorSystem extends Observable implements IElevatorSystem {

	public final int NUM_ELEVATORS;
	public final int NUM_FLOORS;
	public final int FLOOR_HEIGHT;

	protected Elevator mElevators[];

	private boolean mUpButtons[];
	private boolean mDownButtons[];

	private boolean mIsConnected = false;
		
	public ElevatorSystem(IElevatorConnection connection) {
		NUM_ELEVATORS = connection.getElevatorNum();
		NUM_FLOORS = connection.getFloorNum();
		FLOOR_HEIGHT = connection.getFloorHeight();

		mElevators = new Elevator[NUM_ELEVATORS];
		mUpButtons = new boolean[NUM_FLOORS];
		mDownButtons = new boolean[NUM_FLOORS];
		
		for (int i = 0; i < NUM_ELEVATORS; i++) {
			int capacity = connection.getElevatorCapacity(i);
			mElevators[i] = new Elevator(i, capacity, NUM_FLOORS);
		}

		for (int i = 0; i < NUM_FLOORS; i++) {
			mUpButtons[i] = false;
			mDownButtons[i] = false;
		}
		
		startPolling(connection);
	}
	
	protected void startPolling(IElevatorConnection conn)
	{
        PollingTask pTask = new PollingTask(conn);
        pTask.setElevatorSystem(this);
		if (!pTask.startPolling(500))
		{
			assert(false) : "Polling task no started!";
		}
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
	
	protected void setConnectionStatus(boolean status)
	{
		if (mIsConnected != status)
		{
			mIsConnected = status;
			setChanged();
			notifyObservers(CONNECTION_POPERTY_CHANGED);
		}
	}
	
	@Override
	public boolean isConnected()
	{
		return mIsConnected;
	}
	
	private long mSimTime = -1;
	
	protected void setSimulationTime(long t)
	{
		if (mSimTime != t)
		{
			mSimTime = t;
			setChanged();
			notifyObservers(SYSTEM_PROPERTY_CHANGED);
		}
	}
	
	public long getSimulationTime()
	{
		return mSimTime;
	}
}
