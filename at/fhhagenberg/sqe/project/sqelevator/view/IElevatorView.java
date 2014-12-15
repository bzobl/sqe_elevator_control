package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.event.ActionListener;

public interface IElevatorView
{
	/**
	 * valid directions for setDirection()
	 */
	public final static int DIRECTION_UP = 0;
	public final static int DIRECTION_DOWN = 1;
	public final static int DIRECTION_NONE = 2;

	/**
	 * set direction of elevator.
	 * 
	 * @param dir
	 *            any of DIRECTION_xxx
	 */
	public void setDirection(int dir);

	/**
	 * get floor view of a given floor number.
	 * 
	 * @param num
	 *            floor number
	 * @return floor or null if number is not valid
	 */
	public IFloorView getFloorView(int num);

	/**
	 * set position of elevator.
	 * 
	 * @param position
	 */
	public void setPosition(int position);

	/**
	 * set acceleration of elevator
	 * 
	 * @param acc
	 */
	public void setAcceleration(int acc);

	/**
	 * set payload of elevator
	 * 
	 * @param payload
	 */
	public void setPayload(int payload);

	/**
	 * set current speed of elevator
	 * 
	 * @param speed
	 */
	public void setSpeed(int speed);
	
	/**
	 * add a action listener to the mode button
	 * @param l	action listener
	 */
	public void addModeButtonListener(ActionListener l);
	
	/**
	 * remove a action listener from the mode button
	 * @param l action listener
	 */
	public void removeModeButtonListener(ActionListener l);

	int getElevatorNumber();
}
