package com.ipaste.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;
import com.ipaste.paste.PasteValidColors;
import com.ipaste.paste.PasteValidExpiryDates;
import com.ipaste.paste.PasteValidStatuses;
import com.ipaste.paste.PasteValidSyntaxes;
import com.ipaste.response.IPasteExtraResponseFormat;
import com.ipaste.response.IPasteResponseFormat;
/**
 * iPaste.eu core class.
 * Instantiate IPaste in order to be able to clall the iPaste webservice. 
 */
public class IPaste implements IPasteCore {
	/**
	 * initialize this variable if you want to use only the public IPaste(String
	 * username, String password) constructor
	 */
	private final static String DEV_KEY = null;

	private String devKey;
	private String username;
	private String password;
	private String tmpKey;

	/**
	 * it's not recommended to use this constructor because it does not validate
	 * and initialize the username and password class instance variables (you
	 * have to set them manually).
	 * 
	 * @throws IPasteException
	 */
	public IPaste() throws IPasteException {
		/**
		 * if DEV_KEY was initialized, you can use the public IPaste(String
		 * username, String password) constructor, otherwise you should use the
		 * public IPaste(String devKey, String username, String password)
		 * constructor
		 */
		if (DEV_KEY != null) {
			this.validateDeveloperKey(DEV_KEY);
			this.devKey = DEV_KEY;
		}

	}

	/**
	 * use this constructor if you defined the DEV_KEY variable. This
	 * constructor initialize the username and password variables just after it
	 * called the parameter free constructor. Finally it calls the login()
	 * method.
	 * 
	 * @param username
	 * @param password
	 * @throws IPasteException
	 */
	public IPaste(String username, String password) throws IPasteException {
		this();
		this.validateUsername(username);
		this.validateRawPassword(password);
		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		this.login();
	}

	/**
	 * initializes class instance variables and tries to login. It ignores the
	 * DEV_KEY variable. It tries to make a login.
	 * 
	 * @param devKey
	 * @param username
	 * @param password
	 * @throws IPasteException
	 */
	public IPaste(String devKey, String username, String password) throws IPasteException {
		this.validateUsername(username);
		this.validateRawPassword(password);
		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		this.validateDeveloperKey(devKey);
		this.devKey = devKey;
		// tries to login
		this.login();
	}

	/**
	 * validates login data and tries to login
	 * 
	 * @return String developer key
	 * @throws IPasteException
	 */
	@Override
	public String login() throws IPasteException {
		this.validateHashedPassword(this.password);
		this.validateUsername(this.username);
		this.validateDeveloperKey(this.devKey);

		String response;
		try {
			response = this.call("act=login&a=" + URLEncoder.encode(this.devKey, "UTF-8") + URLEncoder.encode(this.username, "UTF-8") + URLEncoder.encode(this.password, "UTF-8")).trim();
		} catch (UnsupportedEncodingException e) {
			throw new IPasteException(CLIENT_EXCEPTION + "internal error");
		}
		if (this.isErrorResponse(response))
			throw new IPasteException(response);
		// check if the returned tmpKey is correct
		this.validateTmpKey(response);
		this.tmpKey = response;
		return response;
	}

	/**
	 * tries to login using the login data passed as parameter
	 * 
	 * @param devKey
	 * @param username
	 * @param password
	 * @return String developer key
	 * @throws IPasteException
	 */
	@Override
	public String login(String devKey, String username, String password) throws IPasteException {
		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		this.devKey = devKey;
		return this.login();
	}

	/**
	 * Gets user pastes in JSON format and decodes them in a list of integers
	 * 
	 * @return List<Integer> list of user pastes
	 * @throws IPasteException
	 */
	@Override
	public List<Integer> getUserPastes() throws IPasteException {
		List<Integer> list = null;
		this.validateTmpKey(this.tmpKey);
		String response = this.call("act=get_all_user_pastes&frm=" + IPasteResponseFormat.JSON + "&a=" + this.tmpKey);
		if (!this.isErrorResponse(response))
			list = this.jsonToIntegerList(response);
		else
			throw new IPasteException(response);
		return list;
	}

	/**
	 * Returns the users pastes according to the responseFormat as output and
	 * converts them in a list of integers that will be returned
	 * 
	 * @param responseFormat
	 * @return List<Integer> list of user pastes
	 * @throws IPasteException
	 */
	@Override
	public List<Integer> getUserPastes(String responseFormat) throws IPasteException {
		List<Integer> list = null;
		this.validateTmpKey(this.tmpKey);
		this.validateField(responseFormat, IPasteResponseFormat.class);
		String response = this.call("act=get_all_user_pastes&frm=" + responseFormat + "&a=" + this.tmpKey);
		if (!this.isErrorResponse(response)) {
			if (responseFormat == IPasteResponseFormat.JSON)
				list = this.jsonToIntegerList(response);
			else if (responseFormat == IPasteResponseFormat.RAW || responseFormat == IPasteResponseFormat.TEXT)
				list = this.textToIntegerList(response);
		} else
			throw new IPasteException(response);
		return list;
	}

	/**
	 * Returns the users paste IDs according to the responseFormat as output and
	 * converts them in a list of integers that will be returned
	 * 
	 * @param responseFormat
	 * @return List<Integer> list of user pastes
	 * @throws IPasteException
	 */
	@Override
	public List<Integer> getUserPastes(String responseFormat, String tmpKey) throws IPasteException {
		this.tmpKey = tmpKey;
		return this.getUserPastes(responseFormat);
	}

	@Override
	public boolean update(Paste paste) throws IPasteException {
		paste = paste.clone();
		this.validateTmpKey(this.tmpKey);
		this.validatePasteBeforeUpdate(paste);
		String response;
		try {
			response = this.call("act=update" + "&a=" + URLEncoder.encode(this.tmpKey, "UTF-8") + "&id=" + URLEncoder.encode("" + paste.getId(), "UTF-8") + "&pasteTitle="
					+ URLEncoder.encode("" + paste.getTitle(), "UTF-8") + "&pasteDescription=" + URLEncoder.encode("" + paste.getDescription(), "UTF-8") + "&pasteContent="
					+ URLEncoder.encode("" + paste.getContent(), "UTF-8") + "&pasteStatus=" + URLEncoder.encode("" + paste.getStatus(), "UTF-8") + "&c="
					+ URLEncoder.encode("" + paste.getPassword(), "UTF-8") + "&pasteSource=" + URLEncoder.encode("" + paste.getSource(), "UTF-8") + "&pasteTags="
					+ URLEncoder.encode("" + paste.getTags(), "UTF-8") + "&pasteExpiryDate=" + URLEncoder.encode("" + paste.getExpiryDate(), "UTF-8") + "&pasteSyntax="
					+ URLEncoder.encode("" + paste.getSyntax(), "UTF-8") + "&pasteColor=" + URLEncoder.encode("" + paste.getColor(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}

		if (this.isErrorResponse(response))
			throw new IPasteException(response);

		return true;
	}

	@Override
	public boolean update(Paste paste, String tmpKey) throws IPasteException {
		this.tmpKey = tmpKey;
		return this.update(paste);
	}

	@Override
	public int paste(Paste paste) throws IPasteException {
		int ret = -1;
		paste = paste.clone();
		this.validateTmpKey(this.tmpKey);
		this.validatePasteBeforeInsert(paste);
		String response;
		try {
			response = this.call("act=insert" + "&a=" + URLEncoder.encode(this.tmpKey, "UTF-8") + "&pasteTitle=" + URLEncoder.encode("" + paste.getTitle(), "UTF-8") + "&pasteDescription="
					+ URLEncoder.encode("" + paste.getDescription(), "UTF-8") + "&pasteContent=" + URLEncoder.encode("" + paste.getContent(), "UTF-8") + "&pasteStatus="
					+ URLEncoder.encode("" + paste.getStatus(), "UTF-8") + "&c=" + URLEncoder.encode("" + paste.getPassword(), "UTF-8") + "&pasteSource="
					+ URLEncoder.encode("" + paste.getSource(), "UTF-8") + "&pasteTags=" + URLEncoder.encode("" + paste.getTags(), "UTF-8") + "&pasteExpiryDate="
					+ URLEncoder.encode("" + paste.getExpiryDate(), "UTF-8") + "&pasteSyntax=" + URLEncoder.encode("" + paste.getSyntax(), "UTF-8") + "&pasteColor="
					+ URLEncoder.encode("" + paste.getColor(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}

		if (this.isErrorResponse(response))
			throw new IPasteException(response);

		try {
			// it returns 1234\r\n -> Integer.parseInt() does not recognize the
			// new line characters and it throws an exception, so we remove the
			// EOL with the trim() method
			ret = Integer.parseInt(response.trim());
		} catch (NumberFormatException e) {
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste id: " + e);
		}

		return ret;
	}

	@Override
	public int paste(Paste paste, String tmpKey) throws IPasteException {
		this.tmpKey = tmpKey;
		return this.paste(paste);
	}

	@Override
	public boolean remove(int pasteId) throws IPasteException {
		this.validateTmpKey(this.tmpKey);
		if (pasteId == 0)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste id");
		String response = null;
		try {
			response = this.call("act=remove" + "&a=" + URLEncoder.encode(this.tmpKey, "UTF-8") + "&id=" + URLEncoder.encode("" + pasteId, "UTF-8")).trim();
		} catch (UnsupportedEncodingException e) {
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}
		if (this.isErrorResponse(response))
			throw new IPasteException(response);
		return true;
	}

	@Override
	public boolean remove(int pasteId, String tmpKey) throws IPasteException {
		this.tmpKey = tmpKey;
		return this.remove(pasteId);
	}

	@Override
	public String get(int pasteId) throws IPasteException {
		this.validateTmpKey(this.tmpKey);
		if (pasteId == 0)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste id");
		String format = IPasteExtraResponseFormat.TEXT;
		String response = null;

		try {
			response = this.call("act=get" + "&a=" + URLEncoder.encode(this.tmpKey, "UTF-8") + "&id=" + URLEncoder.encode("" + pasteId, "UTF-8") + "&frm=" + URLEncoder.encode(format, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}
		if (this.isErrorResponse(response))
			throw new IPasteException(response);
		return response;
	}

	@Override
	public String get(int pasteId, String format) throws IPasteException {
		this.validateTmpKey(this.tmpKey);
		if (pasteId == 0)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste id");
		String response = null;

		try {
			response = this.call("act=get" + "&a=" + URLEncoder.encode(this.tmpKey, "UTF-8") + "&id=" + URLEncoder.encode("" + pasteId, "UTF-8") + "&frm=" + URLEncoder.encode(format, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}
		if (this.isErrorResponse(response))
			throw new IPasteException(response);
		return response;
	}

	@Override
	public String get(int pasteId, String format, String tmpKey) throws IPasteException {
		this.tmpKey = tmpKey;
		return this.get(pasteId, format);
	}

	private void pastePreProcess(Paste paste) throws IPasteException {
		paste.setPassword(this.md5(paste.getPassword()));
	}

	private void validatePasteBeforeUpdate(Paste paste) throws IPasteException {
		this.validatePasteBeforeInsert(paste);
		if (paste.getId() == 0)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste id");
	}

	// private boolean isNumeric(String str){
	// try{
	// Integer.parseInt(str);
	// return true;
	// }catch(NumberFormatException e){
	// return false;
	// }
	// }

	private void validatePasteBeforeInsert(Paste paste) throws IPasteException {
		this.pastePreProcess(paste);
		if (paste.getTitle() == null || paste.getTitle().length() > 500)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste title");
		if (paste.getContent() == null || paste.getContent().length() > 16777215)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste content");
		if (paste.getPassword() == null || paste.getPassword().length() != 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste password");
		if (paste.getSource() == null || paste.getSource().length() > 500)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste source");
		if (paste.getTags() == null || paste.getTags().length() > 10000)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste tags");
		if (paste.getDescription() == null || paste.getDescription().length() > 5000)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid paste description");
		this.validateField(paste.getStatus(), PasteValidStatuses.class);
		this.validateField(paste.getExpiryDate(), PasteValidExpiryDates.class);
		this.validateField(paste.getSyntax(), PasteValidSyntaxes.class);
		this.validateField(paste.getColor(), PasteValidColors.class);
	}

	private String call(String param) throws IPasteException {

		String uri = "http://www.ipaste.eu/api";
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(uri);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(param.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			OutputStream os = connection.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(param);
			writer.close();
			os.close();
			InputStream is = connection.getInputStream();

			StringBuilder response = null;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(is));
				String linee;
				response = new StringBuilder();

				while ((linee = br.readLine()) != null) {
					response.append(linee + "\r\n");
				}
				response.setLength(response.length() - 2);
				br.close();
			} catch (IOException e) {
				throw new IPasteException(CLIENT_EXCEPTION + e);
			} finally {
				if (br != null)
					try {
						br.close();
					} catch (IOException e2) {
						throw new IPasteException(CLIENT_EXCEPTION + e2);
					}
			}
			connection.disconnect();
			return response.toString();

		} catch (Exception e) {
			throw new IPasteException(CLIENT_EXCEPTION + e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public String md5(String input) throws IPasteException {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new IPasteException(e);
		}
	}

	private boolean isEmpty(String str) {
		return (str == null || str.isEmpty());
	}

	@SuppressWarnings("unused")
	private void validateEmpty(String str) throws IPasteException {
		if (this.isEmpty(str))
			throw new IPasteException(CLIENT_EXCEPTION + "invalid input value");
	}

	private void validateUsername(String username) throws IPasteException {
		if (this.isEmpty(username) || username.length() > 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid username: " + username);
	}

	private void validateTmpKey(String tmpKey) throws IPasteException {
		if (this.isEmpty(tmpKey) || tmpKey.length() < 20 || tmpKey.length() > 40)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid tmpKey: " + tmpKey);
	}

	private void validateHashedPassword(String hashedPassword) throws IPasteException {
		if (this.isEmpty(hashedPassword) || hashedPassword.length() != 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid hashed password: " + hashedPassword);
	}

	private void validateDeveloperKey(String developerKey) throws IPasteException {
		if (this.isEmpty(developerKey) || developerKey.length() != 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid developer key: " + developerKey);
	}

	private void validateRawPassword(String rawPassword) throws IPasteException {
		if (this.isEmpty(rawPassword) || rawPassword.length() < 6 || rawPassword.length() > 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid password: " + rawPassword);
	}

	private void validateField(String field, Class<?> cl) throws IPasteException {
		try {
			boolean found = false;
			for (Field f : cl.getDeclaredFields()) {
				int mod = f.getModifiers();
				if (Modifier.isStatic(mod) && Modifier.isPublic(mod) && Modifier.isFinal(mod)) {
					if (f.get(null).equals(field)) {
						found = true;
					}
				} else {
				}
			}
			if (!found)
				throw new IPasteException(field);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new IPasteException(CLIENT_EXCEPTION + "invalid input: " + e);
		}
	}

	private List<Integer> jsonToIntegerList(String response) throws IPasteException {
		List<Integer> list = null;
		Object obj = JSONValue.parse(response);
		list = new ArrayList<Integer>();
		// loop array
		JSONArray msg = (JSONArray) obj;
		@SuppressWarnings("unchecked")
		// Using legacy API
		Iterator<String> iterator = msg.iterator();
		while (iterator.hasNext()) {
			list.add(Integer.parseInt(iterator.next()));
		}
		return list;
	}

	private List<Integer> textToIntegerList(String response) throws IPasteException {
		List<Integer> list = new ArrayList<Integer>();
		String[] arr = response.split("\\r?\\n");
		try {
			for (String s : arr) {
				list.add(Integer.parseInt(s));
			}
		} catch (NumberFormatException e) {
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}

		return list;
	}

	private boolean isErrorResponse(String response) {
		return response.startsWith("KO -");
	}

	public String getDevKey() {
		return devKey;
	}

	public void setDevKey(String devKey) {
		this.devKey = devKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTmpKey() {
		return tmpKey;
	}

	public void setTmpKey(String tmpKey) {
		this.tmpKey = tmpKey;
	}

}
