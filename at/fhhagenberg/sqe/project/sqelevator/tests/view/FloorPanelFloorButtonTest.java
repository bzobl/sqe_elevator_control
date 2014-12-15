package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.FloorPanel;

public class FloorPanelFloorButtonTest extends ComponentTestFixture
{
	private final String FILENAME_FLOOR_BTN_UP_OFF = "colorArrowUp_off_small.png";
	private final String FILENAME_FLOOR_BTN_UP_ON = "colorArrowUp_small.png";
	private final String FILENAME_FLOOR_BTN_DOWN_OFF = "colorArrowDown_off_small.png";
	private final String FILENAME_FLOOR_BTN_DOWN_ON = "colorArrowDown_small.png";
	
	private FloorPanel mFloorPanel;
	private JLabel mFloorUpImage;
	private JLabel mFloorDownImage;

	@Before
	public void setUp() throws Exception 
	{
		mFloorPanel = new FloorPanel(1);
		showFrame(mFloorPanel);

		mFloorUpImage = (JLabel) getFinder().find(new ClassMatcher(JLabel.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && (((JLabel) c).getName() != null) && ((JLabel) c).getName().equals("floorUpImage");
			}
		});
		
		mFloorDownImage = (JLabel) getFinder().find(new ClassMatcher(JLabel.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && (((JLabel) c).getName() != null) && ((JLabel) c).getName().equals("floorDownImage");
			}
		});
	}

	@Test
	public void testInitialStatus() 
	{
		assertTrue(mFloorUpImage.isVisible());
		assertTrue(mFloorUpImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_UP_OFF));
		assertFalse(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_UP));
		
		assertTrue(mFloorDownImage.isVisible());
		assertTrue(mFloorDownImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_DOWN_OFF));
		assertFalse(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN));
	}

	public void testUpButton() 
	{
		assertTrue(mFloorUpImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_UP_OFF));
		assertFalse(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_UP));
		mFloorPanel.SetFloorButton(FloorPanel.FLOOR_BUTTON_UP, true);
		assertTrue(mFloorUpImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_UP_ON));
		assertTrue(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_UP));

		mFloorPanel.SetFloorButton(FloorPanel.FLOOR_BUTTON_UP, false);
		assertTrue(mFloorUpImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_UP_OFF));
		assertFalse(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_UP));
	}
	
	public void testDownButton() 
	{
		assertTrue(mFloorDownImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_DOWN_OFF));
		assertFalse(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN));
		mFloorPanel.SetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN, true);
		assertTrue(mFloorDownImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_DOWN_ON));
		assertTrue(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN));

		mFloorPanel.SetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN, false);
		assertTrue(mFloorDownImage.getIcon().toString().contains(FILENAME_FLOOR_BTN_DOWN_OFF));
		assertFalse(mFloorPanel.GetFloorButton(FloorPanel.FLOOR_BUTTON_DOWN));
	}
	
	// TODO:
//	public void testInvalidButton() {
//	}
	
}
