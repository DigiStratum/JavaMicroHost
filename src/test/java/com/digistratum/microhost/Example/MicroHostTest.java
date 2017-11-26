package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Config.ConfigFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolFactory;
import com.digistratum.microhost.RestServer.RestServerImpl;
import com.digistratum.microhost.RestServer.ServerFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MicroHostTest {
	ConfigFactory mockMHConfigFactory;
	ConfigImpl mockMHConfigImpl;

	MySqlConnectionPoolImpl mockMySqlConnectionPoolImpl;
	MySqlConnectionPoolFactory mockMySqlConnectionPoolFactory;

	ServerFactory mockServerFactory;
	RestServerImpl mockServer;

	TestableMicroHost sut;

	@BeforeEach
	public void setup() throws Exception {

		// Mock our various factories and their prducts
		mockMHConfigFactory = mock(ConfigFactory.class);
		mockMHConfigImpl = mock(ConfigImpl.class);
		doReturn(mockMHConfigImpl).when(mockMHConfigFactory).createMHConfig(anyString());

		mockServerFactory = mock(ServerFactory.class);
		mockServer = mock(RestServerImpl.class);
		doReturn(mockServer).when(mockServerFactory).createServer(anyObject());

		mockMySqlConnectionPoolFactory = mock(MySqlConnectionPoolFactory.class);
		mockMySqlConnectionPoolImpl = mock(MySqlConnectionPoolImpl.class);
		doReturn(mockMySqlConnectionPoolImpl).when(mockMySqlConnectionPoolFactory).createMySqlConnectionPool(anyObject());

		sut = new TestableMicroHost();
	}

	public void testThatRunLoopsUntilStopped() {
		// FIXME - run enters a perpetual loop (by design), but it's the JUnit test thread
		// which is looping, so it needs to happen asynchronously... or something
		sut.testRun(mockMHConfigFactory, mockMySqlConnectionPoolFactory, mockServerFactory);
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

	private class TestableMicroHost extends MicroHost {
		public void testRun(ConfigFactory mhcf, MySqlConnectionPoolFactory mscpf, ServerFactory sf) {
			run(mhcf, mscpf, sf);
		}
	}

}