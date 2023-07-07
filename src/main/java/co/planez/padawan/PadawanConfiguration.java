package co.planez.padawan;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;

public class PadawanConfiguration extends Configuration {
	private static PadawanConfiguration instance;
	
	@JsonProperty String mongodb;
	@JsonProperty String mode;

	public PadawanConfiguration() {
		PadawanConfiguration.instance = this;
	}

	public static PadawanConfiguration instance() {
		return instance;
	}
	
	public String getMongodb() {
		return mongodb;
	}

	public String getMode() {
		return mode;
	}
}
