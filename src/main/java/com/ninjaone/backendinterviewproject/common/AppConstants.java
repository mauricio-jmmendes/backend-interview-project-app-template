package com.ninjaone.backendinterviewproject.common;

public final class AppConstants {

	public static final String CUSTOMER = "Customer";
	public static final String DEVICE = "Device";
	public static final String SERVICE = "Service";

	private AppConstants() {
		throw new IllegalStateException("Utility class");
	}

	public enum DeviceType {
		WINDOWS_WORKSTATION, WINDOWS_SERVER, LINUX, MAC
	}

	public enum ServiceType {
		DEVICE_MAINTENANCE, ANTIVIRUS, BACKUP, PSA, SCREEN_SHARE
	}

	public enum ServiceStatus {
		PENDING, DONE
	}
}
