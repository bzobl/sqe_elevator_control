package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;

import javax.swing.JLabel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.ElevatorPanel;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;

public class ElevatorPaneElevatorButtonTest extends ComponentTestFixture 
{
	private final String FILENAME_BUTTON_PRESSED = "buttonPressed_small.png";
	private final String FILENAME_BUTTON_RELEASED = "buttonReleased_small.png";
	
	private IElevatorView mElevatorView;
	private JLabel mButton0;
	private JLabel mButton1;
	
	@Before
	public void setUp() throws Exception
	{
		ElevatorPanel elevatorPanel = new ElevatorPanel(1, 2);
		showFrame(elevatorPanel);
	
		mElevatorView = elevatorPanel;
		
		mButton0 = (JLabel) getFinder().find(
				new ClassMatcher(JLabel.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JLabel) c).getText().equals(
										"0");
					}
				});
		
		mButton1 = (JLabel) getFinder().find(
				new ClassMatcher(JLabel.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JLabel) c).getText().equals(
										"1");
					}
				});
	}

	@Test
	public void testInitialState()
	{
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
	}
	
	@Test
	public void testPressSingleButton()
	{
		mElevatorView.setElevatorButton(0, true);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_PRESSED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		mElevatorView.setElevatorButton(0, false);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		mElevatorView.setElevatorButton(1, true);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_PRESSED));
		mElevatorView.setElevatorButton(1, false);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
	}
	
	@Test
	public void testPressAllButton()
	{
		mElevatorView.setElevatorButton(0, true);
		mElevatorView.setElevatorButton(1, true);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_PRESSED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_PRESSED));
		mElevatorView.setElevatorButton(0, false);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_PRESSED));
		mElevatorView.setElevatorButton(1, false);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
	}
	
	@Test
	public void testPressInvalidButton()
	{
		mElevatorView.setElevatorButton(2, true);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		mElevatorView.setElevatorButton(-1, true);
		assertTrue(mButton0.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
		assertTrue(mButton1.getIcon().toString().contains(FILENAME_BUTTON_RELEASED));
	}

}
