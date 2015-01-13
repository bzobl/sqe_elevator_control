package at.fhhagenberg.sqe.project.sqelevator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import com.sun.xml.internal.ws.Closeable;

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;
import java.awt.Window.Type;

public class ConnectingDialog extends JDialog
{

	private final JPanel contentPanel = new JPanel();

	private final IElevatorConnection mConnection;
	
	/**
	 * Create the dialog.
	 */
	public ConnectingDialog(IElevatorConnection conn)
	{		
		setResizable(false);
		setAlwaysOnTop(true);
		mConnection = conn;
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
			JLabel lblNewLabel_1 = new JLabel(mConnection.getConnectionName());
			contentPanel.add(lblNewLabel_1);
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
						dispose();
						System.exit(0);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
	
	public boolean waitForConnection()
	{		
		Thread connThread = new Thread(new Runnable()
		{			
			@Override
			public void run()
			{			
				// avoid race condition!
				while (!isVisible());
				
				while (mConnection.connect() == false);	
				assert (mConnection.isConnected() == true);
				
				setVisible(false);
			}
		});
		connThread.start();	
		setVisible(true);
		while (!mConnection.isConnected());
		return mConnection.isConnected();
	}
}
