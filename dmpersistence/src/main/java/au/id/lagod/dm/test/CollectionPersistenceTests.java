package au.id.lagod.dm.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.junit.Test;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.DomainCollectionManager;

public abstract class CollectionPersistenceTests<ObjectType extends BaseDomainObject>
		extends BasePersistenceTests<ObjectType> {

	public CollectionPersistenceTests() {
		super();
	}
	
	@Test
	/*
	 * Note: this implicitly tests the create() method as well as get()
	 */
	public void testGet() {
		ObjectType domainObject2 = getChildObject();
		
		assertTrue(domainObject2 != null);
		assertEquals(domainObject.getId(), domainObject2.getId());
		
		checkCreatedObject(domainObject2);
	}

	@Test
	public void testGetAttached() {
		sf.getCurrentSession().update(domainObject);
		
		ObjectType domainObject2 = getChildObject();
		
		assertEquals(domainObject.getId(), domainObject2.getId());
		assertEquals(domainObject, domainObject2);

		checkCreatedObject(domainObject2);
	}

	@Test
	public void testDelete() {
		ObjectType obj = createChildObjectInTest();
		
		sf.getCurrentSession().flush();
		
		getChildObjectManager().remove(obj);
		
		ObjectType obj2 = getChildObjectInTest();
		
		assertEquals(null, obj2);
	}
	
	@Test
	public void testDeletePersisted() {
		ObjectType obj = createChildObjectInTest();
		
		sf.getCurrentSession().flush();
		
		getChildObjectManager().remove(obj);
		
		ObjectType obj2 = getChildObjectInTest();
		
		assertEquals(null, obj2);
	}
	
	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testCreateDuplicate() {
		createChildObjectInTest();
		
		//sf.getCurrentSession().flush();

		createChildObjectInTest();
	}
	
	@Test
	public void testFindOneArray() {
		ObjectType obj2 = getChildObjectManager().findOne(new String[] {getFindKey()},new Object[] {getFindValue()});

		assertEquals(domainObject.getId(), obj2.getId());
		checkCreatedObject(obj2);
	}
	
	@Test
	public void testFindOneMap() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(getFindKey(),getFindValue());
		ObjectType obj2 = getChildObjectManager().findOne(params);

		assertEquals(domainObject.getId(), obj2.getId());
		checkCreatedObject(obj2);
	}
	
	
	@Test
	public void testFindArray() {
		List<ObjectType> results = getChildObjectManager().find(new String[] {getFindKey()},new Object[] {getFindValue()});

		assertEquals(1, results.size());
		ObjectType obj2 = results.get(0);
		assertEquals(domainObject.getId(), obj2.getId());
		checkCreatedObject(obj2);
	}
	
	@Test
	public void testFindMap() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put(getFindKey(),getFindValue());
		List<ObjectType> results = getChildObjectManager().find(params);

		assertEquals(1, results.size());
		ObjectType obj2 = results.get(0);
		assertEquals(domainObject.getId(), obj2.getId());
		checkCreatedObject(obj2);
	}
	
	@Test 
	public void testGetAll() {
		List<ObjectType> results = getChildObjectManager().getAll();

		// TODO: really only a smoke test at this stage
		assertTrue(results.size() > 0);
	}
	


	protected abstract DomainCollectionManager<ObjectType> getChildObjectManager();
	protected abstract ObjectType getChildObject();
	protected abstract ObjectType getChildObjectInTest();
	protected abstract ObjectType createChildObject();
	protected abstract ObjectType createChildObjectInTest();
	
	protected abstract String getFindKey();
	protected abstract Object getFindValue();
	
	// Subclasses can override this to make extra assertions on objects created by createChildObject
	protected void checkCreatedObject(ObjectType createdObject) {}
	
	protected void doSetupBeforeTransaction() {
		domainObject = createChildObject();
	}

	protected void doTeardownAfterTransaction() {
		getChildObjectManager().remove(getChildObject());
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