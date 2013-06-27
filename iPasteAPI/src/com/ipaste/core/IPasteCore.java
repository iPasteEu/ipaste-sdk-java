package com.ipaste.core;

import java.util.List;

import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;

public interface IPasteCore extends IPasteConstants {
	/**
	 * Call first this function in order to be able to make other types of
	 * request.
	 * 
	 * @return
	 * @throws IPasteException
	 */
	public String login() throws IPasteException;

	/**
	 * Call first this function in order to be able to make other types of
	 * request.
	 * 
	 * @param devKey
	 * @param username
	 * @param password
	 * @return
	 * @throws IPasteException
	 */
	public String login(String devKey, String username, String password) throws IPasteException;

	/**
	 * Retrieves users paste IDs formatted according to the input format value.
	 * 
	 * @return
	 * @throws IPasteException
	 */
	public List<Integer> getUserPastes() throws IPasteException;

	/**
	 * Retrieves users paste IDs formatted according to the input format value.
	 * 
	 * @param format
	 * @param tmpKey
	 * @return
	 * @throws IPasteException
	 */
	public List<Integer> getUserPastes(String responseFormat) throws IPasteException;

	public List<Integer> getUserPastes(String format, String tmpKey) throws IPasteException;

	public List<Integer> getUserPastes(String responseFormat, String username, String tmpKey) throws IPasteException;

	/**
	 * Updates users paste. You should set the iPastePaste id otherwise it will
	 * return an KO.
	 * 
	 * @param paste
	 * @return
	 * @throws IPasteException
	 */
	public boolean update(Paste paste) throws IPasteException;

	/**
	 * Updates users paste. You should set the iPastePaste id otherwise it will
	 * return an KO.
	 * 
	 * @param paste
	 * @param tmpKey
	 * @return
	 * @throws IPasteException
	 */
	public boolean update(Paste paste, String tmpKey) throws IPasteException;

	/**
	 * Inserts a new paste.
	 * 
	 * @param paste
	 * @return
	 * @throws IPasteException
	 */
	public int paste(Paste paste) throws IPasteException;

	/**
	 * Inserts a new paste.
	 * 
	 * @param paste
	 * @param tmpKey
	 * @return
	 * @throws IPasteException
	 */
	public int paste(Paste paste, String tmpKey) throws IPasteException;

	/**
	 * Remove a paste by passing it's paste ID. If you will try to remove an
	 * unexisting paste (for example by removing the same paste for many times),
	 * 
	 * @param pasteId
	 * @return
	 * @throws IPasteException
	 */
	public boolean remove(int pasteId) throws IPasteException;

	/**
	 * Remove a paste by passing it's paste ID. If you will try to remove an
	 * unexisting paste (for example by removing the same paste for many times),
	 * it will return always OK as response.
	 * 
	 * @param pasteId
	 * @param tmpKey
	 * @return
	 * @throws IPasteException
	 */
	public boolean remove(int pasteId, String tmpKey) throws IPasteException;

	/**
	 * Retrieves paste
	 * 
	 * @param pasteId
	 * @return
	 * @throws IPasteException
	 */
	public String get(int pasteId) throws IPasteException;

	/**
	 * Retrieves paste
	 * 
	 * @param pasteId
	 * @param format
	 * @return
	 * @throws IPasteException
	 */
	public String get(int pasteId, String format) throws IPasteException;

	/**
	 * Retrieves paste
	 * 
	 * @param pasteId
	 * @param format
	 * @param tmpKey
	 * @return
	 * @throws IPasteException
	 */
	public String get(int pasteId, String format, String tmpKey) throws IPasteException;

}
