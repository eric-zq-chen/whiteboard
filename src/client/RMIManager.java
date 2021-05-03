package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import client.GUI.Manager;
import remote.IRemoteAuth;
import remote.IRemoteChat;
import remote.IRemoteWhiteboard;

/**
 * This class retrieves a reference to the remote object from the RMI registry.
 * It invokes the methods on the remote object as if it was a local object of
 * the type of the remote interface.
 *
 */
public class RMIManager {
	public static HashMap<String, String> query = new HashMap<String, String>();
	public static boolean isAction;
	public static DrawPanel panel;
	public static File file = null;
	public static void main(String hostname, int port, String username) {

		try {
			// Connect to the rmiregistry that is running on localhost
			Registry registry = LocateRegistry.getRegistry(hostname, port);

			// Retrieve the stub/proxy for the remote math object from the registry
			IRemoteChat remoteChat = (IRemoteChat) registry.lookup("Chat");
			IRemoteWhiteboard remoteWhiteboard = (IRemoteWhiteboard) registry.lookup("Whiteboard");

			IRemoteAuth remoteAuth = (IRemoteAuth) registry.lookup("Auth");

			// Call methods on the remote object as if it was a local object

			System.out.println(remoteChat.testConnection(username));
			System.out.println(remoteWhiteboard.testConnection(username));
			System.out.println(remoteAuth.testConnection(username));

		

			query = null;
			int ChatResultLength = 0;
			DefaultListModel LocalPendingUserList = null;
			DefaultListModel LocalApprovedUserList = null;
			byte[] imageArray = remoteWhiteboard.getImageArray();
			Manager.graphicsPanel.loadImageArray(imageArray);
			// Initiate whiteboard when manager logs in
			DefaultListModel ApprovedUserList = remoteAuth.UpdateApprovedUsers();
			remoteWhiteboard.newWhiteboard(ApprovedUserList);
			
			while (remoteAuth.CheckConnected(username)) {
				System.out.print("");
				// keeps asking if server has an update

				// Chat Update
				String chatUpdateResponse = remoteChat.updateChat(ChatResultLength);
				if (chatUpdateResponse.length() > 0) {
					Manager.textAreaChatOutput.append(chatUpdateResponse);
				}
				ChatResultLength += chatUpdateResponse.length();

				// Whiteboard Update//
				// On user mouse release
				// Send currentshape or location and shapetype to server, server sends back to
				// other clients by clients constantly updating, other client draws.



				// Userlist Update //
				ApprovedUserList = remoteAuth.UpdateApprovedUsers();
				if (LocalApprovedUserList == null || LocalApprovedUserList.size() != ApprovedUserList.size()) {
					Manager.listApprovedUsers.setModel(ApprovedUserList);
					LocalApprovedUserList = ApprovedUserList;
				}

				DefaultListModel PendingUserList = remoteAuth.UpdatePendingUsers();
				if (LocalPendingUserList == null || LocalPendingUserList.size() != PendingUserList.size()) {
					Manager.listPendingUsers.setModel(PendingUserList);
					LocalPendingUserList = PendingUserList;
				}
				
				
				// keeps asking if user has a query
				if (query != null) {
					System.out.println(query);
					query.put("username", username);
					// do something with the query
					if (query.get("queryType").equals("chatInput")) {
						String serverResponse = remoteChat.serverChatInput(query);
						System.out.println("Server: " + serverResponse);
					} else if (query.get("queryType").equals("ApproveUser")) {
						String serverResponse = remoteAuth.ApproveUser(query.get("content"));
						System.out.println("Server: " + serverResponse);
					} else if (query.get("queryType").equals("RejectUser")) {
						String serverResponse = remoteAuth.RejectUser(query.get("content"));
						System.out.println("Server: " + serverResponse);
					} else if (query.get("queryType").equals("KickUser")) {
						String serverResponse = remoteAuth.KickUser(query.get("content"));
						System.out.println("Server: " + serverResponse);
					} else if (query.get("queryType").equals("OnExit")) {
		                if (remoteAuth.ManagerDisconnect(query.get("content"))) {
		                	System.exit(0);
		                }
					} else if (query.get("queryType").equals("ClearWhiteboard")) {
						remoteWhiteboard.newWhiteboard(ApprovedUserList);
					} else if (query.get("queryType").equals("CloseWhiteboard")) {
						remoteWhiteboard.closeWhiteboard(ApprovedUserList);
					} else {
						System.out.println("Unkown Query, Query not processed");
					}
					query = null;
				}
				
			//  Whiteboard Update//
        		// On user mouse release
        		// Send currentshape or location and shapetype to server, server sends back to other clients by clients constantly updating, other client draws.

//        		System.out.println(remoteWhiteboard.getPanel());
        		if (DrawPanel.isAction){
        			//draw onto the server
        			remoteWhiteboard.update(DrawPanel.lastShape, ApprovedUserList);
        			DrawPanel.isAction = false;
        		}
        		
        		if (DrawPanel.isPen) {
        			remoteWhiteboard.updatePen(DrawPanel.penList, ApprovedUserList);
        			DrawPanel.isPen = false;
        			DrawPanel.penList.clear();
        		}
        		
        		DefaultListModel userUpdateList = remoteWhiteboard.getUserList();
        		
        		if (userUpdateList.contains(username)) {
        			imageArray = remoteWhiteboard.getImageArray();
        			Manager.graphicsPanel.loadImageArray(imageArray);
        			userUpdateList.removeElement(username);
        			
        		}
        		//Check if image is the most updated, if not pull
        		
        		
        		
        		
        		// Check if whiteboard is opened or closed
				if (remoteWhiteboard.getWhiteboardStatus()) {
	        		Manager.graphicsPanel.setVisible(true);
					Manager.graphicsPanel.setEnabled(true);
				} else {
					Manager.graphicsPanel.setVisible(false);
					Manager.graphicsPanel.setEnabled(false);
				}
				
				//Check if manager wants to open a new file
				if (file!=null) {
					String serverResponse = remoteWhiteboard.openFile(file, ApprovedUserList);
					System.out.println("Server: " + serverResponse);
					JOptionPane.showMessageDialog(null, serverResponse);
					file = null;
				}
				
        		

			}
			JOptionPane.showMessageDialog(null, "Disconnected from server");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Disconnected from server");
			System.exit(0);
		}

	}

}
