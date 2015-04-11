package com.leohart.buildwhisperer.repositories.rss;

import java.util.Comparator;

import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author Leo Hart
 */
public class SyndEntryImplPublishedDateComparator implements
		Comparator<SyndEntryImpl> {

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(final SyndEntryImpl o1, final SyndEntryImpl o2) {
		return o1.getPublishedDate().compareTo(o2.getPublishedDate());
	}

}
