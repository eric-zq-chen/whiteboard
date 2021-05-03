package remote;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;


/**
 * RMI Remote interface - must be shared between client and server.
 * All methods must throw RemoteException.
 * All parameters and return types must be either primitives or Serializable.
 *  
 * Any object that is a remote object must implement this interface.
 * Only those methods specified in a "remote interface" are available remotely.
 */
public interface IRemoteChat extends Remote {

	public String testConnection(String username) throws RemoteException;

	public String updateChat(int start) throws RemoteException;

	public String serverChatInput(HashMap<String, String> clientMsg) throws RemoteException;
	
}
