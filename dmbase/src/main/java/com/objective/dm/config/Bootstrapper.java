package com.objective.dm.config;

import com.objective.dm.collections.Model;

public abstract class Bootstrapper {


	protected abstract void doBootstrap(Model model);

	public void bootstrap(Model model) {
		if (!model.isBootstrapped()) {
			doBootstrap(model);
			model.setBootstrapped();
		}
		else {
			throw new Error("Attempted to rebootstrap already bootstrapped model");
		}
	}


}