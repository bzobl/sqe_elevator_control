
package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import com.sun.istack.internal.logging.Logger;

public class ElevatorButtonListener implements ActionListener {
	
	private static Logger LOG = Logger.getLogger(ElevatorButtonListener.class);
	
	enum ListenerType {
		MODE_BUTTON_LISTENER,
		CALL_BUTTON_LISTENER,
		SERVICE_BUTTON_LISTENER
	}

	private ListenerType mType;
	private ElevatorControl mControl;
	private int mElevatorNum;
	private int mFloorNum;
	
	public ElevatorButtonListener(ListenerType t, ElevatorControl c,
							  int e, int f) {
		mType = t;
		mControl = c;
		mElevatorNum = e;
		mFloorNum = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (mType) {
		case MODE_BUTTON_LISTENER:
			assert (e.getSource() instanceof JToggleButton) : "unexpected button type";
			mControl.modeButtonClicked(mElevatorNum, (JToggleButton) e.getSource());
			break;

		case CALL_BUTTON_LISTENER:
			assert (e.getSource() instanceof JButton) : "unexpected button type";
			mControl.callButtonClicked(mElevatorNum, mFloorNum, (JButton) e.getSource());
			break;
			
		case SERVICE_BUTTON_LISTENER:
			assert (e.getSource() instanceof JToggleButton) : "unexpected button type";
			mControl.serviceButtonClicked(mElevatorNum, mFloorNum, (JToggleButton) e.getSource());
			break;

		default:
			LOG.severe("unknown type of listener: " + mType.toString());
			break;
		}
	}
}
