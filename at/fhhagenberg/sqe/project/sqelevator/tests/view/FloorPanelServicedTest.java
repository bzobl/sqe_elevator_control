package at.fhhagenberg.sqe.project.sqelevator.tests.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;
import abbot.tester.JComponentTester;
import at.fhhagenberg.sqe.project.sqelevator.view.FloorPanel;

public class FloorPanelServicedTest extends ComponentTestFixture
{
	private FloorPanel mFloorPanel;
	private JCheckBox mServicedChk;

	@Before
	public void setUp() throws Exception
	{
		mFloorPanel = new FloorPanel(1);
		showFrame(mFloorPanel);

		mServicedChk = (JCheckBox) getFinder().find(
				new ClassMatcher(JCheckBox.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JCheckBox) c).getText().equals("serviced");
					}
				});
	}

	@After
	public void tearDown() throws Exception
	{}

	@Test
	public void testInitState()
	{
		assertTrue(mServicedChk.isSelected());
		assertTrue(mFloorPanel.getServiceStatus());
	}

	@Test
	public void testToggle()
	{
		mFloorPanel.setServiceStatus(false);
		assertFalse(mServicedChk.isSelected());
		assertFalse(mFloorPanel.getServiceStatus());

		mFloorPanel.setServiceStatus(true);
		assertTrue(mServicedChk.isSelected());
		assertTrue(mFloorPanel.getServiceStatus());
	}

	private int mActionCounter = 0;
	private boolean mActionStatus = true;

	@Test
	public void testServicesListener()
	{
		ActionListener l = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mActionCounter++;
				mActionStatus = ((JCheckBox) e.getSource()).isSelected();
			}
		};

		mFloorPanel.addServiceButtonListener(l);

		JComponentTester t = new JComponentTester();
		t.actionClick(mServicedChk);

		assertEquals(1, mActionCounter);
		assertFalse(mActionStatus);

		t.actionClick(mServicedChk);
		assertEquals(2, mActionCounter);
		assertTrue(mActionStatus);
	}

	@Test
	public void testServicesListenerRemove()
	{
		ActionListener l = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mActionCounter++;
				mActionStatus = ((JCheckBox) e.getSource()).isSelected();
			}
		};

		mFloorPanel.addServiceButtonListener(l);

		JComponentTester t = new JComponentTester();
		t.actionClick(mServicedChk);

		assertEquals(1, mActionCounter);
		assertFalse(mActionStatus);

		mFloorPanel.removeServiceButtonListener(l);

		t.actionClick(mServicedChk);
		assertEquals(1, mActionCounter);
		assertFalse(mActionStatus);
	}
}
