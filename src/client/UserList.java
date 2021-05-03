package client;

import java.util.HashMap;

public class UserList {
	public static void Approve(String username){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "ApproveUser");
		tempQuery.put("content", username);
		RMIManager.query = tempQuery;
	}
	public static void Reject(String username){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "RejectUser");
		tempQuery.put("content", username);
		RMIManager.query = tempQuery;
	}
	public static void Kick(String username){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "KickUser");
		tempQuery.put("content", username);
		RMIManager.query = tempQuery;
	}
	public static void ManagerOnExit(String username){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "OnExit");
		tempQuery.put("content", username);
		RMIManager.query = tempQuery;
	}
	
	
	public static void UserOnExit(String username){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "OnExit");
		tempQuery.put("content", username);
		RMIUser.query = tempQuery;
	}
	
	
}
