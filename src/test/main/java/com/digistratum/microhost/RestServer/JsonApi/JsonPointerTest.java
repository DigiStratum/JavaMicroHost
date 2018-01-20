package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonPointerTest {
	private JsonPointer sut;

	@BeforeEach
	public void setup() throws Exception {
	}

	@Test
	public void testThat_toJson_returnsExpectedOutput() {
		String pointerString = "pointerString";
		sut = new JsonPointer(pointerString);
		assertEquals(pointerString, sut.toJson());
	}
}
