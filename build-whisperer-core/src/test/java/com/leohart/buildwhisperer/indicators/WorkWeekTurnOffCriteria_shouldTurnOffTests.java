package com.leohart.buildwhisperer.indicators;

import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.PeriodType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Leo Hart
 */
public class WorkWeekTurnOffCriteria_shouldTurnOffTests {

	WorkWeekTurnOffCriteria criteria;
	
	private static final Integer MONDAY = 1;
	private static final Integer FRIDAY = 5;
	private static final Integer SEVEN_AM = 7;
	private static final Integer SIX_AM = 6;
	private static final Integer SEVEN_PM = 19;
	private static final Integer FIFTY_NINE_MINUTES = 59;
	private static final Integer ONE_MINUTE = 1;

	@Before
	public void setUp() throws Exception {
		this.criteria = new WorkWeekTurnOffCriteria();
	}

	@Test
	public void shouldNotTurnOffIfCurrentTimeIsDuringWorkingHours() {
		this.criteria.setWorkWeekStartDay(MONDAY);
		this.criteria.setWorkWeekEndDay(FRIDAY);
		this.criteria.setWorkDayStartHour(SEVEN_AM);
		this.criteria.setWorkDayEndHour(SEVEN_PM);
		
		setCurrentTime(
				new LocalDateTime().withDayOfWeek(MONDAY)
									.withHourOfDay(SEVEN_AM) //Just as work starts
									.withMinuteOfHour(0)
									.withSecondOfMinute(0)
									.withMillisOfSecond(0));
		
		Assert.assertFalse(String.format(
				"Should have been left on (false) for current time %s and criteria %s: ",
				DateTimeUtils.getPeriodType(PeriodType.dayTime()), this.criteria),
				this.criteria.shouldTurnOff());
	}

	private void setCurrentTime(LocalDateTime timeToSet) {
		DateTimeUtils.setCurrentMillisFixed(timeToSet.toDateTime().toInstant()
				.getMillis());
	}

	@Test
	public void shouldTurnOffIfCurrentTimeIsOutsideWorkingHours() {
		this.criteria.setWorkWeekStartDay(MONDAY);
		this.criteria.setWorkWeekEndDay(FRIDAY);
		this.criteria.setWorkDayStartHour(SEVEN_AM);
		this.criteria.setWorkDayEndHour(SEVEN_PM);
		
		setCurrentTime(
				new LocalDateTime().withDayOfWeek(MONDAY)
									.withHourOfDay(SIX_AM)
									.withMinuteOfHour(FIFTY_NINE_MINUTES)
									.withSecondOfMinute(0)
									.withMillisOfSecond(0)); //Just before work starts

		Assert.assertTrue(String.format(
				"Should have been turned off (true) for current time %s and criteria %s: ",
				DateTimeUtils.getPeriodType(PeriodType.dayTime()), this.criteria),
				this.criteria.shouldTurnOff());
		
		setCurrentTime(
				new LocalDateTime().withDayOfWeek(FRIDAY)
									.withHourOfDay(SEVEN_PM)
									.withMinuteOfHour(ONE_MINUTE)
									.withSecondOfMinute(0)
									.withMillisOfSecond(0)); //Just before work starts

		Assert.assertTrue(String.format(
				"Should have been turned off (true) for current time %s and criteria %s: ",
				DateTimeUtils.getPeriodType(PeriodType.dayTime()), this.criteria),
				this.criteria.shouldTurnOff());
	}

}
