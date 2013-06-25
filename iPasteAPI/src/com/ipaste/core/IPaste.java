package com.ipaste.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;
import com.ipaste.response.IPasteExtraResponseFormat;
import com.ipaste.response.IPasteResponseFormat;

public class IPaste implements IPasteCore {

	// put here your developer key if you want to use the IPaste(String
	// username,
	// String password) constructor
	//private final String DEV_KEY = null;

	private String devKey;
	private String username;
	private String password;
	private String tmpKey;
	private int reconnections;

	private IPaste(String username, String password) throws IPasteException {
		// you must assign a value to the DEV_KEY variable in order to be able
		// to use this constructor
		// otherwise use the other constructor
		//if (this.DEV_KEY == null)
			//throw new IPasteException(CLIENT_EXCEPTION + "invalid developer key, please assign a value to the DEV_KEY variable, otherwise use the other construct");
		this.validateUsername(username);
		this.validateRawPassword(password);

		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		this.reconnections = 0;

	}

	public IPaste(String devKey, String username, String password) throws IPasteException {
		this(username, password);
		this.validateTmpKey(tmpKey);
		this.devKey = devKey;
		// tries to login
		this.login();
	}

	@Override
	public String login() throws IPasteException {
		this.validateHashedPassword(this.password);
		this.validateTmpKey(this.tmpKey);
		this.validateUsername(this.username);

		this.reconnections = 0;
		String response;
		try {
			response = this.call("act=login&a=" + URLEncoder.encode(this.tmpKey, "UTF-8") + URLEncoder.encode(this.username, "UTF-8") + URLEncoder.encode(this.password, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IPasteException(CLIENT_EXCEPTION + "internal error");
		}
		if (this.isErrorResponse(response))
			throw new IPasteException(response);
		this.tmpKey = response;
		return response;
	}

	@Override
	public String login(String devKey, String username, String password) throws IPasteException {
		this.devKey = devKey;
		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		return this.login();
	}

	@Override
	public List<Integer> getUserPastes() throws IPasteException {
		List<Integer> list = null;
		if (this.isEmpty(this.tmpKey))
			throw new IPasteException(CLIENT_EXCEPTION + "invalid tmpKey");
		String response = this.call("act=get_all_user_pastes&frm=" + IPasteResponseFormat.JSON + "&a=" + this.tmpKey);

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(response);

			JSONObject jsonObject = (JSONObject) obj;

			list = new ArrayList<Integer>();

			// loop array
			JSONArray msg = (JSONArray) jsonObject.keySet();
			@SuppressWarnings("unchecked")
			// Using legacy API
			Iterator<String> iterator = msg.iterator();
			while (iterator.hasNext()) {
				list.add(Integer.parseInt(iterator.next()));
			}

		} catch (ParseException e) {
			throw new IPasteException(CLIENT_EXCEPTION + e);
		}

		return list;
	}

	public List<Integer> getUserPastes(String format, String username) throws IPasteException {
		if (!this.validateField(format, IPasteResponseFormat.class))
			throw new IPasteException(CLIENT_EXCEPTION + "invalid response format: " + format);
		if (this.isEmpty(username) || username.length() > 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid username: " + username);
		return this.getUserPastes();
	}

	@Override
	public List<Integer> getUserPastes(String format, String username, String tmpKey) throws IPasteException {
		if (this.isEmpty(tmpKey))
			throw new IPasteException(CLIENT_EXCEPTION + "invalid tmpKey: " + tmpKey);
		this.tmpKey = tmpKey;
		return this.getUserPastes(format, username);

	}

	private boolean validateField(String field, Class<IPasteResponseFormat> cl) {
		try {
			cl.getField(field);
		} catch (NoSuchFieldException | SecurityException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Paste paste) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Paste paste, String tmpKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int paste(Paste paste) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int paste(Paste paste, String tmpKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean remove(int pasteId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int pasteId, IPasteExtraResponseFormat format, String tmpKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Paste get(int pasteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paste get(int pasteId, IPasteExtraResponseFormat format, String tmpKeys) {
		// TODO Auto-generated method stub
		return null;
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
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream());) {
				wr.writeBytes(param);
				wr.flush();
			}
			InputStream is = connection.getInputStream();
			StringBuilder response = null;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(is));
				String line;
				response = new StringBuilder();
				while ((line = br.readLine()) != null) {
					response.append(line);
				}
			} catch (Exception e) {
				throw new IPasteException(CLIENT_EXCEPTION + e);
			} finally {
				if (br != null)
					try {
						br.close();
					} catch (Exception e2) {
						throw new IPasteException(CLIENT_EXCEPTION + e2);
					}
			}
			return response.toString();

		} catch (Exception e) {
			throw new IPasteException(CLIENT_EXCEPTION + e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private String md5(String str) throws IPasteException {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return md5.digest(str.getBytes()).toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new IPasteException(e);
		}
	}

	private boolean isEmpty(String str) {
		return (str == null || str.isEmpty());
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
		if (this.isEmpty(password) || tmpKey.length() != 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid password: " + password);
	}

	private void validateRawPassword(String rawPassword) throws IPasteException {
		if (this.isEmpty(password) || tmpKey.length() < 6 || tmpKey.length() > 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid password: " + password);
	}

	private boolean isErrorResponse(String response) {
		return response.contains("-");
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

	public int getReconnections() {
		return reconnections;
	}

	public void setReconnections(int reconnections) {
		this.reconnections = reconnections;
	}

}
