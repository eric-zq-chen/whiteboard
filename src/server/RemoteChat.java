package server;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


import remote.IRemoteChat;
import server.GUI.Server;

/**
 * Server side implementation of the remote interface.
 * Must extend UnicastRemoteObject, to allow the JVM to create a 
 * remote proxy/stub.
 *
 */
public class RemoteChat extends UnicastRemoteObject implements IRemoteChat {
	private String ChatHistory = "";
//	private int numberOfComputations;
	
	protected RemoteChat() throws RemoteException {
//		numberOfComputations = 0;
	}
	
	public String testConnection(String username) throws RemoteException {
		System.out.println(username + " connected to chat");
		Server.log.append("# " + username + " connected to chat " + '\n');
        return "Chat Server Connected, username: " + username;
	}
	
	public String serverChatInput(HashMap<String, String> clientMsg) {
		String username = clientMsg.get("username");
		String message = clientMsg.get("content");
		String userMsg = username + ": " + message + "\n";
		ChatHistory = ChatHistory + userMsg; 
//		try(FileWriter fileWriter = new FileWriter("ChatHistory.txt", true)) {
//		    String fileContent = username + ": " + message + "\n";
//		    fileWriter.write(fileContent);
//		} catch (IOException e) {
//			System.out.println(e);
//		}
		Server.chat.setText(ChatHistory);
		Server.log.append("# Msg Received from: " + username + " , Message: " + userMsg + '\n');
		
		return "Msg Received from: " + username + " , Message: " + message;
	}
	
	public String updateChat(int start) throws RemoteException {
		return ChatHistory.substring(start);
		
//		String response = "";
//		try(FileReader fileReader = new FileReader("ChatHistory.txt")) {
//			int ch = fileReader.read();
//		    while(ch != -1) {
//		    	response = response + (char)ch;
//		        ch = fileReader.read();
//		    }
//		    response = response.substring(start);
//		} catch (IOException e) {
//			System.out.println(e);
//		}
//		return response;
	}
}
