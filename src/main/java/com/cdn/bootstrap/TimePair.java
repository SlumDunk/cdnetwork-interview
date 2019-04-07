package com.cdn.bootstrap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.cdn.commons.Constants.YYYY_MM_DD;

/**
 * @Author: zerongliu
 * @Date: 4/6/19 21:21
 * @Description: a class store the start timestamp and end timestamp of a day
 */
public class TimePair {
    /**
     * String of date
     */
    private String dateStr;
    /**
     * the start timestamp
     */
    private long startTime;
    /**
     * the end timestamp
     */
    private long endTime;

    public TimePair(String dateStr) {
        this.dateStr = dateStr;
    }

    /**
     * get the start time
     *
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * get the end time
     *
     * @return
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * parse the string of the date and get the start time and end time of the log
     *
     * @return
     * @throws ParseException
     */
    public TimePair invoke() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Date date = dateFormat.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        startTime = calendar.getTimeInMillis() / 1000;
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        endTime = calendar.getTimeInMillis() / 1000;

        return this;
    }
}
