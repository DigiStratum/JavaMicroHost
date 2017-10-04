package com.digistratum.microhost;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MHConfig {
	protected Map<String, String> config;

	/**
	 * Default constructor
	 */
	public MHConfig() {
		config = new HashMap<>();
	}

	/**
	 * Props loading constructor
	 *
	 * @param path String path to the properties file we want to load from
	 */
	public MHConfig(String path) {
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

	/**
	 * Get the named config entry (String), and supply default if undefined
	 *
	 * @param name String name of the config entry that we want
	 * @param def String default to use if the entry is not found (optional)
	 */
	public String get(String name, String def) {
		return (config.containsKey(name)) ? config.get(name) : def;
	}

	/**
	 * Get the named config entry (Integer), and supply default if undefined
	 *
	 * @param name String name of the config entry that we want
	 * @param def Integer default to use if the entry is not found (optional)
	 */
	public Integer get(String name, Integer def) {
		return (config.containsKey(name)) ? Integer.parseInt(config.get(name)) : def;
	}

	/**
	 * Get the named config entry (boolean), and supply default if undefined
	 *
	 * @param name String name of the config entry that we want
	 * @param def Boolean default to use if the entry is not found (optional)
	 */
	public Boolean get(String name, Boolean def) {
		return (config.containsKey(name)) ? Boolean.parseBoolean(config.get(name)) : def;
	}

	/**
	 * Set the named config entry to the supplied value
	 *
	 * Note that this is used to supplement the configuration data internally; it is not intended, nor makes any effort,
	 * to store updated configuration data so that it may be reloaded later.
	 *
	 * @param name String name of the config entry we want to set
	 * @param value String value we want to set it to
	 */
	public void set(String name, String value) {
		config.put(name, value);
	}
}
