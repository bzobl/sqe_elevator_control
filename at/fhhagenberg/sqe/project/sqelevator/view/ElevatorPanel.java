package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class ElevatorPanel extends JPanel implements IElevatorView
{
	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = -5003028963381832505L;

	/**
	 * path to the button icons
	 */
	private final String SRC_BUTTON_PRESSED = "/img/buttonPressed_small.png";
	private final String SRC_BUTTON_RELEASED = "/img/buttonReleased_small.png";
	
	/**
	 * panel which contains all floors
	 */
	private JPanel mFloorsPane;

	/**
	 * number of floors
	 */
	private final int NUMBER_OF_FLOORS;

	/**
	 * number of this elevator
	 */
	private int NUMBER_ELEVATOR;
	
	/**
	 * panel for elevator buttons
	 */
	private JPanel mElevatorButtonPanel;
	
	/**
	 * text fields from front end
	 */
	private JTextField mTextPosition;
	private JTextField mTextPayload;
	private JTextField mTextSpeed;
	private JTextField mTextAcceleration;

	/**
	 * mode button for auto/manual mode
	 */
	private JToggleButton mModeButton;
	private JSeparator separator_1;
	private JLabel lblFeet;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	
	/**
	 * set icon for elevator floor buttons
	 * @param l	label with the icon 
	 * @param on	status pressed/released
	 */
	private void setElevatorButtonIcon(JLabel l, boolean on)
	{
		if (on)
		{
			l.setIcon(new ImageIcon(ElevatorPanel.class.getResource(SRC_BUTTON_PRESSED)));
		}
		else
		{
			l.setIcon(new ImageIcon(ElevatorPanel.class.getResource(SRC_BUTTON_RELEASED)));
		}
	}
	
	/**
	 * add floor to the panel
	 * 
	 * @param num
	 *            floor number
	 */
	private void addFloor(Integer num)
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
		
		JLabel label = new JLabel(num.toString());
		Dimension d = label.getPreferredSize();
		d.width = 40;
		d.height += 10;
		label.setPreferredSize(d);
		label.setMinimumSize(d);
		setElevatorButtonIcon(label, false);
		mElevatorButtonPanel.add(label);
	}

	/**
	 * Create the panel.
	 * @param floors 
	 * 
	 * @numFloors number of floors
	 */
	public ElevatorPanel(int num, int floors)
	{
		NUMBER_ELEVATOR = num;
		NUMBER_OF_FLOORS = floors;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 85, 25, 35, 75, 25, 45, 0 };
		gridBagLayout.rowHeights = new int[] { 20, 5, 0, 5, 25, 25, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 2.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.anchor = GridBagConstraints.EAST;
		gbc_separator.gridwidth = 6;
		gbc_separator.insets = new Insets(5, 5, 5, 5);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);
		
		mElevatorButtonPanel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.gridwidth = 6;
		gbc_panel.insets = new Insets(5, 15, 5, 15);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(mElevatorButtonPanel, gbc_panel);

		final int MaxCols = 6;
		GridLayout gl = new GridLayout(num / MaxCols, MaxCols);
		
		mElevatorButtonPanel.setLayout(gl);
		
		separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1.anchor = GridBagConstraints.EAST;
		gbc_separator_1.gridwidth = 6;
		gbc_separator_1.insets = new Insets(5, 5, 5, 5);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 3;
		add(separator_1, gbc_separator_1);
		
		JLabel lblPosition = new JLabel("Position:");
		GridBagConstraints gbc_lblPosition = new GridBagConstraints();
		gbc_lblPosition.insets = new Insets(5, 5, 5, 5);
		gbc_lblPosition.anchor = GridBagConstraints.EAST;
		gbc_lblPosition.gridx = 0;
		gbc_lblPosition.gridy = 4;
		add(lblPosition, gbc_lblPosition);

		mTextPosition = new JTextField();
		mTextPosition.setEditable(false);
		mTextPosition.setName("textPosition");
		mTextPosition.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_textPosition = new GridBagConstraints();
		gbc_textPosition.insets = new Insets(5, 0, 5, 0);
		gbc_textPosition.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPosition.gridx = 1;
		gbc_textPosition.gridy = 4;
		add(mTextPosition, gbc_textPosition);
		mTextPosition.setColumns(10);
		
		lblFeet = new JLabel("ft");
		GridBagConstraints gbc_lblFeet = new GridBagConstraints();
		gbc_lblFeet.anchor = GridBagConstraints.WEST;
		gbc_lblFeet.insets = new Insets(5, 5, 5, 0);
		gbc_lblFeet.gridx = 2;
		gbc_lblFeet.gridy = 4;
		add(lblFeet, gbc_lblFeet);

		JLabel lblSpeed = new JLabel("Speed:");
		GridBagConstraints gbc_lblSpeed = new GridBagConstraints();
		gbc_lblSpeed.anchor = GridBagConstraints.EAST;
		gbc_lblSpeed.insets = new Insets(5, 5, 5, 5);
		gbc_lblSpeed.gridx = 3;
		gbc_lblSpeed.gridy = 4;
		add(lblSpeed, gbc_lblSpeed);
		
		mTextSpeed = new JTextField();
		mTextSpeed.setEditable(false);
		mTextSpeed.setName("textSpeed");
		mTextSpeed.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_textSpeed = new GridBagConstraints();
		gbc_textSpeed.insets = new Insets(5, 0, 5, 0);
		gbc_textSpeed.fill = GridBagConstraints.HORIZONTAL;
		gbc_textSpeed.gridx = 4;
		gbc_textSpeed.gridy = 4;
		add(mTextSpeed, gbc_textSpeed);
		mTextSpeed.setColumns(10);
		
		lblNewLabel_2 = new JLabel("ft/s");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel_2.gridx = 5;
		gbc_lblNewLabel_2.gridy = 4;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblPayload = new JLabel("Payload:");
		GridBagConstraints gbc_lblPayload = new GridBagConstraints();
		gbc_lblPayload.anchor = GridBagConstraints.EAST;
		gbc_lblPayload.insets = new Insets(5, 5, 5, 5);
		gbc_lblPayload.gridx = 0;
		gbc_lblPayload.gridy = 5;
		add(lblPayload, gbc_lblPayload);

		mTextPayload = new JTextField();
		mTextPayload.setEditable(false);
		mTextPayload.setName("textPayload");
		mTextPayload.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_textPayload = new GridBagConstraints();
		gbc_textPayload.insets = new Insets(5, 0, 5, 0);
		gbc_textPayload.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPayload.gridx = 1;
		gbc_textPayload.gridy = 5;
		add(mTextPayload, gbc_textPayload);
		mTextPayload.setColumns(10);
		
		lblNewLabel = new JLabel("kg");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(5, 5, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblAcceleration = new JLabel("Acc.:");
		GridBagConstraints gbc_lblAcceleration = new GridBagConstraints();
		gbc_lblAcceleration.anchor = GridBagConstraints.EAST;
		gbc_lblAcceleration.insets = new Insets(5, 5, 5, 5);
		gbc_lblAcceleration.gridx = 3;
		gbc_lblAcceleration.gridy = 5;
		add(lblAcceleration, gbc_lblAcceleration);

		JScrollPane scrollPane = new JScrollPane();

		// only allow vertical scrolling
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		mFloorsPane = new JPanel();
		mFloorsPane.setLayout(new GridBagLayout());
		scrollPane.setViewportView(mFloorsPane);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipady = 5;
		gbc.gridwidth = 6;

		add(scrollPane, gbc);
		
		mTextAcceleration = new JTextField();
		mTextAcceleration.setEditable(false);
		mTextAcceleration.setName("textAcceleration");
		mTextAcceleration.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_textAcceleration = new GridBagConstraints();
		gbc_textAcceleration.insets = new Insets(5, 0, 5, 0);
		gbc_textAcceleration.fill = GridBagConstraints.HORIZONTAL;
		gbc_textAcceleration.gridx = 4;
		gbc_textAcceleration.gridy = 5;
		add(mTextAcceleration, gbc_textAcceleration);
		mTextAcceleration.setColumns(10);
		
		lblNewLabel_1 = new JLabel("ft/s²");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(5, 5, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 5;
		add(lblNewLabel_1, gbc_lblNewLabel_1);

		mModeButton = new JToggleButton("Auto Mode");
		GridBagConstraints gbc_tglbtnAutoMode = new GridBagConstraints();
		gbc_tglbtnAutoMode.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnAutoMode.anchor = GridBagConstraints.EAST;
		gbc_tglbtnAutoMode.gridwidth = 6;
		gbc_tglbtnAutoMode.insets = new Insets(5, 5, 5, 5);
		gbc_tglbtnAutoMode.gridx = 0;
		gbc_tglbtnAutoMode.gridy = 6;
		add(mModeButton, gbc_tglbtnAutoMode);

		// create floors
		for (int i = 0; i < NUMBER_OF_FLOORS; i++)
		{
			addFloor(i);
		}
	}
	
	@Override
	public int getElevatorNumber()
	{
		return NUMBER_ELEVATOR;
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

	@Override
	public void setElevatorButton(int floorNum, boolean on)
	{
		if ((floorNum >= 0) && (floorNum < mElevatorButtonPanel.getComponentCount()))
		{
			JLabel l = (JLabel)mElevatorButtonPanel.getComponent(floorNum);
			setElevatorButtonIcon(l, on);
		}
	}
}
