package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinksTest {
	private Gson gson;
	private Links sut;

	@BeforeEach
	public void setup() throws Exception {
		sut = new Links();
		gson = new Gson();
	}

	@Test
	public void testThat_addPaginationLinkFirst_addsALink() throws Exception {
		String link = "http://bananas/";
		Links res = sut.addPaginationLinkFirst(new URL(link));
		assertTrue(sut.equals(res));
		assertEquals(link, sut.get("first"));
	}

	@Test
	public void testThat_addPaginationLinkLast_addsALink() throws Exception {
		String link = "http://bananas/";
		Links res = sut.addPaginationLinkLast(new URL(link));
		assertTrue(sut.equals(res));
		assertEquals(link, sut.get("last"));
	}

	@Test
	public void testThat_addPaginationLinkNext_addsALink() throws Exception {
		String link = "http://bananas/";
		Links res = sut.addPaginationLinkNext(new URL(link));
		assertTrue(sut.equals(res));
		assertEquals(link, sut.get("next"));
	}

	@Test
	public void testThat_addPaginationLinkPrev_addsALink() throws Exception {
		String link = "http://bananas/";
		Links res = sut.addPaginationLinkPrev(new URL(link));
		assertTrue(sut.equals(res));
		assertEquals(link, sut.get("prev"));
	}

	@Test
	public void testThat_gson_toJson_returnsExpectedResult() throws Exception {
		String linkFirst = "http://first/";
		Links res = sut.addPaginationLinkFirst(new URL(linkFirst));
		String linkLast = "http://last/";
		res = res.addPaginationLinkLast(new URL(linkLast));
		String linkNext = "http://next/";
		res = res.addPaginationLinkNext(new URL(linkNext));
		String linkPrev = "http://prev/";
		res = res.addPaginationLinkPrev(new URL(linkPrev));
		assertTrue(sut.equals(res));

		// ref: https://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
		String json = res.toJson();
		PaginationLinks links = gson.fromJson(json, PaginationLinks.class);
		assertEquals(linkFirst, links.first);
		assertEquals(linkLast, links.last);
		assertEquals(linkNext, links.next);
		assertEquals(linkPrev, links.prev);
	}

	private class PaginationLinks {
		String first;
		String last;
		String next;
		String prev;
	}
}
