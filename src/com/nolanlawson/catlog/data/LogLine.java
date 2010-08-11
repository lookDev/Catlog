package com.nolanlawson.catlog.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.nolanlawson.catlog.util.UtilLogger;


public class LogLine {

	private static Pattern logPattern = Pattern.compile("(\\w)/([^(]+)\\(\\s*(\\d+)\\): (.*)");
	
	private static UtilLogger log = new UtilLogger(LogLine.class);
	
	private String originalLine;
	private int logLevel;
	private String tag;
	private String logOutput;
	private int processId;
	private boolean expanded = false;
	
	public String getOriginalLine() {
		return originalLine;
	}
	public void setOriginalLine(String originalLine) {
		this.originalLine = originalLine;
	}
	public int getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLogOutput() {
		return logOutput;
	}
	public void setLogOutput(String logOutput) {
		this.logOutput = logOutput;
	}
	
	
	public int getProcessId() {
		return processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	
	
	
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public static LogLine newLogLine(String originalLine) {
		
		//log.d("originalLine is: " + originalLine);
		
		LogLine logLine = new LogLine();
		logLine.setOriginalLine(originalLine);
		
		Matcher matcher = logPattern.matcher(originalLine);
		
		if (matcher.matches()) {
			char logLevelChar = matcher.group(1).charAt(0);
			
			logLine.setLogLevel(convertCharToLogLevel(logLevelChar));
			logLine.setTag(matcher.group(2));
			logLine.setProcessId(Integer.parseInt(matcher.group(3)));
			logLine.setLogOutput(matcher.group(4));
			
			
		} else {
			log.e("Line doesn't match pattern: " + originalLine);
		}
		
		return logLine;
		
	}
	public static int convertCharToLogLevel(char logLevelChar) {
		
		int logLevel = -1;
		switch (logLevelChar) {
		case 'D':
			logLevel = Log.DEBUG;
			break;
		case 'E':
			logLevel = Log.ERROR;
			break;
		case 'I':
			logLevel = Log.INFO;
			break;
		case 'V':
			logLevel = Log.VERBOSE;
			break;
		case 'W':
			logLevel = Log.WARN;
			break;
		}
		// TODO: wtf log level in 2.2
		return logLevel;
	}
	
	public static char convertLogLevelToChar(int logLevel) {
		
		char result = 'X';
		switch (logLevel) {
		case Log.DEBUG:
			result = 'D';
			break;
		case Log.ERROR:
			result = 'E';
			break;
		case Log.INFO:
			result = 'I';
			break;
		case Log.VERBOSE:
			result = 'V';
			break;
		case Log.WARN:
			result = 'W';
			break;
		}
		// TODO: wtf log level in 2.2
		return result;
	}	
}
