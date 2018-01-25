package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Exception.JsonException;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RelationshipTest {
	private Relationship sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new Relationship();
	}

	@Test
	public void testThat_toJson_failsByDefaultBecauseInvalid() {
		assertThrows(
				JsonException.class,
				() -> {
					sut.toJson();
				}
		);
	}

	@Test
	public void testThat_setters_areChainable() {
		Resources resources = new Resources();
		Links links = new Links();
		links.set("self", "http://localhost/");
		Meta meta = new Meta();
		String res =  sut.setData(resources).setLinks(links).setMeta(meta).toJson();
		assertEquals("{\"links\":{\"self\":\"http://localhost/\"},\"data\":null,\"meta\":{}}", res);
	}

	@Test
	public void testThat_getMeta_returnsMetadata() {
		Meta meta = new Meta();
		meta.set("key", "value");
		Meta res = sut.setMeta(meta).getMeta();
		assertEquals(meta.get("key"), res.get("key"));
	}

	@Test
	public void testThat_isValid_rejectsUninitializedRelationship() {
		assertFalse(sut.isValid());
	}

	@Test
	public void testThat_isValid_acceptsRelationshipWithAnyMeta() {
		assertTrue(sut.setMeta(new Meta()).isValid());
	}

	@Test
	public void testThat_isValid_acceptsRelationshipWithAnyData() {
		assertTrue(sut.setData(new Resources()).isValid());
	}

	@Test
	public void testThat_isValid_rejectsRelationshipWithLinksUninitialized() {
		assertFalse(sut.setLinks(new Links()).isValid());
	}

	@Test
	public void testThat_isValid_rejectsRelationshipWithLinksWithBogusLink() {
		Links links = new Links();
		links.set("boguslink", "http://bogushost/");
		assertFalse(sut.setLinks(links).isValid());
	}

	@Test
	public void testThat_isValid_acceptsRelationshipWithLinksWithSelfLink() {
		Links links = new Links();
		links.set("self", "http://bogushost/");
		assertTrue(sut.setLinks(links).isValid());
	}

	@Test
	public void testThat_isValid_acceptsRelationshipWithLinksWithRelatedLink() {
		Links links = new Links();
		links.set("related", "http://bogushost/");
		assertTrue(sut.setLinks(links).isValid());
	}
}
