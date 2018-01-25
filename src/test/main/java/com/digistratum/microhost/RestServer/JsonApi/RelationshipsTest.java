package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RelationshipsTest {
	private TestableRelationships sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new TestableRelationships();
	}

	@Test
	public void testThat_set_addsARelationship() {
		String key = "testkey";
		assertFalse(sut.hasRelationship(key));
		Relationship testRelationship = new Relationship();
		sut.set(key, testRelationship);
		assertTrue(sut.hasRelationship(key));
	}

	@Test
	public void testThat_get_getsARelationship() {
		String key = "testkey";
		assertFalse(sut.hasRelationship(key));
		Relationship testRelationship = new Relationship();
		testRelationship.setLinks(new Links());
		Meta meta = new Meta();
		String metaKey = "testmeta";
		String metaValue = "testvalue";
		meta.set(metaKey, metaValue);
		testRelationship.setMeta(meta);
		testRelationship.setData(new Resources());
		sut.set(key, testRelationship);

		Relationship verifyRelationship = sut.get(key);
		assertEquals(verifyRelationship.getMeta().get(metaKey), metaValue);
	}

	// TODO: Add more tests around JSON serialization and validation checks

	private class TestableRelationships extends Relationships {
		public boolean hasRelationship(String key) {
			return relationships.containsKey(key);
		}
	}
}
