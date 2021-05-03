package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

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
public class RMIUser {
	public static HashMap<String, String> query = new HashMap<String, String>();
	public static boolean isAction;
	public static DrawPanel panel;

	public static void main(String hostname, int port, String username) {

		try {
			// Connect to the rmiregistry that is running on localhost
			Registry registry = LocateRegistry.getRegistry(hostname, port);

			// Retrieve the stub/proxy for the remote math object from the registry
			IRemoteChat remoteChat = (IRemoteChat) registry.lookup("Chat");
			IRemoteWhiteboard remoteWhiteboard = (IRemoteWhiteboard) registry.lookup("Whiteboard");

//			remoteWhiteboard.newPanel();

			IRemoteAuth remoteAuth = (IRemoteAuth) registry.lookup("Auth");

			// Call methods on the remote object as if it was a local object
			System.out.println(remoteChat.testConnection(username));
			System.out.println(remoteWhiteboard.testConnection(username));

//			Manager.graphicsPanel.copy(remoteWhiteboard.getPanel());

			query = null;
			int ChatResultLength = 0;
			
			byte[] imageArray = remoteWhiteboard.getImageArray();
			User.graphicsPanel.loadImageArray(imageArray);

			DefaultListModel LocalApprovedUserList = null;
			while (remoteAuth.CheckConnected(username)) {
				// keeps asking if user has a query
				if (query != null) {
					System.out.println(query);
					query.put("username", username);
					// do something with the query
					if (query.get("queryType").equals("chatInput")) {
						String serverResponse = remoteChat.serverChatInput(query);
						System.out.println("Server: " + serverResponse);
					} else if (query.get("queryType").equals("OnExit")){
						if(remoteAuth.UserDisconnect(query.get("content"))) {
							System.exit(0);
						}
					} else {
						System.out.println("Unkown Query, Query not processed");
					}
					query = null;
				}

				System.out.print("");
				// keeps asking if server has an update

				// Chat Update
				String chatUpdateResponse = remoteChat.updateChat(ChatResultLength);
				if (chatUpdateResponse.length() > 0) {
					User.textAreaChatOutput.append(chatUpdateResponse);
				}
				ChatResultLength += chatUpdateResponse.length();

				// Whiteboard Update//
				// On user mouse release
				// Send currentshape or location and shapetype to server, server sends back to
				// other clients by clients constantly updating, other client draws.

//        		System.out.println(remoteWhiteboard.getPanel());
//        		if (isAction){
//        			User.graphicsPanel.copy(remoteWhiteboard.getPanel());
//        		}

				// Userlist Update //
				DefaultListModel ApprovedUserList = remoteAuth.UpdateApprovedUsers();
				if (LocalApprovedUserList == null || LocalApprovedUserList.size() != ApprovedUserList.size()) {
					User.listApprovedUsers.setModel(ApprovedUserList);
					LocalApprovedUserList = ApprovedUserList;
				}

//        		DefaultListModel PendingUserList = remoteAuth.UpdatePendingUsers();
//        		if (LocalPendingUserList == null || LocalPendingUserList.size()!=PendingUserList.size()) {
//        			Manager.listPendingUsers.setModel(PendingUserList);
//        			LocalPendingUserList = PendingUserList;
//        		}
				
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
        			User.graphicsPanel.loadImageArray(imageArray);
        			userUpdateList.removeElement(username);
        			
        		}
        		
        		if (remoteWhiteboard.getWhiteboardStatus()) {
	        		User.graphicsPanel.setVisible(true);
	        		User.graphicsPanel.setEnabled(true);
				} else {
					User.graphicsPanel.setVisible(false);
					User.graphicsPanel.setEnabled(false);
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
