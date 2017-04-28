package org.eclipse.capra.core;

public class CapraException extends Exception {
	private static final long serialVersionUID = 4927897555593083617L;

	public CapraException(String cause) {
		super(cause);
	}
	
	public CapraException(Throwable cause){
		super(cause);
	}
}
