package com.cdn.bootstrap;

import com.cdn.utils.CommonUtil;
import com.cdn.utils.StringUtil;

import java.io.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.cdn.commons.Constants.*;

/**
 * @Author: zerongliu
 * @Date: 4/6/19 15:13
 * @Description: provide the user interface to query logs
 */
public class LogQueryer {
    /**
     * first level: IP
     * second level: cpu_id
     * thrid level: timestamp
     */
    private static Map<String, Map<String, TreeMap<Long, Integer>>> logMap = new HashMap<>();

    /**
     * template of the output string
     */
    private static final String TEMPLATE = "(" + "{0}" + COMMA + "{1}%" + ")";

    /**
     * template of the header
     */
    private static final String HEADER_TEMPLATE = "CPU" + "{0} usage on {1}:" + LINE_SEPARATOR;

    /**
     * date format for the input condition
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM);

    public static void main(String[] args) {
        String dataPath;
        if (args.length > 0) {
            dataPath = args[0];
        } else {
            System.out.println("please input the valid file path");
            return;
        }
        File inputFile = new File(dataPath);
        if (!inputFile.exists()) {
            System.out.println("the path of log data does not exist");
        } else {
            initialLogMap(inputFile);
            //accept the input command from console
            System.out.println("please input your command");
            Scanner cmdScanner = new Scanner(System.in);
            CommandParser commandParser = readConsoleCommand(cmdScanner, new CommandParser());
            while (!EXIT_CMD.equals(commandParser.getCommand())) {
                if (commandParser.getCommand() != null) {
                    switch (commandParser.getCommand()) {
                        case QUERY_CMD:
                            try {
                                String message = validateParameter(commandParser.getIp(), commandParser.getCpuID(), commandParser.getStartTime(), commandParser.getEndTime());
                                if (!"".equals(message)) {
                                    System.out.println(message);
                                } else {
                                    long start = System.currentTimeMillis();
                                    queryLog(commandParser.getIp(), commandParser.getCpuID(), commandParser.getStartTime(), commandParser.getEndTime());
                                    System.out.println("the time cost of current query is: " + (System.currentTimeMillis() - start) / 1000);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                System.out.println("the format of input start time or end time is invalid, it should be " + YYYY_MM_DD_HH_MM);
                            }
                            break;
                        default:
                            System.out.println("the input command should be one of the commands: QUERY, EXIT!");
                            break;
                    }
                }
                commandParser = readConsoleCommand(cmdScanner, commandParser);
            }
            System.out.println("Bye Bye!");
            //accept the exit command
            cmdScanner.close();
        }

    }

    /**
     * validate the input parameters
     *
     * @param ip        ip of host
     * @param cpuID     no. of cpu
     * @param startTime start time
     * @param endTime   end time
     * @return if the parameters are valid, return empty string
     */
    private static String validateParameter(String ip, String cpuID, String startTime, String endTime) {
        StringBuilder buffer = new StringBuilder("");
        if (StringUtil.isEmpty(ip)) {
            buffer.append("input ip could not be empty");
        } else if (!CommonUtil.isIP(ip)) {
            buffer.append("input ip is invalid");
        }
        if (buffer.length() > 0) {
            buffer.append(LINE_SEPARATOR);
        }
        if (StringUtil.isEmpty(cpuID)) {
            buffer.append("input cpu id could not be empty");
        } else if (!CommonUtil.isCPU(cpuID)) {
            buffer.append("input cpu_id must be 0 or 1");
        }
        if (buffer.length() > 0) {
            buffer.append(LINE_SEPARATOR);
        }
        if (StringUtil.isEmpty(startTime)) {
            buffer.append("input start time could not be empty");
        } else {
            try {
                dateFormat.parse(startTime);
            } catch (ParseException e) {
                buffer.append("the format of the start time is illegal, it should be: " + YYYY_MM_DD_HH_MM);
            }
        }
        if (buffer.length() > 0) {
            buffer.append(LINE_SEPARATOR);
        }
        if (StringUtil.isEmpty(endTime)) {
            buffer.append("input end time could not be empty");
        } else {
            try {
                dateFormat.parse(endTime);
            } catch (ParseException e) {
                buffer.append("the format of the end time is illegal, it should be: " + YYYY_MM_DD_HH_MM);
            }
        }
        if (!StringUtil.isEmpty(startTime) && !StringUtil.isEmpty(endTime)) {
            try {
                if (!dateFormat.parse(endTime).after(dateFormat.parse(startTime))) {
                    buffer.append("the end time could not before start time!");
                }
            } catch (ParseException e) {
                System.out.println("the format of the end time or start time is illegal, it should be: " + YYYY_MM_DD_HH_MM);
            }
        }
        return buffer.toString();
    }

    /**
     * read command from the console and parse the input command
     *
     * @param cmdScanner    input source
     * @param commandParser command parser
     * @return command parser
     */
    private static CommandParser readConsoleCommand(Scanner cmdScanner, CommandParser commandParser) {
        System.out.printf(">");
        String commandLine = cmdScanner.nextLine();
        commandParser.setCommandLine(commandLine);
        commandParser = commandParser.invoke();
        return commandParser;
    }

    /**
     * query logs according to the input conditions
     *
     * @param ip        ip of the host
     * @param cpuID     no. of cpu
     * @param startTime start time
     * @param endTime   end time
     */
    private static void queryLog(String ip, String cpuID, String startTime, String endTime) throws ParseException {
        long startTimestamp = dateFormat.parse(startTime).getTime() / 1000;
        long endTimestamp = dateFormat.parse(endTime).getTime() / 1000;
        if (logMap.containsKey(ip)) {
            Map<String, TreeMap<Long, Integer>> cpuMap = logMap.get(ip);
            if (cpuMap.containsKey(cpuID)) {
                StringBuilder buffer = new StringBuilder("");
                buffer.append(MessageFormat.format(HEADER_TEMPLATE, cpuID, ip));
                TreeMap<Long, Integer> usageMap = cpuMap.get(cpuID);
                SortedMap<Long, Integer> sortedMap = usageMap.subMap(startTimestamp, endTimestamp);
                Date currentDate;
                int counter = 0;
                for (Map.Entry<Long, Integer> item :
                        sortedMap.entrySet()) {
                    currentDate = new Date(item.getKey() * 1000);
                    buffer.append(MessageFormat.format(TEMPLATE, dateFormat.format(currentDate), item.getValue()));
                    buffer.append(COMMA);
                    counter++;
                    //beautify the output
                    if (counter % 4 == 0) {
                        buffer.append(LINE_SEPARATOR);
                    }
                }
                if (buffer.length() > 0 && counter > 0) {
                    if (buffer.lastIndexOf(COMMA) != -1) {
                        buffer.deleteCharAt(buffer.lastIndexOf(COMMA));
                    }
                    System.out.println(buffer.toString());
                } else {
                    System.out.println("there is no log records from " + startTime + " to " + endTime + " for this cpu: " + cpuID + " on this machine: " + ip);
                }
            } else {
                System.out.println("there is no log records for this cpu: " + cpuID + " on this machine: " + ip);
            }
        } else {
            System.out.println("there is no log records for this machine: " + ip);
        }

    }

    /**
     * parse the log file and initiate the log map
     *
     * @param inputFile path of input file, directory or common file
     */
    private static void initialLogMap(File inputFile) {
        List<File> fileList = new ArrayList<>();
        if (inputFile.isDirectory() && inputFile.listFiles() != null) {//it is a directory
            fileList = Arrays.asList(inputFile.listFiles());
        } else {//it is just one file
            fileList.add(inputFile);
        }
        //initiate memory map to store the log records
        if (fileList.size() > 0) {
            BufferedReader reader = null;
            for (File logFile : fileList) {
                try {
                    reader = openFile(logFile);
                    String rowContent;
                    rowContent = reader.readLine();
                    //check the header to determine whether it is a legal log file, if it is illegal, skip this file
                    if (!validateLogFile(rowContent)) {
                        System.out.println("the format of the file: " + logFile.getName() + " is illegal, skip it");
                        continue;
                    }
                    //parse log
                    LogParser logParser;
                    while ((rowContent = reader.readLine()) != null && rowContent.trim().length() > 0) {
                        logParser = new LogParser(rowContent).invoke();
                        //get the timestamp
                        long timestamp = logParser.getTimestamp();
                        //get the usage of cpu at current timestamp
                        int usage = logParser.getUsage();
                        //get the ip of host
                        String ip = logParser.getIp();
                        //get the no. of cpu
                        String cpu = logParser.getCpu();
                        //get the cpu map of this ip
                        Map<String, TreeMap<Long, Integer>> cpuMap = logMap.getOrDefault(ip, new HashMap<>());
                        //get the usage map of this cpu
                        TreeMap<Long, Integer> usageMap = cpuMap.getOrDefault(cpu, new TreeMap<>());
                        //store the cpu usage
                        usageMap.put(timestamp, usage);
                        //put into cpu map
                        cpuMap.put(cpu, usageMap);
                        //put into log map
                        logMap.put(ip, cpuMap);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("could not find file " + logFile.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("read file: " + logFile.getName() + " error");
                } finally {
                    closeReader(reader, logFile.getName());
                }
            }
            System.out.println("parse log files successful!");
        }
    }

    /**
     * validate the log file by the header of the log
     *
     * @param header header of the log file
     * @return true if it is valid, false if it is invalid
     */
    private static boolean validateLogFile(String header) {
        return header.startsWith(COL_TIMESTAMP);
    }

    /**
     * open the log and return reader of current file
     *
     * @param logFile file of log
     * @return reader of the log file
     * @throws FileNotFoundException could not find such file
     */
    private static BufferedReader openFile(File logFile) throws FileNotFoundException {
        return new BufferedReader(new FileReader(logFile));
    }

    /**
     * close the file reader
     *
     * @param reader      file reader
     * @param logFileName name of the log file
     */
    private static void closeReader(BufferedReader reader, String logFileName) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("close file: " + logFileName + " error!");
            }
        }
    }
}
