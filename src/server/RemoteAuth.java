package server;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import remote.IRemoteAuth;
import remote.IRemoteChat;
import server.GUI.Server;
import server.GUI.ServerWhiteboard;

/**
 * Server side implementation of the remote interface.
 * Must extend UnicastRemoteObject, to allow the JVM to create a 
 * remote proxy/stub.
 *
 */
public class RemoteAuth extends UnicastRemoteObject implements IRemoteAuth {

//	private int numberOfComputations;
	
	protected RemoteAuth() throws RemoteException {
//		numberOfComputations = 0;
	}
	
	private DefaultListModel AllUserlist = new DefaultListModel();
	private DefaultListModel ApprovedUserList = new DefaultListModel();
	private DefaultListModel PendingUserList = new DefaultListModel();
	boolean contains(String element) {
		return true;
	}
	
	public String testConnection(String username) throws RemoteException {
		System.out.println(username + " connected to Auth");
		Server.log.append("# " + username + " connected to Auth" + '\n');
        return "Auth Server Connected, username: " + username;
	}
	
	public String Login(String username) throws RemoteException {
//		System.out.println(userlist.size());
		if (AllUserlist.size() == 0) {
			AllUserlist.addElement(username);
			ApprovedUserList.addElement(username);
			System.out.println(Server.allList);
			Server.allList.setModel(AllUserlist);
			Server.approvedList.setModel(ApprovedUserList);
			Server.log.append("# First user as Manager: " + username + '\n');
			return "Manager";
		}
		else if (AllUserlist.contains(username)){
			return "Username Already Exist";
		} else {
			AllUserlist.addElement(username);
			PendingUserList.addElement(username);
			Server.allList.setModel(AllUserlist);
			Server.pendingList.setModel(PendingUserList);
			Server.log.append("# " + username + " waiting for approval" + '\n');
			return "User";
		}
	}
	
	public DefaultListModel UpdateApprovedUsers() throws RemoteException {
		return ApprovedUserList;
	}
	
	public DefaultListModel UpdatePendingUsers() throws RemoteException {
		return PendingUserList;
	}
	
	public String ApproveUser(String username) throws RemoteException {
		// delete username from pendinguserlist
		PendingUserList.removeElement(username);
		Server.pendingList.setModel(PendingUserList);
		// add username to approveduserlist
		ApprovedUserList.addElement(username);
		Server.approvedList.setModel(ApprovedUserList);
		System.out.println("Aprproved " +username);
		Server.log.append("# " + username + " connected successfully" + '\n');
		return username +" Approved";
	}
	
	public String RejectUser(String username) throws RemoteException {
		// delete username from pendinguserlist
		PendingUserList.removeElement(username);
		AllUserlist.removeElement(username);
		Server.allList.setModel(AllUserlist);
		Server.pendingList.setModel(PendingUserList);
		System.out.println("Rejected " +username);
		Server.log.append("# manager rejected connection request from: " + username + '\n');
		return username + " Rejected";
	}
	
	public String KickUser(String username) throws RemoteException {
		// delete username from ApprovedUserList
		ApprovedUserList.removeElement(username);
		AllUserlist.removeElement(username);
		Server.allList.setModel(AllUserlist);
		Server.approvedList.setModel(ApprovedUserList);
		// Action to kick user
		System.out.println("Kicked " +username);
		Server.log.append("# manager kick out : " + username + "from whiteboard" + '\n');
		return username +" Kicked";
	}
	
	public boolean CheckAll(String username) throws RemoteException{
		return AllUserlist.contains(username);
	}
	
	
	public boolean CheckConnected(String username) throws RemoteException{
		return ApprovedUserList.contains(username);
	}
	
	public boolean CheckPending(String username) throws RemoteException{
		return PendingUserList.contains(username);
	}
	
	public boolean UserDisconnect(String username) throws RemoteException{
		ApprovedUserList.removeElement(username);
		PendingUserList.removeElement(username);
		AllUserlist.removeElement(username);
		Server.allList.setModel(AllUserlist);
		Server.approvedList.setModel(ApprovedUserList);
		Server.pendingList.setModel(PendingUserList);
		System.out.println(username + " Disconnected");
		Server.log.append("# " + username + "disconnected with server" + '\n');
		return true;
	}
	
	public boolean ManagerDisconnect(String username) throws RemoteException, IOException{
		// reinitialise variables
		AllUserlist.removeAllElements();
		ApprovedUserList.removeAllElements();
		PendingUserList.removeAllElements();
		Server.allList.setModel(AllUserlist);
		Server.approvedList.setModel(ApprovedUserList);
		Server.pendingList.setModel(PendingUserList);
		System.out.println("Manager Disconnected");
		Server.log.append("# manager disconnected with server" + '\n');
		ServerWhiteboard.graphicsPanel.clear();
		RemoteWhiteboard.imageArray = ServerWhiteboard.graphicsPanel.getImageArray();
		RemoteWhiteboard.WhiteboardStatus = true;
		return true;
	}
}
