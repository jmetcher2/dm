package au.id.lagod.dmexample.model.group;

import java.util.Set;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dmexample.model.customer.Customer;

public class GroupManager extends DomainObjectCollectionManager<Group> {
	
	private Customer customer;
	
	public GroupManager(Customer customer, Set<Group> g) {
		super(g);
		this.customer = customer;
	}

	@Override
	protected Group instantiate(String name) {
		return new Group(customer, name);
	}

	@Override
	public Class<Group> getManagedObjectClass() {
		return Group.class;
	}

}
