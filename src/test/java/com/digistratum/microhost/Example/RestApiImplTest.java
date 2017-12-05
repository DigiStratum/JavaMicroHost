package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.RestServer.RestApiImpl;
import com.digistratum.microhost.RestServer.RestServer;
import org.junit.jupiter.api.*;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class RestApiImplTest {
	@Inject
	Config mockConfig;
	@Inject
	MySqlConnectionPool mockPool;
	@Inject
	RestServer mockRestServer;

//	TestableMicroHost sut;

	@BeforeEach
	public void setup() throws Exception {
		//ObjectGraph.create(new RestApiTestModule());

		/*
		// Mock our various factories and their prducts
		mockMHConfigFactory = mock(ConfigFactory.class);
		mockMHConfigImpl = mock(ConfigImpl.class);
		doReturn(mockMHConfigImpl).when(mockMHConfigFactory).createMHConfig(anyString());

		mockRestServerFactory = mock(RestServerFactory.class);
		mockServer = mock(RestServerImpl.class);
		doReturn(mockServer).when(mockRestServerFactory).createServer(anyObject());

		mockMySqlConnectionPoolFactory = mock(MySqlConnectionPoolFactory.class);
		mockMySqlConnectionPoolImpl = mock(MySqlConnectionPoolImpl.class);
		doReturn(mockMySqlConnectionPoolImpl).when(mockMySqlConnectionPoolFactory).createMySqlConnectionPool(anyObject());
		sut = new TestableMicroHost(mockConfig, mockPool, mockRestServer);
*/
	}
/*
	@Test
	public void testThatRunLoopsUntilStopped() {
		// FIXME - run enters a perpetual loop (by design), but it's the JUnit test thread
		// which is looping, so it needs to happen asynchronously... or something
		sut.testRun();
		try {
			Thread.sleep(1000);
			assertTrue(sut.isRunning());
			sut.stop();
			Thread.sleep(1000);
			assertFalse(sut.isRunning());
		} catch (Exception e) {
			// Any exception is a failed test
			assertTrue(false);
		}
	}

	private class TestableMicroHost extends RestApiImpl {
		public TestableMicroHost(Config config, MySqlConnectionPool pool, RestServer restServer) {
			super(config, pool, restServer);
		}

		public void testRun() {
			run();
		}
	}
*/
}