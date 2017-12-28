package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicClassTest {

	private DynamicClass sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new TestableDynamicClass();
	}

	@Test
	public void testThat_toString_returnsAJsonObject() {
		sut.set("copyright", "2018");
		String json = sut.toString();
		assertEquals("{\"copyright\":\"2018\"}", json);
	}

	private class TestableDynamicClass extends DynamicClass {
		public TestableDynamicClass() {
			super();
		}
	}

}
