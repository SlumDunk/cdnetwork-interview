package com.cdn.bootstrap;

import static com.cdn.commons.Constants.SPACE;

/**
 * @Author: zerongliu
 * @Date: 4/6/19 22:43
 * @Description: class for parsing input command
 */
public class CommandParser {
    /**
     * input command line
     */
    private String commandLine;
    /**
     * command
     */
    private String command;
    /**
     * ip
     */
    private String ip;
    /**
     * no. of cpu
     */
    private String cpuID;
    /**
     * start time
     */
    private String startTime;
    /**
     * end time
     */
    private String endTime;

    /**
     * @return return command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @return return ip in parameter
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return return no. of cpu in parameter
     */
    public String getCpuID() {
        return cpuID;
    }

    /**
     * @return return the start time in parameter
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @return return the end time in parameter
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param commandLine set the command line
     */
    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public CommandParser() {

    }

    public CommandParser(String commandLine) {
        this.commandLine = commandLine;
    }

    /**
     * parse the input command line
     *
     * @return
     */
    public CommandParser invoke() {
        resetConditions();
        String[] arrCommand = commandLine.trim().split(SPACE);
        int len = arrCommand.length;
        if (len >= 1) {
            command = arrCommand[0].toUpperCase();
        }

        ip = arrCommand.length > 1 ? arrCommand[1].trim() : null;
        cpuID = arrCommand.length > 2 ? arrCommand[2].trim() : null;
        startTime = arrCommand.length > 4 ? arrCommand[3].trim() + SPACE + arrCommand[4].trim() : null;
        endTime = arrCommand.length > 6 ? arrCommand[5].trim() + SPACE + arrCommand[6].trim() : null;

        return this;
    }

    /**
     * reset the query conditions before parse the new command line
     */
    private void resetConditions() {
        this.command = null;
        this.ip = null;
        this.cpuID = null;
        this.startTime = null;
        this.endTime = null;
    }
}
