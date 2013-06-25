package com.ipaste.core;

import java.util.List;

import com.ipaste.exception.IPasteException;
import com.ipaste.paste.Paste;

public interface IPasteCore extends IPasteConstants {
	/**
	 * Call first this function in order to be abble to make other types of
	 * request.
	 * 
	 * @param string
	 *            $devKey is your private key which you find on your iPaste
	 *            profile page
	 * @param string
	 *            $username users username, could be your iPaste username
	 * @param string
	 *            $password users password, could be your iPaste password
	 * @return mixed temporary key or KO with error description
	 * @throws IPasteException 
	 */
	public String login() throws IPasteException;

	/**
	 * Call first this function in order to be abble to make other types of
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
	 * @param string
	 *            $format response format @see iPasteIResponseListFormat for
	 *            valid formats
	 * @param string
	 *            $username users username, could be your username. If username
	 *            is null, it will return all logged in users paste IDs (both
	 *            private and pubblic), else it will return public users pastes.
	 * @param string
	 *            $tmpKey temporary key returned by the login function. Leave it
	 *            to null if you want to use the temporary key returned by login
	 *            function.
	 * @return mixed textual or JSON list (according to the input format) of
	 *         users pastes or an KO with the error description
	 * @throws IPasteException 
	 */
	public List<Integer> getUserPastes() throws IPasteException;

	/**
	 * Retrieves users paste IDs formatted according to the input format value.
	 * 
	 * @param format
	 * @param username
	 * @param tmpKey
	 * @return
	 * @throws IPasteException 
	 */
	public List<Integer> getUserPastes(String format, String tmpKey) throws IPasteException;

	/**
	 * Updates users paste. You shoud set the iPastePaste id otherwise it will
	 * return an KO.
	 * 
	 * @param iPastePaste
	 *            $paste paste that must be updated
	 * @param string
	 *            $tmpKey temporary key returned by the login function. Leave it
	 *            to null if you want to use the temporary key returned by login
	 *            function.
	 * @return mixed paste ID or an KO with the error description
	 * @throws IPasteException 
	 */
	public boolean update(Paste paste) throws IPasteException;

	/**
	 * Updates users paste. You shoud set the iPastePaste id otherwise it will
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
	 * @param iPastePaste
	 *            $paste that must be inserted
	 * @param string
	 *            $tmpKey temporary key returned by the login function. Leave it
	 *            to null if you want to use the temporary key returned by login
	 *            function.
	 * @return mixed paste ID of the new paste or an KO with the error
	 *         description
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
	 * it will return always OK as response.
	 * 
	 * @param int $pasteId paste identification number
	 * @param string
	 *            $tmpKey temporary key returned by the login function. Leave it
	 *            to null if you want to use the temporary key returned by login
	 *            function.
	 * @return mixed OK or an KO with the error description
	 * @throws IPasteException 
	 */
	public boolean remove(int pasteId) throws IPasteException;

	/**
	 * Remove a paste by passing it's paste ID. If you will try to remove an
	 * unexisting paste (for example by removing the same paste for many times),
	 * it will return always OK as response.
	 * 
	 * @param pasteId
	 * @param format
	 * @param tmpKey
	 * @return
	 * @throws IPasteException 
	 */
	public boolean remove(int pasteId, String tmpKey) throws IPasteException;

	/**
	 * Retrieves paste
	 * 
	 * @param $pasteID
	 * @param string
	 *            $format
	 * @param null $tmpKeys
	 * @return mixed
	 * @throws IPasteException 
	 */
	public String get(int pasteId) throws IPasteException;

	/**
	 * Retrieves paste
	 * 
	 * @param pasteId
	 * @param format
	 * @param tmpKeys
	 * @return
	 */
	
	public String get(int pasteId, String format) throws IPasteException;
	public String get(int pasteId, String format, String tmpKey) throws IPasteException;
}
