package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorSourceTest {
	private ErrorSource sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new ErrorSource();
	}

	@Test
	public void testThat_toJson_returnsBareObjectForBareSource() {
		assertEquals("{}", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsLoadedObjectForLoadedSource() {
		JsonPointer jsonPointer = new JsonPointer("yoh");
		sut.setParameter("parameterName").setPointer(jsonPointer);
		assertEquals("{\"pointer\":\"yoh\",\"parameter\":\"parameterName\"}", sut.toJson());
	}
}
