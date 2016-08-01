package au.id.lagod.dmexample.di;

import au.id.lagod.dmexample.collections.Model;

public class ModelFactory extends au.id.lagod.dm.di.ModelFactory<Model> {

	static protected Model instantiateModel() {
		return new Model();
	}
}
