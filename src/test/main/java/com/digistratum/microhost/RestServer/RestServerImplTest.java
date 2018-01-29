package com.digistratum.microhost.RestServer;

import com.digistratum.Config.Config;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.RestServer.Http.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
	private Controller mockCtrl;

	@BeforeEach
	public void setup() throws Exception {
		mockConfig = mock(Config.class);
		doReturn(1).when(mockConfig).get(anyString(), anyInt());
		mockHttpServerFactory = mock(HttpServerFactory.class);
		mockHttpServer = mock(HttpServer.class);
		doReturn(mockHttpServer).when(mockHttpServerFactory).getInstance(anyInt());
		mockCtrl = mock(Controller.class);
		sut = new RestServerImpl(mockConfig, mockHttpServerFactory);
	}

	@Test
	public void testThat_controller_initializesAnHttpServer() throws Exception {
		verify(mockHttpServerFactory, times(1)).getInstance(anyInt());
		verify(mockHttpServer, times(1)).setExecutor(anyObject());
		verify(mockHttpServer, times(1)).start();
	}

	@Test
	public void testThat_controller_disablesDefaultEndpointsByDefault() throws Exception {
		assertFalse(sut.hasContext("/microhost"));
	}

	@Test
	public void testThat_controller_enablesDefaultEndpointsIfConfigured() throws Exception {
		doReturn("on").when(mockConfig).get(eq("microhost.context.microhost"), anyString());
		sut = new RestServerImpl(mockConfig, mockHttpServerFactory);
		assertTrue(sut.hasContext("/microhost"));
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
		assertThrows(
				MHException.class,
				()-> {
					sut.addControllerContext(mockCtrl, null);
				}
		);
	}

	@Test
	public void testThat_addControllerContext_rejectsEmptyContext() {
		assertThrows(
				MHException.class,
				()-> {
					sut.addControllerContext(mockCtrl, "");
				}
		);
	}

	@Test
	public void testThat_addControllerContext_rejectsDuplicateContext() throws Exception {
		String context = "/testContext";
		sut.addControllerContext(mockCtrl, context);
		assertThrows(
				MHException.class,
				() -> {
					sut.addControllerContext(mockCtrl, context);
				}
		);
	}

	@Test
	public void testThat_addControllerContext_addsContextToServer() throws Exception {
		sut.addControllerContext(mockCtrl, "/testContext");
		// Note: sut constructor calls createContext() for "/" as well, so it's our call + that one: 2
		verify(mockHttpServer, times(2)).createContext(anyString(), anyObject());
	}

	@Test
	public void testThat_hasContext_rejectsNullContext() {
		assertThrows(
				MHException.class,
				()-> {
					sut.hasContext( "");
				}
		);
	}

	@Test
	public void testThat_hasContext_rejectsEmptyContext() {
		assertThrows(
				MHException.class,
				()-> {
					sut.hasContext( null);
				}
		);
	}

	@Test
	public void testThat_hasContext_nacksUndefinedContext() throws Exception {
		assertFalse(sut.hasContext( "/missingContext"));
	}

	@Test
	public void testThat_hasContext_acksDefinedContext() throws Exception {
		String context = "/testContext";
		sut.addControllerContext(mockCtrl, context);
		assertTrue(sut.hasContext( context));
	}

	@Test
	public void testThat_removeContext_rejectsUndefinedContext() {
		assertThrows(
				MHException.class,
				()-> {
					sut.removeContext( "/missingContext");
				}
		);
	}

	@Test
	public void testThat_removeContext_removesDefinedContext() throws Exception {
		String context = "/testContext";
		sut.addControllerContext(mockCtrl, context);
		sut.removeContext( context);
		verify(mockHttpServer, times(1)).removeContext(anyString());
	}

	@Test
	public void testThat_stop_stopsTheServer() {
		sut.stop();
		verify(mockHttpServer, times(1)).stop(anyInt());
	}
}
