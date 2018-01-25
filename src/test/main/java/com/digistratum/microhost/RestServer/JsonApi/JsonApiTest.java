package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonApiTest {
	private JsonApi sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new JsonApi();
	}

	@Test
	public void testThat_setVersion_isChainable() {
		JsonApi res = sut.setVersion("yoh");
		assertTrue(sut.equals(res));
	}

	@Test
	public void testThat_setMeta_isChainable() {
		Meta meta = new Meta();
		JsonApi res = sut.setMeta(meta);
		assertTrue(sut.equals(res));
	}

	@Test
	public void testThat_toJson_returnsExpectedJson() {
		Meta meta = new Meta();
		meta.set("this", "that");
		String json = sut.setVersion("yoh").setMeta(meta).toJson();
		assertEquals("{\"version\":\"yoh\",\"meta\":{\"this\":\"that\"}}", json);
	}

}
