package com.objective.keystone.config;

import org.hibernate.SessionFactory;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.person.Person;

import au.id.lagod.dm.config.Bootstrapper;
import au.id.lagod.dm.persistence.TableSet;

public class DBBootstrapper extends Bootstrapper {

	private SessionFactory sf;

	public DBBootstrapper(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public void doBootstrap(au.id.lagod.dm.collections.Model model) {
		// model.getCountries().setCollection(new TableSet<Country>(sf, Country.class, "Country"));
		Model keystoneModel = (Model) model;
		keystoneModel.getPersons().setCollection(new TableSet<Person>(sf, Person.class));
		keystoneModel.getCustomers().setCollection(new TableSet<Customer>(sf, Customer.class));
	}

}
