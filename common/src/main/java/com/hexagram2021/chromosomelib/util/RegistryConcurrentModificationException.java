package com.hexagram2021.chromosomelib.util;

public class RegistryConcurrentModificationException extends RuntimeException {
	public RegistryConcurrentModificationException(String className, String containerName, String typeName, Throwable t) {
		super("Concurrent modification detected in relation registration! This is NOT a bug of chromosomelib. " +
				"Please use CMESuckMyDuck mod to find which mod modify the container in Worker threads instead of the main thread! "+
				"Argument: \"" + className + ";" + containerName + ";" + typeName + ";static\"", t);
	}
}
