package au.id.lagod.dm.base;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import au.id.lagod.dm.test.TestObjectManager;

public class DomainObjectCollectionManagerTest {
	
	private TestObjectManager manager;

	@Before
	public void setup() {
		manager = new TestObjectManager();
		manager.create("test1");
		manager.create("test2");
	}

	@Test
	public void testRemoveAllSelfManager() {
		assertFalse(manager.isEmpty());
		assertEquals(2, manager.size());
		
		manager.removeAll(manager);
		
		
		assertTrue(manager.isEmpty());
		assertEquals(0, manager.size());
	}
	
	@Test
	public void testRemoveAllSelfCollection() {
		assertFalse(manager.isEmpty());
		assertEquals(2, manager.size());
		
		manager.removeAll(manager.getCollection());
		
		
		assertTrue(manager.isEmpty());
		assertEquals(0, manager.size());
	}
	
	@Test
	public void testRemoveAllCollectionCopy() {
		assertFalse(manager.isEmpty());
		assertEquals(2, manager.size());
		
		manager.removeAll(manager.getAll());
		
		
		assertTrue(manager.isEmpty());
		assertEquals(0, manager.size());
	}
	

	
}
