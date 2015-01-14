
package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.io.Serializable;

import at.fhhagenberg.sqe.project.sqelevator.view.IMainView;

public interface IControl extends Serializable{

	public void changeControlMode(int elevator, boolean autoMode);
	public void setCallRequest(int elevator, int floor);
	public void setServicedFloor(int elevator, int floor, boolean isServiced);
	
	public void updateView();
	public void setView(IMainView view);
}
