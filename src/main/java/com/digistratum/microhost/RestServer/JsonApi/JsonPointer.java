package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonClass;

/**
 * ref: https://tools.ietf.org/html/rfc6901
 * ref: http://rapidjson.org/md_doc_pointer.html
 *
 * todo: Add some helper methods around this to ensure that the pointer is well-formed
 */
public class JsonPointer implements JsonClass {
	protected String ptr;

	public JsonPointer(String ptr) {
		this.ptr = ptr;
	}

	@Override
	public String toJson() {
		return ptr;
	}
}
