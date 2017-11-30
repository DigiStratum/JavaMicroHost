package com.digistratum.microhost.Example;

public class ExampleHost {

	/*
	 * Application entry point
	 *
	 * ref: https://github.com/google/dagger/blob/master/examples/simple/src/main/java/coffee/CoffeeApp.java
	 * ref: https://medium.com/@Zhuinden/that-missing-guide-how-to-use-dagger2-ef116fbea97
	 * ref: https://github.com/raphaelbrugier/dagger2-sample
	 */
	public static void main(String[] args) {
		RestApiImpl restApiImpl = DaggerExampleHost_RestApiComponent
				.builder()
				.restApiModule( new RestApiModule())
				.build();
		restApiImpl.run();
	}
}
