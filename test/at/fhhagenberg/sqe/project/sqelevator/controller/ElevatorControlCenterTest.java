/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.controller;

import static org.junit.Assert.*;

import java.awt.event.ActionListener;

import org.junit.Before;
import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorConnectionTestShunt;
import at.fhhagenberg.sqe.project.sqelevator.view.IConnectingView;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IMainView;

public class ElevatorControlCenterTest implements IMainView {
	
	ElevatorControlCenter mECC;
	ElevatorConnectionTestShunt mConShunt = new ElevatorConnectionTestShunt(5, 10, 10);
	ConnectingViewShunt mViewShunt = new ConnectingViewShunt();
	private boolean mVisible = false;
	private String mStatusText;
	private int mFloors = -1;
	private int mElevators = -1;
	private boolean mResetViewCalled = false;
	
	class ConnectingViewShunt implements IConnectingView {
		
		public String RemoteName;
		public boolean Visible = true;
		public ActionListener CancelListener;

		@Override
		public void setRemoteName(String remote) {
			RemoteName = remote;
		}

		@Override
		public void setVisible(boolean visible) {
			Visible = visible;
		}

		@Override
		public void setCancelActionListener(ActionListener a) {
			CancelListener = a;
		}
	}

	@Before
	public void setUp() {
		mECC = new ElevatorControlCenter(mConShunt);
		mECC.setView(this);
		mECC.setConnectingDialog(mViewShunt);
	}

	@Override
	public IElevatorView getElevatorView(int num) {
		return null;
	}

	@Override
	public void setVisible(boolean visible) {
		mVisible = visible;
	}

	@Override
	public void setStatusText(String statusText) {
		mStatusText = statusText;
	}

	@Override
	public void setNumFloors(int n) {
		mFloors = n;
	}

	@Override
	public void setNumElevators(int n) {
		mElevators = n;
	}

	@Override
	public void resetView() {
		mResetViewCalled = true;
	}
	
	@Test
	public void testWaitForConnection() {
        mConShunt.IsConnected = true;
		mECC.waitForConnection();
		assertFalse(mViewShunt.Visible);
		assertTrue(mVisible);
		assertEquals(5, mFloors);
		assertEquals(1, mElevators);
	}

	@Test
	public void testSetConnectionDialog() {
		mECC.setConnectingDialog(mViewShunt);
		assertNotNull(mViewShunt.CancelListener);
	}

	@Test
	public void testSetServicedFloor() {
		mECC.setServicedFloor(0, 1, false);
		assertFalse(mConShunt.SetServicesFloor[1]);
	}
}
