package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.RestServer.Http.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RestServerImplTest {
	private RestServerImpl sut;
	private Config mockConfig;
	private HttpServerFactory mockHttpServerFactory;


	@BeforeEach
	public void setup() throws Exception {
		mockConfig = mock(Config.class);
		doReturn(1).when(mockConfig).get(anyString(), anyInt());
		mockHttpServerFactory = mock(HttpServerFactory.class);
		doReturn(mock(HttpServer.class)).when(mockHttpServerFactory).getInstance(anyInt());
		sut = new RestServerImpl(mockConfig, mockHttpServerFactory);
	}

	@Test
	public void testThat_addControllerContext_rejectsNullContext() {
		assertThrows(
			MHException.class,
			()-> {
				Controller mockCtrl = mock(Controller.class);
				sut.addControllerContext(mockCtrl, null);
			}
		);
	}
}
