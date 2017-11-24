package com.digistratum.microhost.Example;

import com.digistratum.microhost.*;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPoolFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class MicroHostTest {
	MHConfigFactory mockMHConfigFactory;
	MHConfig mockMHConfig;

	MySqlConnectionPool mockMySqlConnectionPool;
	MySqlConnectionPoolFactory mockMySqlConnectionPoolFactory;

	ServerFactory mockServerFactory;
	Server mockServer;

	TestableMicroHost sut;

	@BeforeEach
	public void setup() throws Exception {

		// Mock our various factories and their prducts
		mockMHConfigFactory = mock(MHConfigFactory .class);
		mockMHConfig = mock(MHConfig.class);
		doReturn(mockMHConfig).when(mockMHConfigFactory).createMHConfig(anyString());

		mockServerFactory = mock(ServerFactory.class);
		mockServer = mock(Server.class);
		doReturn(mockServer).when(mockServerFactory).createServer(anyObject());

		mockMySqlConnectionPoolFactory = mock(MySqlConnectionPoolFactory.class);
		mockMySqlConnectionPool = mock(MySqlConnectionPool.class);
		doReturn(mockMySqlConnectionPool).when(mockMySqlConnectionPoolFactory).createMySqlConnectionPool(anyObject());

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

	private class TestableMicroHost extends com.digistratum.microhost.MicroHost {
		public void testRun(MHConfigFactory mhcf, MySqlConnectionPoolFactory mscpf, ServerFactory sf) {
			run(mhcf, mscpf, sf);
		}
	}

}