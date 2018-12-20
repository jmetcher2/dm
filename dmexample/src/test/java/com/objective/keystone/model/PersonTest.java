package com.objective.keystone.model;

import com.objective.dm.test.TextKeyCollectionPersistenceTests;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.PersonManager;

public class PersonTest extends TextKeyCollectionPersistenceTests<Person> {

	@Override
	protected PersonManager getChildObjectManager() {
		return model.getPersons();
	}

}
