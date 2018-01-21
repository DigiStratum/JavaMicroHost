package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceLinkageOneTest {
	ResourceLinkageOne sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new ResourceLinkageOne();
	}

	@Test
	public void testThat_defaultResourceIsEmpty() {
		assertEquals("null", sut.toJson());
	}

	// TODO: Add test to check the JSON here; take inspiration from RelationshipsTest.java
}
