package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;

import javax.swing.JLabel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.MainView;

public class MainViewTest extends ComponentTestFixture
{
	private MainView mView;
	private JLabel mStatusText;
	
	@Before
	public void setUp() throws Exception
	{
		mView = new MainView(null, 1, 1, "testTitle");
		mView.setVisible(true);
		
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
		assertEquals(0, mView.getElevatorView(0).getElevatorNumber());
		assertNull(mView.getElevatorView(1));
		assertEquals(0, mView.getElevatorView(0).getFloorView(0).getFloorNumber());
		assertNull(mView.getElevatorView(0).getFloorView(1));
	}
	
	@Test
	public void testStatusText()
	{
		mView.setStatusText("test");
		assertEquals("test", mStatusText.getText());
	}
}
