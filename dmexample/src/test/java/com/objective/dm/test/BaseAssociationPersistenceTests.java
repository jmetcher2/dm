package com.objective.dm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import au.id.lagod.dm.base.AssociationManager;
import au.id.lagod.dm.base.BaseDomainObject;

/**
 * 
 * @author owner
 *
 * @param <ObjectType>
 * @param <ParentType>
 * @param <Parent2Type>
 * 
 * This class adds a second parent to BaseChildObjectPersistenceTests so we can test association objects.  The
 * methods provided by BaseChildObjectPersistenceTests deal with the primary parent.  This class adds methods
 * to deal with the secondary parent.
 * 
 * Because many associations have uniqueness constraints, we need two secondary parents: one for the pre-baked domain object
 * that we set up outside the test transaction, and another for if we want to create domain objects
 * within the test transaction.
 * 
 * Override doSetupBeforeTransaction and doTeardownAfterTransaction to create the secondary parents.  
 * Note that as this is a subclass of BaseChildObjectPersistence tests, your setup and teardown will also need
 * to create the primary parent.
 * 
 * For parent2, the names are important.  You must use parent2Name1 and parent2Name2.
 * 
 * Override getParent2() to return the second instance (i.e. the one named parent2Name2, the one we 
 * did not use to create the test domain object).
 * 
 * Many tests rely on knowing the text ID (i.e. unique name) of the domain object.  
 * For association objects, the unique name is typically the unique name of the secondary parent, not a 
 * property of the association object itself.  You need to override getTextIDName()
 * to provide the dot path to the secondary parent name (e.g. "category.categoryCode").
 * 
 * For association objects that don't define create(String), you may also need to override some or all of getChildObject(),
 * getChildObject2(), getFindKey(), getFindValue(), getTextIDName(), getTextIDValue() and hasTextIDValue().
 * 
 */
public abstract class BaseAssociationPersistenceTests<ObjectType extends BaseDomainObject, ParentType extends BaseDomainObject, Parent2Type extends BaseDomainObject> extends BaseChildObjectPersistenceTests<ObjectType,ParentType> {
	
	protected String parent2Name1 = "p2Obj11";
	protected String parent2Name2 = "p2Obj22";
	
	/*
	 * Base tests
	 */
	
	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testCreateFromParent2ByString_Notfound() {
		org.junit.Assume.assumeTrue(hasTextIDName());

		createChildObject("Bogus");
			
		sf.getCurrentSession().flush();
	}
	
	@Test
	@Ignore
	public void testCreateByObject() {
		// TODO: what's this test doing?  looks like create by string.  Check SVN history
		// to see if it has been refactored away from genuine create by object
		
		ObjectType child = createChildObject2();
		sf.getCurrentSession().flush();
		
		boolean exists = checkRecordExists(child.getId());
		
		assertTrue(exists);
		// assertEquals(2, getChildObjectManager().size());  TODO: reinstate once we've refactored collection managers
		assertEquals(child, getChildObjectManager().get(getParent2()));
	}
	
	/*
	 * Template methods
	 */
	@Override
	protected void doSetupBeforeTransaction() {
		testObjectName = parent2Name1;
		testObject2Name = parent2Name2;
		super.doSetupBeforeTransaction();
	}

	
	/*
	 * Base class methods
	 */
	
	// We redefine the method signature of this method to return an AssociationManager
	// The base DomainObjectManager does not define create(Parent2Type), which we need
	// for our create-by-object tests.
	@Override
	protected abstract AssociationManager<ObjectType,Parent2Type> getChildObjectManager();

	protected abstract Parent2Type getParent2();
	
	
}
