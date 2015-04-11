package com.leohart.buildwhisperer.repositories.rss;

import com.leohart.buildwhisperer.status.BuildStatus;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author A122695
 */
public interface SyndEntryImplBuildStatusExtractor {

	BuildStatus getBuildStatus(SyndEntryImpl entry);

}
