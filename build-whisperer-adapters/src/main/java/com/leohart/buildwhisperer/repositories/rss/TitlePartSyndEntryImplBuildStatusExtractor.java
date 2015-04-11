package com.leohart.buildwhisperer.repositories.rss;

import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author Leo Hart
 */
public class TitlePartSyndEntryImplBuildStatusExtractor implements
		SyndEntryImplBuildStatusExtractor {

	private static final String HUDSON_SPLIT_REGEX = " ";
	private static final String HUDSON_SUCCESS_STRING = "(SUCCESS)";
	private static final int HUDSON_SPLIT_PART_INDEX = 2;

	private String splitRegex = HUDSON_SPLIT_REGEX;
	private String successString = HUDSON_SUCCESS_STRING;
	private int splitPartIndex = HUDSON_SPLIT_PART_INDEX;

	/**
	 * 
	 */
	public TitlePartSyndEntryImplBuildStatusExtractor() {
		super();
	}

	/**
	 * @param splitRegex
	 * @param successString
	 * @param splitPartIndex
	 */
	public TitlePartSyndEntryImplBuildStatusExtractor(final String splitRegex,
			final String successString, final int splitPartIndex) {
		super();
		this.splitRegex = splitRegex;
		this.successString = successString;
		this.splitPartIndex = splitPartIndex;
	}

	/**
	 * @see com.leohart.buildwhisperer.repositories.rss.SyndEntryImplBuildStatusExtractor#getBuildStatus(com.sun.syndication.feed.synd.SyndEntryImpl)
	 */
	public BuildStatus getBuildStatus(final SyndEntryImpl entry) {
		if (entry.getTitle().split(this.splitRegex)[this.splitPartIndex]
				.equals(this.successString)) {
			return new SimpleBuildStatus(true);
		}

		return new SimpleBuildStatus(false);
	}

}
