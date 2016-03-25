package gui;

import java.util.Arrays;
import java.util.LinkedList;

public class PreferenceDialogTools {
	
	/**
	 * wandelt String der Werte im Format: 
	 * 	value\r\nvalue2\r\n...\r\nvaluen
	 * enthält in eine LinkedList<String> um
	 * @param stringValue
	 * @return
	 */
	public static LinkedList<String> getListFromString(String stringValue) {
		return new LinkedList<>(Arrays.asList((stringValue.split("\r\n"))));
	}
	public static String getStringFromList(LinkedList<String> listValue) {
		return String.join("\r\n",listValue);
		
	}

}
