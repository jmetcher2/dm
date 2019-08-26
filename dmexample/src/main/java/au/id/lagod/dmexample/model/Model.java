package au.id.lagod.dmexample.model;

import au.id.lagod.dm.config.Bootstrapper;

public class Model extends au.id.lagod.dm.collections.Model {
	
	/*
	 * SINGLETON STUFF
	 */
	public static Model getModel(Bootstrapper bootstrapper) {
		Model model = new Model();
		bootstrapper.bootstrap(model);
		return model;
	}
	

}
