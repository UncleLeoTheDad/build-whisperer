package com.leohart.buildwhisperer.status;

import junit.framework.Assert;

import org.junit.Test;

public class SimpleBuildStatusTest {

	@Test
	public void testThatToStringReturnsSuccessIndication() {
		final String EXPECTED_TO_STRING = "{successful=true}";

		Assert.assertEquals(EXPECTED_TO_STRING, new SimpleBuildStatus(true).toString());
	}

}
