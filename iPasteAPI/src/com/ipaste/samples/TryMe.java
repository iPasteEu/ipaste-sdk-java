package com.ipaste.samples;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.ipaste.core.IPaste;
import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;
import com.ipaste.paste.PasteValidColours;
import com.ipaste.paste.PasteValidExpiryDates;
import com.ipaste.paste.PasteValidStatuses;
import com.ipaste.paste.PasteValidSyntaxes;
import com.ipaste.response.IPasteExtraResponseFormat;
import com.ipaste.response.IPasteResponseFormat;

public class TryMe {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(new File("C:\\test.properties")));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		String DEV_KEY = prop.getProperty("DEV_KEY");
		String USERNAME = prop.getProperty("USERNAME");
		String PASSWORD = prop.getProperty("PASSWORD");

		IPaste ipaste = null;
		try {
			ipaste = new IPaste(DEV_KEY, USERNAME, PASSWORD);
		} catch (IPasteException e) {
			System.out.println(e);
		}
		System.out.println("login() - Your tmpKey is: " + ipaste.getTmpKey());
		try {
			System.out.println("login(dev_key, username, password) - Your tmpKey is: " + ipaste.login(DEV_KEY, USERNAME, PASSWORD));
		} catch (IPasteException e) {
			System.out.println(e);
		}
//		try {
//			System.out.println("getUserPastes() - Your pastes are: " + ipaste.getUserPastes());
//		} catch (IPasteException e) {
//			System.out.println(e);
//		}
		
		try {
			System.out.println("\ngetUserPastes(IPasteResponseFormat.TEXT) - Your pastes are: " + ipaste.getUserPastes(IPasteResponseFormat.CVS, "testt")+"\n");
		} catch (IPasteException e1) {
			System.out.println(e1);
		}
//		try {
//			System.out.println("getUserPastes(IPasteResponseFormat.TEXT) - Your pastes are: " + ipaste.getUserPastes(IPasteResponseFormat.TEXT));
//		} catch (IPasteException e) {
//			System.out.println(e);
//		}
		int ret = -1;
		try {
			// ret = ipaste.paste(new Paste("Title Title Title",
			// "Description Description Description Description",
			// "content content contentcontent content content content",
			// PasteValidStatuses.HIDDEN,
			// "password password password", "https://www.ipaste.eu",
			// "tags tags tags tagstags",
			// PasteValidExpiryDates.ONE_HUNDRED_YEARS, PasteValidSyntaxes.TEXT,
			// PasteValidColours.RED));
			// System.out.println("paste(Paste paste) - Result:" + ret);
			ret = ipaste.paste(new Paste("Title", "Description nDescription", "content ncontent"));
			System.out.println("paste(Paste paste) - Result:" + ret);
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("remove(int pasteId) - Result: " + ipaste.remove(ret));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId) - Result: " + ipaste.get(3943));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId, YAML) - Result: " + ipaste.get(3943, IPasteExtraResponseFormat.YAML));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId, HTML) - Result: " + ipaste.get(3943, IPasteExtraResponseFormat.HTML));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId, XML) - Result: " + ipaste.get(3943, IPasteExtraResponseFormat.XML));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId, JSON) - Result: " + ipaste.get(3943, IPasteExtraResponseFormat.JSON));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId, TEXT) - Result: " + ipaste.get(3943, IPasteExtraResponseFormat.TEXT));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("get(int pasteId, RAW = TEXT) - Result: " + ipaste.get(3943, IPasteExtraResponseFormat.RAW));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {

			System.out.println("update(Paste paste) - Result:"
					+ ipaste.update(new Paste(3952, "CCCC Title Title", "CCCC Description Description Description", "CCCC content contentcontent content content content", PasteValidStatuses.HIDDEN,
							"CCCC password password", "https://www.ipasteCCCC.eu", "CCCC tags tags tagstags", PasteValidExpiryDates.ONE_MONTH, PasteValidSyntaxes.TERA_TERM_MACRO,
							PasteValidColours.RED)));
		} catch (IPasteException e) {
			System.out.println(e);
		}

	}

}
