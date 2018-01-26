package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonPointerTest {
	private JsonPointer sut;

	@BeforeEach
	public void setup() throws Exception {
	}

	@Test
	public void testThat_toJson_returnsExpectedOutput() {
		String pointerString = "pointerString";
		sut = new JsonPointer(pointerString);
		assertEquals("\"pointerString\"", sut.toJson());
	}

	@Test
	public void testThat_constructor_rejectsInvalidPointers() {
		assertThrows(
				JsonApiException.class,
				() -> {
					String pointerString = "!";
					new JsonPointer(pointerString);
				}
		);
	}
}
