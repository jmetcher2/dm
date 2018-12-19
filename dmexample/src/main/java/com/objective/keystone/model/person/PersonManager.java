package com.objective.keystone.model.person;

import java.util.UUID;

import com.objective.dm.base.DomainObjectCollectionManager;
import com.objective.keystone.model.person.Person;

public class PersonManager extends DomainObjectCollectionManager<Person> {

	@Override
	protected Person instantiate(String name) {
		return new Person(PersonType.user, name, name, UUID.randomUUID().toString());
	}

	@Override
	public Class<Person> getManagedObjectClass() {
		return Person.class;
	}
	

}
