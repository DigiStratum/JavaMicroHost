package com.digistratum.microhost.RestServer.JsonApi;

public class Resource {

	// Required
	String id; // Not required if client is requestng new resource creation
	String type;

	// Optional
	Attributes attributes;
	Relationships relationships;
	Links links;
	Meta meta;
}
