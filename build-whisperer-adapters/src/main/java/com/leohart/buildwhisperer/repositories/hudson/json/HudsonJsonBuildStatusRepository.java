package com.leohart.buildwhisperer.repositories.hudson.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.Resource;

import com.leohart.buildwhisperer.repositories.BuildStatusRepository;
import com.leohart.buildwhisperer.repositories.BuildStatusRepositoryException;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;

public class HudsonJsonBuildStatusRepository implements
		BuildStatusRepository<BuildStatus> {

	public static final String BLUE = "blue";
	public static final String BLUE_RUNNING = "blue_anime";
	public static final String RED = "red";
	public static final String RED_RUNNING = "red_anime";
	public static final String YELLOW = "yellow";
	public static final String YELLOW_RUNNING = "yellow_anime";
	public static final int NUMBER_OF_TIMES_TO_TRY_TO_CONNECT = 10;

	private static final String ERROR_MSG = "An error occured while retrieving build status: ";
	private static final BuildStatus SUCCESSFUL_STATUS = new SimpleBuildStatus(
			true);
	private static final BuildStatus FAILURE_STATUS = new SimpleBuildStatus(
			false);

	private static final Log LOG = LogFactory
			.getLog(HudsonJsonBuildStatusRepository.class);

	private Map<String, BuildStatus> colorsAndStatus;
	private Resource jsonResource;
	private ObjectMapper mapper;
	private Integer numberOfConnectionAttempts;

	public HudsonJsonBuildStatusRepository(
			final Map<String, BuildStatus> colorsAndStatus,
			final Resource jsonResource) {
		super();
		this.colorsAndStatus = colorsAndStatus;
		this.jsonResource = jsonResource;
		this.mapper = new ObjectMapper();
	}

	public HudsonJsonBuildStatusRepository(
			final Resource jsonResource) {
		super();

		this.jsonResource = jsonResource;

		this.colorsAndStatus = new HashMap<String, BuildStatus>();
		this.colorsAndStatus.put(HudsonJsonBuildStatusRepository.BLUE,
				HudsonJsonBuildStatusRepository.SUCCESSFUL_STATUS);
		this.colorsAndStatus.put(HudsonJsonBuildStatusRepository.BLUE_RUNNING,
				HudsonJsonBuildStatusRepository.SUCCESSFUL_STATUS);
		this.colorsAndStatus.put(HudsonJsonBuildStatusRepository.YELLOW,
				HudsonJsonBuildStatusRepository.FAILURE_STATUS);
		this.colorsAndStatus.put(
				HudsonJsonBuildStatusRepository.YELLOW_RUNNING,
				HudsonJsonBuildStatusRepository.FAILURE_STATUS);
		this.colorsAndStatus.put(HudsonJsonBuildStatusRepository.RED,
				HudsonJsonBuildStatusRepository.FAILURE_STATUS);
		this.colorsAndStatus.put(HudsonJsonBuildStatusRepository.RED_RUNNING,
				HudsonJsonBuildStatusRepository.FAILURE_STATUS);

		this.mapper = new ObjectMapper();
	}

	HudsonJsonBuildStatusRepository(
			final Map<String, BuildStatus> colorsAndStatus,
			final Resource jsonResource,
			final ObjectMapper mapper) {
		super();
		this.colorsAndStatus = colorsAndStatus;
		this.jsonResource = jsonResource;
		this.mapper = mapper;
	}

	HudsonJsonBuildStatusRepository(
			final Resource jsonResource,
			final ObjectMapper mapper) {
		this(jsonResource);
		this.mapper = mapper;
	}

	@Override
	public BuildStatus getBuildStatus() {
		boolean connectedSuccessfully = false;

		this.numberOfConnectionAttempts = 0;

		HudsonView view = null;

		while (!connectedSuccessfully) { //|| this.numberOfConnectionAttempts <= HudsonJsonBuildStatusRepository.NUMBER_OF_TIMES_TO_TRY_TO_CONNECT
			try {
				this.numberOfConnectionAttempts++;

				view = this.mapper.readValue(this.jsonResource.getURL(),
							HudsonView.class);

				connectedSuccessfully = true;
			}
			catch (JsonMappingException ex) {
				throw new BuildStatusRepositoryException(
							HudsonJsonBuildStatusRepository.ERROR_MSG, ex);
			}
			catch (JsonParseException ex) {
				throw new BuildStatusRepositoryException(
							HudsonJsonBuildStatusRepository.ERROR_MSG, ex);
			}
			catch (IOException ex) {
				this.tryConnectingAgain(ex);
			}
		}

		return this.failIfAtLeastOneJobIsFailing(view.getJobs());

	}

	public Integer getNumberOfConnectionAttempts() {
		return this.numberOfConnectionAttempts;
	}

	private BuildStatus failIfAtLeastOneJobIsFailing(final List<HudsonJob> jobs) {
		try {
			for (HudsonJob job : jobs) {
				if (this.colorsAndStatus.get(job.getColor()) == null
						|| !this.colorsAndStatus.get(job.getColor())
								.isSuccessful()) {
					return HudsonJsonBuildStatusRepository.FAILURE_STATUS;
				}
			}
			return HudsonJsonBuildStatusRepository.SUCCESSFUL_STATUS;
		}
		catch (NullPointerException ex) {
			String errorMessage = String.format(
					"Unable to read status from Jobs.  Jobs were: %s", jobs);

			throw new BuildStatusRepositoryException(errorMessage, ex);
		}
	}

	private void tryConnectingAgain(final Exception ex) {
		HudsonJsonBuildStatusRepository.LOG
				.warn("FYI: an exception occurred while trying to access Hudson's JSON API.  We'll try connecting again in a bit: ",
						ex);
		if (this.numberOfConnectionAttempts >= HudsonJsonBuildStatusRepository.NUMBER_OF_TIMES_TO_TRY_TO_CONNECT) {
			throw new BuildStatusRepositoryException(
					"Couldn't connect after multiple attempts: ",
					ex);
		}

		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException ex1) {
			throw new BuildStatusRepositoryException(
						"Couldn't connect after multiple attempts: ",
						ex1);
		}
	}

}
