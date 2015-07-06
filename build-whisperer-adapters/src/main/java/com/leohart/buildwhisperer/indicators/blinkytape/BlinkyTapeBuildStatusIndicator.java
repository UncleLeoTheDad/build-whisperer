package com.leohart.buildwhisperer.indicators.blinkytape;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leohart.buildwhisperer.indicators.BuildStatusIndicatorException;
import com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */

///CLOVER:OFF Not worth testing
public class BlinkyTapeBuildStatusIndicator implements
		PoweredBuildStatusIndicator {

	Log log = LogFactory.getLog(BlinkyTapeBuildStatusIndicator.class);

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)
	 */
	@Override
	public void indicate(final BuildStatus status) {
		this.log.info("Device was told to indicate status of " + status);
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus[])
	 */
	public void indicate(final BuildStatus[] statuses)
			throws BuildStatusIndicatorException {
		this.log.info("Device was told to indicate statuses of "
				+ ArrayUtils.toString(statuses));
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator#turnOff()
	 */
	@Override
	public void turnOff() {
		this.log.info("Device was requested to turn off.");
	}

}
///CLOVER:ON
