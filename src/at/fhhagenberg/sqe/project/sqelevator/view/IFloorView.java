package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.event.ActionListener;

public interface IFloorView
{
	/**
	 * get current status of a floor button.
	 * 
	 * @param btn
	 *            any of FLOOR_BUTTON_xxx
	 * @return true, if active, else false
	 */
	public boolean getFloorButton(int btn);

	/**
	 * set status of floor buttons.
	 * 
	 * @param btn
	 *            any of FLOOR_BUTTON_xxx
	 * @param status
	 *            true to set active, else false.
	 */
	public void setFloorButton(int btn, boolean status);

	/**
	 * add action listener for call button.
	 * 
	 * @param l
	 *            action listener
	 */
	public void addCallButtonListener(ActionListener l);

	/**
	 * remove action listener from call button
	 * 
	 * @param l
	 *            action listener
	 */
	public void removeCallButtonListener(ActionListener l);

	/**
	 * set move status for elevator. See MOVE_STATUS_xxx for valid states.
	 * 
	 * @param moveStatus
	 *            state to set.
	 */
	public void setMoveStatus(int moveStatus);

	/**
	 * Get current move status.
	 * 
	 * @return any of MOVE_STATUS_xxx
	 */
	public int getMoveStatus();

	/**
	 * enables or disables call button.
	 * 
	 * @param on
	 *            enable if true, disable if false
	 */
	public void enableCallButton(boolean on);

	/**
	 * set status for elevator icon. see ELEVATOR_STATUS_xxx for valid states.
	 * 
	 * @param elevatorStatus
	 *            status to set.
	 */
	public void setElevatorStatus(int elevatorStatus);

	/**
	 * get current elevator icon status.
	 * 
	 * @return any of ELEVATOR_STATUS_xxx
	 */
	public int getElevatorStatus();

	/**
	 * floor number of current floor
	 */
	public int getFloorNumber();

	/**
	 * add action listener to the services button
	 * 
	 * @param l
	 *            action listener
	 */
	public void addServiceButtonListener(ActionListener l);

	/**
	 * remove a action listener from the services button
	 * 
	 * @param l
	 *            action listener
	 */
	public void removeServiceButtonListener(ActionListener l);

	/**
	 * set the services status
	 * 
	 * @param on
	 *            status
	 */
	public void setServiceStatus(boolean on);

	/**
	 * get the services status of the floor
	 */
	public boolean getServiceStatus();

	/**
	 * different elevator modes. ELEVATOR_STATUS_CLOSED: doors are closed
	 * ELEVATOR_STATUS_CLOSING: doors are closing ELEVATOR_STATUS_OPENED: doors
	 * are opened ELEVATOR_STATUS_OPENING: doors are opening
	 * ELEVATOR_STATUS_AWAY: elevator is not in this floor. icon is hidden.
	 */
	public final static int ELEVATOR_STATUS_CLOSED = 0;
	public final static int ELEVATOR_STATUS_CLOSING = 1;
	public final static int ELEVATOR_STATUS_OPENED = 2;
	public final static int ELEVATOR_STATUS_OPENING = 3;
	public final static int ELEVATOR_STATUS_AWAY = 4;
	public final static int ELEVATOR_STATUS_TARGET = 5;
	public final static int ELEVATOR_STATUS_OUT_OF_ORDER = 6;

	/**
	 * move status of elevator. MOVE_STATUS_UP: elevator is moving to upper
	 * floor. MOVE_STATUS_DOWN: elevator is moving to lower floor.
	 * MOVE_STATUS_STANDING: elevator is in current floor and does not move.
	 * MOVE_STATUS_AWAY: elevator is not in this floor. icon is hidden.
	 */
	public final static int MOVE_STATUS_UP = 0;
	public final static int MOVE_STATUS_DOWN = 1;
	public final static int MOVE_STATUS_STANDING = 2;
	public final static int MOVE_STATUS_AWAY = 3;

	/**
	 * floor buttons
	 */
	public final static int FLOOR_BUTTON_UP = 0;
	public final static int FLOOR_BUTTON_DOWN = 1;
}
