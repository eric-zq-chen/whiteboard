package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import client.GUI.Login;
import client.GUI.Manager;
import client.GUI.User;
import remote.IRemoteAuth;
import remote.IRemoteChat;
import remote.IRemoteWhiteboard;

/**
 * This class retrieves a reference to the remote object from the RMI registry.
 * It invokes the methods on the remote object as if it was a local object of
 * the type of the remote interface.
 *
 */
public class LoginCheckConnection {
	public static boolean isAction;
	public static DrawPanel panel;

	public static void main(String hostname, int port, String username) {

		try {
			// Connect to the rmiregistry that is running on localhost
			Registry registry = LocateRegistry.getRegistry(hostname, port);

//			remoteWhiteboard.newPanel();

			IRemoteAuth remoteAuth = (IRemoteAuth) registry.lookup("Auth");
			boolean notConnected = true;
			// Call methods on the remote object as if it was a local object
			Login.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			    @Override
			    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			    	System.out.println("Login Page System Closed");
	    			// notify Server that client is going to exit
			    	try {
						remoteAuth.UserDisconnect(username);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                System.exit(0);
			    }
			});
			while (notConnected) {
				if (remoteAuth.CheckConnected(username)) {
					try {
						User window = new User(username);
						window.frame.setVisible(true);
						Thread t = new Thread(() -> RMIUser.main(hostname, port, username));
						t.start();
						Login.frame.dispose();
						notConnected = false;
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Error Loading User Whiteboard");
					}
				} else if (!remoteAuth.CheckAll(username)) {
					Login.lblStatus.setText("Rejected by manager");
					Login.btnConnect.setEnabled(true);
					break;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
