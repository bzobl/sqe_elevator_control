package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.event.ActionListener;

public interface IConnectingView
{
	public void setRemoteName(String remote);
	public void setVisible(boolean visible);
	
	public void setCancelActionListener(ActionListener a);
}
