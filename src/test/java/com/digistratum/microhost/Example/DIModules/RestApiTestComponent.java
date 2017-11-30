package com.digistratum.microhost.Example.DIModules;

import com.digistratum.microhost.Example.RestApiImpl;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = RestApiTestModule.class)
public interface RestApiTestComponent {
	RestApiImpl restApi();
}