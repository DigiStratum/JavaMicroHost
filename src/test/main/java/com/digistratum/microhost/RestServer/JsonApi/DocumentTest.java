package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DocumentTest {
	private Document sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new Document();
	}

	@Test
	public void testThat_isValid_rejectsByDefault() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.toJson();
				}
		);
	}

	@Test
	public void testThat_isValid_acceptsMetaOnly() {
		assertTrue(sut.setMetadata("name", "value").isValid());
	}

	@Test
	public void testThat_isValid_acceptsDataOnly() {
		assertTrue(sut.addResource(new Resource("data","type")).isValid());
	}

	@Test
	public void testThat_isValid_acceptsErrorsOnly() {
		assertTrue(sut.addError(new Error()).isValid());
	}

	@Test
	public void testThat_isValid_acceptsMetaAndData() {
		assertTrue(sut.addResource(new Resource("data","type")).setMetadata("name", "value").isValid());
	}

	@Test
	public void testThat_isValid_acceptsMetaAndErrors() {
		assertTrue(sut.addError(new Error()).setMetadata("name", "value").isValid());
	}

	@Test
	public void testThat_addError_deniesIfDataPresent() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.addResource(new Resource("data","type")).addError(new Error());
				}
		);
	}

	@Test
	public void testThat_addResource_deniesIfErrorPresent() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.addError(new Error()).addResource(new Resource("data","type"));
				}
		);
	}

	@Test
	public void testThat_includeResoure_deniesIfDataUnset() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.includeResource(new Resource("resource","type"));
				}
		);
	}

	@Test
	public void testThat_setJsonApiVersion_doesItsThing() throws MalformedURLException {
		Document res = sut
				.setJsonApiVersion("3.3.3")
				.setMetadata("name", "value")
				.addResource(new Resource("primaryResourceId1","primaryResourceType"))
				.addResource(new Resource("primaryResourceId2","primaryResourceType"))
				.includeResource(new Resource("includedResourceId","includedResourceType"))
				.setLink("self", new URL("http://localhost/"))
				.setLink("related", new URL("http://localhost/"))
				.setLink("first", new URL("http://localhost/"))
				.setLink("last", new URL("http://localhost/"))
				.setLink("prev", new URL("http://localhost/"))
				.setLink("next", new URL("http://localhost/"));
		assertTrue(sut.equals(res));
		assertEquals(
				"{\"data\":[{\"id\":\"primaryResourceId1\",\"type\":\"primaryResourceType\"},{\"id\":\"primaryResourceId2\",\"type\":\"primaryResourceType\"}],\"meta\":{\"name\":\"value\"},\"jsonapi\":{\"version\":\"3.3.3\"},\"links\":{\"next\":\"http://localhost/\",\"related\":\"http://localhost/\",\"last\":\"http://localhost/\",\"prev\":\"http://localhost/\",\"self\":\"http://localhost/\",\"first\":\"http://localhost/\"},\"included\":[{\"id\":\"includedResourceId\",\"type\":\"includedResourceType\"}]}",
				sut.toJson()
		);
	}

	@Test
	public void testThat_setLink_rejectsNullLinkNames() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.setLink(null, new URL("http://localhost/"));
				}
		);
	}

	@Test
	public void testThat_setLink_rejectsNullLinkUrls() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.setLink("mylink", null);
				}
		);
	}

	@Test
	public void testThat_setLink_rejectsUnapprovedLinkNames() {
		assertThrows(
				JsonApiException.class,
				() -> {
					sut.setLink("unapproved", null);
				}
		);
	}
}
