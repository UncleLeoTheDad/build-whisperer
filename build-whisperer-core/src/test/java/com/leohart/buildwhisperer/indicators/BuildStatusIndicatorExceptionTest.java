package com.leohart.buildwhisperer.indicators;

import org.junit.Assert;
import org.junit.Test;

public class BuildStatusIndicatorExceptionTest {

	private static final String THE_MESSAGE = "Word";
	private static final Exception THE_EXCEPTION = new Exception();

	@Test
	public void shouldPassAlongMessageAndExceptionWhileWrapping() {
		BuildStatusIndicatorException exception = new BuildStatusIndicatorException(THE_MESSAGE, THE_EXCEPTION);
		
		Assert.assertEquals(THE_MESSAGE, exception.getMessage());
		Assert.assertEquals(THE_EXCEPTION, exception.getCause());
	}

}
