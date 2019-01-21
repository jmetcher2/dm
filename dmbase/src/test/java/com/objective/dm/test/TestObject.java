package com.objective.dm.test;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.validators.Restricted;

public class TestObject extends BaseDomainObject {
	
	Integer intField;
	String stringField;
	TestObject nestedObject;
	
	@Restricted
	public TestObject(Integer intField, String stringField, TestObject nestedObject) {
		this.intField = intField;
		this.stringField = stringField;
		this.nestedObject = nestedObject;
	}

	public Long getId() { return null; };
	
	public Integer getIntField() {
		return intField;
	}

	public String getStringField() {
		return stringField;
	}

	public TestObject getNestedObject() {
		return nestedObject;
	}

	public void setIntField(Integer intField) {
		this.intField = intField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public void setNestedObject(TestObject nestedObject) {
		this.nestedObject = nestedObject;
	}
	
	

}