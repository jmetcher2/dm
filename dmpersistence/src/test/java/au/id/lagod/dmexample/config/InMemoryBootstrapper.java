package au.id.lagod.dmexample.config;

import org.hibernate.SessionFactory;

import au.id.lagod.dm.collections.Model;
import au.id.lagod.dm.config.Bootstrapper;
import au.id.lagod.dm.persistence.TableSet;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;

public class InMemoryBootstrapper extends Bootstrapper {

	private SessionFactory sf;

	public InMemoryBootstrapper(SessionFactory sf) {
		this.sf = sf;
	}


	@Override
	protected void doBootstrap(Model model) {
		ExampleModel dmModel = (ExampleModel) model;
		dmModel.getCustomers().setCollection(new TableSet<Customer>(sf, Customer.class));
	}

}
