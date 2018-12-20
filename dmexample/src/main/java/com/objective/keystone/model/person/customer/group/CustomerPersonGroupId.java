package com.objective.keystone.model.person.customer.group;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CustomerPersonGroupId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="link_id") Long customerPersonId;
	@Column(name="group_id") Long groupId;
	
	protected CustomerPersonGroupId() {}

	public CustomerPersonGroupId(Long customerPersonId, Long groupId) {
		super();
		this.customerPersonId = customerPersonId;
		this.groupId = groupId;
	}

	public Long getCustomerPersonId() {
		return customerPersonId;
	}

	public Long getGroupId() {
		return groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerPersonId == null) ? 0 : customerPersonId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		CustomerPersonGroupId other = (CustomerPersonGroupId) obj;
		if (customerPersonId == null) {
			if (other.customerPersonId != null)
				return false;
		} else if (!customerPersonId.equals(other.customerPersonId))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		return true;
	}
	
	
}
