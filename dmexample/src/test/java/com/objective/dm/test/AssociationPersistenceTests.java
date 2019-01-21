package com.objective.dm.test;

import com.objective.dm.base.AssociationCollectionManager;
import com.objective.dm.base.BaseAssociationDomainObject;
import com.objective.dm.base.BaseDomainObject;
import com.objective.dm.base.DomainCollectionManager;
import com.objective.dm.base.DomainObjectCollectionManager;

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
 * Example:
 * 
  	@Override
	protected void doSetupBeforeTransaction(){
		Customer c = model.getCustomers().create(getParentName());
		Person p = model.getPersons().create(parent2Name1);
		model.getPersons().create(parent2Name2);

		domainObject = c.getCustomerPersons().create(p);
		super.doSetupBeforeTransaction();
	}
	
	@Override
	protected void doTeardownAfterTransaction(){
		model.getPersons().remove(model.persons(parent2Name1));
		model.getPersons().remove(model.persons(parent2Name2));
		model.getCustomers().remove(getParent());
		super.doTeardownAfterTransaction();
	}


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
public abstract class AssociationPersistenceTests<ObjectType extends BaseAssociationDomainObject, Parent1Type extends BaseDomainObject, Parent2Type extends BaseDomainObject> extends CollectionPersistenceTests<ObjectType> {
	
	protected String parent1Name = "parent1";
	protected String parent2Name = "parent2";
	protected String parent2InTestName = "parent2InTest";

	/*
	 * Base class methods
	 */
	
	// We redefine the method signature of this method to return an AssociationManager
	// The base DomainObjectManager does not define create(Parent2Type), which we need
	// for our create-by-object tests.
	@Override
	protected abstract AssociationCollectionManager<Parent1Type, ObjectType, Parent2Type> getChildObjectManager();
	
	protected abstract DomainCollectionManager<Parent1Type> getParent1Manager();
	protected abstract DomainCollectionManager<Parent2Type> getParent2Manager();
	
	protected abstract Parent1Type getParent1();
	protected abstract Parent2Type getParent2();
	protected abstract Parent2Type getParent2InTest();
	
	
	protected ObjectType createChildObject() {
		return getChildObjectManager().create(getParent2());
	}
	
	protected ObjectType getChildObject() {
		return getChildObjectManager().getAssociationWith(getParent2());
	}


	protected void removeParent1() {
		getParent1Manager().remove(getParent1());
		
	}

	protected void removeParent2() {
		getParent2Manager().remove(getParent2());
	}
	
	protected ObjectType getChildObjectInTest() {
		return getChildObjectManager().getAssociationWith(getParent2InTest());
	}

	protected ObjectType createChildObjectInTest() {
		return getChildObjectManager().create(getParent2InTest());
	}





}
