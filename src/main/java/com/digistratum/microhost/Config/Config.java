package com.digistratum.microhost.Config;

public interface Config {

	/**
	 * Get the named configImpl entry (String), and supply default if undefined
	 *
	 * @param name String name of the configImpl entry that we want
	 * @param def String default to use if the entry is not found (optional)
	 */
	public String get(String name, String def);

	/**
	 * Get the named configImpl entry (Integer), and supply default if undefined
	 *
	 * @param name String name of the configImpl entry that we want
	 * @param def Integer default to use if the entry is not found (optional)
	 */
	public Integer get(String name, Integer def);

	/**
	 * Get the named configImpl entry (boolean), and supply default if undefined
	 *
	 * @param name String name of the configImpl entry that we want
	 * @param def Boolean default to use if the entry is not found (optional)
	 */
	public Boolean get(String name, Boolean def);

	/**
	 * Set the named configImpl entry to the supplied value
	 *
	 * Note that this is used to supplement the configuration data internally; it is not intended, nor makes any effort,
	 * to store updated configuration data so that it may be reloaded later.
	 *
	 * @param name String name of the configImpl entry we want to set
	 * @param value String value we want to set it to
	 */
	public void set(String name, String value);
}
