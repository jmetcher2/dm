package au.id.lagod.dm.di;

import au.id.lagod.dm.collections.Model;
import au.id.lagod.dm.config.Bootstrapper;

 public abstract class ModelFactory<T extends Model> {
	 
	 static Object model;
	
	// TODO: I'm undecided as to whether to support the ModelInjector/ModelClient approach, 
	// or just stash the Model in a static field in this class and let any domain object
	// call ModelFactory.getModel() directly.  Will defer this decision until after the persistence layer
	// is implemented, as it might come down to which approach plays best with Hibernate
	static public <T extends Model> T getModel() {
		if (model == null) {
			model = instantiateModel();
		}
		return (T) model;
	}
	
	static public boolean hasModel() {
		return model != null;
	}
	
	static public void initModel(Bootstrapper bootstrapper) {
		bootstrapper.bootstrap(getModel());
	}
	
	static protected <T extends Model> T instantiateModel() {return null;};
	
}
