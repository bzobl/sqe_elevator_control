package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import sun.applet.Main;
import abbot.finder.matchers.ClassMatcher;
import at.fhhagenberg.sqe.project.sqelevator.view.FloorPanel;

public class FloorPanelCallButton extends ComponentTestFixture
{
	private final String FILENAME_MOVE_STATUS_UP = "upArrow_small.png";
	private final String FILENAME_MOVE_STATUS_DOWN = "downArrow_small.png";
	private final String FILENAME_MOVE_STATUS_STANDING = "standing_small.png";
	
	private FloorPanel mFloorPanel;
	private JToggleButton mCallButton;
	private int mActionCounter = 0;

	@Before
	public void setUp() throws Exception 
	{
		mFloorPanel = new FloorPanel(1);
		showFrame(mFloorPanel);

		mCallButton = (JToggleButton) getFinder().find(new ClassMatcher(JToggleButton.class)
		{
			public boolean matches(Component c)
			{
				return super.matches(c) && (((JToggleButton) c).getText() != null) 
										&& ((JToggleButton) c).getText().equals("Call");
			}
		});
	}

	@Test
	public void testCallButton() 
	{
		ActionListener l = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mActionCounter++;
			}
		};
		mFloorPanel.addCallButtonListener(l);
		
		assertEquals(0,  mActionCounter);
        mCallButton.doClick();
		assertEquals(1, mActionCounter);
		assertTrue(mCallButton.isSelected());
        mCallButton.doClick();
		assertEquals(2, mActionCounter);
		assertFalse(mCallButton.isSelected());
	}
}
