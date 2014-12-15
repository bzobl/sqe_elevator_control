package at.fhhagenberg.sqe.project.sqelevator.view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;

public class FloorPanel extends JPanel
{

	/**
	 * Create the panel.
	 */
	public FloorPanel(int floorNumber)
	{
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
		
		JLabel lblElevatorImage = new JLabel("");
		lblElevatorImage.setIcon(new ImageIcon("/home/werner/Workspace/SQE/SQE-Project/img/ellevator_door_opened_small.png"));
		GridBagConstraints gbc_lblElevatorImage = new GridBagConstraints();
		gbc_lblElevatorImage.gridheight = 3;
		gbc_lblElevatorImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblElevatorImage.gridx = 1;
		gbc_lblElevatorImage.gridy = 0;
		add(lblElevatorImage, gbc_lblElevatorImage);
		
		JLabel lblUpDownImage = new JLabel("");
		lblUpDownImage.setIcon(new ImageIcon("/home/werner/Workspace/SQE/SQE-Project/img/upArrow_small.png"));
		GridBagConstraints gbc_lblUpDownImage = new GridBagConstraints();
		gbc_lblUpDownImage.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpDownImage.gridheight = 3;
		gbc_lblUpDownImage.gridx = 2;
		gbc_lblUpDownImage.gridy = 0;
		add(lblUpDownImage, gbc_lblUpDownImage);
		
		JToggleButton tglbtnCall = new JToggleButton("Call");
		GridBagConstraints gbc_tglbtnCall = new GridBagConstraints();
		gbc_tglbtnCall.anchor = GridBagConstraints.SOUTH;
		gbc_tglbtnCall.insets = new Insets(0, 0, 5, 0);
		gbc_tglbtnCall.gridx = 3;
		gbc_tglbtnCall.gridy = 0;
		add(tglbtnCall, gbc_tglbtnCall);
		
		JRadioButton rdbtnUp = new JRadioButton("UP");
		rdbtnUp.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnUp = new GridBagConstraints();
		gbc_rdbtnUp.anchor = GridBagConstraints.SOUTHWEST;
		gbc_rdbtnUp.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnUp.gridx = 3;
		gbc_rdbtnUp.gridy = 1;
		add(rdbtnUp, gbc_rdbtnUp);
		
		JRadioButton rdbtnDown = new JRadioButton("DOWN");
		rdbtnDown.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_rdbtnDown = new GridBagConstraints();
		gbc_rdbtnDown.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnDown.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnDown.gridx = 3;
		gbc_rdbtnDown.gridy = 2;
		add(rdbtnDown, gbc_rdbtnDown);

	}
}
