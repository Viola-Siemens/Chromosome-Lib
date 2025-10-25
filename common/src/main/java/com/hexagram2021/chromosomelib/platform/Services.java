package com.hexagram2021.chromosomelib.platform;

import com.hexagram2021.chromosomelib.common.util.CLLogger;
import com.hexagram2021.chromosomelib.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {
	public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

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