package client;

import java.util.HashMap;


public class ChatApplication {
	public static void managerChatInput(String inputStr){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "chatInput");
		tempQuery.put("content", inputStr);
		RMIManager.query = tempQuery;
	}
	public static void userChatInput(String inputStr, String username){
		HashMap<String, String> tempQuery = new HashMap<String, String>();
		tempQuery.put("queryType", "chatInput");
		tempQuery.put("content", inputStr);
		RMIUser.query = tempQuery;
	}
	public static void changeImage() {
		
	}
}
