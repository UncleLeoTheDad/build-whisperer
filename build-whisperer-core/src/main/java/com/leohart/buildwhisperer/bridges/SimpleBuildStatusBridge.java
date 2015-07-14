package com.leohart.buildwhisperer.bridges;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leohart.buildwhisperer.indicators.BuildStatusIndicator;
import com.leohart.buildwhisperer.repositories.BuildStatusRepository;
import com.leohart.buildwhisperer.status.BuildStatus;

/**
 * @author Leo Hart
 */
public class SimpleBuildStatusBridge implements BuildStatusBridge {

	private static final Log LOG = LogFactory.getLog(SimpleBuildStatusBridge.class);

	protected BuildStatusRepository<BuildStatus> repository;
	protected BuildStatusIndicator indicator;

	public SimpleBuildStatusBridge(BuildStatusIndicator indicator,
			BuildStatusRepository<BuildStatus> repository) {
		this.indicator = indicator;
		this.repository = repository;
	}

	@Override
	public void bridgeBuildStatus() {
		SimpleBuildStatusBridge.LOG.info("Getting build status.");
		BuildStatus status = this.repository.getBuildStatus();

		SimpleBuildStatusBridge.LOG.info("Instructing feedback indicator to indicate status.");
		this.indicator.indicate(status);
	}

}
