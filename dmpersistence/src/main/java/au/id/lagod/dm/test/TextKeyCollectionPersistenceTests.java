package au.id.lagod.dm.test;

import static org.junit.Assert.assertEquals;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.DomainObjectCollectionManager;

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
public abstract class TextKeyCollectionPersistenceTests<ObjectType extends BaseDomainObject> extends CollectionPersistenceTests<ObjectType> {
	
	protected String testObjectName;
	protected String testObject2Name;
	
	/*
	 * Base tests
	 */
	@Override 
	protected void doSetupBeforeTransaction() {
		setTestObjectName("xtest");
		setTestObject2Name("xtes2");
		super.doSetupBeforeTransaction();
	}

	@Override
	protected abstract DomainObjectCollectionManager<ObjectType> getChildObjectManager();

	@Override protected void checkCreatedObject(ObjectType createdObject) {
		assertEquals(getTestObjectName(), getTextIDValue(createdObject));
	}
	
	@Override
	protected String getFindKey() {
		try {
			return getTextIDName();
		}
		catch (Exception e) {
			throw new Error("This class has no text key defined.  Either annotate the text key field with @TextKey, or subclass getFindKey and getFindValue in your test class to provide alternative targets for testing the finder functions");
		}
	}
	
	@Override
	protected Object getFindValue() {
		return getTextIDValue(domainObject);
	}
	
	/*
	 * Template methods
	 */
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

	/*
	 * For top level objects the parent is the service singleton.
	 * For other objects the parent will be another domain object. For those cases,
	 * override getParentObjectManager to return a manager that can create a parent object 
	 */
	
	
	protected ObjectType createChildObject(String name) {
		return getChildObjectManager().create(name);
	}
	
	protected ObjectType createChildObject() {
		return createChildObject(getTestObjectName());
	}

	protected ObjectType createChildObjectInTest() {
		return createChildObject(getTestObject2Name());
	}
	
	protected ObjectType getChildObject(String name) {
		return getChildObjectManager().get(name);
	}
	
	protected ObjectType getChildObject() {
		return getChildObject(getTestObjectName());
	}
	
	protected ObjectType getChildObjectInTest() {
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
	
}
