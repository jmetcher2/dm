package com.objective.keystone.model.person.customer.group;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.objective.dm.base.AssociationParents;
import com.objective.dm.base.BaseAssociationDomainObject;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.customer.CustomerPerson;

@Entity
@Table(name="publisher_group_person_lnk")
public class CustomerPersonGroup extends BaseAssociationDomainObject<CustomerPerson, Group> {
	
	@EmbeddedId
	private CustomerPersonGroupId id = new CustomerPersonGroupId();

	@MapsId("groupId")
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	@MapsId("customerPersonId")
	@ManyToOne
	@JoinColumn(name="link_id")
	private CustomerPerson customerPerson;
	
	protected CustomerPersonGroup() {}

	public CustomerPersonGroup(CustomerPerson customerPerson, Group group) {
		super();
		this.group = group;
		this.customerPerson = customerPerson;
	}

	@Override
	public AssociationParents<CustomerPerson, ? extends BaseAssociationDomainObject<CustomerPerson, Group>, Group> getAssociationParents() {
		return new AssociationParents<CustomerPerson, CustomerPersonGroup, Group>(customerPerson.getGroups(), group.getCustomerPersons());
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	public Group getGroup() {
		return group;
	}


	public CustomerPerson getCustomerPerson() {
		return customerPerson;
	}
	
}
