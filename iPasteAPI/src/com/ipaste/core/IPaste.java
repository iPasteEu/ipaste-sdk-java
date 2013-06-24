package com.ipaste.core;

import java.util.List;

import com.ipaste.paste.Paste;
import com.ipaste.response.IPasteResponseFormatAdvanced;
import com.ipaste.response.IPasteResponseFormat;

public class IPaste implements IPasteCore {

	private String devKey;
	private String username;
	private String password;
	private String tmpKey;
	private int reconnections;

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
	public int update(Paste paste) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Paste paste, String tmpKey) {
		// TODO Auto-generated method stub
		return 0;
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
	public boolean remove(int pasteId, IPasteResponseFormatAdvanced format, String tmpKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Paste get(int pasteId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paste get(int pasteId, IPasteResponseFormatAdvanced format, String tmpKeys) {
		// TODO Auto-generated method stub
		return null;
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
