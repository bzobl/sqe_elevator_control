package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;

import javax.swing.JLabel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.controller.IControl;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

public class MainViewTest extends ComponentTestFixture implements IControl
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 123456789876543211L;
	private MainView mView;
	private JLabel mStatusText;
	
	@Before
	public void setUp() throws Exception
	{
		mView = new MainView("testTitle");
		mView.setVisible(true);
		
		mView.setController(this);
		mView.resetView();
		
		mStatusText = (JLabel) getFinder().find(
				new ClassMatcher(JLabel.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JLabel) c).getName() != null 
								&& ((JLabel) c).getName().equals("statusText");
					}
				});
	}

	@Test
	public void testInitState()
	{
		assertEquals("", mStatusText.getText());
		assertNull(mView.getElevatorView(0));
	}
	
	@Test
	public void testStatusText()
	{
		mView.setStatusText("test");
		assertEquals("test", mStatusText.getText());
	}
	
	public void testElevatorViewNull()
	{
		assertNull(mView.getElevatorView(0));
	}
	
	public void testElevatorViewOneElevator()
	{
		mView.setNumElevators(1);
		mView.resetView();
		assertNotNull(mView.getElevatorView(0));
		assertNull(mView.getElevatorView(1));
	}
	
	public void testElevatorViewSeveralElevators()
	{
		mView.setNumElevators(5);
		mView.resetView();
		assertNotNull(mView.getElevatorView(0));
		assertNotNull(mView.getElevatorView(4));
		assertNull(mView.getElevatorView(5));
	}

	@Override
	public void changeControlMode(int elevator, boolean autoMode)
	{
		// dummy
	}

	@Override
	public void setCallRequest(int elevator, int floor)
	{
		// dummy
	}

	@Override
	public void setServicedFloor(int elevator, int floor, boolean isServiced)
	{
		// dummy
	}

	@Override
	public void updateView()
	{
		// dummy
	}

	@Override
	public void setView(IMainView view)
	{
		// dummy
	}
}
