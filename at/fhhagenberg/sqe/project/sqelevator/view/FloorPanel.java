package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;

public class FloorPanel extends JPanel implements IFloorView
{
	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = -6790668946717203148L;

	/**
	 * path to the elevator icons
	 */
	private final String SRC_ELEVATOR_OPENED = "/img/ellevator_door_opened_small.png";
	private final String SRC_ELEVATOR_OPENING = "/img/ellevator_door_opening_small.png";
	private final String SRC_ELEVATOR_CLOSED = "/img/ellevator_door_closed_small.png";
	private final String SRC_ELEVATOR_CLOSING = "/img/ellevator_door_closing_small.png";
	private final String SRC_ELEVATOR_TARGET = "/img/ellevator_target_small.png";
	private final String SRC_ELEVATOR_OUT_OF_ORDER = "/img/ellevator_ooo_small.png";

	/**
	 * path to the (moving) arrow icons
	 */
	private final String SRC_ARROW_UP = "/img/upArrow_small.png";
	private final String SRC_ARROW_DOWN = "/img/downArrow_small.png";
	private final String SRC_ARROW_NONE = "/img/standing_small.png";

	/**
	 * path to the floor button icons
	 */
	private final String SRC_FLOOR_BTN_UP_OFF = "/img/colorArrowUp_off_small.png";
	private final String SRC_FLOOR_BTN_UP_ON = "/img/colorArrowUp_small.png";
	private final String SRC_FLOOR_BTN_DOWN_OFF = "/img/colorArrowDown_off_small.png";
	private final String SRC_FLOOR_BTN_DOWN_ON = "/img/colorArrowDown_small.png";

	/**
	 * check box serviced
	 */
	private JCheckBox chckbxServiced;
	
	/**
	 * label with elevator image
	 */
	private JLabel mElevatorImage;

	/**
	 * label with moving up/down image
	 */
	private JLabel mMovingImage;

	/**
	 * label with floor button up image
	 */
	private JLabel mFloorUp;

	/**
	 * label with floor button down image
	 */
	private JLabel mFloorDown;

	/**
	 * current status of floor buttons
	 */
	private boolean mFloorButtonStates[] = new boolean[2];
	
	/**
	 * call button
	 */
	private JButton mCallButton;
	
	/**
	 * current elevator status. see ELEVATOR_STATUS_xxx for valid states.
	 */
	private int mElevatorStatus = ELEVATOR_STATUS_AWAY;

	/**
	 * current moving status. see MOVE_STATUS_xxx for valid states.
	 */
	private int mMoveStatus = MOVE_STATUS_AWAY;

	/**
	 * floor number as shown in label
	 */
	private final int FLOOR_NUMBER;

	@Override
	public int getFloorNumber()
	{
		return FLOOR_NUMBER;
	}

	@Override
	public void enableCallButton(boolean on)
	{
		mCallButton.setEnabled(on);
	}

	@Override
	public void setElevatorStatus(int elevatorStatus)
	{
		mElevatorStatus = elevatorStatus;

		switch (elevatorStatus)
		{
			case ELEVATOR_STATUS_CLOSED:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ELEVATOR_CLOSED)));
				mElevatorImage.setVisible(true);
				break;

			case ELEVATOR_STATUS_CLOSING:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ELEVATOR_CLOSING)));
				mElevatorImage.setVisible(true);
				break;

			case ELEVATOR_STATUS_OPENED:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ELEVATOR_OPENED)));
				mElevatorImage.setVisible(true);
				break;

			case ELEVATOR_STATUS_OPENING:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ELEVATOR_OPENING)));
				mElevatorImage.setVisible(true);
				break;

			case ELEVATOR_STATUS_TARGET:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ELEVATOR_TARGET)));
				mElevatorImage.setVisible(true);
				break;

			case ELEVATOR_STATUS_OUT_OF_ORDER:
				mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ELEVATOR_OUT_OF_ORDER)));
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

	@Override
	public int getElevatorStatus()
	{
		return mElevatorStatus;
	}

	@Override
	public void setMoveStatus(int moveStatus)
	{
		mMoveStatus = moveStatus;

		switch (moveStatus)
		{
			case MOVE_STATUS_UP:
				mMovingImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ARROW_UP)));
				mMovingImage.setVisible(true);
				break;

			case MOVE_STATUS_DOWN:
				mMovingImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ARROW_DOWN)));
				mMovingImage.setVisible(true);
				break;

			case MOVE_STATUS_STANDING:
				mMovingImage.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_ARROW_NONE)));
				mMovingImage.setVisible(true);
				break;

			case MOVE_STATUS_AWAY:
				mMovingImage.setVisible(false);
				break;

			default:
				assert false : "Unexpected move status";
				mMoveStatus = MOVE_STATUS_AWAY;
				break;
		}
	}

	@Override
	public int getMoveStatus()
	{
		return mMoveStatus;
	}

	@Override
	public void addCallButtonListener(ActionListener l)
	{
		mCallButton.addActionListener(l);
	}

	@Override
	public void removeCallButtonListener(ActionListener l)
	{
		mCallButton.removeActionListener(l);
	}

	@Override
	public void setFloorButton(int btn, boolean status)
	{
		if (btn == FLOOR_BUTTON_DOWN)
		{
			mFloorButtonStates[btn] = status;
			if (status)
			{
				mFloorDown.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_FLOOR_BTN_DOWN_ON)));
			}
			else
			{
				mFloorDown.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_FLOOR_BTN_DOWN_OFF)));
			}
		}
		else if (btn == FLOOR_BUTTON_UP)
		{
			mFloorButtonStates[btn] = status;
			if (status)
			{
				mFloorUp.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_FLOOR_BTN_UP_ON)));
			}
			else
			{
				mFloorUp.setIcon(new ImageIcon(FloorPanel.class
						.getResource(SRC_FLOOR_BTN_UP_OFF)));
			}
		}
		else
		{
			assert false : "Unexpected floor button";
		}
	}

	@Override
	public boolean getFloorButton(int btn)
	{
		if (btn == FLOOR_BUTTON_DOWN)
		{
			return mFloorButtonStates[btn];
		}
		else if (btn == FLOOR_BUTTON_UP)
		{
			return mFloorButtonStates[btn];
		}
		else
		{
			assert false : "Unexpected floor button";

			// makes compiler happy!
			return false;
		}
	}

	/**
	 * Create the panel and initialize states.
	 */
	public FloorPanel(int floorNumber)
	{
		FLOOR_NUMBER = floorNumber;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowHeights = new int[] { 35, 30, 0 };
		gridBagLayout.columnWidths = new int[] { 45, 75, 40, 75 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
		setLayout(gridBagLayout);

		JLabel lblFloorNumber = new JLabel(String.valueOf(floorNumber));
		lblFloorNumber.setFont(new Font("Dialog", Font.BOLD, 24));
		GridBagConstraints gbc_lblFloorNumber = new GridBagConstraints();
		gbc_lblFloorNumber.anchor = GridBagConstraints.SOUTH;
		gbc_lblFloorNumber.gridheight = 2;
		gbc_lblFloorNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblFloorNumber.gridx = 0;
		gbc_lblFloorNumber.gridy = 0;
		add(lblFloorNumber, gbc_lblFloorNumber);

		mElevatorImage = new JLabel("");
		mElevatorImage.setIcon(new ImageIcon(FloorPanel.class
				.getResource(SRC_ELEVATOR_OPENING)));
		mElevatorImage.setName("elevatorImage");
		GridBagConstraints gbc_lblElevatorImage = new GridBagConstraints();
		gbc_lblElevatorImage.gridheight = 3;
		gbc_lblElevatorImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblElevatorImage.gridx = 1;
		gbc_lblElevatorImage.gridy = 0;
		add(mElevatorImage, gbc_lblElevatorImage);

		mMovingImage = new JLabel("");
		mMovingImage.setIcon(new ImageIcon(FloorPanel.class
				.getResource(SRC_ARROW_DOWN)));
		mMovingImage.setName("upDownImage");
		GridBagConstraints gbc_lblUpDownImage = new GridBagConstraints();
		gbc_lblUpDownImage.insets = new Insets(0, 0, 0, 5);
		gbc_lblUpDownImage.gridheight = 3;
		gbc_lblUpDownImage.gridx = 2;
		gbc_lblUpDownImage.gridy = 0;
		add(mMovingImage, gbc_lblUpDownImage);
		
		mCallButton = new JButton("Call");
		GridBagConstraints gbc_mCallButton = new GridBagConstraints();
		gbc_mCallButton.insets = new Insets(0, 0, 5, 0);
		gbc_mCallButton.gridx = 3;
		gbc_mCallButton.gridy = 0;
		add(mCallButton, gbc_mCallButton);

		mFloorUp = new JLabel("");
		mFloorUp.setIcon(new ImageIcon(FloorPanel.class
				.getResource("/img/colorArrowUp_off_small.png")));
		mFloorUp.setName("floorUpImage");
		GridBagConstraints gbc_mFloorUp = new GridBagConstraints();
		gbc_mFloorUp.insets = new Insets(0, 0, 5, 0);
		gbc_mFloorUp.gridx = 3;
		gbc_mFloorUp.gridy = 1;
		add(mFloorUp, gbc_mFloorUp);
		
		chckbxServiced = new JCheckBox("serviced");
		chckbxServiced.setSelected(true);
		GridBagConstraints gbc_chckbxServiced = new GridBagConstraints();
		gbc_chckbxServiced.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxServiced.gridx = 0;
		gbc_chckbxServiced.gridy = 2;
		add(chckbxServiced, gbc_chckbxServiced);

		mFloorDown = new JLabel("");
		mFloorDown.setIcon(new ImageIcon(FloorPanel.class
				.getResource("/img/colorArrowDown_off_small.png")));
		mFloorDown.setName("floorDownImage");
		GridBagConstraints gbc_mFloorDown = new GridBagConstraints();
		gbc_mFloorDown.gridx = 3;
		gbc_mFloorDown.gridy = 2;
		add(mFloorDown, gbc_mFloorDown);

		setFloorButton(FLOOR_BUTTON_DOWN, false);
		setFloorButton(FLOOR_BUTTON_UP, false);
		setElevatorStatus(mElevatorStatus);
		setMoveStatus(mMoveStatus);
	}

	@Override
	public void addServiceButtonListener(ActionListener l)
	{
		chckbxServiced.addActionListener(l);
	}

	@Override
	public void removeServiceButtonListener(ActionListener l)
	{
		chckbxServiced.removeActionListener(l);
	}

	@Override
	public void setServiceStatus(boolean on)
	{
		chckbxServiced.setSelected(on);
	}

	@Override
	public boolean getServiceStatus()
	{
		return chckbxServiced.isSelected();
	}
}
