package com.leohart.buildwhisperer.indicators;

import junit.framework.Assert;

import org.junit.Test;

import com.leohart.buildwhisperer.bridges.StoreLastIndicatedBuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

/**
 * @author Leo Hart
 */
public class PartTimeDecorator_indicateTests {

	public class ShouldOrShouldNotBuildStatusIndicatorTurnOffCriteria
			implements BuildStatusIndicatorTurnOffCriteria {

		private boolean shouldTurnOff;

		public ShouldOrShouldNotBuildStatusIndicatorTurnOffCriteria(
				final boolean shouldTurnOff) {
			this.shouldTurnOff = shouldTurnOff;
		}

		@Override
		public boolean shouldTurnOff() {
			return this.shouldTurnOff;
		}

	}

	public class StoreLastIndicatedPoweredBuildStatusIndicator implements
			PoweredBuildStatusIndicator {

		private StoreLastIndicatedBuildStatusIndicator storeLastIndicatedIndicator = new StoreLastIndicatedBuildStatusIndicator();
		private boolean wasTurnedOff;

		public BuildStatus getLastStatusIndicated() {
			return this.storeLastIndicatedIndicator.getLastStatusIndicated();
		}

		@Override
		public void indicate(final BuildStatus status) {
			this.storeLastIndicatedIndicator.indicate(status);
		}

		@Override
		public void turnOff() throws BuildStatusIndicatorException {
			this.wasTurnedOff = true;
		}

		public boolean wasTurnedOff() {
			return this.wasTurnedOff;
		}
	}

	private StoreLastIndicatedPoweredBuildStatusIndicator indicator;
	private ShouldOrShouldNotBuildStatusIndicatorTurnOffCriteria criteria;
	private PartTimeDecorator decorator;

	@Test
	public void shouldIndicateIfItWasNotTurnedOff() {
		this.shouldIndicateDependingOnWhetherItWasTurnedOff(false);
	}

	@Test
	public void shouldNotIndicateIfItWasTurnedOff() {
		this.shouldIndicateDependingOnWhetherItWasTurnedOff(true);
	}

	private void shouldIndicateDependingOnWhetherItWasTurnedOff(
			final boolean wasTurnedOff) {
		final boolean SHOULD_TURN_OFF = wasTurnedOff;
		final BuildStatus ANY_STATUS = new SimpleBuildStatus(true);

		this.criteria = new ShouldOrShouldNotBuildStatusIndicatorTurnOffCriteria(
				SHOULD_TURN_OFF);
		this.indicator = new StoreLastIndicatedPoweredBuildStatusIndicator();
		this.decorator = new PartTimeDecorator(this.indicator,
				this.criteria);

		this.decorator.indicate(ANY_STATUS);
		Assert.assertEquals(wasTurnedOff != true ? ANY_STATUS : null,
				this.indicator.getLastStatusIndicated());
	}
}
