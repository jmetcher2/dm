package com.objective.keystone.model;


import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerManager;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;

import au.id.lagod.dm.config.Bootstrapper;

public class Model extends au.id.lagod.dm.collections.Model {
	
	/*
	 * SINGLETON STUFF
	 */
	private static Model model;
	
	public static Model getModel(Bootstrapper bootstrapper) {
		if (model == null) {
			// TODO: make this thread-safe
			model = new Model();
			bootstrapper.bootstrap(model);
		}
		return model;
	}
	
	public static Model getModel() {
		if (model == null) {
			throw new Error("Model not bootstrapped");
		}
		return model;
	}
	
	public static void resetModel() {
		model = null;
	}

	private PersonManager persons = new PersonManager();
	private CustomerManager customers = new CustomerManager();
	
	/*
	 * Base collections
	 */
	
	public PersonManager getPersons() {
		return persons;
	}
	
	public Person persons(String code) {
		return getPersons().get(code);
	}
	
	public CustomerManager getCustomers() {
		return customers;
	}
	
	public Customer customers(String identifier) {
		return getCustomers().get(identifier);
	}

}
