package au.id.lagod.dm.config;

import au.id.lagod.dm.collections.Model;

public abstract class Bootstrapper {


	protected abstract void doBootstrap(Model model);

	public void bootstrap(Model model) {
		if (!model.isBootstrapped()) {
			doBootstrap(model);
		}
		else {
			throw new Error("Attempted to rebootstrap already bootstrapped model");
		}
	}


}