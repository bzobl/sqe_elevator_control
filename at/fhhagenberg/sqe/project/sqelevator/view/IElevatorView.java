package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.event.ActionListener;

public interface IElevatorView
{
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
	 * 
	 * @param l
	 *            action listener
	 */
	public void addModeButtonListener(ActionListener l);

	/**
	 * remove a action listener from the mode button
	 * 
	 * @param l
	 *            action listener
	 */
	public void removeModeButtonListener(ActionListener l);

	/**
	 * get elevator number of this elevator
	 * 
	 * @return number
	 */
	public int getElevatorNumber();
}
