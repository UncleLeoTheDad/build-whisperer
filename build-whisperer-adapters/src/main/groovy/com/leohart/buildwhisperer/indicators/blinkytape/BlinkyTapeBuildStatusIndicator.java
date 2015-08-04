package com.leohart.buildwhisperer.indicators.blinkytape;

import java.net.URL;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leohart.blinkytape.BlinkyFrame;
import com.leohart.blinkytape.BlinkyFrameArrayBuilder;
import com.leohart.blinkytape.BlinkyTapeController;
import com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

/**
 * @author Leo Hart
 */
public class BlinkyTapeBuildStatusIndicator implements PoweredBuildStatusIndicator {

	private static final Log LOG = LogFactory.getLog(BlinkyTapeBuildStatusIndicator.class);
	private static final Integer DEFAULT_DELAY_BETWEEN_FRAMES = 50;
	private static final String DEFAULT_OFF_PATTERN_URL = "/com/leohart/buildwhisperer/indicators/blinkytape/OffPattern.png";

	private BlinkyTapeController controller;
	private BlinkyFrame[] successFrames;
	private BlinkyFrame[] failureFrames;
	private BlinkyFrame[] offFrames;
	private Integer delayBetweenFrames = DEFAULT_DELAY_BETWEEN_FRAMES;

	public BlinkyTapeBuildStatusIndicator(BlinkyTapeController controller,
			URL successBlinkyFramePattern, URL failureBlinkyFramePattern) {
		super();
		this.controller = controller;

		this.successFrames = new BlinkyFrameArrayBuilder().withImage(successBlinkyFramePattern)
				.build();
		this.failureFrames = new BlinkyFrameArrayBuilder().withImage(failureBlinkyFramePattern)
				.build();

		URL offPatternUrl = this.getClass().getResource(DEFAULT_OFF_PATTERN_URL);
		this.offFrames = new BlinkyFrameArrayBuilder().withImage(offPatternUrl).build();
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus)
	 */
	@Override
	public void indicate(BuildStatus status) {
		LOG.info("Device was told to indicate status of " + status);

		if (status.isSuccessful()) {
			controller.renderFrames(successFrames, delayBetweenFrames);
		} else {
			controller.renderFrames(failureFrames, delayBetweenFrames);
		}
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicator#indicate(com.leohart.buildwhisperer.status.BuildStatus[])
	 */
	public void indicate(BuildStatus[] statuses) {
		LOG.info("Device was told to indicate statuses of " + ArrayUtils.toString(statuses));

		for (BuildStatus status : statuses) {
			if (!status.isSuccessful()) {
				this.indicate(new SimpleBuildStatus(false));
				return;
			}
		}
		this.indicate(new SimpleBuildStatus(true));
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.PoweredBuildStatusIndicator#turnOff()
	 */
	@Override
	public void turnOff() {
		LOG.info("Device was requested to turn off.");

		controller.renderFrames(offFrames);
	}

}
