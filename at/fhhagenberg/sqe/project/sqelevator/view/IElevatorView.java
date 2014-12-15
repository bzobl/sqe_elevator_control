package at.fhhagenberg.sqe.project.sqelevator.view;

public interface IElevatorView
{
	public final static int DIRECTION_UP = 0;
	public final static int DIRECTION_DOWN = 1;
	public final static int DIRECTION_NONE = 2;
	
	public IFloorView getFloorView(int num);
	public void setPosition(int position);
	public void setAcceleration(int acc);
	public void setDirection(int dir);
	public void setPayload(int payload);
	public void setSpeed(int speed);
}
