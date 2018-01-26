package com.digistratum.microhost.Json;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Expand upon Gson's capabilities a bit to handle some difficult situations
 */
public class Json {
	protected static Logger log;
	protected Gson gson;
	protected boolean verbose = false;

	// TODO: Use this to initialize Gson with GsonBuilder()
	protected boolean serializeNulls = false;

	/**
	 * Default Cosntructor
	 */
	public Json() {
		gson = new Gson();
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
		if (! verbose) return;
		log = Logger.getLogger(Json.class);
	}

	public void verboseLog(String msg) {
		if (! verbose) return;
		log.debug("JsonBuilder: " + msg);
	}

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
	 * @return String  JSON representation of the supplied object; may be null
	 */
	public String toJson(Object obj) {

		// If Object is null...
		if (null == obj) return (serializeNulls) ? toJsonGson(obj) : null;

		// If Object is a primitive type, return gson.toJson(obj)
		String simpleName = obj.getClass().getSimpleName();
		verboseLog("toJson() encoding object; simpleName = '" + simpleName + "'");
		if ("String".equals(simpleName)) return toJsonGson(obj);
		if ("Integer".equals(simpleName)) return toJsonGson(obj);
		if ("Float".equals(simpleName)) return toJsonGson(obj);
		if ("Double".equals(simpleName)) return toJsonGson(obj);
		if ("Boolean".equals(simpleName)) return toJsonGson(obj);

		// If Object implements JsonClass, return obj.toJson()
		if (obj instanceof JsonSerializeable) {
			verboseLog("toJson() encoding JsonClass");
			return ((JsonSerializeable) obj).toJson();
		}

		// Else, If Object is an Array:
		// 		Make new String[] values array
		//		For each Array element {
		//			Capture this.toJson(element) in values[]
		//		}
		// 	 	return final JSON string as "[]" with all values[] in the middle
		// TODO: Support for any other kind of array needed here?
		StringBuilder sb = new StringBuilder();
		if (obj instanceof List) {
			verboseLog("toJson() encoding List");
			sb.append("[");
			boolean first = true;
			for (Object listElement : ((List) obj)) {
				if (! first) sb.append(",");
				else first = false;
				sb.append(toJson(listElement));
			}
			sb.append("]");
			return sb.toString();
		}

		// ref: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		// HashMap, Map, etc..
		// Note: This cannot simply use Gson.toJson() because the  values may be JavaClass instances
		// which themselves cannot be interpreted directly by Gson and require JsonBuilder.toJson() to
		// properly encode.
		if (obj instanceof Map) {
			verboseLog("toJson() encoding Map");
			sb.append("{");
			boolean first = true;
			Iterator it = ((Map) obj).entrySet().iterator();
			while (it.hasNext()) {
				if (! first) sb.append(",");
				else first = false;
				Map.Entry pair = (Map.Entry) it.next();
				sb.append(toJson(pair.getKey()) + ":" + toJson(pair.getValue()));
				it.remove();
			}
			sb.append("}");
			return sb.toString();
		}

		// Else:
		// 		Make new Map<String, String> properties name:value pair map
		// 		For each Object property {
		//     		properties.add(property.name, this.toJson(property))
		// 		}
		//		return final JSON string as "{}" with all properties in the middle
		verboseLog("toJson() encoding properties of general class");
		Field[] fields = obj.getClass().getFields();
		sb.append("{");
		boolean first = true;
		for (Field field : fields) {
			String fieldName = field.getName();

			//  Skip the magic, Java-built-in this$0 property
			if (fieldName.equals("this$0")) {
				verboseLog("toJson() skipping this$0 internal property");
				continue;
			}

			// Get the encoded value...
			String value = null;
			try {
				value = toJson(field.get(obj));
			} catch (IllegalAccessException e) {
				// Shouldn't happen; swallow it until there's some indication that this can actually fail
				System.out.println("Unexpected Exception accessing Field of Object in JsonBuilder.toJson()");
			}

			// If the encoded value came back null, but we are not serializing nulls, then move on
			if ((null == value) && (! serializeNulls)) continue;

			// Add this field: value to our output
			verboseLog("toJson() encoding property: " + fieldName + " value: " + value);
			if (! first) sb.append(",");
			else first = false;
			sb.append("\"");
			sb.append(fieldName);
			sb.append("\":");
			sb.append(value);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Use Gson to convert the supplied object to JSON
	 *
	 * @param obj Object instance which we want to represent as JSON
	 *
	 * @return String JSON representation of the supplied object
	 */
	protected String toJsonGson(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * Check whether the supplied identifier is a valid one
	 *
	 * True for anything that can be used as a JSON property name;
	 *
	 * TODO: Note that JSON itself does support other special characters in keys such as blank keys
	 * and everything in [/%^|\" ~], and likely more. This pattern matcher should be expanded to
	 * verify the complete possible set.
	 *
	 * @param identifier String identifier to check
	 *
	 * @return boolean true if the identifier is valid, else false
	 */
	public static boolean isValidJsonIdentifier(String identifier) {
		return Pattern.matches("[A-Za-z0-9_]*", identifier);
	}
}
