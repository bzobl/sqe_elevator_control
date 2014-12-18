package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;

import javax.swing.JLabel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.FloorPanel;

public class FloorPanelMoveStatusTest extends ComponentTestFixture
{
	private final String FILENAME_MOVE_STATUS_UP = "upArrow_small.png";
	private final String FILENAME_MOVE_STATUS_DOWN = "downArrow_small.png";
	private final String FILENAME_MOVE_STATUS_STANDING = "standing_small.png";
	
	private FloorPanel mFloorPanel;
	private JLabel mUpDownImage;

	@Before
	public void setUp() throws Exception 
	{
		mFloorPanel = new FloorPanel(1);
		showFrame(mFloorPanel);

		mUpDownImage = (JLabel) getFinder().find(new ClassMatcher(JLabel.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && (((JLabel) c).getName() != null) && ((JLabel) c).getName().equals("upDownImage");
			}
		});
	}

	@Test
	public void testInitialStatus() 
	{
		int status = mFloorPanel.getMoveStatus();	
		assertEquals(FloorPanel.MOVE_STATUS_AWAY, status);
	}

	public void testEachStatus() 
	{
		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_AWAY);
		assertEquals(FloorPanel.MOVE_STATUS_AWAY, mFloorPanel.getMoveStatus());

		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_DOWN);
		assertEquals(FloorPanel.MOVE_STATUS_DOWN, mFloorPanel.getMoveStatus());

		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_STANDING);
		assertEquals(FloorPanel.MOVE_STATUS_STANDING, mFloorPanel.getMoveStatus());

		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_UP);
		assertEquals(FloorPanel.MOVE_STATUS_UP, mFloorPanel.getMoveStatus());
	}
	
	public void testStatusImages()  
	{
		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_AWAY);
		assertEquals(FloorPanel.MOVE_STATUS_AWAY, mFloorPanel.getMoveStatus());
		assertFalse(mUpDownImage.isVisible());

		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_DOWN);
		assertEquals(FloorPanel.MOVE_STATUS_DOWN, mFloorPanel.getMoveStatus());
		assertTrue(mUpDownImage.getIcon().toString().contains(FILENAME_MOVE_STATUS_DOWN));
		assertTrue(mUpDownImage.isVisible());
		
		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_STANDING);
		assertEquals(FloorPanel.MOVE_STATUS_STANDING, mFloorPanel.getMoveStatus());
		assertTrue(mUpDownImage.getIcon().toString().contains(FILENAME_MOVE_STATUS_STANDING));
		assertTrue(mUpDownImage.isVisible());

		mFloorPanel.setMoveStatus(FloorPanel.MOVE_STATUS_UP);
		assertEquals(FloorPanel.MOVE_STATUS_UP, mFloorPanel.getMoveStatus());
		assertTrue(mUpDownImage.getIcon().toString().contains(FILENAME_MOVE_STATUS_UP));
		assertTrue(mUpDownImage.isVisible());
	}
}
