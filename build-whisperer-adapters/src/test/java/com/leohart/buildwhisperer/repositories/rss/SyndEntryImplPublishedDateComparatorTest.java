package com.leohart.buildwhisperer.repositories.rss;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leohart.buildwhisperer.repositories.rss.SyndEntryImplPublishedDateComparator;
import com.sun.syndication.feed.synd.SyndEntryImpl;

/**
 * @author Leo Hart
 */
public class SyndEntryImplPublishedDateComparatorTest {

	SyndEntryImpl entry1;
	SyndEntryImpl entry2;
	SyndEntryImplPublishedDateComparator comparator;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.entry1 = createMock(SyndEntryImpl.class);
		this.entry2 = createMock(SyndEntryImpl.class);

		this.comparator = new SyndEntryImplPublishedDateComparator();

	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.repositories.rss.SyndEntryImplPublishedDateComparator#compare(com.sun.syndication.feed.synd.SyndEntryImpl, com.sun.syndication.feed.synd.SyndEntryImpl)}
	 * .
	 */
	@Test
	public void testCompareEqual() {
		Date equal = new Date(1);

		expect(this.entry1.getPublishedDate()).andReturn(equal);
		expect(this.entry2.getPublishedDate()).andReturn(equal);

		replay(this.entry1);
		replay(this.entry2);

		Assert.assertEquals("Dates should have been found equal: ", 0,
				this.comparator.compare(this.entry1, this.entry2));
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.repositories.rss.SyndEntryImplPublishedDateComparator#compare(com.sun.syndication.feed.synd.SyndEntryImpl, com.sun.syndication.feed.synd.SyndEntryImpl)}
	 * .
	 */
	@Test
	public void testCompareGreater() {
		Date earlier = new Date(1);
		Date later = new Date(2);

		expect(this.entry1.getPublishedDate()).andReturn(earlier);
		expect(this.entry2.getPublishedDate()).andReturn(later);

		replay(this.entry1);
		replay(this.entry2);

		Assert.assertEquals(
				"First entry should have been found less than second: ", -1,
				this.comparator.compare(this.entry1, this.entry2));
	}

	/**
	 * Test method for
	 * {@link com.leohart.buildwhisperer.repositories.rss.SyndEntryImplPublishedDateComparator#compare(com.sun.syndication.feed.synd.SyndEntryImpl, com.sun.syndication.feed.synd.SyndEntryImpl)}
	 * .
	 */
	@Test
	public void testCompareLesser() {
		Date earlier = new Date(1);
		Date later = new Date(2);

		expect(this.entry1.getPublishedDate()).andReturn(later);
		expect(this.entry2.getPublishedDate()).andReturn(earlier);

		replay(this.entry1);
		replay(this.entry2);

		Assert.assertEquals(
				"First entry should have been found greater than second: ", 1,
				this.comparator.compare(this.entry1, this.entry2));
	}

}
