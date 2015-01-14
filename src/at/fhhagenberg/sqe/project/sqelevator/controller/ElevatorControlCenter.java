/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import sqelevator.IElevator;
import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorException;
import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.model.FloorException;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorModel;
import at.fhhagenberg.sqe.project.sqelevator.model.IElevatorSystem;
import at.fhhagenberg.sqe.project.sqelevator.view.IConnectingView;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IFloorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IMainView;

import com.sun.istack.internal.logging.Logger;

public class ElevatorControlCenter implements IControl, Observer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1767952243101768193L;

	private static Logger LOG = Logger.getLogger(ElevatorControlCenter.class);

	private static final Map<Integer, Integer> DIRECTION_LUT = new HashMap<>();
	private static final Map<Integer, Integer> DOORSTATUS_LUT = new HashMap<>();

	static
	{
		DIRECTION_LUT.put(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED,
				IFloorView.MOVE_STATUS_STANDING);
		DIRECTION_LUT.put(IElevator.ELEVATOR_DIRECTION_UP,
				IFloorView.MOVE_STATUS_UP);
		DIRECTION_LUT.put(IElevator.ELEVATOR_DIRECTION_DOWN,
				IFloorView.MOVE_STATUS_DOWN);

		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_CLOSED,
				IFloorView.ELEVATOR_STATUS_CLOSED);
		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_CLOSING,
				IFloorView.ELEVATOR_STATUS_CLOSING);
		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_OPEN,
				IFloorView.ELEVATOR_STATUS_OPENED);
		DOORSTATUS_LUT.put(IElevator.ELEVATOR_DOORS_OPENING,
				IFloorView.ELEVATOR_STATUS_OPENING);
	}

	private IElevatorConnection mElevatorConnection;
	private IElevatorSystem mModel;
	private IMainView mView;

	/**
	 * handle connection lost
	 */
	private void onConnectionLost()
	{
		mView.setVisible(false);
		waitForConnection();
	}
	
	/**
	 * show connecting dialog and wait for a connection to the elevator.
	 */
	public void waitForConnection()
	{
		mConnectingView.setRemoteName(mElevatorConnection.getConnectionName());
		mConnectingView.setVisible(true);
		
		Thread connThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (!mElevatorConnection.isConnected())
				{
					try
					{
						if (mElevatorConnection.connect())
						{
							// Workaround:
							// once the simulator was started, always a
							// connection can be established,
							// also if the simulator was closed. calling a
							// remote procedure without a
							// running simulator causes an exception and sets
							// the connected flag to false.
							mElevatorConnection.getClockTick();
						}
					}
					catch (Exception e)
					{
						LOG.info("Connecting failed with exception (WORKAROUND).");
					}
				}
				assert (mElevatorConnection.isConnected() == true);
			}
		});

		connThread.start();

		try
		{
			// wait until connected
			connThread.join();
		}
		catch (InterruptedException ex)
		{
			LOG.severe("exception in connecting threa.join()");

			// ASSERTION?
		}
		mConnectingView.setVisible(false);
		connectionEstablished();
	}

	/**
	 * build and show main view after a connection is established.
	 */
	private void connectionEstablished()
	{
		mModel = new ElevatorSystem(mElevatorConnection);

		mManuAlgo = new ManualElevatorAlgorithm(mModel, mElevatorConnection);
		mAuto = new boolean[mModel.getNumberOfElevators()];

		mView.setNumElevators(mModel.getNumberOfElevators());
		mView.setNumFloors(mModel.getNumberOfFloors());
		mView.setStatusText("connected");
		mView.resetView();
		mView.setVisible(true);

		mModel.addObserver(this);
	}

	private IConnectingView mConnectingView;

	public void setConnectingDialog(IConnectingView cv)
	{
		mConnectingView = cv;
		
		// action listener for cancel button
		mConnectingView.setCancelActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// shutdown application
				mConnectingView.setVisible(false);
				System.exit(0);
			}
		});
	}

	boolean mAuto[];

	IElevatorAlgorithm mAutoAlgo;
	IElevatorAlgorithm mManuAlgo;

	public ElevatorControlCenter(IElevatorConnection connection)
	{
		mElevatorConnection = connection;
		mAutoAlgo = null;
		mManuAlgo = null;

		LOG.info("Elevator control successfully initialized, starting to poll");
	}

	/**
	 * @return whether or not any elevator is in auto mode
	 */
	private boolean isAnyElevatorAuto()
	{
		for (int e = 0; e < mModel.getNumberOfElevators(); e++)
		{
			if (mAuto[e]) return true;
		}
		return false;
	}

	/**
	 * @param elevator
	 * @param btn
	 */
	@Override
	public void changeControlMode(int elevator, boolean autoMode)
	{
		LOG.info("Mode button of Elevator " + elevator + " clicked");
		IElevatorView eleView = mView.getElevatorView(elevator);

		mAuto[elevator] = autoMode;
		if (mAutoAlgo != null)
		{
			mAutoAlgo.enableElevator(elevator, autoMode);
			if (autoMode)
			{
				updateAlgorithm();
			}
		}

		for (int i = 0; i < mModel.getNumberOfFloors(); i++)
		{
			IFloorView floorView = eleView.getFloorView(i);
			floorView.enableCallButton(!mAuto[elevator]);
		}
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	@Override
	public void setCallRequest(int elevator, int floor)
	{
		assert (mAuto[elevator] == false) : "Call Button cannot be pressed when in auto mode";
		LOG.info("Call Button of Elevator " + elevator + ", Floor " + floor
				+ " clicked");

		mManuAlgo.setElevatorRequest(elevator, floor);
	}

	/**
	 * @param elevator
	 * @param floor
	 * @param btn
	 */
	@Override
	public void setServicedFloor(int elevator, int floor, boolean isServiced)
	{
		LOG.info("Service Button of Elevator " + elevator + ", Floor " + floor
				+ " clicked");
		mElevatorConnection.setServicesFloors(elevator, floor, isServiced);
	}

	@Override
	public void updateView()
	{
		try
		{
			for (int i = 0; i < mModel.getNumberOfElevators(); i++)
			{
				updateView(mModel.getElevator(i));
			}
		}
		catch (ElevatorException e)
		{
			LOG.severe(e.getMessage());
		}

		updateView(mModel);
	}

	@Override
	public void setView(IMainView view)
	{
		mView = view;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (arg.equals(IElevatorSystem.SYSTEM_PROPERTY_CHANGED))
		{
			assert (o instanceof IElevatorSystem);
			IElevatorSystem sys = (IElevatorSystem) o;
			if (mView != null)
			{
				updateView(sys);
			}
			else
			{
				LOG.info("ElevatorSystem updated, but no view is set");
			}
		}
		else if (arg.equals(IElevatorSystem.ELEVATOR_PROPERTY_CHANGED))
		{
			assert (o instanceof IElevatorModel);
			IElevatorModel elev = (IElevatorModel) o;
			if (mView != null)
			{
				updateView(elev);
			}
			else
			{
				LOG.info("Elevator updated, but no view is set");
			}
			if (!mAuto[elev.getElevatorNumber()])
			{
				// check whether or not the elevator is in the target floor and
				// clear direction accordingly
				if (elev.getFloor() == elev.getTargetFloor())
				{
					mManuAlgo.setElevatorRequest(elev.getElevatorNumber(),
							elev.getTargetFloor());
				}
			}
		}
		else if (arg.equals(IElevatorSystem.CONNECTION_POPERTY_CHANGED))
		{
			if (!mElevatorConnection.isConnected())
			{
				onConnectionLost();
			}
		}
		else
		{
			LOG.warning("Unexpected class notified ElevatorControl: "
					+ o.getClass().getCanonicalName());
		}

		if ((mAutoAlgo != null) && isAnyElevatorAuto())
		{
			updateAlgorithm();
		}
	}

	private void updateAlgorithm()
	{
		assert (isAnyElevatorAuto()) : "update Algorithm called with no elevator in auto mode";

		for (int e = 0; e < mModel.getNumberOfElevators(); e++)
		{
			try
			{
				if (mAuto[e])
				{
					updateAlgorithm(mModel.getElevator(e));
				}
			}
			catch (ElevatorException exc)
			{
				LOG.severe("invalid elevator number: " + exc.getMessage());
			}
		}

		updateAlgorithm(mModel);
	}

	/**
	 * @param elev
	 */
	private void updateAlgorithm(IElevatorModel elev)
	{
		assert (mAutoAlgo != null) : "Auto algorithm not set";
		assert (mAuto[elev.getElevatorNumber()]) : "elevator is in manual mode";

		if (elev.getFloor() == elev.getTargetFloor())
		{
			mElevatorConnection.setCommittedDirection(elev.getElevatorNumber(),
					IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		}

		for (int f = 0; f < mModel.getNumberOfFloors(); f++)
		{
			try
			{
				if (elev.getButtonStatus(f))
				{
					mAutoAlgo.setElevatorRequest(elev.getElevatorNumber(), f);
				}
			}
			catch (FloorException e)
			{
				LOG.severe(e.getMessage());
			}
		}
	}

	/**
	 * @param sys
	 */
	private void updateAlgorithm(IElevatorSystem sys)
	{
		assert (mAutoAlgo != null) : "Auto algorithm not set";
		assert (isAnyElevatorAuto()) : "all elevators are in manual mode";

		for (int f = 0; f < mModel.getNumberOfFloors(); f++)
		{
			try
			{
				if (sys.getFloorButton(f, true))
				{
					mAutoAlgo.setFloorRequest(f, true);
				}
				else if (sys.getFloorButton(f, false))
				{
					mAutoAlgo.setFloorRequest(f, false);
				}
			}
			catch (FloorException e)
			{
				LOG.severe(e.getMessage());
			}
		}
	}

	private void updateView(IElevatorModel elev)
	{
		assert (mView != null) : "update of view requested when no view was set";

		IElevatorView eleView = mView.getElevatorView(elev.getElevatorNumber());
		if (eleView == null)
		{
			LOG.severe("Could not find ElevatorView for elevator "
					+ elev.getElevatorNumber());
			return;
		}

		eleView.setPosition(elev.getPosition());
		eleView.setAcceleration(elev.getAcceleration());
		eleView.setPayload(elev.getWeight());
		eleView.setSpeed(elev.getSpeed());

		for (int i = 0; i < mModel.getNumberOfFloors(); i++)
		{
			IFloorView floorView = eleView.getFloorView(i);

			int moveStatus = IFloorView.MOVE_STATUS_AWAY;
			int elevatorStatus = IFloorView.ELEVATOR_STATUS_AWAY;

			if (elev.getFloor() == i)
			{
				elevatorStatus = DOORSTATUS_LUT.get(elev.getDoorstatus());
				moveStatus = DIRECTION_LUT.get(elev.getDirection());
			}
			else if ((elev.getTargetFloor() == i))
			{
				elevatorStatus = IFloorView.ELEVATOR_STATUS_TARGET;
			}

			try
			{
				if (!elev.getServicesFloors(i))
				{
					elevatorStatus = IFloorView.ELEVATOR_STATUS_OUT_OF_ORDER;
					floorView.setServiceStatus(false);
				}
				else
				{
					floorView.setServiceStatus(true);
				}

				eleView.setElevatorButton(i, elev.getButtonStatus(i));
			}
			catch (FloorException e)
			{
				LOG.severe(e.getMessage());
			}

			floorView.setMoveStatus(moveStatus);
			floorView.setElevatorStatus(elevatorStatus);
		}
	}

	private void updateView(IElevatorSystem sys)
	{
		assert (mView != null) : "update of view requested when no view was set";

		mView.setStatusText("Simulation time: "
				+ String.valueOf(sys.getSimulationTime()));

		for (int e = 0; e < mModel.getNumberOfElevators(); e++)
		{
			for (int f = 0; f < mModel.getNumberOfFloors(); f++)
			{
				IFloorView floorView = mView.getElevatorView(e).getFloorView(f);
				try
				{
					floorView.setFloorButton(IFloorView.FLOOR_BUTTON_UP,
							sys.getFloorButton(f, true));
					floorView.setFloorButton(IFloorView.FLOOR_BUTTON_DOWN,
							sys.getFloorButton(f, false));
				}
				catch (FloorException ex)
				{
					LOG.severe(ex.getMessage());
				}
			}
		}
	}
}
