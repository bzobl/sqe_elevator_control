package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import at.fhhagenberg.sqe.project.sqelevator.controller.IControl;
import at.fhhagenberg.sqe.project.sqelevator.view.ElevatorButtonListener.ListenerType;

import com.sun.istack.internal.logging.Logger;

public class MainView extends JFrame implements IMainView
{
	private static Logger LOG = Logger.getLogger(MainView.class);

	private static final long serialVersionUID = -563161883789974073L;

	private final int NUM_ELEVATORS;
	private final int NUM_FLOORS;

	private JPanel mElevatorPane;
	
	public MainView(IControl control, int numElevators, int numFloors, String title)
	{
		NUM_ELEVATORS = numElevators;
		NUM_FLOORS = numFloors;

		setTitle(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		mElevatorPane = new JPanel();
		mElevatorPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mElevatorPane);
		
		// set more space between elevator panels
		GridLayout gl = new GridLayout(1, 0, 0, 0);
		gl.setHgap(20);
		
		mElevatorPane.setLayout(gl);
		
		initializeAllViews(control);
	}
	
	private void initializeAllViews(IControl control) {
        for (int e = 0; e < NUM_ELEVATORS; e++) {
        	IElevatorView eleView = addNewElevator(e, NUM_FLOORS);
        	ElevatorButtonListener mbl = new ElevatorButtonListener(ListenerType.MODE_BUTTON_LISTENER, control, e, -1);
        	eleView.addModeButtonListener(mbl);
        	LOG.info("initialization of IElevatorView " + e + " almost done");
        	
        	for (int f = 0; f < NUM_FLOORS; f++) {
        		IFloorView floorView = eleView.getFloorView(f);
        		
        		ElevatorButtonListener cbl = new ElevatorButtonListener(ListenerType.CALL_BUTTON_LISTENER, control, e, f);
        		ElevatorButtonListener sbl = new ElevatorButtonListener(ListenerType.SERVICE_BUTTON_LISTENER, control, e, f);
        		
        		floorView.addCallButtonListener(cbl);
        		floorView.addServiceButtonListener(sbl);
        		floorView.enableCallButton(true);
        	}
        }
	}
	
	private IElevatorView addNewElevator(int num, int floors) {
		ElevatorPanel pane = new ElevatorPanel(num, floors);
		mElevatorPane.add(pane);
		return pane;
	}

	/* (non-Javadoc)
	 * @see at.fhhagenberg.sqe.project.sqelevator.view.IMainView#getElevatorView(int)
	 */
	@Override
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
