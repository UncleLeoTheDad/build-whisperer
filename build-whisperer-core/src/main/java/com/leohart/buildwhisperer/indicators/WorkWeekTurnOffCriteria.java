package com.leohart.buildwhisperer.indicators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;

/**
 * @author Leo Hart
 *         <p>
 *         Simple BuildStatusIndicatorTurnOffCriteria for 7-7 M-F (default)
 *         work-week.
 *         </p>
 */
public class WorkWeekTurnOffCriteria implements
		BuildStatusIndicatorTurnOffCriteria {

	private static final Log LOG = LogFactory
			.getLog(WorkWeekTurnOffCriteria.class);

	public static final int DEFAULT_START_HOUR_OF_DAY = 7;
	public static final int DEFAULT_END_HOUR_OF_DAY = 19;
	public static final int DEFAULT_WORK_WEEK_START_DAY = DateTimeConstants.MONDAY;
	public static final int DEFAULT_WORK_WEEK_END_DAY = DateTimeConstants.FRIDAY;

	private int workDayStartHour = DEFAULT_START_HOUR_OF_DAY;
	private int workDayEndHour = DEFAULT_END_HOUR_OF_DAY;
	private int workWeekStartDay = DEFAULT_WORK_WEEK_START_DAY;
	private int workWeekEndDay = DEFAULT_WORK_WEEK_END_DAY;

	/**
	 * @param endHourOfDay
	 *            The hour a work-day ends.
	 */
	public void setWorkDayEndHour(final int endHourOfDay) {
		this.workDayEndHour = endHourOfDay;
	}

	/**
	 * @param startHourOfDay
	 *            The hour that a work-day starts.
	 */
	public void setWorkDayStartHour(final int startHourOfDay) {
		this.workDayStartHour = startHourOfDay;
	}

	/**
	 * @param workWeekEndDay
	 *            The day of the week on which the work-week ends.
	 * @see org.joda.time.DateTimeConstants
	 */
	public void setWorkWeekEndDay(final int workWeekEndDay) {
		this.workWeekEndDay = workWeekEndDay;
	}

	/**
	 * @param workWeekStartDay
	 *            The day of the week on which the work-week begins.
	 * @see org.joda.time.DateTimeConstants
	 */
	public void setWorkWeekStartDay(final int workWeekStartDay) {
		this.workWeekStartDay = workWeekStartDay;
	}

	/**
	 * @see com.leohart.buildwhisperer.indicators.BuildStatusIndicatorTurnOffCriteria#shouldTurnOff()
	 */
	@Override
	public boolean shouldTurnOff() {
		LOG.debug("Determining whether to turn indicator off based on work-week.");

		LocalDateTime now = new LocalDateTime();
		
		LocalDateTime workWeekStart = now.withDayOfWeek(workWeekStartDay)
										 .withHourOfDay(workDayStartHour)
										 .withMinuteOfHour(0)
										 .withSecondOfMinute(0)
										 .withMillisOfSecond(0);
		
		LocalDateTime workWeekEnd = now.withDayOfWeek(workWeekEndDay)
									   .withHourOfDay(workDayEndHour)
									   .withMinuteOfHour(0)
									   .withSecondOfMinute(0)
									   .withMillisOfSecond(0);
		
		Interval workWeek = new Interval(workWeekStart.toDateTime(), workWeekEnd.toDateTime());
		LOG.info("Workweek: " + workWeek);		
		
		if (workWeek.contains(now.toDateTime())) {

			LOG.info("Device should not be turned off @" + now);
			return false;
		}

		LOG.info("Device should be turned off @" +  now);
		return true;
	}

	@Override
	public String toString() {
		return String
				.format("WorkWeekTurnOffCriteria [workWeekStartsAt %s@%s and workWeekEndsAt %s@%s]",
						this.workWeekStartDay, this.workDayStartHour,
						this.workWeekEndDay, this.workDayEndHour);
	}

}
