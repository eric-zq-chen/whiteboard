package client;

import java.rmi.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import client.GUI.Login;
import client.GUI.Manager;
import client.GUI.User;
import remote.IRemoteAuth;
import remote.IRemoteChat;
import remote.IRemoteWhiteboard;

public class Navigation {
	public static void login(String hostname, int port, String username) {
		// result = connect to server(hostname, port, username) .....
		try {
			// Connect to the rmiregistry that is running on localhost
			Registry registry = LocateRegistry.getRegistry(hostname, port);

			// Retrieve the stub/proxy for the remote math object from the registry
			IRemoteAuth remoteAuth = (IRemoteAuth) registry.lookup("Auth");
			// Call methods on the remote object as if it was a local object
			String serverResponse = remoteAuth.Login(username);
			System.out.println("Server Response: " + serverResponse);
			if (serverResponse.equals("Manager")) {
				try {
					Manager window = new Manager(username);
					window.frame.setVisible(true);
					Thread t = new Thread(() -> RMIManager.main(hostname, port, username));
					t.start();
					Login.frame.dispose();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error Loading Manager Whiteboard");
				}
			} else if (serverResponse.equals("User")) { // if result = user....
				Login.lblStatus.setText("Waiting for approval...");
				Thread t = new Thread(() -> LoginCheckConnection.main(hostname, port, username));
				t.start();
				Login.btnConnect.setEnabled(false);
			} else if (serverResponse.equals("Username Already Exist")) {
				Login.lblStatus.setText("Username already exist, please use another name.");
			} else {
				Login.lblStatus.setText("Invalid Server Response");
			}

		} catch (UnknownHostException e1) {
			Login.lblStatus.setText("Error: Unknown Host");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed connecting to server");
			Login.lblStatus.setText("Error connecting to server");
		}

	}

//	public static void createWhiteboard(String name) {
//		try {
//			Manager window = new Manager();
//			window.frame.setVisible(true);
//			CreateWhiteboard.frame.dispose();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}