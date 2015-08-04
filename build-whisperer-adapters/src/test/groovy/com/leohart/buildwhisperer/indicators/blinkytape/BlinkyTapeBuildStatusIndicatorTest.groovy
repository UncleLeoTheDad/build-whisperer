package com.leohart.buildwhisperer.indicators.blinkytape;

import static org.junit.Assert.*

import java.awt.Color

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import com.leohart.blinkytape.FakeBlinkyTapeController
import com.leohart.buildwhisperer.status.BuildStatus
import com.leohart.buildwhisperer.status.SimpleBuildStatus

class BlinkyTapeBuildStatusIndicatorTest {
	
	private static final Integer ANY_LIGHT_IN_FRAME = 0;
	
	FakeBlinkyTapeController controller = new FakeBlinkyTapeController();
	URL failureUrl;
	URL successUrl;
	
	@Before
	public void init(){
		failureUrl = this.getClass().getResource("/com/leohart/buildwhisperer/indicators/blinkytape/FailurePattern.png");
		successUrl = this.getClass().getResource("/com/leohart/buildwhisperer/indicators/blinkytape/SuccessPattern.png");
	}


	@Test
	public void indicate_ShouldFailIfAnyStatusInArrayIsFailure() {		
		BlinkyTapeBuildStatusIndicator indicator = new BlinkyTapeBuildStatusIndicator(controller, successUrl, failureUrl);
		
		BuildStatus[] statuses = new BuildStatus[2];
		
		statuses[0] = new SimpleBuildStatus(true);
		statuses[1] = new SimpleBuildStatus(false); 
		
		indicator.indicate(statuses);
		
		Assert.assertEquals("Should have been a failure color, red: ", Color.RED, controller.getFramesRendered().last().getColorOfLight(ANY_LIGHT_IN_FRAME));
		
	}
	
	@Test
	public void indicate_ShouldFailIfAllStatusInArrayAreSuccess() {
		BlinkyTapeBuildStatusIndicator indicator = new BlinkyTapeBuildStatusIndicator(controller, successUrl, failureUrl);
		
		BuildStatus[] statuses = new BuildStatus[2];
		
		statuses[0] = new SimpleBuildStatus(true);
		statuses[1] = new SimpleBuildStatus(true);
		
		indicator.indicate(statuses);
		
		Assert.assertEquals("Should have been a success color, green: ", Color.GREEN, controller.getFramesRendered().last().getColorOfLight(ANY_LIGHT_IN_FRAME));
		
	}

	@Test
	public void turnOff_ShouldShowTheDefaultTurnOffPattern() {
		BlinkyTapeBuildStatusIndicator indicator = new BlinkyTapeBuildStatusIndicator(controller, successUrl, failureUrl);
		
		indicator.turnOff();
		
		Assert.assertEquals("Should have been an 'off' color, black: ", Color.BLACK, controller.getFramesRendered().last().getColorOfLight(ANY_LIGHT_IN_FRAME));
	}

}
