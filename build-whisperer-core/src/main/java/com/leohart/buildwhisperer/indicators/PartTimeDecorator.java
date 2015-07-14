package com.leohart.buildwhisperer.indicators;

import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */
public class PartTimeDecorator extends PoweredBuildStatusIndicatorDecorator {

	BuildStatusIndicatorTurnOffCriteria turnOffCriteria;

	/**
	 * @param indicator
	 */
	public PartTimeDecorator(PoweredBuildStatusIndicator indicator,
			BuildStatusIndicatorTurnOffCriteria turnOffCriteria) {
		super(indicator);
		this.turnOffCriteria = turnOffCriteria;
	}

	@Override
	public void indicate(BuildStatus status) {
		if (!this.wasTurnedOff()) {
			this.indicator.indicate(status);
		}
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator#turnOff()
	 */
	@Override
	public void turnOff() {
		this.indicator.turnOff();
	}

	private boolean wasTurnedOff() {
		if (this.turnOffCriteria.shouldTurnOff()) {
			this.turnOff();
			return true;
		}
		return false;
	}

}
