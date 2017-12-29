package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentTest {
	private TestableDocument sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new TestableDocument();
	}

	@Test
	public void testThat_setJsonApi_doesItsThing() {
		TestableJsonApi jsonApi = new TestableJsonApi();
		String version = "3.3.3";
		jsonApi.setVersion(version);
		TestableDocument res = (TestableDocument) sut.setJsonApi(jsonApi);
		assertTrue(sut.equals(res));
		assertEquals(version, jsonApi.getVersion());
	}

	@Test
	public void testThat_toJson_returnsCorrectJson() {
		TestableJsonApi jsonApi = new TestableJsonApi();
		jsonApi.setVersion("3.3.3");
		sut.setJsonApi(jsonApi);
		String json = sut.toJson();
	}

	private class TestableDocument extends Document {
		public JsonApi getJsonApi() {
			return jsonapi;
		}
	}

	private class TestableJsonApi extends JsonApi {
		public String getVersion() {
			return properties.version;
		}
	}
}
