package com.cdn.commons;

/**
 * @Author: zerongliu
 * @Date: 4/6/19 20:15
 * @Description: store the constants
 */
public interface Constants {

    /**
     * symbol of line break
     */
    String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * symbol of column separator
     */
    String COL_SEPARATOR = "     ";
    /**
     * suffix of log file
     */
    String LOG_SUFFIX = ".log";
    /**
     * root path of current directory
     */
    String ROOT_PATH = System.getProperty("user.dir");

    /**
     * format of log date
     */
    String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * header of timestamp
     */
    String COL_TIMESTAMP = "timestamp";
    /**
     * header of ip
     */
    String COL_IP = "IP";
    /**
     * header of cpu_id
     */
    String COL_CPU_ID = "cpu_id";
    /**
     * header of cpu usage
     */
    String COL_USAGE = "usage";
    /**
     * ip of the server
     */
    String IP = "192.168.1.10";
    /**
     * no. of first cpu
     */
    String CPU_ONE = "0";
    /**
     * no. of second cpu
     */
    String CPU_TWO = "1";
    /**
     * length of the column named timestamp
     */
    Integer COL_TIMESTAMP_LEN = 10;
    /**
     * length of the column named cpu_id
     */
    Integer COL_CPU_ID_LEN = 6;
    /**
     * length of the column named ip
     */
    Integer COL_IP_LEN = 16;
    /**
     * length of the column named usage
     */
    Integer COL_USAGE_LEN = 5;

    Integer COL_SEPARATOR_LEN = COL_SEPARATOR.length();
    /**
     * format string indicate align left
     */
    String ALIGN_LEFT = "%-";
    /**
     * suffix of format string
     */
    String FORMAT_SUFFIX = "s";

    /**
     * the format of time_start and time_end in the query parameters
     */
    String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    /**
     * comma symbol
     */
    String COMMA = ",";
    /**
     * QUERY COMMAND
     */
    String QUERY_CMD = "QUERY";
    /**
     * EXIT COMMAND
     */
    String EXIT_CMD = "EXIT";
    /**
     * Space
     */
    String SPACE = " ";

}
