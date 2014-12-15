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
	 * 
	 */
	private static final long serialVersionUID = -5003028963381832505L;
	
	private JPanel mPane;
	private final int MAX_FLOORS = 3;
	
	private void addFloor(int num)
	{
		FloorPanel floor = new FloorPanel(num);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = (MAX_FLOORS - num) * 2 - 1;
		mPane.add(floor, gbc);
		
		if (num < (MAX_FLOORS - 1))
		{
			gbc.gridy = (MAX_FLOORS - num) * 2 - 2;
			gbc.ipady = 5;
			mPane.add(new JSeparator(JSeparator.HORIZONTAL), gbc);
		}	
	}
	
	/**
	 * Create the panel.
	 */
	public ElevatorPanel()
	{
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
		mPane = new JPanel();
		mPane.setLayout(new GridBagLayout());
		setViewportView(mPane);
		
		for (int i = 0; i < MAX_FLOORS; i++) {
			addFloor(i);
		}

		
		//
//		gbl.setConstraints(comp, constraints);
//		
//		JPanel pane = new JPanel(gbl);
//		
//		JPanel floorPanel = new FloorPanel();
//		pane.add(floorPanel);
//		
//		JSeparator sepp = new JSeparator(JSeparator.HORIZONTAL);
//		sepp.setMaximumSize(new Dimension(floorPanel.getWidth(), 2));
//		pane.add(sepp);
//		
//		JPanel floorPanel1 = new FloorPanel();
//		pane.add(floorPanel1);
//		
//		JSeparator sepp1 = new JSeparator(JSeparator.HORIZONTAL);
//		sepp1.setSize(floorPanel.getWidth(), 3);
//		pane.add(sepp1);
//		
//		setViewportView(pane);
//		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	
	}

}
