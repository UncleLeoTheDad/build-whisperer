package com.leohart.buildwhisperer.indicators;

/**
 * @author Leo Hart
 */
public class BuildStatusIndicatorException extends RuntimeException {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 * @param cause
	 */
	public BuildStatusIndicatorException(String message, Throwable cause) {
		super(message, cause);
	}

}
