package com.leohart.buildwhisperer.indicators;

import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Leo Hart
 */
public class WorkWeekTurnOffCriteria_shouldTurnOffTests {

	WorkWeekTurnOffCriteria criteria;

	@Before
	public void setUp() throws Exception {
		this.criteria = new WorkWeekTurnOffCriteria();
	}

	@Test
	public void shouldNotTurnOffIfCurrentTimeIsDuringWorkingHours() {
		LocalDateTime time = new LocalDateTime().withDayOfWeek(
				WorkWeekTurnOffCriteria.DEFAULT_WORK_WEEK_START_DAY)
				.withHourOfDay(
						WorkWeekTurnOffCriteria.DEFAULT_START_HOUR_OF_DAY);

		DateTimeUtils.setCurrentMillisFixed(time.toDateTime().toInstant()
				.getMillis());

		Assert.assertFalse(String.format(
				"Should have been false for Day %s, Hour %s: ",
				WorkWeekTurnOffCriteria.DEFAULT_WORK_WEEK_START_DAY,
				WorkWeekTurnOffCriteria.DEFAULT_START_HOUR_OF_DAY),
				this.criteria.shouldTurnOff());
	}

	@Test
	public void shouldTurnOffIfCurrentTimeIsOutsideWorkingHours() {
		LocalDateTime time = new LocalDateTime().withDayOfWeek(
				WorkWeekTurnOffCriteria.DEFAULT_WORK_WEEK_END_DAY)
				.withHourOfDay(
						WorkWeekTurnOffCriteria.DEFAULT_END_HOUR_OF_DAY);

		DateTimeUtils.setCurrentMillisFixed(time.toDateTime().toInstant()
				.getMillis());

		Assert.assertTrue(String.format(
				"Should have been true for Day %s, Hour %s: ",
				WorkWeekTurnOffCriteria.DEFAULT_WORK_WEEK_END_DAY,
				WorkWeekTurnOffCriteria.DEFAULT_END_HOUR_OF_DAY),
				this.criteria.shouldTurnOff());
	}

}
