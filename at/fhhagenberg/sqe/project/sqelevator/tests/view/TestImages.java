package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.FloorPanel;

public class TestImages extends ComponentTestFixture
{
	private JToggleButton mCallButton;
	private JLabel mElevatorImage;
	private JLabel mUpDownImage;
	private JRadioButton mFloorButtonUp;
	private JRadioButton mFloorButtonDown;

	private JLabel mFloorNumber;

	@Before
	public void setUp() throws Exception
	{
		JFrame frame = new JFrame();
		JPanel floorPanel = new FloorPanel(1);
		frame.add(floorPanel);
		frame.setVisible(true);

		mCallButton = (JToggleButton) getFinder().find(
				new ClassMatcher(JToggleButton.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JToggleButton) c).getText().equals("Call");
					}
				});

		mFloorNumber = (JLabel) getFinder().find(new ClassMatcher(JLabel.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && ((JLabel) c).getText().equals("1");
			}
		});

		mElevatorImage = (JLabel) getFinder().find(
				new ClassMatcher(JLabel.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& (((JLabel) c).getName() != null)
								&& ((JLabel) c).getName().equals(
										"elevatorImage");
					}
				});

		mUpDownImage = (JLabel) getFinder().find(new ClassMatcher(JLabel.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && (((JLabel) c).getName() != null)
						&& ((JLabel) c).getName().equals("upDownImage");
			}
		});

		mFloorButtonDown = (JRadioButton) getFinder().find(
				new ClassMatcher(JRadioButton.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JRadioButton) c).getText().equals("DOWN");
					}
				});

		mFloorButtonUp = (JRadioButton) getFinder().find(
				new ClassMatcher(JRadioButton.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JRadioButton) c).getText().equals("UP");
					}
				});
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testInitialState()
	{
		assertEquals("1", mFloorNumber.getText());
		assertFalse(mFloorButtonDown.isSelected());
		assertFalse(mFloorButtonUp.isSelected());
		assertFalse(mElevatorImage.isVisible());
		assertFalse(mUpDownImage.isVisible());
		assertFalse(mCallButton.isSelected());
	}

}
