package com.leohart.buildwhisperer.repositories.rss;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import com.leohart.buildwhisperer.repositories.BuildStatusRepository;
import com.leohart.buildwhisperer.repositories.BuildStatusRepositoryException;
import com.leohart.buildwhisperer.status.BuildStatus;
import com.leohart.buildwhisperer.status.SimpleBuildStatus;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * Reads an RSS feed, looks at the latest entry and uses a
 * SyndEntryImplBuildStatusExtractor to determine BuildStatus.
 * 
 * @author Leo Hart
 */
public class RssBuildStatusRepository implements
		BuildStatusRepository<BuildStatus> {

	private final URL rssUrl;
	private SyndEntryImplBuildStatusExtractor buildStatusExtractor;

	/**
	 * @param rssUrlString
	 */
	public RssBuildStatusRepository(final URL rssUrl) {
		super();
		this.rssUrl = rssUrl;
	}

	/**
	 * @param rssUrlString
	 * @param failString
	 */
	public RssBuildStatusRepository(final URL rssUrl,
			final SyndEntryImplBuildStatusExtractor buildStatusExtractor) {
		super();
		this.rssUrl = rssUrl;
		this.buildStatusExtractor = buildStatusExtractor;
	}

	/**
	 * @see com.com.leohart.buildwhisperer.repositories.BuildStatusRepository#getBuildStatus()
	 */
	public BuildStatus getBuildStatus() throws BuildStatusRepositoryException {
		try {
			SyndFeed feed = this.getFeed();

			if (this.feedHasFailures(feed)) {
				return new SimpleBuildStatus(false);
			}

			return new SimpleBuildStatus(true);
		}
		catch (Exception ex) {
			throw new BuildStatusRepositoryException(
					"There was a problem accessing the feed: ", ex);
		}
	}

	private boolean feedHasFailures(final SyndFeed feed) {
		SyndEntryImpl entry = this.getLatestEntry(feed);

		if (!this.buildStatusExtractor.getBuildStatus(entry).isSuccessful()) {
			return true;
		}

		return false;
	}

	private SyndFeed getFeed() throws FeedException, IOException {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(this.rssUrl));

		return feed;
	}

	@SuppressWarnings("unchecked")
	private SyndEntryImpl getLatestEntry(final SyndFeed feed) {
		List<SyndEntryImpl> entries = feed.getEntries();

		Collections.sort(entries, Collections
				.reverseOrder(new SyndEntryImplPublishedDateComparator()));

		return entries.get(0);
	}

}
