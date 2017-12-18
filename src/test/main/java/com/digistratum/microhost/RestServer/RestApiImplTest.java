package com.digistratum.microhost.RestServer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class RestApiImplTest {
	private RestServer mockRestServer;

	private RestApiImpl sut;

	@BeforeEach
	public void setup() throws Exception {
		mockRestServer = mock(RestServer.class);
		sut = new RestApiImpl(mockRestServer);
	}

	@Test
	public void testThatRunLoopsUntilStopped() throws Exception {

		// Start and monitor...
		Thread t = new Thread(sut, "JUnit: RestApiImpl Instance");
		t.start();
		for (int x = 0; x < 100; x++) {
			Thread.sleep(10);
			assertTrue(sut.isRunning());
		}

		// Request the service to stop and pause for effect...
		sut.stop();
		for (int x = 0; x < 100; x++) {
			Thread.sleep(100);
			if (! sut.isRunning()) break;
		}

		// Verify that we are stopped
		assertFalse(sut.isRunning());
	}
}
