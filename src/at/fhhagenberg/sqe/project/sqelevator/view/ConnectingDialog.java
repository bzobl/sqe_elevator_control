package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ConnectingDialog extends JDialog implements IConnectingView
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6107331954132616961L;

	private final JPanel CONTENT_PANEL = new JPanel();	
	
	private JLabel mLabelRemoteName;
	
	private JButton mCancelButton;
	
	/**
	 * Create the dialog.
	 */
	public ConnectingDialog()
	{		
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Connecting");
		setBounds(100, 100, 300, 120);
		getContentPane().setLayout(new BorderLayout());
		CONTENT_PANEL.setLayout(new FlowLayout());
		CONTENT_PANEL.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(CONTENT_PANEL, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Connecting to remote simulator at");
			CONTENT_PANEL.add(lblNewLabel);
		}
		{
			mLabelRemoteName = new JLabel();
			mLabelRemoteName.setName("remoteLabel");
			CONTENT_PANEL.add(mLabelRemoteName);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				mCancelButton = new JButton("Cancel");		
				buttonPane.add(mCancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	@Override
	public void setRemoteName(String remote)
	{
		assert(mLabelRemoteName != null) : "label must exist";
		mLabelRemoteName.setText(remote);;
	}
	
	@Override
	public void setCancelActionListener(ActionListener a)
	{
		mCancelButton.addActionListener(a);
	}
}
