package com.digistratum.microhost.RestServer.JsonApi;

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
		testRelationship.links = new Links();
		testRelationship.meta = new Meta();
		String metaKey = "testmeta";
		String metaValue = "testvalue";
		testRelationship.meta.set(metaKey, metaValue);
		testRelationship.data = new ResourceLinkageOne();
		sut.set(key, testRelationship);
		Relationship verifyRelationship = sut.get(key);
		assertEquals(verifyRelationship.meta.get(metaKey), metaValue);
	}

	private class TestableRelationships extends Relationships {
		public boolean hasRelationship(String key) {
			return relationships.containsKey(key);
		}
	}
}
