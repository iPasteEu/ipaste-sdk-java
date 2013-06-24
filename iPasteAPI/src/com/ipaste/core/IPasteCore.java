package com.ipaste.core;

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
	 */
	public String login();

	public String login(String devKey, String username, String password);

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
	 */
	public String getUserPastes();

	public String getUserPastes(PasteResponseListFormat format, String username, String tmpKey);

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
	 */
	public String update(Paste paste);

	public String update(Paste paste, String tmpKey);

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
	 */
	public String paste(Paste paste);

	public String paste(Paste paste, String tmpKey);

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
	 */
	public String remove(int pasteId);

	public String remove(int pasteId, String tmpKey);

	/**
	 * Retrieves paste
	 * 
	 * @param $pasteID
	 * @param string $format
	 * @param null $tmpKeys
	 * @return mixed
	 */
	public String get(int pasteId);

	public String get(int pasteId, ResponsePasteFormat format, String tmpKeys);
}
