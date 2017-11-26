package com.digistratum.microhost.Config;

public class ConfigFactory {
	public ConfigImpl createMHConfig(String propsFile) {
		return new ConfigImpl(propsFile);
	}
}
