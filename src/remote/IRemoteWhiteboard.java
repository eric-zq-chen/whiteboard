package remote;
import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;

import client.DrawPanel;
import client.shapeData;


/**
 * RMI Remote interface - must be shared between client and server.
 * All methods must throw RemoteException.
 * All parameters and return types must be either primitives or Serializable.
 *  
 * Any object that is a remote object must implement this interface.
 * Only those methods specified in a "remote interface" are available remotely.
 */
public interface IRemoteWhiteboard extends Remote {

	public String testConnection(String username) throws RemoteException;
	
	public DefaultListModel getUserList() throws RemoteException;
	
	public void removeUser(String u) throws RemoteException;
	
	public void update(shapeData sd, DefaultListModel userlist) throws RemoteException, IOException;

	public void updatePen(List<shapeData> penList, DefaultListModel userlist) throws RemoteException, IOException;
	
	public void newWhiteboard(DefaultListModel userlist) throws RemoteException, IOException;
	
	public void closeWhiteboard(DefaultListModel userlist) throws RemoteException, IOException;
	
	public String openFile(File file, DefaultListModel userlist) throws RemoteException;
	
	public boolean getWhiteboardStatus() throws RemoteException;
	
	public void setImageArray(byte[] ia) throws RemoteException;

	public byte[] getImageArray() throws RemoteException;

}