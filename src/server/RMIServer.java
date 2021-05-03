package server;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.DrawPanel;
import remote.IRemoteAuth;
import remote.IRemoteChat;
import remote.IRemoteWhiteboard;
import server.GUI.Server;
import server.GUI.ServerWhiteboard;

/**
 * Creates an instance of the RemoteMath class and
 * publishes it in the rmiregistry
 * 
 */
public class RMIServer{
	
//	private static final long serialVersionUID = 1L;
	
	public static void main(int port)  {
		
		try {
			
			//Export the remote math object to the Java RMI runtime so that it
			//can receive incoming remote calls.
			//Because RemoteMath extends UnicastRemoteObject, this
			//is done automatically when the object is initialized.
			//
		    //RemoteMath obj = new RemoteMath();
			//IRemoteMath stub = (IRemoteMath) UnicastRemoteObject.exportObject(obj, 0);
			// 
			IRemoteChat remoteChat = new RemoteChat();
			IRemoteWhiteboard remoteWhiteboard = new RemoteWhiteboard();
			IRemoteAuth remoteAuth = new RemoteAuth();

            Registry registry = LocateRegistry.getRegistry(port);
            System.out.println(registry);

            registry.bind("Chat", remoteChat);
            registry.bind("Whiteboard", remoteWhiteboard);
            registry.bind("Auth", remoteAuth);
            
            System.out.println("Server ready");
            Server.log.append("# Server opened"+'\n');
			ServerWhiteboard window = new ServerWhiteboard();
			window.frame.setVisible(true);
            //The server will continue running as long as there are remote objects exported into
            //the RMI runtime, to re	move remote objects from the
            //RMI runtime so that they can no longer accept RMI calls you can use:
            // UnicastRemoteObject.unexportObject(remoteMath, false);
		} catch (Exception e) {
//			e.printStackTrace();
			Server.log.append("Cannot find registry at port:"+ port +'\n');
		}
		
	}
}
