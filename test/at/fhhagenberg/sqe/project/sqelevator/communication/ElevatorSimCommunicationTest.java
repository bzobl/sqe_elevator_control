package at.fhhagenberg.sqe.project.sqelevator.communication;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import sqelevator.IElevator;

public class ElevatorSimCommunicationTest implements IElevator
{
	private ElevatorSimCommunication mEC;
	
	private void setConnectionState(boolean status)
	{
		try
		{
			Field field = ElevatorSimCommunication.class.getDeclaredField("mIsConnected");
			field.setAccessible(true);
			field.set(mEC, status);
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	
	@Before
	public void setUp() throws Exception
	{
		mEC = new ElevatorSimCommunication("TEST");		
		Field field = ElevatorSimCommunication.class.getDeclaredField("mRemote");
		field.setAccessible(true);
		field.set(mEC, this);
	}
	
	@Test
	public void testConnectingFailed()
	{
		assertFalse(mEC.isConnected());
		assertFalse(mEC.connect());
		assertFalse(mEC.isConnected());
	}

	@Test
	public void testGetClockTick()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getClockTick());
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetCommitedDirection()
	{
		setConnectionState(true);	
		assertEquals(ELEVATOR_DIRECTION_UNCOMMITTED, mEC.getCommittedDirection(0));
		assertFalse(mEC.isConnected());
	}

	@Test
	public void testGetElevatorAccel()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorAccel(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorButton()
	{
		setConnectionState(true);	
		assertFalse(mEC.getElevatorButton(0, 0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorDoorStatus()
	{
		setConnectionState(true);	
		assertEquals(ELEVATOR_DOORS_CLOSED, mEC.getElevatorDoorStatus(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorFloor()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorFloor(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorNum()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorFloor(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorPosition()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorPosition(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorSpeed()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorSpeed(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorWeight()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorWeight(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorCapacity()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorCapacity(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetFloorBtnUp()
	{
		setConnectionState(true);	
		assertFalse(mEC.getFloorButtonUp(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetFloorBtnDown()
	{
		setConnectionState(true);	
		assertFalse(mEC.getFloorButtonDown(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetFloorHeight()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getFloorHeight());
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetFloorNum()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getFloorNum());
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetServicesFloor()
	{
		setConnectionState(true);	
		assertFalse(mEC.getServicesFloors(0,0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testGetElevatorNumber()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getElevatorNum());
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testConnectionName()
	{
		assertEquals("TEST", mEC.getConnectionName());
	}
	
	@Test
	public void testGetTarget()
	{
		setConnectionState(true);	
		assertEquals(0, mEC.getTarget(0));
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testSetCommitedDirection()
	{
		setConnectionState(true);	
		mEC.setCommittedDirection(0,ELEVATOR_DIRECTION_UP);
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testSetServicesFloor()
	{
		setConnectionState(true);	
		mEC.setServicesFloors(0, 0, true);
		assertFalse(mEC.isConnected());
	}
	
	@Test
	public void testSetTarget()
	{
		setConnectionState(true);	
		mEC.setTarget(0, 1);
		assertFalse(mEC.isConnected());
	}
	
	
	//--------------------------------------------
	// Implement self shunt
	//--------------------------------------------

	@Override
	public int getCommittedDirection(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorAccel(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor)
			throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorFloor(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorNum() throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorPosition(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorSpeed(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorWeight(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getElevatorCapacity(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public boolean getFloorButtonDown(int floor) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public boolean getFloorButtonUp(int floor) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getFloorHeight() throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getFloorNum() throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor)
			throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public int getTarget(int elevatorNumber) throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public void setCommittedDirection(int elevatorNumber, int direction)
			throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public void setServicesFloors(int elevatorNumber, int floor, boolean service)
			throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public void setTarget(int elevatorNumber, int target)
			throws RemoteException
	{
		throw new RemoteException();
	}


	@Override
	public long getClockTick() throws RemoteException
	{
		throw new RemoteException();
	}
	
}
