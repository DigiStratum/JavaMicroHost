package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {
	private Error sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new Error();
	}

	@Test
	public void testThat_toJson_returnsEmptyStructure() {
		String res = sut.toJson();
		assertEquals("{}", res);
	}

	// TODO: Fill in additional test cases covering the various, chained setters
}
