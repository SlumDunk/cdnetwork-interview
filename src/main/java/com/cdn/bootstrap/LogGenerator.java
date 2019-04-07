package com.cdn.bootstrap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Random;

import static com.cdn.commons.Constants.*;

/**
 * @Author: zerongliu
 * @Date: 4/6/19 11:55
 * @Description: generate a log for cpu usage every minute
 */
public class LogGenerator {
    /**
     * the default date of the log
     */
    private static final String DEFAULT_DATE = "2014-10-31";

    /**
     * the default name of output File
     */
    private static final String DEFAULT_OUTPUT_FILE_NAME = DEFAULT_DATE + LOG_SUFFIX;
    /**
     * the default ouput path of the log
     */
    private static final String DEFAULT_OUTPUT_FILE_PATH = ROOT_PATH + File.separator;

    /**
     * template of each record of log
     */
    private static final String TEMPLATE = "{0}" + COL_SEPARATOR + "{1}" + COL_SEPARATOR + "{2}" + COL_SEPARATOR + "{3}" + LINE_SEPARATOR;

    /**
     * default data path
     */
    private static final String DEFAULT_DATA_PATH = DEFAULT_OUTPUT_FILE_PATH;
    /**
     * a random number generator
     */
    private static Random random = new Random();

    public static void main(String[] args) {
        //day of log
        String dateStr = DEFAULT_DATE;
        //the data path of the log files
        String dataPath = DEFAULT_DATA_PATH;
        //the name of the output file
        String outputFileName = DEFAULT_OUTPUT_FILE_NAME;
        //read the data path from arguments
        if (args.length >= 1 && args[0] != null) {
            dataPath = args[0];
        } else {
            System.out.println("the input data path is empty, so use the default data path:" + dataPath);
        }
        //read the date from arguments, if the input date is null, use the default date
        if (args.length > 1 && args[1] != null) {
            dateStr = args[1];
            outputFileName = dateStr + LOG_SUFFIX;
        } else {
            System.out.println("generate log for default date:" + DEFAULT_DATE);
        }
        //parse the in date string to get the start timestamp and end timestamp
        TimePair timePair;
        try {
            timePair = new TimePair(dateStr).invoke();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("parse date error");
            return;
        }
        long startTime = timePair.getStartTime();
        long endTime = timePair.getEndTime();
        //write log records into file
        BufferedWriter writer = null;
        try {
            writer = getFileWriter(dataPath, outputFileName);
            generateHeader(writer);
            generateLog(startTime, endTime, writer);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("write logs to file error!");
        } finally {
            closeWriter(writer);
        }
        System.out.println("generate logs for " + dateStr + " successful!");

    }

    /**
     * close the writer
     *
     * @param writer the writer of the output file
     */
    private static void closeWriter(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("close output file stream error!");
            }
        }
    }

    /**
     * generate the header of the log
     *
     * @param writer writer of th output file
     * @throws IOException write file exception
     */
    private static void generateHeader(BufferedWriter writer) throws IOException {
        StringBuilder buffer = new StringBuilder("");
        //write the header into the file
        buffer.append(MessageFormat.format(TEMPLATE, String.format(ALIGN_LEFT + COL_TIMESTAMP_LEN + FORMAT_SUFFIX, COL_TIMESTAMP), String.format(ALIGN_LEFT + COL_IP_LEN + FORMAT_SUFFIX, COL_IP), String.format(ALIGN_LEFT + COL_CPU_ID_LEN + FORMAT_SUFFIX, COL_CPU_ID), String.format(ALIGN_LEFT + COL_USAGE_LEN + FORMAT_SUFFIX, COL_USAGE)));
        writer.write(buffer.toString());
        writer.flush();
    }

    /**
     * generate the log records every minute
     *
     * @param startTimestamp start timestamp
     * @param endTimestamp   end timestamp
     * @param writer         output writer
     * @throws IOException write ouput file exception
     */
    private static void generateLog(long startTimestamp, long endTimestamp, BufferedWriter writer) throws IOException {
        //generate one log record every minute
        int counter = 0;
        StringBuilder buffer = new StringBuilder("");
        while (startTimestamp < endTimestamp) {
            //clear the string last time
            buffer.delete(0, buffer.length());
            buffer.append(MessageFormat.format(TEMPLATE, String.format(ALIGN_LEFT + COL_TIMESTAMP_LEN + FORMAT_SUFFIX, startTimestamp + ""), String.format(ALIGN_LEFT + COL_IP_LEN + FORMAT_SUFFIX, IP), String.format(ALIGN_LEFT + COL_CPU_ID_LEN + FORMAT_SUFFIX, CPU_ONE), String.format(ALIGN_LEFT + COL_USAGE_LEN + FORMAT_SUFFIX, random.nextInt(100))));
            buffer.append(MessageFormat.format(TEMPLATE, String.format(ALIGN_LEFT + COL_TIMESTAMP_LEN + FORMAT_SUFFIX, startTimestamp + ""), String.format(ALIGN_LEFT + COL_IP_LEN + FORMAT_SUFFIX, IP), String.format(ALIGN_LEFT + COL_CPU_ID_LEN + FORMAT_SUFFIX, CPU_TWO), String.format(ALIGN_LEFT + COL_USAGE_LEN + FORMAT_SUFFIX, random.nextInt(100))));
            writer.write(buffer.toString());
            counter++;
            //flush to file every 100 times
            if (counter == 100) {
                writer.flush();
            }
            startTimestamp += 60;
        }
    }

    /**
     * get the writer for the output file
     *
     * @param dataPath       path of the output file
     * @param outputFileName name of the output file
     * @return writer of the output file
     * @throws IOException open file writer exception
     */
    private static BufferedWriter getFileWriter(String dataPath, String outputFileName) throws IOException {
        File outputFile = new File(dataPath + outputFileName);
        //delete the old file if it exists
        if (outputFile.exists()) {
            outputFile.delete();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        return writer;
    }
}
