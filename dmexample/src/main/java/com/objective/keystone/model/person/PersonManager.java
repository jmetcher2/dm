package com.objective.keystone.model.person;

import java.util.UUID;

import com.objective.keystone.model.person.Person;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class PersonManager extends DomainObjectCollectionManager<Person> {

	@Override
	public Person create(String name) {
		Person u = new Person(PersonType.user, name, name, UUID.randomUUID().toString());
		add(u);
		return u;
	}

	@Override
	public Class<Person> getManagedObjectClass() {
		return Person.class;
	}
	

}
