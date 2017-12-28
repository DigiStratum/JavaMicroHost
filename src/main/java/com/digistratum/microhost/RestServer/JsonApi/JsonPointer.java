package com.digistratum.microhost.RestServer.JsonApi;

/**
 * ef: https://tools.ietf.org/html/rfc6901
 * ref: http://rapidjson.org/md_doc_pointer.html
 */
public class JsonPointer {
	String ptr;

	public JsonPointer(String ptr) {
		this.ptr = ptr;
	}

	@Override
	public String toString() {
		return ptr;
	}
}
