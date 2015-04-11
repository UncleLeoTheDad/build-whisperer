package com.leohart.buildwhisperer.status;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class CompositeBuildStatusTest {

	CompositeBuildStatus buildStatus;

	@Before
	public void setUp() {
		this.buildStatus = new CompositeBuildStatus();
	}

	@Test
	public void testThatAddBuildStatusAdds() {
		BuildStatus child = new SimpleBuildStatus(true);

		this.buildStatus.addBuildStatus(child);

		Assert.assertTrue(this.buildStatus.getChildStatuses().contains(child));
	}

	@Test
	public void testThatIsSuccessfulIsFalseWhenAtLeastOneChildIsFalse() {
		BuildStatus success = new SimpleBuildStatus(true);
		BuildStatus failure = new SimpleBuildStatus(false);

		this.buildStatus.addBuildStatus(success);
		this.buildStatus.addBuildStatus(failure);

		Assert.assertEquals(false, this.buildStatus.isSuccessful());
	}

	@Test
	public void testThatIsSuccessfulIsTrueWhenAllChildrenShowTrue() {
		BuildStatus success = new SimpleBuildStatus(true);
		BuildStatus success2 = new SimpleBuildStatus(true);

		this.buildStatus.addBuildStatus(success);
		this.buildStatus.addBuildStatus(success2);

		Assert.assertEquals(true, this.buildStatus.isSuccessful());
	}

	@Test
	public void testThatToStringReturnsSuccessIndication() {
		final String EXPECTED_TO_STRING = "{successful=true}";

		CompositeBuildStatus status = new CompositeBuildStatus();
		status.addBuildStatus(new SimpleBuildStatus(true));

		Assert.assertEquals(EXPECTED_TO_STRING, status.toString());
	}

}
