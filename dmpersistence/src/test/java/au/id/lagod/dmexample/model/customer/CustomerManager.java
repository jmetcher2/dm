package au.id.lagod.dmexample.model.customer;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class CustomerManager extends DomainObjectCollectionManager<Customer> {

	@Override
	protected Customer instantiate(String name) {
		return new Customer(name);
	}

	@Override
	public Class<Customer> getManagedObjectClass() {
		return Customer.class;
	}

}
