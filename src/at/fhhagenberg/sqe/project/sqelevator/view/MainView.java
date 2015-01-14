package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import at.fhhagenberg.sqe.project.sqelevator.controller.IControl;
import at.fhhagenberg.sqe.project.sqelevator.view.ElevatorButtonListener.ListenerType;

import com.sun.istack.internal.logging.Logger;

public class MainView extends JFrame implements IMainView
{
	private static Logger LOG = Logger.getLogger(MainView.class);

	private static final long serialVersionUID = -563161883789974073L;

	private JPanel mElevatorPane;
	private JPanel mMainPanel;
	private JLabel mLblStatus;
	
	private int mNumFloors = 0;
	private int mNumElevators = 0;
	
	private IControl mController;
	
	@Override
	public void resetView()
	{
		mElevatorPane = new JPanel();
		mElevatorPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// set more space between elevator panels
		GridLayout gl = new GridLayout(1, 0, 0, 0);
		gl.setHgap(20);
		mElevatorPane.setLayout(gl);
		
		GridBagLayout glb = new GridBagLayout();
		glb.rowWeights = new double[]{1.0, 0.0};
		glb.columnWeights = new double[]{1.0};
		glb.columnWidths = new int[]{0};
		
		glb.rowHeights = new int[]{0, 22};
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		
		GridBagConstraints gbcStatusBar = new GridBagConstraints();
		gbcStatusBar.fill = GridBagConstraints.BOTH;
		gbcStatusBar.gridx = 0;
		gbcStatusBar.gridy = 1;
		JPanel mStatusPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) mStatusPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		mStatusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));		
		
		mMainPanel = new JPanel(glb);
		mMainPanel.setBorder(new EmptyBorder(5,5,5,5));
		mMainPanel.add(mElevatorPane, gbc);
		mMainPanel.add(mStatusPanel, gbcStatusBar);
		
		mLblStatus = new JLabel("");
		mLblStatus.setName("statusText");
		mLblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		mStatusPanel.add(mLblStatus);
		
		setContentPane(mMainPanel);
		
		initializeAllViews();
	}
	
	@Override
	public void setNumFloors(int n)
	{
		if (n < 0)
			n = 0;
		
		if (n != mNumFloors)
		{
			mNumFloors = n;
		}
	}
	
	@Override
	public void setNumElevators(int n)
	{
		if (n < 0)
			n = 0;
		
		if (n != mNumElevators)
		{
			mNumElevators = n;
		}
	}
	
	public void setController(IControl ctrl)
	{
	    mController = ctrl;
	}
	
	public MainView(String title)
	{	
		setTitle(title);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
	}
	
	private void initializeAllViews() {
		assert(mController != null) : "controller must not be null";
		
        for (int e = 0; e < mNumElevators; e++) {
        	IElevatorView eleView = addNewElevator(e, mNumFloors);
        	ElevatorButtonListener mbl = new ElevatorButtonListener(ListenerType.MODE_BUTTON_LISTENER, mController, e, -1);
        	eleView.addModeButtonListener(mbl);
        	LOG.info("initialization of IElevatorView " + e + " almost done");
        	
        	for (int f = 0; f < mNumFloors; f++) {
        		IFloorView floorView = eleView.getFloorView(f);
        		
        		ElevatorButtonListener cbl = new ElevatorButtonListener(ListenerType.CALL_BUTTON_LISTENER, mController, e, f);
        		ElevatorButtonListener sbl = new ElevatorButtonListener(ListenerType.SERVICE_BUTTON_LISTENER, mController, e, f);
        		
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

	@Override
	public void setStatusText(String statusText)
	{
		mLblStatus.setText(statusText);	
	}
}
