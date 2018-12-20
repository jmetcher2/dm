package com.objective.keystone.model.person.customer.group;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.customer.CustomerPerson;

public class GroupCustomerPersonManager extends AssociationCollectionManager<Group, CustomerPersonGroup, CustomerPerson> {

	public GroupCustomerPersonManager(Group parent) {
		super(parent);
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
