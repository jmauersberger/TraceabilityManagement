package org.eclipse.capra.core;

/**
 * Capra does alot of operations which are not typesafe. Additionally, data may
 * be missing at runtime or other issues may occur.
 * 
 * @author Sascha Baumgart
 */
public class CapraException extends Exception {
	private static final long serialVersionUID = 4927897555593083617L;

	public CapraException(String cause) {
		super(cause);
	}

	public CapraException(Throwable cause) {
		super(cause);
	}
}
