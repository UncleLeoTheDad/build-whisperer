package com.leohart.buildwhisperer.indicators;

/**
 * @author Leo Hart
 */
public abstract class PoweredBuildStatusIndicatorDecorator implements
		PoweredBuildStatusIndicator {

	protected PoweredBuildStatusIndicator indicator;

	/**
	 * @param indicator
	 */
	public PoweredBuildStatusIndicatorDecorator(
			final PoweredBuildStatusIndicator indicator) {
		super();
		this.indicator = indicator;
	}

}
