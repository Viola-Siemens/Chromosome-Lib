package com.hexagram2021.chromosomelib.platform;

import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

/**
 * Services class for loading platform-specific services.
 *
 * @author liudongyu
 */
public final class Services {
	public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

	/**
	 * Load a service.
	 * @param clazz	the service class
	 * @return the loaded service
	 * @param <T>	the service type
	 */
	public static <T> T load(Class<T> clazz) {
		final T loadedService = ServiceLoader.load(clazz, clazz.getClassLoader())
				.findFirst()
				.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
		CLLogger.info("Loaded {} for service {}", loadedService, clazz);
		return loadedService;
	}

	private Services() {
	}
}