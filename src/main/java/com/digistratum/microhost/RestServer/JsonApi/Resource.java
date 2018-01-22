package com.digistratum.microhost.RestServer.JsonApi;

public class Resource {

	// Required
	public String id; // Not required if client is requestng new resource creation
	public String type;

	// Optional
	public Attributes attributes;
	public Relationships relationships;
	public Links links;
	public Meta meta;
}