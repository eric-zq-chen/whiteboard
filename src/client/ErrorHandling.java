package client;

public class ErrorHandling {
	// Validating errors of a string
	// Returns a error result or the modified original String
	public static String verifyStr(String inputStr) {
		// Modifying String
		inputStr = inputStr.trim();
		inputStr = inputStr.toLowerCase();
		// Validating String
		if (inputStr.equals("") || inputStr.equals(null)) {
			return "String is empty";
		} else {
			// If no errors return valid
			return "Valid";
		}	
	}

	// Validating errors for port number
	public static String verifyPort(String portStr) {
		// Modifying String
		portStr = portStr.trim();
		String zero = "0";
		if (portStr.equals("")||portStr.equals(null)) {
			return "Please enter a port number";
		}
		else if (portStr.charAt(0) == zero.charAt(0)) {
			return "Port number cannot start with 0";
		}
		else{
			try {
				int port = Integer.parseInt(portStr);
				return "Valid";
			} catch (NumberFormatException e) {
				return "Invalid port number";
			}
		}
	}
}
