package com.objective.dm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.LockMode;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.junit.Ignore;
import org.junit.Test;

import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.DomainCollectionManager;
import com.objective.dm.base.DomainObjectManager;
import com.objective.dm.base.Utility;

/**
 * 
 * @author owner
 *
 * @param <ObjectType>
 * 
 * Provide an object manager by overriding getChildObjectManager().
 * This should be a singleton object manager that's available through the Spring context
 * If the object under test is a child of a transient object, you should be using 
 * BaseChildObjectPersistenceTest instead.
 * 
 * A child object instance will be created outside the test transaction, referenced by testObjectName.
 * Tests can reference this object via getChildObjectManager().get(testObjectName).  Note that the instance variable 
 * domainObject is there for reflection purposes, and should not be used as a test target.
 * 
 * If a test needs to create a test object, rather than use the pre-baked one, it can call createChildObject2()
 * 
 */
public abstract class BaseCollectionPersistenceTests<ObjectType extends BaseDomainObject> extends BasePersistenceTests<ObjectType> {
	
	protected String testObjectName = "xtest";
	protected String testObject2Name = "xtes2";
	
	/*
	 * Base tests
	 */
	
	
	@Test
	/*
	 * Note: this implicitly tests the create() method as well as get()
	 */
	public void testGet() {
		
		ObjectType domainObject2 = getChildObject();
		
		assertTrue(domainObject2 != null);
		
		if (hasTextIDName()) {
			assertEquals(getTestObjectName(), getTextIDValue(domainObject2));
		}
		assertEquals(domainObject.getId(), domainObject2.getId());

	}
	
	@Test
	public void testGetByID() {
		
		//ObjectType domainObject2 = (ObjectType) getChildObjectManager().findByID(domainObject.getId());
		
		//if (hasTextIDName()) {
			//assertEquals(testObjectName, getTextIDValue(domainObject2));
		//}
		//assertEquals(domainObject.getId(), domainObject2.getId());

	}
	
	@Test
	public void testGetAttached() {
		sf.getCurrentSession().update(domainObject);
		
		ObjectType domainObject2 = getChildObject();
		
		if (hasTextIDName()) {
			assertEquals(getTestObjectName(),  getTextIDValue(domainObject2));
		}
		assertEquals(domainObject.getId(), domainObject2.getId());
		assertEquals(domainObject, domainObject2);

	}

	@Test
	public void testDelete() {
		ObjectType obj = createChildObject2();
		
		sf.getCurrentSession().flush();
		
		getChildObjectManager().remove(obj);
		
		ObjectType obj2 = getChildObject2();
		
		assertEquals(null, obj2);
	}
	
	@Test
	public void testDeletePersisted() {
		ObjectType obj = createChildObject2();
		
		sf.getCurrentSession().flush();
		
		getChildObjectManager().remove(obj);
		
		ObjectType obj2 = getChildObject2();
		
		assertEquals(null, obj2);
	}
	
	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testCreateDuplicate() {
		
		createChildObject2();
		
		//sf.getCurrentSession().flush();

		createChildObject2();
		
	}
	
	@Test
	public void testFindOneArray() {
		ObjectType obj2 = getChildObjectManager().findOne(new String[] {getFindKey()},new Object[] {getFindValue()});

		if (hasTextIDName()) {
			assertEquals(getTestObjectName(), getTextIDValue(obj2));
		}
		assertEquals(domainObject.getId(), obj2.getId());
	}
	
	@Test
	public void testFindOneMap() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(getFindKey(),getFindValue());
		ObjectType obj2 = getChildObjectManager().findOne(params);

		if (hasTextIDName()) {
			assertEquals(getTestObjectName(), getTextIDValue(obj2));
		}
		assertEquals(domainObject.getId(), obj2.getId());
	}
	
	
	@Test
	public void testFindArray() {
		List<ObjectType> results = getChildObjectManager().find(new String[] {getFindKey()},new Object[] {getFindValue()});

		assertEquals(1, results.size());
		ObjectType obj2 = results.get(0);
		if (hasTextIDName()) {
			assertEquals(getTestObjectName(), getTextIDValue(obj2));
		}
		assertEquals(domainObject.getId(), obj2.getId());
	}
	
	@Test
	public void testFindMap() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(getFindKey(),getFindValue());
		List<ObjectType> results = getChildObjectManager().find(params);

		assertEquals(1, results.size());
		ObjectType obj2 = results.get(0);
		if (hasTextIDName()) {
			assertEquals(getTestObjectName(), getTextIDValue(obj2));
		}
		assertEquals(domainObject.getId(), obj2.getId());
	}
	
	@Test 
	public void testGetAll() {
		List<ObjectType> results = getChildObjectManager().getAll();

		// TODO: really only a smoke test at this stage
		assertTrue(results.size() > 0);
	}
	
	/*
	 * Template methods
	 */
	protected abstract DomainCollectionManager<ObjectType> getChildObjectManager();
	
	protected String getTestObjectName() {
		return testObjectName;
	}

	protected void setTestObjectName(String testObjectName) {
		this.testObjectName = testObjectName;
	}

	protected String getTestObject2Name() {
		return testObject2Name;
	}

	protected void setTestObject2Name(String testObject2Name) {
		this.testObject2Name = testObject2Name;
	}

	protected String getFindKey() {
		try {
			return getTextIDName();
		}
		catch (Exception e) {
			throw new Error("This class has no text key defined.  Either annotate the text key field with @TextKey, or subclass getFindKey and getFindValue in your test class to provide alternative targets for testing the finder functions");
		}
	}
	
	protected Object getFindValue() {
		return getTextIDValue(domainObject);
	}
	
	/*
	 * For top level objects the parent is the service singleton.
	 * For other objects the parent will be another domain object. For those cases,
	 * override getParentObjectManager to return a manager that can create a parent object 
	 */
	
	protected void doSetupBeforeTransaction() {
		domainObject = createChildObject();
	}

	protected void doTeardownAfterTransaction() {
		getChildObjectManager().remove(getChildObject(getTestObjectName()));
	}
	
	
	protected ObjectType createChildObject(String name) {
		return getChildObjectManager().create(name);
	}
	
	protected ObjectType createChildObject() {
		return createChildObject(getTestObjectName());
	}

	protected ObjectType createChildObject2() {
		return createChildObject(getTestObject2Name());
	}
	
	protected ObjectType getChildObject(String name) {
		return getChildObjectManager().get(name);
	}
	
	protected ObjectType getChildObject() {
		return getChildObject(getTestObjectName());
	}
	
	protected ObjectType getChildObject2() {
		return getChildObject(getTestObject2Name());
	}
	
	/*
	 * Base class methods
	 */
	
	protected String getTextIDName() {
		String textIDName = BaseDomainObject.getTextKeyField(domainObject.getClass());

		if (textIDName == null) {
			throw new Error("No text key found.  Either override getTextIDName() or hasTextIDName() in your test class, or annotate the text key field of your entity class (" + domainObject.getClass().getName() + ")");
		}
		
		return textIDName;
	}
	
	protected boolean hasTextIDName() {
		String textIDName = BaseDomainObject.getTextKeyField(domainObject.getClass());
		return textIDName != null;
	}
	
	protected  String getTextIDValue(ObjectType o) {
		return domainObject.getTextKey();
	}
	
	protected boolean checkRecordExists(Long id) {
		ClassMetadata hibernateMetadata = sf.getClassMetadata(domainObject.getClass().getName());

		if (hibernateMetadata == null)
		{
		    return false;
		}

		if (hibernateMetadata instanceof AbstractEntityPersister)
		{
		     AbstractEntityPersister persister = (AbstractEntityPersister) hibernateMetadata;
		     String tableName = persister.getTableName();
		     String[] columnNames = persister.getKeyColumnNames();
		     String keyName = columnNames[0]; // assumes no composite keys
		     
		     String qry = "select count(*) from " + tableName + " where " + keyName + " =  ?";
			
		     Integer count = jdbcTemplate.queryForObject(qry, new Object[] {id}, Integer.class);
		     
			return count != null && count != 0;
		}		
		
		return false;
		
	}
	
}
