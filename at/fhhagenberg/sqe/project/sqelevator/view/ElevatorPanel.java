package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

public class ElevatorPanel extends JPanel implements IElevatorView
{
	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = -5003028963381832505L;

	/**
	 * panel which contains all floors
	 */
	private JPanel mFloorsPane;

	/**
	 * number of floors
	 */
	private final int NUMBER_OF_FLOORS;
	
	/**
	 * text fields from front end
	 */
	private JTextField mTextPosition;
	private JTextField mTextDirection;
	private JTextField mTextPayload;
	private JTextField mTextSpeed;
	private JTextField mTextAcceleration;

	/**
	 * mode button for auto/manual mode
	 */
	private JToggleButton mModeButton;
	
	/**
	 * add floor to the panel
	 * 
	 * @param num
	 *            floor number
	 */
	private void addFloor(int num)
	{
		FloorPanel floor = new FloorPanel(num);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = (NUMBER_OF_FLOORS - num) * 2 - 1;
		mFloorsPane.add(floor, gbc);

		// add separator only between two floors
		if (num < (NUMBER_OF_FLOORS - 1))
		{
			gbc.gridy = (NUMBER_OF_FLOORS - num) * 2 - 2;
			gbc.insets = new Insets(5, 0, 5, 0);
			mFloorsPane.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		}
	}

	/**
	 * Create the panel.
	 * 
	 * @numFloors number of floors
	 */
	public ElevatorPanel(int numFloors)
	{
		NUMBER_OF_FLOORS = numFloors;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 80, 60, 70, 60, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 65, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.gridwidth = 4;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);

		JLabel lblPosition = new JLabel("Position:");
		GridBagConstraints gbc_lblPosition = new GridBagConstraints();
		gbc_lblPosition.anchor = GridBagConstraints.EAST;
		gbc_lblPosition.insets = new Insets(0, 0, 5, 5);
		gbc_lblPosition.gridx = 0;
		gbc_lblPosition.gridy = 2;
		add(lblPosition, gbc_lblPosition);

		mTextPosition = new JTextField();
		mTextPosition.setName("textPosition");
		GridBagConstraints gbc_textPosition = new GridBagConstraints();
		gbc_textPosition.insets = new Insets(0, 0, 5, 5);
		gbc_textPosition.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPosition.gridx = 1;
		gbc_textPosition.gridy = 2;
		add(mTextPosition, gbc_textPosition);
		mTextPosition.setColumns(10);

		JLabel lblSpeed = new JLabel("Speed:");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.anchor = GridBagConstraints.EAST;
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.gridx = 2;
		gbc_lblSpeed.gridy = 2;
		add(lblSpeed, gbc_lblSpeed);

		mTextSpeed = new JTextField();
		mTextSpeed.setName("textSpeed");
		GridBagConstraints gbc_textSpeed = new GridBagConstraints();
		gbc_textSpeed.insets = new Insets(0, 0, 5, 0);
		gbc_textSpeed.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSpeed.gridx = 3;
		gbc_textSpeed.gridy = 2;
		add(mTextSpeed, gbc_textSpeed);
		mTextSpeed.setColumns(10);

		JLabel lblDirection = new JLabel("Direction:");
		GridBagConstraints gbc_lblDirection = new GridBagConstraints();
		gbc_lblDirection.anchor = GridBagConstraints.EAST;
		gbc_lblDirection.insets = new Insets(0, 0, 5, 5);
		gbc_lblDirection.gridx = 0;
		gbc_lblDirection.gridy = 3;
		add(lblDirection, gbc_lblDirection);

		mTextDirection = new JTextField();
		mTextDirection.setName("textDirection");
		GridBagConstraints gbc_textDirection = new GridBagConstraints();
		gbc_textDirection.insets = new Insets(0, 0, 5, 5);
		gbc_textDirection.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDirection.gridx = 1;
		gbc_textDirection.gridy = 3;
		add(mTextDirection, gbc_textDirection);
		mTextDirection.setColumns(10);

		JLabel lblAcceleration = new JLabel("Acc.:");
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.anchor = GridBagConstraints.EAST;
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 2;
		gbc_lblAcceleration.gridy = 3;
		add(lblAcceleration, gbc_lblAcceleration);

		mTextAcceleration = new JTextField();
		mTextAcceleration.setName("textAcceleration");
		GridBagConstraints gbc_textAcceleration = new GridBagConstraints();
		gbc_textAcceleration.insets = new Insets(0, 0, 5, 0);
		gbc_textAcceleration.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAcceleration.gridx = 3;
		gbc_textAcceleration.gridy = 3;
		add(mTextAcceleration, gbc_textAcceleration);
		mTextAcceleration.setColumns(10);

		JLabel lblPayload = new JLabel("Payload:");
		GridBagConstraints gbc_lblPayload = new GridBagConstraints();
		gbc_lblPayload.anchor = GridBagConstraints.EAST;
		gbc_lblPayload.insets = new Insets(0, 0, 0, 5);
		gbc_lblPayload.gridx = 0;
		gbc_lblPayload.gridy = 4;
		add(lblPayload, gbc_lblPayload);

		mTextPayload = new JTextField();
		mTextPayload.setName("textPayload");
		GridBagConstraints gbc_textPayload = new GridBagConstraints();
		gbc_textPayload.insets = new Insets(0, 0, 0, 5);
		gbc_textPayload.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPayload.gridx = 1;
		gbc_textPayload.gridy = 4;
		add(mTextPayload, gbc_textPayload);
		mTextPayload.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();

		// only allow vertical scrolling
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		mFloorsPane = new JPanel();
		mFloorsPane.setLayout(new GridBagLayout());
		scrollPane.setViewportView(mFloorsPane);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipady = 5;
		gbc.gridwidth = 4;

		add(scrollPane, gbc);

		mModeButton = new JToggleButton("Auto Mode");
		GridBagConstraints gbc_tglbtnAutoMode = new GridBagConstraints();
		gbc_tglbtnAutoMode.gridwidth = 2;
		gbc_tglbtnAutoMode.insets = new Insets(0, 0, 0, 0);
		gbc_tglbtnAutoMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnAutoMode.gridx = 2;
		gbc_tglbtnAutoMode.gridy = 4;
		add(mModeButton, gbc_tglbtnAutoMode);

		// create floors
		for (int i = 0; i < NUMBER_OF_FLOORS; i++)
		{
			addFloor(i);
		}
	}

	@Override
	public IFloorView getFloorView(int num)
	{
		for (Component c : mFloorsPane.getComponents())
		{
			if (c instanceof IFloorView)
			{
				if (((IFloorView) c).getFloorNumber() == num)
				{
					return (IFloorView) c;
				}
			}
		}
		return null;
	}

	@Override
	public void setPosition(int position)
	{
		mTextPosition.setText(String.valueOf(position));
	}

	@Override
	public void setAcceleration(int acc)
	{
		mTextAcceleration.setText(String.valueOf(acc));
	}

	@Override
	public void setDirection(int dir)
	{
		switch (dir)
		{
			case DIRECTION_DOWN:
				mTextDirection.setText("down");
				break;

			case DIRECTION_UP:
				mTextDirection.setText("up");
				break;

			case DIRECTION_NONE:
				mTextDirection.setText("none");
				break;

			default:
				assert false : "unexpected direction";
				mTextDirection.setText("");
				break;
		}
	}

	@Override
	public void setPayload(int payload)
	{
		mTextPayload.setText(String.valueOf(payload));
	}

	@Override
	public void setSpeed(int speed)
	{
		mTextSpeed.setText(String.valueOf(speed));
	}

	@Override
	public void addModeButtonListener(ActionListener l)
	{
		mModeButton.addActionListener(l);
	}

	@Override
	public void removeModeButtonListener(ActionListener l)
	{
		mModeButton.removeActionListener(l);
	}
}
