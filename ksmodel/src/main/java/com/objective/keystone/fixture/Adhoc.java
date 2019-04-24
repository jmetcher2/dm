package com.objective.keystone.fixture;

import javax.transaction.Transactional;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;

public class Adhoc {

	protected String prefix;

	public Adhoc(String prefix) {
		this.prefix = prefix;
	}
	
	@Transactional
	public void run(Model model) {
		String[] customerNames = new String[] {"PSTtest2"};
		String[] personNames = new String[] { "PSTtestPerson1", "PSTtestPerson2" };
		
		for (String name: customerNames) {
			Customer c = model.customers(name);
			model.getCustomers().remove(c);
		}
		
		
		for (String name: personNames) {
			Person p = model.persons(name);
			model.getPersons().remove(p);
		}
	}

}
