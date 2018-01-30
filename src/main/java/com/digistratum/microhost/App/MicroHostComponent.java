package com.digistratum.microhost.App;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MicroHostModule.class})
public interface MicroHostComponent {
	MicroHostApp getMicroHostApp();
}
