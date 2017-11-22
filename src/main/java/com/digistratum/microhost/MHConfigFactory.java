package com.digistratum.microhost;

public class MHConfigFactory {
	public MHConfig createMHConfig(String propsFile) {
		return new MHConfig(propsFile);
	}
}
