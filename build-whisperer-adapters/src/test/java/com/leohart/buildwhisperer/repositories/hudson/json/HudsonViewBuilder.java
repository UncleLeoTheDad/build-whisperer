package com.leohart.buildwhisperer.repositories.hudson.json;

import java.util.ArrayList;
import java.util.List;

public class HudsonViewBuilder {

	private static final String DUMMY_STRING = "Dummy";

	public HudsonView build() {
		List<HudsonJob> jobs = new ArrayList<HudsonJob>();

		HudsonJob job = new HudsonJob();
		job.setColor(HudsonJsonBuildStatusRepository.BLUE);

		jobs.add(job);

		return new HudsonView(HudsonViewBuilder.DUMMY_STRING, jobs,
				HudsonViewBuilder.DUMMY_STRING, HudsonViewBuilder.DUMMY_STRING);
	}

}
