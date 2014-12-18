/**
 * Project: Elevator_Control_Center
 * Author:  Bernd Zobl
 *          S1310567025
 */

package at.fhhagenberg.sqe.project.sqelevator.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import org.junit.Test;

import at.fhhagenberg.sqe.project.sqelevator.controller.IControl;

/** ElevatorButtonListenertest
 * 
 */
public class ElevatorButtonListenerTest implements IControl {
	
	private int controlModeElevator = -1;
	private boolean controlAutoMode = false;
	
	private int callRequestElevator = -1;
	private int callRequestFloor = -1;
	
	private int setServicedFloorElevator = -1;
	private int setServicedFloorFloor = -1;
	private boolean setServicedFloorEnabled = false;

	@Override
	public void changeControlMode(int elevator, boolean autoMode) {
		controlModeElevator = elevator;
		controlAutoMode = autoMode;
	}

	@Override
	public void setCallRequest(int elevator, int floor) {
		callRequestElevator = elevator;
		callRequestFloor = floor;
	}

	@Override
	public void setServicedFloor(int elevator, int floor, boolean isServiced) {
		setServicedFloorElevator = elevator;
		setServicedFloorFloor = floor;
		setServicedFloorEnabled = isServiced;
	}

	@Override
	public void updateView() { }

	@Override
	public void setView(IMainView view) { }
	
	@Test
	public void testModeButtonListener() {
		ElevatorButtonListener l = new ElevatorButtonListener(ElevatorButtonListener.ListenerType.MODE_BUTTON_LISTENER,
															  this, 1, -1);

		JToggleButton btn = new JToggleButton();
		btn.setSelected(true);
		l.actionPerformed(new ActionEvent(btn, 0, ""));
		
		assertEquals(1, controlModeElevator);
		assertTrue(controlAutoMode);

		assertEquals(-1, callRequestElevator);
		assertEquals(-1, callRequestFloor);
		assertEquals(-1, setServicedFloorElevator);
		assertEquals(-1, setServicedFloorFloor);
		assertFalse(setServicedFloorEnabled);
	}

	@Test
	public void testCallButtonListener() {
		ElevatorButtonListener l = new ElevatorButtonListener(ElevatorButtonListener.ListenerType.CALL_BUTTON_LISTENER,
															  this, 1, 3);

		JButton btn = new JButton();
		l.actionPerformed(new ActionEvent(btn, 0, ""));
		
		assertEquals(1, callRequestElevator);
		assertEquals(3, callRequestFloor);

		assertEquals(-1, controlModeElevator);
		assertFalse(controlAutoMode);
		assertEquals(-1, setServicedFloorElevator);
		assertEquals(-1, setServicedFloorFloor);
		assertFalse(setServicedFloorEnabled);

	}

	@Test
	public void testServiceButtonListener() {
		ElevatorButtonListener l = new ElevatorButtonListener(ElevatorButtonListener.ListenerType.SERVICE_BUTTON_LISTENER,
															  this, 2, 0);

		JToggleButton btn = new JToggleButton();
		btn.setSelected(true);
		l.actionPerformed(new ActionEvent(btn, 0, ""));
		
		assertEquals(2, setServicedFloorElevator);
		assertEquals(0, setServicedFloorFloor);
		assertTrue(setServicedFloorEnabled);

		assertEquals(-1, controlModeElevator);
		assertFalse(controlAutoMode);
		assertEquals(-1, callRequestElevator);
		assertEquals(-1, callRequestFloor);

	}
}
