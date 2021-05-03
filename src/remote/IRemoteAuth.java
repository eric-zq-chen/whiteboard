package remote;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;


/**
 * RMI Remote interface - must be shared between client and server.
 * All methods must throw RemoteException.
 * All parameters and return types must be either primitives or Serializable.
 *  
 * Any object that is a remote object must implement this interface.
 * Only those methods specified in a "remote interface" are available remotely.
 */
public interface IRemoteAuth extends Remote {

	public String testConnection(String username) throws RemoteException;
	public String Login(String username) throws RemoteException;
	public DefaultListModel UpdateApprovedUsers() throws RemoteException;
	public DefaultListModel UpdatePendingUsers() throws RemoteException;
	public String ApproveUser(String username) throws RemoteException;
	public String RejectUser(String username) throws RemoteException;
	public String KickUser(String username) throws RemoteException;
	public boolean CheckAll(String username) throws RemoteException;
	public boolean CheckConnected(String username) throws RemoteException;
	public boolean CheckPending(String username) throws RemoteException;
	public boolean UserDisconnect(String username) throws RemoteException;
	public boolean ManagerDisconnect(String username) throws RemoteException, IOException;
}
