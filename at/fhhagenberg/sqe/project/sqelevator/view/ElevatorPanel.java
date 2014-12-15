package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

public class ElevatorPanel extends JScrollPane
{
	/**
	 * id for serialization
	 */
	private static final long serialVersionUID = -5003028963381832505L;
	
	private JPanel mPane;
	
	/**
	 * 
	 */
	public final int NUMBER_OF_FLOORS;
	
	private void addFloor(int num)
	{
		FloorPanel floor = new FloorPanel(num);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = (NUMBER_OF_FLOORS - num) * 2 - 1;
		mPane.add(floor, gbc);
		
		// add separator only between two floors
		if (num < (NUMBER_OF_FLOORS - 1))
		{
			gbc.gridy = (NUMBER_OF_FLOORS - num) * 2 - 2;
			gbc.gridy = 5;
			mPane.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		}	
	}
	
	/**
	 * Create the panel.
	 */
	public ElevatorPanel(int numFloors)
	{
		NUMBER_OF_FLOORS = numFloors;
		
		// only allow vertical scrolling
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
		mPane = new JPanel();
		mPane.setLayout(new GridBagLayout());
		setViewportView(mPane);
		
		// create floors
		for (int i = 0; i < NUMBER_OF_FLOORS; i++) {
			addFloor(i);
		}	
	}

}
