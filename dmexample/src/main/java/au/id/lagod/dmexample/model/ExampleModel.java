package au.id.lagod.dmexample.model;

import au.id.lagod.dm.config.Bootstrapper;
import au.id.lagod.dmexample.model.customer.CustomerManager;

public class ExampleModel extends au.id.lagod.dm.collections.Model {
	
	/*
	 * SINGLETON STUFF
	 */
	public static ExampleModel getModel(Bootstrapper bootstrapper) {
		ExampleModel model = new ExampleModel();
		bootstrapper.bootstrap(model);
		return model;
	}
	
	private CustomerManager customers = new CustomerManager();

	public CustomerManager getCustomers() {
		return customers;
	}

}
