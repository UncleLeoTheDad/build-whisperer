package com.leohart.buildwhisperer.repositories.hudson.json;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class HudsonView {

	private String description;
	private List<HudsonJob> jobs = new ArrayList<HudsonJob>();
	private String name;
	private String url;

	@JsonCreator
	public HudsonView(
			@JsonProperty("description") final String description,
			@JsonProperty("jobs") final List<HudsonJob> jobs,
			@JsonProperty("name") final String name,
			@JsonProperty("url") final String url) {
		super();
		this.description = description;
		this.jobs = jobs;
		this.name = name;
		this.url = url;
	}

	public String getDescription() {
		return this.description;
	}

	public List<HudsonJob> getJobs() {
		return this.jobs;
	}

	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}

}
