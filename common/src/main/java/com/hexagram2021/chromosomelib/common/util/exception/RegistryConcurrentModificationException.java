package com.hexagram2021.chromosomelib.common.util.exception;

/**
 * Exception thrown when concurrent modification is detected in relation registration. <br/>
 * This indicates that a mod is modifying the container in worker threads instead of the main thread.
 *
 * @author liudongyu
 */
public class RegistryConcurrentModificationException extends RuntimeException {
	/**
	 * Constructs a new exception with diagnostic information.
	 *
	 * @param className The class name where the error occurred
	 * @param containerName The container name that was modified
	 * @param typeName The type name involved
	 * @param t The underlying throwable
	 */
	public RegistryConcurrentModificationException(String className, String containerName, String typeName, Throwable t) {
		super("Concurrent modification detected in relation registration! This is NOT a bug of chromosomelib. " +
				"Please use CMESuckMyDuck mod to find which mod modify the container in Worker threads instead of the main thread! "+
				"Argument: \"" + className + ";" + containerName + ";" + typeName + ";static\"", t);
	}
}
