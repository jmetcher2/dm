package com.objective.keystone.model.person.customer.group;

import java.util.Collection;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseDomainObject;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.customer.CustomerPerson;

public class CustomerPersonGroupManager extends AssociationCollectionManager<CustomerPerson, CustomerPersonGroup, Group> {

	public CustomerPersonGroupManager(CustomerPerson parent, Collection<CustomerPersonGroup> c) {
		super(parent, c);
	}

	@Override
	public String getAssociateName() {
		return "group";
	}

	@Override
	protected Class<Group> getAssociateClass() {
		return Group.class;
	}

	@Override
	protected CustomerPersonGroup newAssociationObject(BaseDomainObject associate) {
		return new CustomerPersonGroup(parent, (Group) associate);
	}

	@Override
	public Class<CustomerPersonGroup> getManagedObjectClass() {
		return CustomerPersonGroup.class;
	}

	@Override
	protected Group getAssociate(CustomerPersonGroup ao) {
		return ao.getGroup();
	}
	
	

}
