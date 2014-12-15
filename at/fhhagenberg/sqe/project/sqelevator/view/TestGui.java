package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TestGui extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					TestGui frame = new TestGui();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestGui()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(new ElevatorPanel(5));
		
		
		/*
		final FloorPanel floor = new FloorPanel(1);
		ActionListener l = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				floor.SetElevatorStatus(((floor.GetElevatorStatus() + 1) % 5));
				floor.SetMoveStatus(((floor.GetMoveStatus() + 1) % 4));
				floor.SetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN, !floor.GetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN));
				floor.SetFloorButton(FloorPanel.FLOOR_BUTTON_UP, !floor.GetFloorButton(FloorPanel.FLOOR_BUTTON_UP));
			}
		};
		floor.AddCallButtonListener(l);
		*/
		
//		contentPane.add(floor);
		
		setContentPane(contentPane);
	}

}
