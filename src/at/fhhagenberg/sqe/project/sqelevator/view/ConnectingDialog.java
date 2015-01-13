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

import at.fhhagenberg.sqe.project.sqelevator.communication.IElevatorConnection;

import com.sun.istack.internal.logging.Logger;

public class ConnectingDialog extends JDialog
{
	private static Logger LOG = Logger.getLogger(ConnectingDialog.class);
	
	
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
				//while ((mConnection.connect() == false) || (mConnection.getClockTick()))	
				while (!mConnection.isConnected())
				{
					try
					{
						mConnection.connect();

						//mConnection.getClockTick();
					}
					catch (Exception e)
					{
						LOG.warning("Connecting failed with exception.");
					}
				}
				assert (mConnection.isConnected() == true);
			}
		});
		setVisible(true);
		connThread.start();	
		
		try
		{
			connThread.join();
		}
		catch (InterruptedException ie)
		{
			LOG.warning("connection thread.join() failed");
		}
			
		return mConnection.isConnected();
	}
}
