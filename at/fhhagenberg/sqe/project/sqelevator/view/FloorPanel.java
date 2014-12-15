package at.fhhagenberg.sqe.project.sqelevator.view;

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import at.fhhagenberg.sqe.project.sqelevator.model.ElevatorSystem;

import com.sun.istack.internal.logging.Logger;

public class FloorPanel extends JPanel
{
	private static Logger LOG = Logger.getLogger(ElevatorSystem.class);
	
	private final String SRC_ELEVATOR_OPENED = "/img/ellevator_door_opened_small.png";
	private final String SRC_ELEVATOR_OPENING = "/img/ellevator_door_opening_small.png";
	private final String SRC_ELEVATOR_CLOSED = "/img/ellevator_door_closed_small.png";
	private final String SRC_ELEVATOR_CLOSING = "/img/ellevator_door_closing_small.png";
	
	private final String SRC_ARROW_UP = "/img/upArrow_small.png";
	private final String SRC_ARROW_DOWN = "/img/downArrow_small.png";
	private final String SRC_ARROW_NONE = "/img/standing_small.png";
	
	private JToggleButton mCallButton;
	private JLabel mElevatorImage;
	private JLabel mUpDownImage;
	private JRadioButton mFloorButtonUp;
	private JRadioButton mFloorButtonDown;
	
	private int mElevatorStatus = ELEVATOR_STATUS_AWAY;
	
	private int mMoveStatus = MOVE_STATUS_AWAY;
	
	public final int FLOOR_NUMBER;
	
	public void EnableCallButton(boolean on)
	{
		mCallButton.setEnabled(on);
	}
	
	public final static int ELEVATOR_STATUS_CLOSED = 1; 
	public final static int ELEVATOR_STATUS_CLOSING = 2; 
	public final static int ELEVATOR_STATUS_OPENED = 3; 
	public final static int ELEVATOR_STATUS_OPENING = 4; 
	public final static int ELEVATOR_STATUS_AWAY = 5; 
	
	public void SetElevatorStatus(int elevatorStatus)
	{
		mElevatorStatus = elevatorStatus;
		
		switch (elevatorStatus)
		{
			case ELEVATOR_STATUS_CLOSED:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ELEVATOR_CLOSED)));
				mElevatorImage.setVisible(true);
				break;
				
			case ELEVATOR_STATUS_CLOSING:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ELEVATOR_CLOSING)));
				mElevatorImage.setVisible(true);
				break;
				
			case ELEVATOR_STATUS_OPENED:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ELEVATOR_OPENED)));
				mElevatorImage.setVisible(true);
				break;
				
			case ELEVATOR_STATUS_OPENING:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ELEVATOR_OPENING)));
				mElevatorImage.setVisible(true);
				break;

			case ELEVATOR_STATUS_AWAY:
				mElevatorImage.setVisible(false);
				break;
				
			default:
				assert false : "Unexpected elevator status";
				
				mElevatorStatus = ELEVATOR_STATUS_AWAY;
				break;
		}
	}
	
	public int GetElevatorStatus()
	{
		return mElevatorStatus;
	}
	
	public final static int MOVE_STATUS_UP = 1;
	public final static int MOVE_STATUS_DOWN = 2;
	public final static int MOVE_STATUS_STANDING = 3;
	public final static int MOVE_STATUS_AWAY = 4;
	
	public void SetMoveStatus(int moveStatus)
	{
		mMoveStatus = moveStatus;
		
		switch(moveStatus)
		{			
			case MOVE_STATUS_UP:
				mUpDownImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ARROW_UP)));
				mUpDownImage.setVisible(true);
				break;
				
			case MOVE_STATUS_DOWN:
				mUpDownImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ARROW_DOWN)));
				mUpDownImage.setVisible(true);
				break;
				
			case MOVE_STATUS_STANDING:
				mUpDownImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ARROW_NONE)));
				mUpDownImage.setVisible(true);
				break;
				
			case MOVE_STATUS_AWAY:
				mUpDownImage.setVisible(false);
				break;
				
			default:
				assert false : "Unexpected move status";
				mMoveStatus = MOVE_STATUS_AWAY;
				break;
		}
	}
	
	public int GetMoveStatus()
	{
		return mMoveStatus;
	}
	
	public void AddCallButtonListener(ActionListener l)
	{
		mCallButton.addActionListener(l);
	}
	
	public void RemoveCallButtonListener(ActionListener l)
	{
		mCallButton.removeActionListener(l);
	}
	
	public final static int FLOOR_BUTTON_UP = 1;
	public final static int FLOOR_BUTTON_DOWN = 2;
	
	public void SetFloorButton(int btn, boolean status)
	{
		if (btn == FLOOR_BUTTON_DOWN)
		{
			mFloorButtonDown.setSelected(status);
		}
		else if (btn == FLOOR_BUTTON_UP)
		{
			mFloorButtonUp.setSelected(status);
		}
		else
		{
			assert false : "Unexpected floor button";
		}
	}
	
	public boolean GetFloorButton(int btn)
	{
		if (btn == FLOOR_BUTTON_DOWN)
		{
			return mFloorButtonDown.isSelected();
		}
		else if (btn == FLOOR_BUTTON_UP)
		{
			return mFloorButtonUp.isSelected();
		}
		else
		{
			assert false : "Unexpected floor button";
			
			// makes compiler happy!
			return false;
		}
	}	
	
	/**
	 * Create the panel.
	 */
	public FloorPanel(int floorNumber)
	{
		FLOOR_NUMBER = floorNumber;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{45, 75, 39, 75};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel lblFloorNumber = new JLabel(String.valueOf(floorNumber));
		lblFloorNumber.setFont(new Font("Dialog", Font.BOLD, 24));
		GridBagConstraints gbc_lblFloorNumber = new GridBagConstraints();
		gbc_lblFloorNumber.gridheight = 3;
		gbc_lblFloorNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblFloorNumber.gridx = 0;
		gbc_lblFloorNumber.gridy = 0;
		add(lblFloorNumber, gbc_lblFloorNumber);
		
		mElevatorImage = new JLabel("");
		mElevatorImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ELEVATOR_OPENING)));
		mElevatorImage.setName("elevatorImage");
		GridBagConstraints gbc_lblElevatorImage = new GridBagConstraints();
		gbc_lblElevatorImage.gridheight = 3;
		gbc_lblElevatorImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblElevatorImage.gridx = 1;
		gbc_lblElevatorImage.gridy = 0;
		add(mElevatorImage, gbc_lblElevatorImage);
		
		mUpDownImage = new JLabel("");
		mUpDownImage.setIcon(new ImageIcon(FloorPanel.class.getResource(SRC_ARROW_DOWN)));
		mUpDownImage.setName("upDownImage");
		GridBagConstraints gbc_lblUpDownImage = new GridBagConstraints();
		gbc_lblUpDownImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpDownImage.gridheight = 3;
		gbc_lblUpDownImage.gridx = 2;
		gbc_lblUpDownImage.gridy = 0;
		add(mUpDownImage, gbc_lblUpDownImage);
		
		mCallButton = new JToggleButton("Call");
		GridBagConstraints gbc_tglbtnCall = new GridBagConstraints();
		gbc_tglbtnCall.anchor = GridBagConstraints.SOUTH;
		gbc_tglbtnCall.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnCall.gridx = 3;
		gbc_tglbtnCall.gridy = 0;
		add(mCallButton, gbc_tglbtnCall);
		
		mFloorButtonUp = new JRadioButton("UP");
		mFloorButtonUp.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnUp = new GridBagConstraints();
		gbc_rdbtnUp.anchor = GridBagConstraints.SOUTHWEST;
		gbc_rdbtnUp.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnUp.gridx = 3;
		gbc_rdbtnUp.gridy = 1;
		add(mFloorButtonUp, gbc_rdbtnUp);
		
		mFloorButtonDown = new JRadioButton("DOWN");
		mFloorButtonDown.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnDown = new GridBagConstraints();
		gbc_rdbtnDown.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnDown.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnDown.gridx = 3;
		gbc_rdbtnDown.gridy = 2;
		add(mFloorButtonDown, gbc_rdbtnDown);
		
		SetElevatorStatus(mElevatorStatus);
		SetMoveStatus(mMoveStatus);
	}
}
