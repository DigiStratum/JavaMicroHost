package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.RestServer.Http.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RestServerImplTest {
	private RestServerImpl sut;
	private Config mockConfig;
	private HttpServerFactory mockHttpServerFactory;
	private HttpServer mockHttpServer;

	@BeforeEach
	public void setup() throws Exception {
		mockConfig = mock(Config.class);
		doReturn(1).when(mockConfig).get(anyString(), anyInt());
		mockHttpServerFactory = mock(HttpServerFactory.class);
		mockHttpServer = mock(HttpServer.class);
		doReturn(mockHttpServer).when(mockHttpServerFactory).getInstance(anyInt());
		sut = new RestServerImpl(mockConfig, mockHttpServerFactory);
	}

	@Test
	public void testThat_addControllerContext_rejectsNullController() {
		assertThrows(
				MHException.class,
				()-> {
					sut.addControllerContext(null, "imaginary context");
				}
		);
	}

	@Test
	public void testThat_addControllerContext_rejectsNullContext() {
		Controller mockCtrl = mock(Controller.class);
		assertThrows(
				MHException.class,
				()-> {
					sut.addControllerContext(mockCtrl, null);
				}
		);
	}

	@Test
	public void testThat_addControllerContext_rejectsEmptyContext() {
		Controller mockCtrl = mock(Controller.class);
		assertThrows(
				MHException.class,
				()-> {
					sut.addControllerContext(mockCtrl, "");
				}
		);
	}

	@Test
	public void testThat_addControllerContext_rejectsDuplicateContext() {
		String context = "/testContext";
		Controller mockCtrl = mock(Controller.class);
		try {
			sut.addControllerContext(mockCtrl, context);
		} catch (MHException e) {
			// Shouldn't happen, but just in case...
			assertTrue(false);
		}
		assertThrows(
				MHException.class,
				() -> {
					sut.addControllerContext(mockCtrl, context);
				}
		);
	}

	@Test
	public void testThat_addControllerContext_addsContextToServer() {
		String context = "/testContext";
		Controller mockCtrl = mock(Controller.class);
		try {
			sut.addControllerContext(mockCtrl, context);
		} catch (MHException e) {
			// Shouldn't happen, but just in case...
			assertTrue(false);
		}
		verify(mockHttpServer, times(1)).createContext(anyString(), anyObject());
	}
}
