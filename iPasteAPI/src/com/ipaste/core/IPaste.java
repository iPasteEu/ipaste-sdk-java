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
import com.ipaste.paste.PasteValidStatuses;
import com.ipaste.paste.PasteValidColors;
import com.ipaste.paste.PasteValidExpiryDates;
import com.ipaste.paste.PasteValidSyntaxes;
import com.ipaste.response.IPasteExtraResponseFormat;
import com.ipaste.response.IPasteResponseFormat;

public class IPaste implements IPasteCore {

	// put here your developer key if you want to use the IPaste(String
	// username,
	// String password) constructor
	// private final String DEV_KEY = null;

	private String devKey;
	private String username;
	private String password;
	private String tmpKey;
	private int reconnections;

	private IPaste(String username, String password) throws IPasteException {
		// you must assign a value to the DEV_KEY variable in order to be able
		// to use this constructor
		// otherwise use the other constructor
		// if (this.DEV_KEY == null)
		// throw new IPasteException(CLIENT_EXCEPTION +
		// "invalid developer key, please assign a value to the DEV_KEY variable, otherwise use the other construct");
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
		this.validateEmpty(tmpKey);
		this.validateEmpty(username);
		this.validateEmpty(password);

		this.devKey = devKey;
		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		return this.login();
	}

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

	public List<Integer> getUserPastes(String responseFormat) throws IPasteException {
		List<Integer> list = null;
		this.validateTmpKey(this.tmpKey);
		this.validateField(responseFormat, IPasteResponseFormat.class);
		String response = this.call("act=get_all_user_pastes&frm=" + responseFormat + "&a=" + this.tmpKey);
		if (!this.isErrorResponse(response))
			list = this.jsonToIntegerList(response);
		else
			throw new IPasteException(response);
		return list;
	}

	public List<Integer> getUserPastes(String responseFormat, String tmpKey) throws IPasteException {
		this.tmpKey = tmpKey;
		return this.getUserPastes(responseFormat);
	}

	private List<Integer> jsonToIntegerList(String response) throws IPasteException {
		List<Integer> list = null;
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
		if (this.isEmpty(password) || tmpKey.length() != 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid password: " + password);
	}

	private void validateRawPassword(String rawPassword) throws IPasteException {
		if (this.isEmpty(password) || tmpKey.length() < 6 || tmpKey.length() > 32)
			throw new IPasteException(CLIENT_EXCEPTION + "invalid password: " + password);
	}

	private void validateField(String field, Class cl) throws IPasteException {
		try {
			cl.getField(field);
		} catch (NoSuchFieldException | SecurityException e) {
			throw new IPasteException(CLIENT_EXCEPTION + "invalid input");
		}
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
