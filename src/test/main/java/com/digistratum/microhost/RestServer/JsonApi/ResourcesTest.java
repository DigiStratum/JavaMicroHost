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
		Resource resource = new Resource("resourceId", "resourceType");
		sut.addResource(resource);
		assertEquals("{\"id\":\"resourceId\",\"type\":\"resourceType\"}", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsCollectionWithSingleObjectElementForBareResourceWithForceCollection() {
		sut.setForceCollection(true);
		Resource resource = new Resource("resourceId", "resourceType");
		sut.addResource(resource);
		assertEquals("[{\"id\":\"resourceId\",\"type\":\"resourceType\"}]", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsCollectionWithMultipleObjectElementsForBareResource() {
		Resource resource = new Resource("resourceId", "resourceType");
		sut.addResource(resource);
		sut.addResource(resource);
		sut.addResource(resource);
		assertEquals("[{\"id\":\"resourceId\",\"type\":\"resourceType\"},{\"id\":\"resourceId\",\"type\":\"resourceType\"},{\"id\":\"resourceId\",\"type\":\"resourceType\"}]", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsSingleObjectForPopulatedResource() {
		Resource resource = new Resource("resourceId", "resourceType");
		Meta meta = new Meta();
		meta.set("name", "value");
		resource.setMeta(meta);
		sut.addResource(resource);
		assertEquals("{\"id\":\"resourceId\",\"type\":\"resourceType\",\"meta\":{\"name\":\"value\"}}", sut.toJson());
	}

	@Test
	public void testThat_toJson_returnsCollectionWithMultipleObjectElementsForPopulatedResources() {

		// Setup
		Resource resource = new Resource("resourceId1", "resourceType1");
		Meta meta = new Meta();
		meta.set("name1", "value1");
		resource.setMeta(meta);
		sut.addResource(resource);

		resource = new Resource("resourceId2", "resourceType2");
		meta = new Meta();
		meta.set("name2", "value2");
		resource.setMeta(meta);
		sut.addResource(resource);

		// Verify
		assertEquals("[{\"id\":\"resourceId1\",\"type\":\"resourceType1\",\"meta\":{\"name1\":\"value1\"}},{\"id\":\"resourceId2\",\"type\":\"resourceType2\",\"meta\":{\"name2\":\"value2\"}}]", sut.toJson());
	}
}
