package com.objective.dm.test;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.objective.dm.base.BaseDomainObject;


/**
 * 
 * @author owner
 *
 * @param <ObjectType>
 * @param <ParentType>
 * 
 * Use this class for testing objects that are children of transients.  In addition to the functionality of 
 * BasePersistenceTests, we need to create and teardown the parent object.
 * 
 * Override doSetupBeforeTransaction() and doTeardownAfterTransaction() to create and destroy the 
 * transient parent.  There's no provision to create parents
 * within the test - you should be doing this in the test class for the parent object if it's required.  
 * You should also override getParent() to return the parent object, and removeParent(), 
 * which is needed for cascade tests.
 * 
 * You can use the parentName variable as a convenient parent object name.  It will never be used by any standard tests,
 * which will go through getParent() instead. 
 * 
 */
public abstract class BaseChildObjectPersistenceTests<ObjectType extends BaseDomainObject, ParentType extends BaseDomainObject> extends BaseCollectionPersistenceTests<ObjectType> {
	
	private String parentName = "parentObj";
	
	/*
	 * Base tests
	 */
	
	
	@Test
	public void testCascade() {
		Long id = getChildObject().getId();
		
		removeParent();
		
		sf.getCurrentSession().flush();
		
		assertFalse(checkRecordExists(id));
	}

	
	/*
	 * Template methods
	 */

	protected abstract void removeParent();
	
	public abstract ParentType getParent();

	/*
	 * Base class methods
	 */
	
	protected String getParentName() {
		return parentName;
	}
	
	
}
