package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainView extends JFrame
{
	private static final long serialVersionUID = -563161883789974073L;
	private JPanel mElevatorPane;
	private int mNumberElevators = 0;
	
	public MainView()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mElevatorPane = new JPanel();
		mElevatorPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mElevatorPane);
		mElevatorPane.setLayout(new GridLayout(1, 0, 0, 0));
	}
	
	public IElevatorView addNewElevator(int floors) {
		ElevatorPanel pane = new ElevatorPanel(mNumberElevators, floors);
		mNumberElevators++;
		mElevatorPane.add(pane);
		return pane;
	}

	public IElevatorView getElevatorView(int num)
	{
		for (Component c : mElevatorPane.getComponents())
		{
			if (c instanceof IElevatorView)
			{
				if (((IElevatorView) c).getElevatorNumber() == num)
				{
					return (IElevatorView) c;
				}
			}
		}
		return null;
	}
}
