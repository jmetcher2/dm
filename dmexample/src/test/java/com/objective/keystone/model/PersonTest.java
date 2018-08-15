package com.objective.keystone.model;

import com.objective.dm.test.BaseCollectionPersistenceTests;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;

public class PersonTest extends BaseCollectionPersistenceTests<Person> {

	@Override
	protected PersonManager getChildObjectManager() {
		return model.getPersons();
	}

}
