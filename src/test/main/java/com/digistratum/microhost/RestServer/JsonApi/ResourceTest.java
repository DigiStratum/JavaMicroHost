package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Attributes;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceTest {
	private Resource sut;

	private String id = "ID";
	private String type = "TYPE";

	@BeforeEach
	public void setup() throws Exception {
		sut = new Resource(id, type);
	}

	@Test
	public void testThat_toJson_returnsDefaultProperties() {
		String res = sut.toJson();
		assertEquals("{\"id\":\"ID\",\"type\":\"TYPE\"}", res);
	}

	@Test
	public void testThat_setters_chainProperly() {
		Meta meta = new Meta();
		Links links = new Links();
		Attributes attributes = new Attributes();
		Relationships relationships = new Relationships();
		String res = sut.setMeta(meta)
				.setLinks(links)
				.setAttributes(attributes)
				.setRelationships(relationships)
				.toJson();
		assertEquals("{\"id\":\"ID\",\"type\":\"TYPE\",\"attributes\":{},\"relationships\":{},\"links\":{},\"meta\":{}}", res);
	}
}
