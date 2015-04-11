package com.leohart.buildwhisperer.repositories;

/**
 * @author Leo Hart
 */
public class BuildStatusRepositoryException extends RuntimeException {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public BuildStatusRepositoryException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

}
