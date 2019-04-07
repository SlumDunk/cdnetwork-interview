package com.cdn.bootstrap;

import static com.cdn.commons.Constants.*;

/**
 * @Author: zerongliu
 * @Date: 4/6/19 22:10
 * @Description: parser for parsing each row of log
 */
public class LogParser {
    /**
     * content of log
     */
    private String logContent;
    /**
     * time stamp
     */
    private long timestamp;
    /**
     * ip of the host
     */
    private String ip;
    /**
     * number of cpu
     */
    private String cpu;
    /**
     * percent of cpu usage
     */
    private int usage;

    public LogParser(String logContent) {
        this.logContent = logContent;
    }

    /**
     * @return return timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return return ip of the host
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return return number of cpu
     */
    public String getCpu() {
        return cpu;
    }

    /**
     * @return return usage of cpu
     */
    public int getUsage() {
        return usage;
    }

    /**
     * parse the log content
     *
     * @return
     */
    public LogParser invoke() {
        int startIndex = 0;
        timestamp = Long.parseLong(logContent.substring(startIndex, startIndex += COL_TIMESTAMP_LEN).trim());
        startIndex += COL_SEPARATOR_LEN;
        ip = logContent.substring(startIndex, startIndex += COL_IP_LEN).trim();
        startIndex += COL_SEPARATOR_LEN;
        cpu = logContent.substring(startIndex, startIndex += COL_CPU_ID_LEN).trim();
        startIndex += COL_SEPARATOR_LEN;
        usage = Integer.parseInt(logContent.substring(startIndex, logContent.length()).trim());
        return this;
    }
}
