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

public class FloorPanelElevatorStatusTest extends ComponentTestFixture
{
	private final String FILENAME_ELEVATOR_OPENED = "ellevator_door_opened_small.png";
	private final String FILENAME_ELEVATOR_OPENING = "ellevator_door_opening_small.png";
	private final String FILENAME_ELEVATOR_CLOSED = "ellevator_door_closed_small.png";
	private final String FILENAME_ELEVATOR_CLOSING = "ellevator_door_closing_small.png";

	private FloorPanel mFloorPanel;
	private JLabel mElevatorImage;

	@Before
	public void setUp() throws Exception 
	{
		mFloorPanel = new FloorPanel(1);
		showFrame(mFloorPanel);

		mElevatorImage = (JLabel) getFinder().find(new ClassMatcher(JLabel.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && (((JLabel) c).getName() != null) && ((JLabel) c).getName().equals("elevatorImage");
			}
		});
	}

	@Test
	public void testInitialStatus() 
	{
		int status = mFloorPanel.GetElevatorStatus();	
		assertEquals(FloorPanel.ELEVATOR_STATUS_AWAY, status);
	}

	public void testEachStatus() 
	{
		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_AWAY);
		assertEquals(FloorPanel.ELEVATOR_STATUS_AWAY, mFloorPanel.GetElevatorStatus());

		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_CLOSED);
		assertEquals(FloorPanel.ELEVATOR_STATUS_CLOSED, mFloorPanel.GetElevatorStatus());

		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_CLOSING);
		assertEquals(FloorPanel.ELEVATOR_STATUS_CLOSING, mFloorPanel.GetElevatorStatus());

		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_OPENED);
		assertEquals(FloorPanel.ELEVATOR_STATUS_OPENED, mFloorPanel.GetElevatorStatus());

		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_OPENING);
		assertEquals(FloorPanel.ELEVATOR_STATUS_OPENING, mFloorPanel.GetElevatorStatus());
	}
	
	public void testStatusImages() 
	{
		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_AWAY);
		assertEquals(FloorPanel.ELEVATOR_STATUS_AWAY, mFloorPanel.GetElevatorStatus());
		assertFalse(mElevatorImage.isVisible());

		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_CLOSED);
		assertEquals(FloorPanel.ELEVATOR_STATUS_CLOSED, mFloorPanel.GetElevatorStatus());
		assertTrue(mElevatorImage.getIcon().toString().contains(FILENAME_ELEVATOR_CLOSED));
		assertTrue(mElevatorImage.isVisible());
		
		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_CLOSING);
		assertEquals(FloorPanel.ELEVATOR_STATUS_CLOSING, mFloorPanel.GetElevatorStatus());
		assertTrue(mElevatorImage.getIcon().toString().contains(FILENAME_ELEVATOR_CLOSING));
		assertTrue(mElevatorImage.isVisible());

		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_OPENED);
		assertEquals(FloorPanel.ELEVATOR_STATUS_OPENED, mFloorPanel.GetElevatorStatus());
		assertTrue(mElevatorImage.getIcon().toString().contains(FILENAME_ELEVATOR_OPENED));
		assertTrue(mElevatorImage.isVisible());
		
		mFloorPanel.SetElevatorStatus(FloorPanel.ELEVATOR_STATUS_OPENING);
		assertEquals(FloorPanel.ELEVATOR_STATUS_OPENING, mFloorPanel.GetElevatorStatus());		
		assertTrue(mElevatorImage.getIcon().toString().contains(FILENAME_ELEVATOR_OPENING));
		assertTrue(mElevatorImage.isVisible());
	}
}
