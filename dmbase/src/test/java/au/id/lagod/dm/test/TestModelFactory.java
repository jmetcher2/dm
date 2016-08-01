package au.id.lagod.dm.test;

import au.id.lagod.dm.di.ModelFactory;
import au.id.lagod.dm.di.ModelInjector;

public class TestModelFactory extends ModelFactory {

	static public void resetModel() {
		ModelInjector injector = ModelInjector.aspectOf();
		injector.resetModel();
	}

}
