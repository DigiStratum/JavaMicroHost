import com.digistratum.microhost.Example.ExampleHost;
import com.digistratum.microhost.RestServer.RestApiImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * ref: FAIL: https://stackoverflow.com/questions/16570029/mockito-mock-objects-returns-null
 * ref: https://medium.com/@fabioCollini/android-testing-using-dagger-2-mockito-and-a-custom-junit-rule-c8487ed01b56
 * ref: https://github.com/fabioCollini/DaggerMock
 */
public class ExampleHostTest {
	private ExampleHost sut;

	private RestApiImpl mockRestApiImpl;

	@BeforeEach
	public void setup() throws Exception {
		mockRestApiImpl = mock(RestApiImpl.class);
		sut = new ExampleHost(mockRestApiImpl);
	}

	@Test
	public void testThatExampleHostStartsApi() {
		verify(mockRestApiImpl, times(1)).run();
	}
}