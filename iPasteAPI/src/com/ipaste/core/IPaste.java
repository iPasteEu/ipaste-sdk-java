package com.ipaste.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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

	public IPaste(String devKey, String username, String password) {
		super();
		this.devKey = devKey;
		this.username = username;
		this.password = password;
		this.tmpKey = this.login();
	}

	@Override
	public String login() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String devKey, String username, String password) {
		// TODO Auto-generated method stub
		return null;
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

	private <T> T call() {

		T ret = null;
		String str = "url";
		try {
			URL url = new URL(str);
			URLConnection urlc = url.openConnection();
			BufferedReader bfr = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
			String line;

			String title, des;
			while ((line = bfr.readLine()) != null) {
				JSONArray jsa = new JSONArray(line);
				for (int i = 0; i < jsa.length(); i++) {
					JSONObject jo = (JSONObject) jsa.get(i);
					title = jo.getString("deal_title"); // tag name
														// "deal_title",will
														// return value that we
														// save in title string
					des = jo.getString("deal_description");
				}
			}
		} catch (Exception e) {
		} finally {

		}
		return ret;
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
