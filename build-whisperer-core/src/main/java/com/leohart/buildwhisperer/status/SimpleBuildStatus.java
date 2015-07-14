package com.leohart.buildwhisperer.status;

public class SimpleBuildStatus implements BuildStatus {

	private boolean successful;

	public SimpleBuildStatus(boolean successful) {
		super();
		this.successful = successful;
	}

	@Override
	public boolean isSuccessful() {
		return this.successful;
	}

	@Override
	public String toString() {
		return String.format("{successful=%s}", this.successful);
	}

}
