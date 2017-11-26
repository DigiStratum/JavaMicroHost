package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.Exception.MHException;

public interface RestServer {

	/**
	 * Add a controller context to this server
	 *
	 * A controller is responsible for all request URIs relative to the context path
	 *
	 * @param ctrl ControllerBaseImpl instance to do the work
	 * @param ctx String context base URI to map this controller to
	 *
	 * @throws MHException
	 */
	public void addControllerContext(Controller ctrl, String ctx) throws MHException;

	/**
	 * Remove a context which is already defined
	 *
	 * @param ctx String context base URI to which this controller is mapped
	 *
	 * @throws MHException
	 */
	public void removeContext(String ctx) throws MHException;

	/**
	 * Check whether the specified context is already defined
	 *
	 * Note that this does not evaluate the URI to see if some other less specific context maps to this...
	 *
	 * @param ctx String context base URI which we want to check
	 *
	 * @return boolean true if the context is defined, else false
	 */
	public boolean hasContext(String ctx);

	/**
	 * RestServerImpl stopper
	 */
	public void stop();
}
