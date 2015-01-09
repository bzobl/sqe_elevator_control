package at.fhhagenberg.sqe.project.sqelevator.communication;

public interface IElevatorConnection extends IElevatorControl, IElevatorStatus
{
	public boolean isConnected();
	public boolean connect();
}