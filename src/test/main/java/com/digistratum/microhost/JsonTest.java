package com.digistratum.microhost;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
	private Gson gson;

	private Json sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new Json();
		gson = new Gson();
	}

	@Test
	public void testThat_toJson_encodesStrings() {
		// A string is always encoded as a JavaString, encapsulated in a set of quotes; so the outermost
		// characters are always double-quotes. Within that, other characters may be literal or escaped
		// as needed. If, for example, our test string itself has quotes within it, then those quotes will
		// be escaped. And to check this in Java here, we must escape the outermost quote, escape the escape,
		// AND escape the inner quote as Json and Java strings have the same escaping requirements. eek!

		assertEquals("\"\\\"test\\\"\"", sut.toJson("\"test\""));
	}

	@Test
	public void testThat_toJson_encodesIntegers() {
		int test = 333;
		assertEquals(((Integer) test).toString(), sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesFloats() {
		float test = (float) 333.3;
		assertTrue(Math.abs((Float.parseFloat(sut.toJson(test))) - test) < 0.01);
	}

	@Test
	public void testThat_toJson_encodesDoubles() {
		double test = (double) 333.3;
		assertTrue(Math.abs((Double.parseDouble(sut.toJson(test))) - test) < 0.0001);
	}

	@Test
	public void testThat_toJson_encodesBooleans() {
		boolean test = true;
		assertEquals(test, Boolean.parseBoolean(sut.toJson(test)));
	}

	@Test
	public void testThat_toJson_encodesJsonClassInstances() {
		JsonSerializeableObject test = new JsonSerializeableObject();
		assertEquals("\"JSON!\"", sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesEmptyArrays() {
		List<Object> test = new ArrayList<>();
		assertEquals("[]", sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesArraysWithSingleElement() {
		List<Object> test = new ArrayList<>();
		String str = "Strings are objects too!";
		test.add(str);
		assertEquals("[\"" + str + "\"]", sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesArraysWithMultipleMixedElements() {
		List<Object> test = new ArrayList<>();
		test.add("abc");
		test.add(123);
		test.add(true);
		assertEquals("[\"abc\",123,true]", sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesEmptyObjects() {
		EmptyObject test = new EmptyObject();
		assertEquals("{}", sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesObjectsWithSingleProperty() {
		SingleObject test = new SingleObject();
		assertEquals("{\"publicProperty\":\"public\"}", sut.toJson(test));
	}

	@Test
	public void testThat_toJson_encodesObjectsWithMixedProperties() {
		MixedObject test = new MixedObject();
		String json = sut.toJson(test);
		MixedObject res = gson.fromJson(json, MixedObject.class);
		assertEquals(res.strProp, test.strProp);
		assertEquals(res.intProp, test.intProp);
		assertEquals(res.integerProp, test.integerProp);
		assertEquals(res.booleanProp, test.booleanProp);
		assertEquals(res.bigBooleanProp, test.bigBooleanProp);
		assertTrue(Math.abs(res.floatProp - test.floatProp) < 0.01);
		assertTrue(Math.abs(res.bigFloatProp - test.bigFloatProp) < 0.01);
		assertTrue(Math.abs(res.doubleProp - test.doubleProp) < 0.0001);
		assertTrue(Math.abs(res.bigDoubleProp - test.bigDoubleProp) < 0.0001);
	}

	@Test
	public void testThat_toJson_eliminatesNullsByDefault() {
		ObjectWithNullProperty test = new ObjectWithNullProperty();
		assertEquals("{}", sut.toJson(test));
	}

	// TODO: Add a test to cover objects with mixed, nested properties (such as other objects, arrays of objects, etc)

	protected class JsonSerializeableObject implements JsonSerializeable {
		@Override
		public String toJson() {
			return "\"JSON!\"";
		}
	}

	protected class EmptyObject {}

	protected class SingleObject {
		public String publicProperty = "public";
	}

	protected class MixedObject {
		public String strProp = "prop1";
		public int intProp = 3;
		public Integer integerProp = 33;
		public boolean booleanProp = true;
		public Boolean bigBooleanProp = false;
		public float floatProp = (float) 33.3;
		public Float bigFloatProp = (float) 333.3;
		public double doubleProp = 3.14159;
		public Double bigDoubleProp = 26.2;
	}

	protected class ObjectWithNullProperty {
		public String nullString = null;
	}
}
