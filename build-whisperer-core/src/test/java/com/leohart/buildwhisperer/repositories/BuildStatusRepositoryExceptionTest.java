package com.leohart.buildwhisperer.repositories;

import org.junit.Assert;
import org.junit.Test;

public class BuildStatusRepositoryExceptionTest {

	private static final String THE_MESSAGE = "Word";
	private static final Exception THE_EXCEPTION = new Exception();

	@Test
	public void shouldPassAlongMessageAndExceptionWhileWrapping() {
		BuildStatusRepositoryException exception = new BuildStatusRepositoryException(
				THE_MESSAGE, THE_EXCEPTION);

		Assert.assertEquals(THE_MESSAGE, exception.getMessage());
		Assert.assertEquals(THE_EXCEPTION, exception.getCause());
	}

}
