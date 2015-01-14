package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.Component;

import javax.swing.JLabel;

import junit.extensions.abbot.ComponentTestFixture;

import org.junit.Before;
import org.junit.Test;

import abbot.finder.matchers.ClassMatcher;

public class ConnectingDialogTest extends ComponentTestFixture
{

	private ConnectingDialog mCD;
	private JLabel mRemoteLabel;
	
	@Before
	public void setUp() throws Exception
	{
		mCD = new ConnectingDialog();

		mRemoteLabel = (JLabel) getFinder().find(
				new ClassMatcher(JLabel.class)
				{
					public boolean matches(Component c)
					{
						return super.matches(c)
								&& ((JLabel) c).getName() != null 
								&& ((JLabel) c).getName().equals("remoteLabel");
					}
				});
	}

	@Test
	public void testSetRemoteName()
	{
		assertEquals("", mRemoteLabel.getText());
		mCD.setRemoteName("TEST");
		assertEquals("TEST", mRemoteLabel.getText());
	}

}
