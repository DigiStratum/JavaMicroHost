package com.digistratum.microhost.RestServer;

/**
 * A class to be implemented by the application layer to set up the RestServer with all the config,
 * controllers, and anything else necessary to get running.
 */
public interface RestServerSetterUpper {

	/**
	 * Add all the contexts to our RestServer
	 *
	 * @param restServer RestServer instance that we want to get set up
	 */
	void addContexts(RestServer restServer);

}
