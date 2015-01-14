/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.view;

/** IMainView
 * 
 */
public interface IMainView {
	public IElevatorView getElevatorView(int num);
	public void setVisible(boolean visible);
	public void setStatusText(String statusText);
	public void setNumFloors(int n);
	public void setNumElevators(int n);
	public void resetView();	
}