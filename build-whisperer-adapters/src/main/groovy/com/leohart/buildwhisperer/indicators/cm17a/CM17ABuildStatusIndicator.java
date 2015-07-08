package com.leohart.buildwhisperer.indicators.cm17a;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leohart.buildwhisperer.indicators.BuildStatusIndicatorException;
import com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;
import com.pragauto.X10Device;

/**
 * @author Leo Hart
 */
public class CM17ABuildStatusIndicator implements PoweredBuildStatusIndicator {

	private static final Log LOG = LogFactory.getLog(CM17ABuildStatusIndicator.class);

	private final X10Device passDevice;
	private final X10Device failDevice;

	/**
	 * @param passDevice
	 * @param failDevice
	 */
	public CM17ABuildStatusIndicator(final X10Device passDevice,
			final X10Device failDevice) {
		super();
		this.passDevice = passDevice;
		this.failDevice = failDevice;
	}

	/**
	 * @see com.com.leohart.buildwhisperer.indicator.BuildStatusIndicator#indicate(com.com.leohart.buildwhisperer.buildstatus.BuildStatus)
	 */
	@Override
	public void indicate(final BuildStatus status) {
		CM17ABuildStatusIndicator.LOG.info("Indicating BuildStatus of "
				+ status.toString());

		try {

			if (status.isSuccessful()) {
				this.passDevice.on();
				this.failDevice.off();
			}
			else {
				this.passDevice.off();
				this.failDevice.on();
			}
		}
		catch (Exception ex) {
			throw new BuildStatusIndicatorException(
					"Could not send command to BuildStatusIndicator: ", ex);
		}
	}

	/**
	 * This implementation aggregates all passed in status and indicates
	 * BuildStatus.Failure if any failures are found. If no failures are found,
	 * BuildStatus.Success is indicated.
	 * 
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus[])
	 */
	public void indicate(final BuildStatus[] statuses) {
		for (BuildStatus status : statuses) {
			if (!status.isSuccessful()) {
				this.indicate(new SimpleBuildStatus(false));
				return;
			}
		}
		this.indicate(new SimpleBuildStatus(true));
	}

	/**
	 * @see com.com.leohart.buildwhisperer.indicator.BuildStatusIndicator#shouldTurnOff()
	 */
	@Override
	public void turnOff() {
		CM17ABuildStatusIndicator.LOG.info("Turning off");
		try {
			this.passDevice.off();
			this.failDevice.off();
		}
		catch (Exception ex) {
			throw new BuildStatusIndicatorException(
					"Could not send command to BuildStatusIndicator: ", ex);
		}
	}
}
