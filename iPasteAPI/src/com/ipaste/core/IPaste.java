package com.ipaste.core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.StringTokenizer;

import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;
import com.ipaste.response.IPasteExtraResponseFormat;
import com.ipaste.response.IPasteResponseFormat;

public class IPaste implements IPasteCore {

	private String devKey;
	private String username;
	private String password;
	private String tmpKey;
	private int reconnections;

	public IPaste() {
	}

	public IPaste(String devKey, String username, String password) throws IPasteException {
		super();
		this.devKey = devKey;
		this.username = this.md5(username.toUpperCase());
		this.password = this.md5(password);
		this.reconnections = 0;

		this.tmpKey = this.login();
	}

	@Override
	public String login() throws IPasteException {
		this.reconnections = 0;
		if (this.devKey == null || this.devKey.isEmpty() || this.username == null || this.username.isEmpty() || this.password == null || this.password.isEmpty())
			throw new IPasteException(KO + " - invalid login data");
		String response = this.call("");
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
	public List<Integer> getUserPastes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getUserPastes(IPasteResponseFormat format, String username, String tmpKey) {
		// TODO Auto-generated method stub
		return null;
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

	private String call(String param) {

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
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
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
			} finally {
				if (br != null)
					try {
						br.close();
					} catch (Exception e2) {
					}
			}

			return response.toString();

		} catch (Exception e) {
			return null;
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
