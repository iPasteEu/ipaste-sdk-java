package com.ipaste.sample;

import com.ipaste.core.IPaste;
import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;
import com.ipaste.paste.PasteValidColors;
import com.ipaste.paste.PasteValidExpiryDates;
import com.ipaste.paste.PasteValidStatuses;
import com.ipaste.paste.PasteValidSyntaxes;
import com.ipaste.response.IPasteExtraResponseFormat;
import com.ipaste.response.IPasteResponseFormat;

public class TryMe {
	private final static String DEV_KEY = "";
	private final static String USERNAME = "";
	private final static String PASSWORD = "";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		try {
			System.out.println("getUserPastes() - Your pastes are: " + ipaste.getUserPastes());
		} catch (IPasteException e) {
			System.out.println(e);
		}
		try {
			System.out.println("getUserPastes(IPasteResponseFormat.TEXT) - Your pastes are: " + ipaste.getUserPastes(IPasteResponseFormat.TEXT));
		} catch (IPasteException e) {
			System.out.println(e);
		}
		int ret = -1;
		try {
			ret = ipaste.paste(new Paste("Title Title Title", "Description Description Description Description", "content content contentcontent content content content", PasteValidStatuses.HIDDEN,
					"password password password", "https://www.ipaste.eu", "tags tags tags tagstags", PasteValidExpiryDates.ONE_HUNDRED_YEARS, PasteValidSyntaxes.TEXT, PasteValidColors.RED));
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

			System.out
					.println("update(Paste paste) - Result:"
							+ ipaste.update(new Paste(3952, "BBBB Title Title", "BBBB Description Description Description", "BBBB content contentcontent content content content",
									PasteValidStatuses.HIDDEN, "BBBB password password", "https://www.ipasteBBBB.eu", "BBBB tags tags tagstags", PasteValidExpiryDates.ONE_MONTH,
									PasteValidSyntaxes.TERA_TERM_MACRO, PasteValidColors.RED)));
		} catch (IPasteException e) {
			System.out.println(e);
		}

	}

}
