package server;
import java.io.File;
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

import client.DrawPanel;
import client.DrawPanelServer;
import client.shapeData;
import remote.IRemoteChat;
import remote.IRemoteWhiteboard;
import server.GUI.Server;
import server.GUI.ServerWhiteboard;


/**
 * Server side implementation of the remote interface.
 * Must extend UnicastRemoteObject, to allow the JVM to create a 
 * remote proxy/stub.
 *
 */
public class RemoteWhiteboard extends UnicastRemoteObject implements IRemoteWhiteboard {
	
	public static byte[] imageArray;
	public static boolean ifUpdate;
	public static DefaultListModel userUpdateList;
	public static boolean WhiteboardStatus = true;
	protected RemoteWhiteboard() throws RemoteException {
//		numberOfComputations = 0;
		userUpdateList = new DefaultListModel();
		imageArray = new byte[] {};

		
	}
	
	public String testConnection(String username) throws RemoteException {
		System.out.println(username + " connected to whiteboard");
		Server.log.append("# " + username + " connected to whiteboard" + '\n');
        return "Whiteboard Server Connected, username: " + username;
	}
	
//	public void addUser(String u) throws RemoteException {
//		userUpdateList.add(u);
//	}
	
	public DefaultListModel getUserList() throws RemoteException {
		return userUpdateList;
	}
	
	public void removeUser(String u) throws RemoteException{
		userUpdateList.removeElement(u);
	}
	
	public void update(shapeData sd, DefaultListModel userlist) throws RemoteException, IOException{
		System.out.println("a");
		ServerWhiteboard.graphicsPanel.drawData(sd);
		imageArray = ServerWhiteboard.graphicsPanel.getImageArray();
		userUpdateList = userlist;
	}
	
	public void updatePen(List<shapeData> penList, DefaultListModel userlist) throws RemoteException, IOException{
		System.out.println("length of penlist " + penList.size());
		for (shapeData sd : penList) {
			ServerWhiteboard.graphicsPanel.drawData(sd);
		}
		imageArray = ServerWhiteboard.graphicsPanel.getImageArray();
		userUpdateList = userlist;
	}
	
	public void newWhiteboard(DefaultListModel userlist) throws RemoteException, IOException{
		ServerWhiteboard.graphicsPanel.clear();
		imageArray = ServerWhiteboard.graphicsPanel.getImageArray();
		userUpdateList = userlist;
		WhiteboardStatus = true;
	}
	
	public void closeWhiteboard(DefaultListModel userlist) throws RemoteException, IOException{
		ServerWhiteboard.graphicsPanel.clear();
		imageArray = ServerWhiteboard.graphicsPanel.getImageArray();
		userUpdateList = userlist;
		WhiteboardStatus = false;
	}
	
	public String openFile(File file, DefaultListModel userlist) throws RemoteException{
		System.out.println("received file from manager");
		Server.log.append("received file from manager");
		try {
			ServerWhiteboard.graphicsPanel.clear();
			ServerWhiteboard.graphicsPanel.load(file);
			imageArray = ServerWhiteboard.graphicsPanel.getImageArray();
			userUpdateList = userlist;
			WhiteboardStatus = true;
			return "File Loaded successfully.";
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Error Loading File";
		}
	}
	
	public boolean getWhiteboardStatus() throws RemoteException{
		return WhiteboardStatus;
	}
	
	public void setImageArray(byte[] ia) throws RemoteException{
		imageArray = ia;
	}
	
	public byte[] getImageArray() throws RemoteException{
		return imageArray;
	}
	
}
