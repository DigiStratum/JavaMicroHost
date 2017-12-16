package com.digistratum.microhost.Example;

import com.digistratum.microhost.RestServer.DaggerRestApiComponent;
import com.digistratum.microhost.RestServer.RestApiComponent;
import com.digistratum.microhost.RestServer.RestApiImpl;
import com.digistratum.microhost.Example.Api.RestApiModule;

public class ExampleHost {

	/*
	 * Application entry point
	 *
	 * App -> API -> Controller -> Endpoint -> [Service Layer] -> model + conn -> database
	 *
	 * ref: https://github.com/google/dagger/blob/master/examples/simple/src/main/java/coffee/CoffeeApp.java
	 * ref: https://medium.com/@Zhuinden/that-missing-guide-how-to-use-dagger2-ef116fbea97
	 * ref: https://github.com/raphaelbrugier/dagger2-sample
	 */
	public static void main(String[] args) {
		RestApiComponent restApiComponent = DaggerRestApiComponent.builder()
				.restApiModule( new RestApiModule())
				.build();
		new ExampleHost((RestApiImpl) restApiComponent.getRestApi());
	}

	/**
	 * Constructor
	 *
	 * Starts our service application using Constructor injection
	 *
	 * @param restApi RestApiImpl instance which is ready to go
	 */
	public ExampleHost(RestApiImpl restApi) {
		restApi.run();
	}
}
