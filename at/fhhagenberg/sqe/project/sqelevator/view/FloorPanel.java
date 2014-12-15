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
	 * call toggle button
	 */
	private JToggleButton mCallButton;

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

	/**
	 * floor number of current floor
	 */
	public int GetFloorNumber()
	{
		return FLOOR_NUMBER;
	}

	/**
	 * enables or disables call button.
	 * 
	 * @param on
	 *            enable if true, disable if false
	 */
	public void EnableCallButton(boolean on)
	{
		mCallButton.setEnabled(on);
	}

	/**
	 * set status for elevator icon. see ELEVATOR_STATUS_xxx for valid states.
	 * 
	 * @param elevatorStatus
	 *            status to set.
	 */
	public void SetElevatorStatus(int elevatorStatus)
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

			case ELEVATOR_STATUS_AWAY:
				mElevatorImage.setVisible(false);
				break;

			default:
				assert false : "Unexpected elevator status";

				mElevatorStatus = ELEVATOR_STATUS_AWAY;
				break;
		}
	}

	/**
	 * get current elevator icon status.
	 * 
	 * @return any of ELEVATOR_STATUS_xxx
	 */
	public int GetElevatorStatus()
	{
		return mElevatorStatus;
	}

	/**
	 * set move status for elevator. See MOVE_STATUS_xxx for valid states.
	 * 
	 * @param moveStatus
	 *            state to set.
	 */
	public void SetMoveStatus(int moveStatus)
	{
		mMoveStatus = moveStatus;

		System.out.println(mMovingImage.getIcon().toString());

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

	/**
	 * Get current move status.
	 * 
	 * @return any of MOVE_STATUS_xxx
	 */
	public int GetMoveStatus()
	{
		return mMoveStatus;
	}

	/**
	 * add action listener for call button.
	 * 
	 * @param l
	 *            action listener
	 */
	public void AddCallButtonListener(ActionListener l)
	{
		mCallButton.addActionListener(l);
	}

	/**
	 * remove action listener from call button
	 * 
	 * @param l
	 *            action listener
	 */
	public void RemoveCallButtonListener(ActionListener l)
	{
		mCallButton.removeActionListener(l);
	}

	/**
	 * current status of floor buttons
	 */
	private boolean mFloorButtonStates[] = new boolean[2];

	/**
	 * set status of floor buttons.
	 * 
	 * @param btn
	 *            any of FLOOR_BUTTON_xxx
	 * @param status
	 *            true to set active, else false.
	 */
	public void SetFloorButton(int btn, boolean status)
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

	/**
	 * get current status of a floor button.
	 * 
	 * @param btn
	 *            any of FLOOR_BUTTON_xxx
	 * @return true, if active, else false
	 */
	public boolean GetFloorButton(int btn)
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
		gbc_lblFloorNumber.gridheight = 3;
		gbc_lblFloorNumber.insets = new Insets(0, 0, 0, 5);
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

		mCallButton = new JToggleButton("Call");
		GridBagConstraints gbc_tglbtnCall = new GridBagConstraints();
		gbc_tglbtnCall.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnCall.gridx = 3;
		gbc_tglbtnCall.gridy = 0;
		add(mCallButton, gbc_tglbtnCall);

		mFloorUp = new JLabel("");
		mFloorUp.setIcon(new ImageIcon(FloorPanel.class
				.getResource("/img/colorArrowUp_off_small.png")));
		GridBagConstraints gbc_mFloorUp = new GridBagConstraints();
		gbc_mFloorUp.insets = new Insets(0, 0, 5, 0);
		gbc_mFloorUp.gridx = 3;
		gbc_mFloorUp.gridy = 1;
		add(mFloorUp, gbc_mFloorUp);

		mFloorDown = new JLabel("");
		mFloorDown.setIcon(new ImageIcon(FloorPanel.class
				.getResource("/img/colorArrowDown_off_small.png")));
		GridBagConstraints gbc_mFloorDown = new GridBagConstraints();
		gbc_mFloorDown.gridx = 3;
		gbc_mFloorDown.gridy = 2;
		add(mFloorDown, gbc_mFloorDown);

		SetFloorButton(FLOOR_BUTTON_DOWN, false);
		SetFloorButton(FLOOR_BUTTON_UP, false);
		SetElevatorStatus(mElevatorStatus);
		SetMoveStatus(mMoveStatus);
	}

	@Override
	public void AddServiceButtonListener(ActionListener l)
	{
		// TODO Auto-generated method stub
		assert false : "not yet implemented";
	}

	@Override
	public void RemoveServiceButtonListener(ActionListener l)
	{
		// TODO Auto-generated method stub
		assert false : "not yet implemented";

	}

	@Override
	public void SetServiceStatus(boolean on)
	{
		// TODO Auto-generated method stub
		assert false : "not yet implemented";
	}

	@Override
	public boolean GetServiceStatus()
	{
		// TODO Auto-generated method stub
		assert false : "not yet implemented";
		return false;
	}
}
