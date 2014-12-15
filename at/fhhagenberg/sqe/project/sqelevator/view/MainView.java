package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class MainView extends JFrame
{

	private JPanel contentPane;

	private final int NUM_FLOORS = 4;
	
	/**
	 * Create the frame.
	 */
	public MainView(int numElevators)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		for(int i = 0; i < numElevators; ++i)
		{
			contentPane.add(new ElevatorPanel(NUM_FLOORS));
		}
	}

}
