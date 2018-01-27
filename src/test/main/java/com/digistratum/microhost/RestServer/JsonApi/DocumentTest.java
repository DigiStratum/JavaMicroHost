package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentTest {
	private TestableDocument sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new TestableDocument();
	}

	@Test
	public void testThat_isValid_rejectsByDefault() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.toJson();
				}
		);
	}

	@Test
	public void testThat_setJsonApiVersion_doesItsThing() {
		TestableDocument res = (TestableDocument) sut
				.setJsonApiVersion("3.3.3")
				.setMetadata("name", "value");
		assertTrue(sut.equals(res));
		assertEquals("{\"meta\":{\"name\":\"value\"},\"jsonapi\":{\"version\":\"3.3.3\"}}", sut.toJson());
	}

	private class TestableDocument extends Document {
	}
}
