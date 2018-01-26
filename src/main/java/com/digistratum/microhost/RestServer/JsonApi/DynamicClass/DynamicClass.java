package com.digistratum.microhost.RestServer.JsonApi.DynamicClass;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import com.digistratum.microhost.Validation.Validatable;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ref: https://stackoverflow.com/questions/12832554/how-to-add-attributes-dynamically-for-java-object
 *
 * TODO: Add support for other JSON-primitive data types (number, boolean, array (of string/number/boolean), etc)
 */
public abstract class DynamicClass implements JsonSerializeable, Validatable {
	protected Map<String, Object> properties;
	protected boolean unrestricted = true;
	protected List<String> requiredKeys;
	protected List<String> optionalKeys;

	/**
	 * Default Constructor
	 */
	public DynamicClass() {
		properties = new HashMap<>();
	}

	/**
	 * Restrictive Constructor
	 *
	 * @param requiredKeys List<String> set of keys which must be set to be valid
	 * @param optionalKeys List<String> set of keys which are permitted to be set
	 *
	 * @throws JsonApiException for and invalid key
	 */
	public DynamicClass(List<String> requiredKeys, List<String> optionalKeys) {
		this();
		if (null != requiredKeys) {
			for (String key : requiredKeys) {
				if (! Json.isValidJsonIdentifier(key)) {
					throw new JsonApiException("Invalid required key: '" + key + "'");
				}
			}
			if (! requiredKeys.isEmpty()) unrestricted = false;
		}
		if (null != optionalKeys) {
			for (String key : optionalKeys) {
				if (! Json.isValidJsonIdentifier(key)) {
					throw new JsonApiException("Invalid optional key: '" + key + "'");
				}
			}
			if (! optionalKeys.isEmpty()) unrestricted = false;
		}
		this.requiredKeys = requiredKeys;
		this.optionalKeys = optionalKeys;
	}

	/**
	 * Get the named property's value
	 *
	 * @param key String name of the property we want to get the value for
	 *
	 * @return String property value or null
	 */
	public String get(String key) {
		return (String) properties.get(key);
	}

	/**
	 * Set the named property's value
	 *
	 * @param key String name of the property we want to set the value for
	 * @param value  String property value
	 *
	 * @throws JsonApiException for and invalid key, or a key which is not required/optional in restricted mode
	 */
	public void set(String key, String value) {
		// If we are in restricted mode
		if (! unrestricted) {
			// This key must be in the set of either optional or required keys
			if (! ((null != requiredKeys) && (requiredKeys.contains(key)) || ((null != optionalKeys) && optionalKeys.contains(key)))) {
				throw new JsonApiException("Attempted to set a key on a restricted set which is neither required nor optional");
			}
		}
		if (! Json.isValidJsonIdentifier(key)) {
			throw new JsonApiException("Invalid key: '" + key + "'");
		}
		properties.put(key, value);
	}

	/**
	 * Check whether we have a value for the named property
	 *
	 * @param key String name of the property we want to check
	 *
	 * @return boolean true if the named property is in our set, else false
	 */
	public boolean has(String key) {
		return properties.containsKey(key);
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(properties);
	}

	@Override
	public boolean isValid() {

		// If we're unrestricted, anything goes...
		if (unrestricted) return true;

		// Otherwise, all required keys must be present
		if (null != requiredKeys) {
			for (String key : requiredKeys) {
				if (!has(key)) return false;
			}
		}

		return true;
	}
}
