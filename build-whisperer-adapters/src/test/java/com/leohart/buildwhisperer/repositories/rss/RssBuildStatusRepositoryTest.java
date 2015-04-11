package com.leohart.buildwhisperer.repositories.rss;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.leohart.buildwhisperer.repositories.BuildStatusRepository;
import com.leohart.buildwhisperer.repositories.BuildStatusRepositoryException;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author Leo Hart
 */
public class RssBuildStatusRepositoryTest {

	SyndEntryImplBuildStatusExtractor buildStatusExtractor;
	BuildStatusRepository<BuildStatus> statusRepository;

	private static final String PACKAGE = "com/leohart/buildwhisperer/repositories/rss";

	private static final String RSS_RESOURCE_FAIL = RssBuildStatusRepositoryTest.PACKAGE
			+ "/rssFailures.xml";
	private static final String RSS_RESOURCE_SUCCESS = RssBuildStatusRepositoryTest.PACKAGE
			+ "/rssSuccesses.xml";
	private static final String RSS_RESOURCE_CORRUPT = RssBuildStatusRepositoryTest.PACKAGE
			+ "/rssCorrupt.xml";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.buildStatusExtractor = EasyMock
				.createMock(SyndEntryImplBuildStatusExtractor.class);

	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.repositories.rss.RssBuildStatusRepository#getBuildStatus()}
	 * .
	 */
	@Test(expected = BuildStatusRepositoryException.class)
	public void testGetBuildStatusException() throws Exception {
		Resource resource = new ClassPathResource(
				RssBuildStatusRepositoryTest.RSS_RESOURCE_CORRUPT);

		EasyMock.expect(
				this.buildStatusExtractor.getBuildStatus(EasyMock
						.isA(SyndEntryImpl.class))).andReturn(
				new SimpleBuildStatus(true));
		EasyMock.replay(this.buildStatusExtractor);

		this.statusRepository = new RssBuildStatusRepository(resource.getURL(),
				this.buildStatusExtractor);

		this.statusRepository.getBuildStatus();
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.repositories.rss.RssBuildStatusRepository#getBuildStatus()}
	 * .
	 */
	@Test
	public void testGetBuildStatusFailure() throws Exception {
		Resource resource = new ClassPathResource(
				RssBuildStatusRepositoryTest.RSS_RESOURCE_FAIL);

		EasyMock.expect(
				this.buildStatusExtractor.getBuildStatus(EasyMock
						.isA(SyndEntryImpl.class))).andReturn(
				new SimpleBuildStatus(false));
		EasyMock.replay(this.buildStatusExtractor);

		this.statusRepository = new RssBuildStatusRepository(resource.getURL(),
				this.buildStatusExtractor);

		Assert.assertEquals("Failures were not found: ", false,
				this.statusRepository.getBuildStatus().isSuccessful());
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.repositories.rss.RssBuildStatusRepository#getBuildStatus()}
	 * .
	 */
	@Test
	public void testGetBuildStatusSuccess() throws Exception {
		Resource resource = new ClassPathResource(
				RssBuildStatusRepositoryTest.RSS_RESOURCE_SUCCESS);

		EasyMock.expect(
				this.buildStatusExtractor.getBuildStatus(EasyMock
						.isA(SyndEntryImpl.class))).andReturn(
				new SimpleBuildStatus(true));
		EasyMock.replay(this.buildStatusExtractor);

		this.statusRepository = new RssBuildStatusRepository(resource.getURL(),
				this.buildStatusExtractor);

		Assert.assertEquals("Failures were found: ", true,
				this.statusRepository.getBuildStatus().isSuccessful());
	}
}
