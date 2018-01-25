package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
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
		String res = sut.setNullable(false).toJson();
		assertEquals("[]", res);
	}

	@Test
	public void testThat_toJson_returnsSingleObjectForBareResource() {
		Resource resource = new Resource("resourceId", "resourceType");
		String res = sut.addResource(resource).toJson();
		assertEquals("{\"id\":\"resourceId\",\"type\":\"resourceType\"}", res);
	}

	@Test
	public void testThat_toJson_returnsCollectionWithSingleObjectElementForBareResourceWithForceCollection() {
		Resource resource = new Resource("resourceId", "resourceType");
		String res = sut.setForceCollection(true).addResource(resource).toJson();
		assertEquals("[{\"id\":\"resourceId\",\"type\":\"resourceType\"}]", res);
	}

	@Test
	public void testThat_toJson_returnsCollectionWithMultipleObjectElementsForBareResource() {
		Resource resource = new Resource("resourceId", "resourceType");
		String res = sut.addResource(resource).addResource(resource).addResource(resource).toJson();
		assertEquals("[{\"id\":\"resourceId\",\"type\":\"resourceType\"},{\"id\":\"resourceId\",\"type\":\"resourceType\"},{\"id\":\"resourceId\",\"type\":\"resourceType\"}]", res);
	}

	@Test
	public void testThat_toJson_returnsSingleObjectForPopulatedResource() {
		Resource resource = new Resource("resourceId", "resourceType");
		Meta meta = new Meta();
		meta.set("name", "value");
		resource.setMeta(meta);
		String res = sut.addResource(resource).toJson();
		assertEquals("{\"id\":\"resourceId\",\"type\":\"resourceType\",\"meta\":{\"name\":\"value\"}}", res);
	}

	@Test
	public void testThat_toJson_returnsCollectionWithMultipleObjectElementsForPopulatedResources() {

		// Setup
		Resource resource1 = new Resource("resourceId1", "resourceType1");
		Meta meta = new Meta();
		meta.set("name1", "value1");
		resource1.setMeta(meta);

		Resource resource2 = new Resource("resourceId2", "resourceType2");
		meta = new Meta();
		meta.set("name2", "value2");
		resource2.setMeta(meta);
		String res = sut.addResource(resource1).addResource(resource2).toJson();

		// Verify
		assertEquals("[{\"id\":\"resourceId1\",\"type\":\"resourceType1\",\"meta\":{\"name1\":\"value1\"}},{\"id\":\"resourceId2\",\"type\":\"resourceType2\",\"meta\":{\"name2\":\"value2\"}}]", res);
	}
}
