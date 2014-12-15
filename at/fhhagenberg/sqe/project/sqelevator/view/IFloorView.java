package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.event.ActionListener;

public interface IFloorView
{
	public boolean GetFloorButton(int btn);
	public void SetFloorButton(int btn, boolean status);
	public void AddCallButtonListener(ActionListener l);
	public void RemoveCallButtonListener(ActionListener l);
	public void SetMoveStatus(int moveStatus);
	public int GetMoveStatus();
	public void EnableCallButton(boolean on);
	public void SetElevatorStatus(int elevatorStatus);
	public int GetElevatorStatus();
	public int GetFloorNumber();
	public void AddServiceButtonListener(ActionListener l);
	public void RemoveServiceButtonListener(ActionListener l);
	public void SetServiceStatus(boolean on);
	public boolean GetServiceStatus();
	
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
