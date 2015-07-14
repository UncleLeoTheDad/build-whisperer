package com.leohart.buildwhisperer.indicators;

/**
 * @author Leo Hart
 */
public abstract class PoweredBuildStatusIndicatorDecorator implements PoweredBuildStatusIndicator {

	protected PoweredBuildStatusIndicator indicator;

	/**
	 * @param indicator
	 */
	public PoweredBuildStatusIndicatorDecorator(PoweredBuildStatusIndicator indicator) {
		super();
		this.indicator = indicator;
	}

}
