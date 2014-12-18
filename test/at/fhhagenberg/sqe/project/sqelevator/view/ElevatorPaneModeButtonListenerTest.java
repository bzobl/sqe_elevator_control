package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;

public class ElevatorPaneModeButtonListenerTest extends ComponentTestFixture
{

	private ElevatorPanel mElevatorView;
	private JToggleButton mModeButton;
	
	@Before
	public void setUp() throws Exception
	{
		ElevatorPanel elevatorPanel = new ElevatorPanel(1, 2);
		showFrame(elevatorPanel);
	
		mElevatorView = elevatorPanel;
		
		mModeButton = (JToggleButton) getFinder().find(
				new ClassMatcher(JToggleButton.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JToggleButton) c).getText().equals(
										"Auto Mode");
					}
				});
	}
	
	@Test
	public void testRegisterSingleListener()
	{
		TestActionListener al = new TestActionListener();
		mElevatorView.addModeButtonListener(al);
		mModeButton.doClick();
		assertEquals(1, al.ActionCounter);
		assertTrue(al.ButtonStatus);
		mModeButton.doClick();
		assertEquals(2, al.ActionCounter);
		assertFalse(al.ButtonStatus);
	}
	
	@Test
	public void testRegisterSeveralListener()
	{
		TestActionListener al = new TestActionListener();
		TestActionListener al2 = new TestActionListener();
		
		mElevatorView.addModeButtonListener(al);
		mElevatorView.addModeButtonListener(al2);
		mModeButton.doClick();
		assertEquals(1, al.ActionCounter);
		assertTrue(al.ButtonStatus);
		assertEquals(1, al2.ActionCounter);
		assertTrue(al2.ButtonStatus);
		mModeButton.doClick();
		assertEquals(2, al.ActionCounter);
		assertFalse(al.ButtonStatus);
		assertEquals(2, al2.ActionCounter);
		assertFalse(al2.ButtonStatus);
	}

	@Test
	public void testAddAndRemoveListener()
	{
		TestActionListener al = new TestActionListener();
		TestActionListener al2 = new TestActionListener();
		
		mElevatorView.addModeButtonListener(al);
		mElevatorView.addModeButtonListener(al2);
		mModeButton.doClick();
		assertEquals(1, al.ActionCounter);
		assertTrue(al.ButtonStatus);
		assertEquals(1, al2.ActionCounter);
		assertTrue(al2.ButtonStatus);
		mElevatorView.removeModeButtonListener(al);
		mModeButton.doClick();
		assertEquals(1, al.ActionCounter);
		assertTrue(al.ButtonStatus);
		assertEquals(2, al2.ActionCounter);
		assertFalse(al2.ButtonStatus);
	}
	
	private class TestActionListener implements ActionListener
	{
		public boolean ButtonStatus = false;
		public int ActionCounter = 0;
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			ActionCounter++;
			ButtonStatus = ((JToggleButton)e.getSource()).isSelected();
		}
		
	};
	
}
