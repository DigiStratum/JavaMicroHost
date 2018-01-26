package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import com.google.gson.Gson;

/**
 * ref: https://tools.ietf.org/html/rfc6901
 * ref: http://rapidjson.org/md_doc_pointer.html
 *
 * todo: Add some helper methods around this to ensure that the pointer is well-formed
 */
public class JsonPointer implements JsonSerializeable {
	protected String ptr;
	protected Gson gson;

	/**
	 * Constructor
	 *
	 * @param ptr String JSONPointer we want to capture
	 */
	public JsonPointer(String ptr) {
		if (! isValidPointer(ptr)) {
			throw new JsonApiException("Supplied string is not a valid RFC6901 JsonPointer");
		}
		this.ptr = ptr;
	}

	/**
	 * Check whether the provided string is a valid JSON Pointer
	 *
	 * This is really just a matter of whether each individual segment is a valid identifier
	 *
	 * @param ptr String that we want to check for validity
	 *
	 * @return boolean true if it's a good JSONPointer, else false
	 */
	protected boolean isValidPointer(String ptr) {
		String[] parts = ptr.split("/");
		for (String part : parts) {
			if (!Json.isValidJsonIdentifier(part)) return false;
		}
		return true;
	}

	@Override
	public String toJson() {
		if (null == gson) gson = new Gson();
		return gson.toJson(ptr);
	}
}
