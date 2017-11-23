package com.digistratum.microhost;

import com.digistratum.microhost.Database.Mysql.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPoolFactory;
import org.junit.jupiter.api.BeforeAll;

import static org.mockito.Mockito.mock;

class MicroHostTest {


	MHConfigFactory mockMHConfigFactory;
	MySqlConnectionPoolFactory mockMySqlConnectionPoolFactory;
	ServerFactory mockServerFactory;

	MySqlConnectionPool mockMySqlConnectionPool;

	@BeforeAll
	public void setup() {
		mockMHConfigFactory = mock(MHConfigFactory .class);
		mockMySqlConnectionPoolFactory = mock(MySqlConnectionPoolFactory.class);
		mockServerFactory = mock(ServerFactory.class);
		
		mockMySqlConnectionPool = mock(MySqlConnectionPool.class);

	}

	public void testThatRunDoesSomething() {

	}

	private class TestableMicroHost extends MicroHost {
		@Override
		public void run(MHConfigFactory mhcf, MySqlConnectionPoolFactory mscpf, ServerFactory sf) {}
	}

}