package com.digistratum.microhost.Json;

/**
 * Expand upon Gson's capabilities a bit to handle some difficult situations
 */
public class JsonBuilder {

	/**
	 * Convert the supplied Object to JSON representation
	 *
	 * Pseudocode:
	 *
	 * If Object is a primitive type, return gson.toJson(obj)
	 *
	 * Else, If Object implements JsonClass, return obj.toJson()
	 *
	 * Else, If Object is an Array:
	 * 		Make new String[] values array
	 * 		For each Array element {
	 * 			Capture this.toJson(element) in values[]
	 * 		}
	 * 	 	return final JSON string as "[]" with all values[] in the middle
	 *
	 * Else:
	 * 		Make new Map<String, String> properties name:value pair map
	 * 		For each Object property {
	 *     		properties.add(property.name, this.toJson(property))
	 * 		}
	 *		return final JSON string as "{}" with all properties in the middle
	 *
	 * @param obj Object to reflect upon and convert to JSON
	 *
	 * @return
	 */
	public String toJson(Object obj) {
		String json = "";

		return json;
	}

}
