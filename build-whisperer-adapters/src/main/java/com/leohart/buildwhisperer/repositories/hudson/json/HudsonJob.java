package com.leohart.buildwhisperer.repositories.hudson.json;

public class HudsonJob {

	private String color;

	private String jobName;

	private String jobUrl;

	public String getColor() {
		return this.color;
	}

	public String getName() {
		return this.jobName;
	}

	public String getUrl() {
		return this.jobUrl;
	}

	public void setColor(final String color) {
		this.color = color;
	}

	public void setName(final String name) {
		this.jobName = name;
	}

	public void setUrl(final String url) {
		this.jobUrl = url;
	}

	@Override
	public String toString() {
		return "HudsonJob [color=" + this.color + ", jobName=" + this.jobName
				+ ", jobUrl=" + this.jobUrl + "]";
	}

}