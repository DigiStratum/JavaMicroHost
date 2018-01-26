package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonSerializeable;
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

	public JsonPointer(String ptr) {
		this.ptr = ptr;
	}

	@Override
	public String toJson() {
		if (null == gson) gson = new Gson();
		return gson.toJson(ptr);
	}
}
