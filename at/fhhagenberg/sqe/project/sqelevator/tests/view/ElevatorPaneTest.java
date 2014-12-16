package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.ElevatorPanel;
import at.fhhagenberg.sqe.project.sqelevator.view.IElevatorView;
import at.fhhagenberg.sqe.project.sqelevator.view.IFloorView;

public class ElevatorPaneTest extends ComponentTestFixture
{

	private JToggleButton mModeButton;
	private JTextField mTextAcceleration;
	private JTextField mTextSpeed;
	private JTextField mTextPosition;
	private JTextField mTextPayload;
	
	private IElevatorView mElevatorView;
	
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
		
		mTextSpeed = (JTextField) getFinder().find(
				new ClassMatcher(JTextField.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c) 
								&& (((JTextField) c).getName() != null)
								&& ((JTextField) c).getName().equals(
										"textSpeed");
					}
				});
		
		mTextPayload = (JTextField) getFinder().find(
				new ClassMatcher(JTextField.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& (((JTextField) c).getName() != null)
								&& ((JTextField) c).getName().equals(
										"textPayload");
					}
				});
		
		mTextAcceleration = (JTextField) getFinder().find(
				new ClassMatcher(JTextField.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& (((JTextField) c).getName() != null)
								&& ((JTextField) c).getName().equals(
										"textAcceleration");
					}
				});
	
		mTextPosition = (JTextField) getFinder().find(
				new ClassMatcher(JTextField.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& (((JTextField) c).getName() != null)
								&& ((JTextField) c).getName().equals(
										"textPosition");
					}
				});
	}

	@Test
	public void testSpeed()
	{
		assertEquals("", mTextSpeed.getText());
		mElevatorView.setSpeed(1);
		assertEquals("1", mTextSpeed.getText());
		mElevatorView.setSpeed(-1);
		assertEquals("-1", mTextSpeed.getText());
	}

	@Test
	public void testAcceleration()
	{
		assertEquals("", mTextAcceleration.getText());
		mElevatorView.setAcceleration(1);
		assertEquals("1", mTextAcceleration.getText());
		mElevatorView.setAcceleration(-1);
		assertEquals("-1", mTextAcceleration.getText());
	}
	
	@Test
	public void testPayload()
	{
		assertEquals("", mTextPayload.getText());
		mElevatorView.setPayload(1);
		assertEquals("1", mTextPayload.getText());
		mElevatorView.setPayload(-1);
		assertEquals("-1", mTextPayload.getText());
	}

	@Test
	public void testPosition()
	{
		assertEquals("", mTextPosition.getText());
		mElevatorView.setPosition(1);
		assertEquals("1", mTextPosition.getText());
		mElevatorView.setPosition(-1);
		assertEquals("-1", mTextPosition.getText());
	}

	@Test
	public void testModeButton()
	{
		assertFalse(mModeButton.isSelected());
		mModeButton.doClick();
		assertTrue(mModeButton.isSelected());
		mModeButton.doClick();
		assertFalse(mModeButton.isSelected());
	}
	
	@Test
	public void testGetFloorView()
	{
		IFloorView v;
		v = mElevatorView.getFloorView(0);
		assertNotNull(v);
		assertEquals(0, v.getFloorNumber());
		
		v = mElevatorView.getFloorView(1);
		assertNotNull(v);
		assertEquals(1, v.getFloorNumber());
	}
	
	@Test
	public void testGetFloorViewIllegalFloor()
	{
		IFloorView v;
		v = mElevatorView.getFloorView(-1);
		assertNull(v);
		
		v = mElevatorView.getFloorView(2);
		assertNull(v);
	}
	
}
