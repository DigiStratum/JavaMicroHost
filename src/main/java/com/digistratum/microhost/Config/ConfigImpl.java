package com.digistratum.microhost.Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigImpl implements Config {
	protected Map<String, String> config;

	/**
	 * Default constructor
	 */
	public ConfigImpl() {
		config = new HashMap<>();
	}

	/**
	 * Props loading constructor
	 *
	 * @param path String path to the properties file we want to load from
	 */
	public ConfigImpl(String path) {
		this();
		loadProperties(path);
	}

	/**
	 * Load properties from a specified path
	 *
	 * ref: https://www.mkyong.com/java/java-properties-file-examples/
	 *
	 * @param path String path to the properties file we want to load from
	 */
	protected void loadProperties(String path) {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(path);

			// load a properties file
			prop.load(input);
		} catch (FileNotFoundException e) {
			System.out.println("Missing properties file: '" + path + "'");
		} catch (IOException e) {
			System.out.println("Error reading properties file: '" + path + "' - " + e.getMessage());
		}

		// Move our props into a Map<String, String> for config
		// ref: https://coderanch.com/t/599586/java/Properties-Class
		for (Map.Entry<Object, Object> e : prop.entrySet()) {
			config.put(e.getKey().toString(), e.getValue().toString());
		}
	}

	@Override
	public String get(String name, String def) {
		return (config.containsKey(name)) ? config.get(name) : def;
	}

	@Override
	public Boolean get(String name, Boolean def) {
		return (config.containsKey(name)) ? Boolean.parseBoolean(config.get(name)) : def;
	}

	@Override
	public Integer get(String name, Integer def) {
		return (config.containsKey(name)) ? Integer.parseInt(config.get(name)) : def;
	}

	@Override
	public void set(String name, String value) {
		config.put(name, value);
	}
}
