package com.objective.keystone.model.person.customer.group;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import au.id.lagod.dm.base.AssociationParents;
import au.id.lagod.dm.base.BaseAssociationDomainObject;
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
		return new AssociationParents<CustomerPerson, CustomerPersonGroup, Group>(customerPerson.getCustomerPersonGroups(), group.getCustomerPersonGroups());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerPerson == null) ? 0 : customerPerson.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerPersonGroup other = (CustomerPersonGroup) obj;
		if (customerPerson == null) {
			if (other.customerPerson != null)
				return false;
		} else if (!customerPerson.equals(other.customerPerson))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		return true;
	}
	
	
	
}
