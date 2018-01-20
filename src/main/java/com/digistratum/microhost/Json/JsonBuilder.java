package com.digistratum.microhost.Json;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Expand upon Gson's capabilities a bit to handle some difficult situations
 */
public class JsonBuilder {
	protected static Logger log;
	protected Gson gson;
	protected boolean verbose = false;

	/**
	 * Default Cosntructor
	 */
	public JsonBuilder() {
		gson = new Gson();
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
		if (! verbose) return;
		log = Logger.getLogger(JsonBuilder.class);
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
	 * @return String  JSON representation of the supplied object
	 */
	public String toJson(Object obj) {

		// If Object is a primitive type, return gson.toJson(obj)
		String simpleName = obj.getClass().getSimpleName();
		verboseLog("toJson() encoding object; simpleName = '" + simpleName + "'");
		if ("String".equals(simpleName)) return toJsonGson(obj);
		if ("Integer".equals(simpleName)) return toJsonGson(obj);
		if ("Float".equals(simpleName)) return toJsonGson(obj);
		if ("Double".equals(simpleName)) return toJsonGson(obj);
		if ("Boolean".equals(simpleName)) return toJsonGson(obj);

		// If Object implements JsonClass, return obj.toJson()
		if (obj instanceof JsonClass) {
			verboseLog("toJson() encoding JsonClass");
			return ((JsonClass) obj).toJson();
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
				first = false;
			}
			sb.append("]");
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

			verboseLog("toJson() encoding property: " + fieldName);
			if (! first) sb.append(",");
			else first = false;
			sb.append("\"");
			sb.append(fieldName);
			sb.append("\":");
			try {
				sb.append(toJson(field.get(obj)));
			} catch (IllegalAccessException e) {
				// Shouldn't happen; swallow it until there's some indication that this can actually fail
				System.out.println("Unexpected Exception accessing Field of Object in JsonBuilder.toJson()");
			}
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
}
