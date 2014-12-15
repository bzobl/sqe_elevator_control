
package at.fhhagenberg.sqe.project.sqelevator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		if (e.getSource() instanceof JToggleButton) {
		
			JToggleButton btn = (JToggleButton) e.getSource();
			
			switch (mType) {
			case MODE_BUTTON_LISTENER:
				mControl.modeButtonClicked(mElevatorNum, btn);
				break;
			case CALL_BUTTON_LISTENER:
				mControl.callButtonClicked(mElevatorNum, mFloorNum, btn);
				break;
				
			case SERVICE_BUTTON_LISTENER:
				mControl.serviceButtonClicked(mElevatorNum, mFloorNum, btn);
				break;
	
			default:
				LOG.severe("unknown type of listener: " + mType.toString());
				break;
			}
		} else {
			LOG.warning("Unexpected source in listener of type " + mType.toString()
					    + ": " + e.getSource().getClass().getCanonicalName());
		}
	}
}
