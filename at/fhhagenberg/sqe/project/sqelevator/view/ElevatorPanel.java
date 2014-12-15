package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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

	private JPanel mElevatorPane;

	/**
	 * number of floors
	 */
	private final int NUMBER_OF_FLOORS;
	private JTextField textPosition;
	private JTextField textDirection;
	private JTextField textPayload;
	private JTextField textSpeed;
	private JTextField textAcceleration;

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
		mElevatorPane.add(floor, gbc);

		// add separator only between two floors
		if (num < (NUMBER_OF_FLOORS - 1))
		{
			gbc.gridy = (NUMBER_OF_FLOORS - num) * 2 - 2;
			gbc.insets = new Insets(5, 0, 5, 0);
			mElevatorPane.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
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

		textPosition = new JTextField();
		textPosition.setName("textPosition");
		GridBagConstraints gbc_textPosition = new GridBagConstraints();
		gbc_textPosition.insets = new Insets(0, 0, 5, 5);
		gbc_textPosition.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPosition.gridx = 1;
		gbc_textPosition.gridy = 2;
		add(textPosition, gbc_textPosition);
		textPosition.setColumns(10);

		JLabel lblSpeed = new JLabel("Speed:");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.anchor = GridBagConstraints.EAST;
		gbc_lblSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpeed.gridx = 2;
		gbc_lblSpeed.gridy = 2;
		add(lblSpeed, gbc_lblSpeed);

		textSpeed = new JTextField();
		textSpeed.setName("textSpeed");
		GridBagConstraints gbc_textSpeed = new GridBagConstraints();
		gbc_textSpeed.insets = new Insets(0, 0, 5, 0);
		gbc_textSpeed.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSpeed.gridx = 3;
		gbc_textSpeed.gridy = 2;
		add(textSpeed, gbc_textSpeed);
		textSpeed.setColumns(10);

		JLabel lblDirection = new JLabel("Direction:");
		GridBagConstraints gbc_lblDirection = new GridBagConstraints();
		gbc_lblDirection.anchor = GridBagConstraints.EAST;
		gbc_lblDirection.insets = new Insets(0, 0, 5, 5);
		gbc_lblDirection.gridx = 0;
		gbc_lblDirection.gridy = 3;
		add(lblDirection, gbc_lblDirection);

		textDirection = new JTextField();
		textDirection.setName("textDirection");
		GridBagConstraints gbc_textDirection = new GridBagConstraints();
		gbc_textDirection.insets = new Insets(0, 0, 5, 5);
		gbc_textDirection.fill = GridBagConstraints.HORIZONTAL;
		gbc_textDirection.gridx = 1;
		gbc_textDirection.gridy = 3;
		add(textDirection, gbc_textDirection);
		textDirection.setColumns(10);

		JLabel lblAcceleration = new JLabel("Acc.:");
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.anchor = GridBagConstraints.EAST;
		gbc_lblAcceleration.insets = new Insets(0, 0, 5, 5);
		gbc_lblAcceleration.gridx = 2;
		gbc_lblAcceleration.gridy = 3;
		add(lblAcceleration, gbc_lblAcceleration);

		textAcceleration = new JTextField();
		textAcceleration.setName("textAcceleration");
		GridBagConstraints gbc_textAcceleration = new GridBagConstraints();
		gbc_textAcceleration.insets = new Insets(0, 0, 5, 0);
		gbc_textAcceleration.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAcceleration.gridx = 3;
		gbc_textAcceleration.gridy = 3;
		add(textAcceleration, gbc_textAcceleration);
		textAcceleration.setColumns(10);

		JLabel lblPayload = new JLabel("Payload:");
		GridBagConstraints gbc_lblPayload = new GridBagConstraints();
		gbc_lblPayload.anchor = GridBagConstraints.EAST;
		gbc_lblPayload.insets = new Insets(0, 0, 0, 5);
		gbc_lblPayload.gridx = 0;
		gbc_lblPayload.gridy = 4;
		add(lblPayload, gbc_lblPayload);

		textPayload = new JTextField();
		GridBagConstraints gbc_textPayload = new GridBagConstraints();
		gbc_textPayload.insets = new Insets(0, 0, 0, 5);
		gbc_textPayload.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPayload.gridx = 1;
		gbc_textPayload.gridy = 4;
		add(textPayload, gbc_textPayload);
		textPayload.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();

		// only allow vertical scrolling
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		mElevatorPane = new JPanel();
		mElevatorPane.setLayout(new GridBagLayout());
		scrollPane.setViewportView(mElevatorPane);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;

		add(scrollPane, gbc);

		JToggleButton tglbtnAutoMode = new JToggleButton("Auto Mode");
		GridBagConstraints gbc_tglbtnAutoMode = new GridBagConstraints();
		gbc_tglbtnAutoMode.gridwidth = 2;
		gbc_tglbtnAutoMode.insets = new Insets(0, 0, 0, 5);
		gbc_tglbtnAutoMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnAutoMode.gridx = 2;
		gbc_tglbtnAutoMode.gridy = 4;
		add(tglbtnAutoMode, gbc_tglbtnAutoMode);

		// create floors
		for (int i = 0; i < NUMBER_OF_FLOORS; i++)
		{
			addFloor(i);
		}
	}

	@Override
	public IFloorView getFloorView(int num)
	{
		for (Component c : mElevatorPane.getComponents())
		{
			if (c instanceof IFloorView)
			{
				if (((IFloorView) c).GetFloorNumber() == num)
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
		textPosition.setText(String.valueOf(position));
	}

	@Override
	public void setAcceleration(int acc)
	{
		textAcceleration.setText(String.valueOf(acc));
	}

	@Override
	public void setDirection(int dir)
	{
		switch (dir)
		{
			case DIRECTION_DOWN:
				textDirection.setText("down");
				break;

			case DIRECTION_UP:
				textDirection.setText("up");
				break;

			case DIRECTION_NONE:
				textDirection.setText("none");
				break;

			default:
				assert false : "unexpected direction";
				textDirection.setText("");
				break;
		}
	}

	@Override
	public void setPayload(int payload)
	{
		textPayload.setText(String.valueOf(payload));
	}

	@Override
	public void setSpeed(int speed)
	{
		textSpeed.setText(String.valueOf(speed));
	}
}
