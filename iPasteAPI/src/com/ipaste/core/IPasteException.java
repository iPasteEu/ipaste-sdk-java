package com.ipaste.core;

public class IPasteException extends Exception {

	private static final long serialVersionUID = 7554065210988319216L;

	public IPasteException() {
		super();
	}

	public IPasteException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public IPasteException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IPasteException(String arg0) {
		super(arg0);
	}

	public IPasteException(Throwable arg0) {
		super(arg0);
	}

}
