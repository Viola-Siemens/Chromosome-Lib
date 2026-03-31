package com.hexagram2021.chromosomelib.common.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.hexagram2021.chromosomelib.ChromosomeLib.MODID;

/**
 * Logger utility class for Chromosome Lib. <br/>
 * Provides convenient logging methods for different log levels.
 *
 * @author liudongyu
 */
@SuppressWarnings("unused")
public class CLLogger {
	/**
	 * The logger instance for Chromosome Lib.
	 */
	public static final Logger logger = LogManager.getLogger(MODID);

	/**
	 * Logs a message at the specified log level.
	 *
	 * @param logLevel The log level
	 * @param object The object to log
	 */
	public static void log(Level logLevel, Object object) {
		logger.log(logLevel, object);
	}

	/**
	 * Logs an error message.
	 *
	 * @param object The object to log
	 */
	public static void error(Object object) {
		log(Level.ERROR, object);
	}

	/**
	 * Logs an info message.
	 *
	 * @param object The object to log
	 */
	public static void info(Object object) {
		log(Level.INFO, object);
	}

	/**
	 * Logs a warning message.
	 *
	 * @param object The object to log
	 */
	public static void warn(Object object) {
		log(Level.WARN, object);
	}

	/**
	 * Logs a debug message.
	 *
	 * @param object The object to log
	 */
	public static void debug(Object object) {
		log(Level.DEBUG, object);
	}

	/**
	 * Logs an error message with parameters.
	 *
	 * @param message The message template
	 * @param params The parameters to substitute into the message
	 */
	public static void error(String message, Object... params) {
		logger.log(Level.ERROR, message, params);
	}

	/**
	 * Logs an info message with parameters.
	 *
	 * @param message The message template
	 * @param params The parameters to substitute into the message
	 */
	public static void info(String message, Object... params) {
		logger.log(Level.INFO, message, params);
	}

	/**
	 * Logs a warning message with parameters.
	 *
	 * @param message The message template
	 * @param params The parameters to substitute into the message
	 */
	public static void warn(String message, Object... params) {
		logger.log(Level.WARN, message, params);
	}

	/**
	 * Logs a debug message with parameters.
	 *
	 * @param message The message template
	 * @param params The parameters to substitute into the message
	 */
	public static void debug(String message, Object... params) {
		logger.log(Level.DEBUG, message, params);
	}

	private CLLogger() {
	}
}
