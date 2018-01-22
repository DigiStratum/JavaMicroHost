package com.digistratum.microhost.RestServer.JsonApi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourcesTest {
	private Resources sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new Resources();
	}

	@Test
	public void testThat_toJson_returnsJsonEmptySetForEmptyResource() {
		assertEquals("null", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsJsonEmptySetForEmptyResourceNonNullable() {
		sut.setNullable(false);
		assertEquals("[]", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsSingleObjectForBareResource() {
		Resource resource = new Resource();
		sut.addResource(resource);
		assertEquals("{}", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsCollectionWithSingleObjectElementForBareResourceWithForceCollection() {
		sut.setForceCollection(true);
		Resource resource = new Resource();
		sut.addResource(resource);
		assertEquals("[{}]", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsCollectionWithMultipleObjectElementsForBareResource() {
		Resource resource = new Resource();
		sut.addResource(resource);
		sut.addResource(resource);
		sut.addResource(resource);
		assertEquals("[{},{},{}]", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsSingleObjectForPopulatedResource() {
		Resource resource = new Resource();
		resource.meta = new Meta();
		resource.meta.set("name", "value");
		sut.addResource(resource);
		assertEquals("{\"meta\":{\"name\":\"value\"}}", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsCollectionWithMultipleObjectElementsForPopulatedResources() {
		Resource resource = new Resource();
		resource.meta = new Meta();
		resource.meta.set("name1", "value1");
		sut.addResource(resource);
		resource = new Resource();
		resource.meta = new Meta();
		resource.meta.set("name2", "value2");
		sut.addResource(resource);
		assertEquals("[{\"meta\":{\"name1\":\"value1\"}},{\"meta\":{\"name2\":\"value2\"}}]", sut.toJson());
	}
}
