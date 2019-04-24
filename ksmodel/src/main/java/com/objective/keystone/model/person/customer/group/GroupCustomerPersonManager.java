package com.objective.keystone.model.person.customer.group;

import java.util.Collection;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.BaseDomainObject;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.customer.CustomerPerson;

public class GroupCustomerPersonManager extends AssociationCollectionManager<Group, CustomerPersonGroup, CustomerPerson> {

	public GroupCustomerPersonManager(Group parent, Collection<CustomerPersonGroup> c) {
		super(parent, c);
	}

	@Override
	public String getAssociateName() {
		return "customerPerson";
	}

	@Override
	protected Class<CustomerPerson> getAssociateClass() {
		return CustomerPerson.class;
	}

	@Override
	protected CustomerPersonGroup newAssociationObject(BaseDomainObject associate) {
		return new CustomerPersonGroup((CustomerPerson) associate, parent);
	}

	@Override
	public Class<CustomerPersonGroup> getManagedObjectClass() {
		return CustomerPersonGroup.class;
	}

	@Override
	protected CustomerPerson getAssociate(CustomerPersonGroup ao) {
		return ao.getCustomerPerson();
	}

}
