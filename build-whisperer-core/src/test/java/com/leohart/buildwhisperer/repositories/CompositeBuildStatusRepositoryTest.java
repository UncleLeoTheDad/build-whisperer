package com.leohart.buildwhisperer.repositories;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.CompositeBuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

public class CompositeBuildStatusRepositoryTest {

	private CompositeBuildStatusRepository repository;

	@Before
	public void setUp() {
		this.repository = new CompositeBuildStatusRepository(
				new ArrayList<BuildStatusRepository<BuildStatus>>());
	}

	@Test
	public void testThatAddRepositoryAdds() {
		BuildStatusRepository<BuildStatus> child = new DummyBuildStatusRepository();

		this.repository.addRepository(child);

		Assert.assertTrue(this.repository.getChildRepositories().contains(child));
	}

	@Test
	public void testThatEmptyListOfRepositoriesIsCreatedIfNoSetIsProvided() {
		this.repository = new CompositeBuildStatusRepository();

		Assert.assertNotNull("Repository list should not have been null: ", this.repository);
		Assert.assertEquals("Should have been 0 repositories added: ", 0, this.repository
				.getChildRepositories().size());
	}

	@Test
	public void testThatGetBuildStatusCompilesRepositoriesIntoComposite() {
		BuildStatus status1 = new SimpleBuildStatus(true);
		BuildStatus status2 = new SimpleBuildStatus(false);
		BuildStatusRepository<BuildStatus> child1 = new DummyBuildStatusRepository(status1);
		BuildStatusRepository<BuildStatus> child2 = new DummyBuildStatusRepository(status2);

		this.repository.addRepository(child1);
		this.repository.addRepository(child2);

		CompositeBuildStatus compositeStatus = (CompositeBuildStatus) this.repository
				.getBuildStatus();

		compositeStatus.getChildStatuses().contains(child1);
		compositeStatus.getChildStatuses().contains(child2);
	}
}
