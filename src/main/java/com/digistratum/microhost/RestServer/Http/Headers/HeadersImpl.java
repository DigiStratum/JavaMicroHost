package com.digistratum.microhost.RestServer.Http.Headers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeadersImpl implements Headers {
	Map<String, String> headers;

	/**
	 * Default constructor for an empy header set
	 */
	public HeadersImpl() {
		headers = new HashMap<>();
	}

	@Override
	public boolean has(String name) {
		return headers.containsKey(name);
	}

	@Override
	public String get(String name) {
		return headers.get(name);
	}

	@Override
	public void set(String name, String value) throws Exception {
		// todo Make this validate the name of the header and that the combined pair is under a total max size
		headers.put(name, value);
	}

	@Override
	public Set<String> list() {
		return headers.keySet();
	}

}
