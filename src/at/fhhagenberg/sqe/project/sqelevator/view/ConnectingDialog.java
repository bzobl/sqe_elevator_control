package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
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

	private final JPanel contentPanel = new JPanel();	
	
	private JLabel mLabelRemoteName;
	
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
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Connecting to remote simulator at");
			contentPanel.add(lblNewLabel);
		}
		{
			mLabelRemoteName = new JLabel();
			mLabelRemoteName.setName("remoteLabel");
			contentPanel.add(mLabelRemoteName);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// close application
						dispose();
						
						// TODO
						System.exit(0);
					}
				});
				buttonPane.add(cancelButton);
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
}
