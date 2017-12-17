package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.RestServer.RestApiImpl;
import com.digistratum.microhost.RestServer.RestServer;
import com.digistratum.microhost.RestServer.RestServerSetterUpper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class RestApiImplTest {
	private Config mockConfig;
	private MySqlConnectionPool mockPool;
	private RestServer mockRestServer;
	private RestServerSetterUpper mockRestServerSetterUpper;

	private RestApiImpl sut;

	@BeforeEach
	public void setup() throws Exception {
		mockConfig = mock(Config.class);
		mockPool = mock(MySqlConnectionPool.class);
		mockRestServer = mock(RestServer.class);
		mockRestServerSetterUpper = mock(RestServerSetterUpper.class);
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
