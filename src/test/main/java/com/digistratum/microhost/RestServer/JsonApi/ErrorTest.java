package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
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

	@Test
	public void testThat_setters_chainPropertly() {
		Meta meta = new Meta();
		ErrorSource source = new ErrorSource();
		Links links = new Links();
		sut.setId("id")
				.setCode("CODE")
				.setDetail("DETAIL")
				.setStatus("STATUS")
				.setTitle("TITLE")
				.setMeta(meta)
				.setSource(source)
				.setLinks(links)
				.setId("ID");
		String res = sut.toJson();
		assertEquals("{\"id\":\"ID\",\"links\":{},\"status\":\"STATUS\",\"code\":\"CODE\",\"title\":\"TITLE\",\"detail\":\"DETAIL\",\"source\":{},\"meta\":{}}", res);
	}
}
