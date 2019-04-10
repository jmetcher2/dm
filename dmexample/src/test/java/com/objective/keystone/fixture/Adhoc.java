package com.objective.keystone.fixture;

import javax.transaction.Transactional;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

public class Adhoc {

	protected String prefix;

	public Adhoc(String prefix) {
		this.prefix = prefix;
	}
	
	@Transactional
	public void run(Model model) {
		Customer c = model.customers("testCustomer");
		System.out.println("b " + c.getFolders().getAll());
		model.getCustomers().remove(c);
		
	//	Person p = model.persons("xtest");
	//	model.getPersons().remove(p);
	}

}
