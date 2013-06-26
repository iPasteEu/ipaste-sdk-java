package com.ipaste.sample;

import com.ipaste.core.IPaste;
import com.ipaste.exception.IPasteException;
import com.ipaste.response.IPasteResponseFormat;

public class TryMe {


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			IPaste ipaste = new IPaste(DEV_KEY, USERNAME, PASSWORD);
			System.out.println("login() - Your tmpKey is: "+ipaste.getTmpKey());
			System.out.println("login(dev_key, username, password) - Your tmpKey is: "+ipaste.login(DEV_KEY, USERNAME, PASSWORD));
			
			System.out.println("getUserPastes() - Your pastes are: "+ipaste.getUserPastes());
			System.out.println("getUserPastes(IPasteResponseFormat.TEXT) - Your pastes are: "+ipaste.getUserPastes(IPasteResponseFormat.TEXT));
			
		} catch (IPasteException e) {
			System.out.println(e);
		}

	}

}
