package at.fhhagenberg.sqe.project.sqelevator.communication;

import java.rmi.Naming;
import java.rmi.RemoteException;

import sqelevator.IElevator;

import com.sun.istack.internal.logging.Logger;

public class ElevatorSimCommunication implements IElevatorStatus,
		IElevatorControl
{
	private static Logger LOG = Logger
			.getLogger(ElevatorSimCommunication.class);

	private IElevator mRemote;

	public boolean connect(String rmiName)
	{
		try
		{
			mRemote = (IElevator) Naming.lookup(rmiName);
			return (mRemote != null);
		}
		catch (Exception e)
		{
			LOG.warning("cant connect to remotes.");
			LOG.warning(e.getMessage());
		}
		return false;
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction)
	{
		try
		{
			mRemote.setCommittedDirection(elevatorNumber, direction);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}
	}

	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service)
	{
		try
		{
			mRemote.setServicesFloors(elevatorNumber, floor, service);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}
	}

	@Override
	public void setTarget(int elevatorNumber, int target)
	{
		try
		{
			mRemote.setTarget(elevatorNumber, target);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}
	}

	@Override
	public int getCommittedDirection(int elevatorNumber)
	{
		try
		{
			int res = mRemote.getCommittedDirection(elevatorNumber);
			assert (res == IElevator.ELEVATOR_DIRECTION_UNCOMMITTED)
					|| (res == IElevator.ELEVATOR_DIRECTION_DOWN)
					|| (res == IElevator.ELEVATOR_DIRECTION_UP) : "unexpected direction recieved from remote";
			return res;
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return IElevator.ELEVATOR_DIRECTION_UNCOMMITTED;
	}

	@Override
	public int getElevatorAccel(int elevatorNumber)
	{
		try
		{
			return mRemote.getElevatorAccel(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor)
	{
		try
		{
			return mRemote.getElevatorButton(elevatorNumber, floor);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return false;
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber)
	{
		try
		{
			int res = mRemote.getElevatorDoorStatus(elevatorNumber);
			assert (res == IElevator.ELEVATOR_DOORS_CLOSED)
				|| (res == IElevator.ELEVATOR_DOORS_CLOSING)
				|| (res == IElevator.ELEVATOR_DOORS_OPEN)
				|| (res == IElevator.ELEVATOR_DOORS_OPENING) : "unexpected door status recieved from remote";
			return res;
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return IElevator.ELEVATOR_DOORS_CLOSED;
	}

	@Override
	public int getElevatorFloor(int elevatorNumber)
	{
		try
		{
			return mRemote.getElevatorFloor(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public int getElevatorNum()
	{
		try
		{
			return mRemote.getElevatorNum();
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public int getElevatorPosition(int elevatorNumber)
	{
		try
		{
			return mRemote.getElevatorPosition(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber)
	{
		try
		{
			return mRemote.getElevatorSpeed(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public int getElevatorWeight(int elevatorNumber)
	{
		try
		{
			return mRemote.getElevatorWeight(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public int getElevatorCapacity(int elevatorNumber)
	{
		try
		{
			return mRemote.getElevatorCapacity(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public boolean getFloorButtonDown(int floor)
	{
		try
		{
			return mRemote.getFloorButtonDown(floor);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return false;
	}

	@Override
	public boolean getFloorButtonUp(int floor)
	{
		try
		{
			return mRemote.getFloorButtonUp(floor);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return false;
	}

	@Override
	public int getFloorHeight()
	{
		try
		{
			return mRemote.getFloorHeight();
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public int getFloorNum()
	{
		try
		{
			return mRemote.getFloorNum();
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor)
	{
		try
		{
			return mRemote.getServicesFloors(elevatorNumber, floor);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return false;
	}

	@Override
	public int getTarget(int elevatorNumber)
	{
		try
		{
			return mRemote.getTarget(elevatorNumber);
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}

	@Override
	public long getClockTick()
	{
		try
		{
			return mRemote.getClockTick();
		}
		catch (RemoteException e)
		{
			LOG.warning(e.getMessage());
		}

		return 0;
	}
}
