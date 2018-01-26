package com.digistratum.microhost.RestServer.JsonApi.DynamicClass;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.DynamicClass;
import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicClassTest {

	private DynamicClass sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new TestableDynamicClass();
	}

	@Test
	public void testThat_toJson_returnsAnEmptyObject() {
		String json = sut.toJson();
		assertEquals("{}", json);
	}

	@Test
	public void testThat_toJson_returnsAFilledObject() {
		sut.set("copyright", "2018");
		String json = sut.toJson();
		assertEquals("{\"copyright\":\"2018\"}", json);
	}

	@Test
	public void testThat_set_rejectsInvalidKeys() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.set("InvalidKey!", null);
				}
		);
	}

	@Test
	public void testThat_set_requiresRequiredKeysForRestrictedMode() {
		List<String> requiredKeys = new ArrayList<>();
		requiredKeys.add("hi");
		sut = new TestableDynamicClass(requiredKeys, null);
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.set("not_a_required_key", null);
				}
		);
	}

	@Test
	public void testThat_set_requiresOptionalKeysForRestrictedMode() {
		List<String> optionalKeys = new ArrayList<>();
		optionalKeys.add("bi");
		sut = new TestableDynamicClass(null, optionalKeys);
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.set("not_an_optional_key", null);
				}
		);
	}

	@Test
	public void testThat_set_requiresRequiredOrOptionalKeysForRestrictedMode() {
		List<String> requiredKeys = new ArrayList<>();
		requiredKeys.add("hi");
		List<String> optionalKeys = new ArrayList<>();
		optionalKeys.add("bi");
		sut = new TestableDynamicClass(requiredKeys, optionalKeys);
		sut.set("hi", "hi_value");
		sut.set("bi", "bi_value");
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.set("not_an_optional_or_required_key", null);
				}
		);
	}

	@Test
	public void testThat_get_returnsSetValue() {
		sut.set("hi", "hi_value");
		sut.set("bi", "bi_value");
		assertEquals("hi_value", sut.get("hi"));
		assertEquals("bi_value", sut.get("bi"));
		assertNull(sut.get("di"));
	}

	@Test
	public void testThat_has_properlyChecksKeys() {
		sut.set("hi", "hi_value");
		sut.set("bi", "bi_value");
		assertTrue(sut.has("hi"));
		assertTrue(sut.has("bi"));
		assertFalse(sut.has("di"));
	}

	@Test
	public void testThat_isValid_acceptsUnrestricted() {
		assertTrue(sut.isValid());
	}

	@Test
	public void testThat_isValid_acceptsRestrictedAndFulfilled() {
		List<String> requiredKeys = new ArrayList<>();
		requiredKeys.add("hi");
		sut = new TestableDynamicClass(requiredKeys, null);
		sut.set("hi", "hi_value");
		assertTrue(sut.isValid());
	}

	@Test
	public void testThat_isValid_rejectsRestrictedMissingRequired() {
		List<String> requiredKeys = new ArrayList<>();
		requiredKeys.add("hi");
		sut = new TestableDynamicClass(requiredKeys, null);
		assertFalse(sut.isValid());
	}

	private class TestableDynamicClass extends DynamicClass {
		public TestableDynamicClass() {
			super();
		}

		public TestableDynamicClass(List<String> requiredKeys, List<String> optionalKeys) {
			super(requiredKeys, optionalKeys);
		}
	}

}
