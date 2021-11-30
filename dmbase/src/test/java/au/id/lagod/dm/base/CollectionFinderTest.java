package au.id.lagod.dm.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import au.id.lagod.dm.base.finders.CollectionFinder;
import au.id.lagod.dm.base.finders.FinderOperator;
import au.id.lagod.dm.base.finders.FinderSpec;
import au.id.lagod.dm.test.TestObject;

public class CollectionFinderTest {
	List<TestObject> list;
	private CollectionFinder<TestObject> finder;
	
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<TestObject>(0);
		
		// Build a list
		list.add(new TestObject(1,"a", new TestObject(2, "b", null)));
		list.add(new TestObject(3,"c", new TestObject(4, "d", null)));
		list.add(new TestObject(5,"e", new TestObject(6, "f", null)));
		list.add(new TestObject(6,"g", new TestObject(8, "h", null)));
		
		// Circular reference
		TestObject to1 = new TestObject(100, "A", null);
		TestObject to2 = new TestObject(101, "B", to1);
		to1.setNestedObject(to2);
		list.add(to1);
		
		// Repeated properties
		TestObject to3 = new TestObject(200, "aa", null);
		list.add(new TestObject(201, "bb1", to3));
		list.add(new TestObject(201, "bb2", to3));
		
		// Null nested object
		list.add(new TestObject(300, "AA", null));
		
		finder = new CollectionFinder<TestObject>(list);
	}
	
	@Test
	public void testFindMapOfStringObject() {
		Map<String, Object> params = new HashMap<String, Object>(0);

		// single field
		params.put("intField", 1);
		List<TestObject> found = finder.find(params);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getStringField() == "a");
		
		// combination of mismatched fields
		params.clear();
		params.put("intField", 1);
		params.put("stringField", "b");
		found = finder.find(params);
		assertTrue(found.isEmpty());

		// combination of matched fields
		params.clear();
		params.put("intField", 3);
		params.put("stringField", "c");
		found = finder.find(params);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getStringField() == "c");

		// nested object field
		params.clear();
		params.put("nestedObject.stringField", "b");
		params.put("stringField", "a");
		found = finder.find(params);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getStringField() == "a");
		
		// nested objects can't be found directly i.e. without the dot syntax
		params.clear();
		params.put("stringField", "b");
		found = finder.find(params);
		assertTrue(found.isEmpty());
		
		// repeated property
		params.clear();
		params.put("nestedObject.stringField", "aa");
		found = finder.find(params);
		assertTrue(found.size() == 2);
		assertTrue(found.get(0).getStringField() == "bb1");
		
		// circular reference - find top
		params.clear();
		params.put("intField", 100);
		found = finder.find(params);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getStringField() == "A");

		// circular reference - can't find nested directly
		params.clear();
		params.put("intField", 101);
		found = finder.find(params);
		assertTrue(found.size() == 0);
		
		// circular reference - nested object field
		params.clear();
		params.put("nestedObject.intField", 101);
		found = finder.find(params);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getStringField() == "A");
		
		// circular reference - nested nested object field
		params.clear();
		params.put("nestedObject.nestedObject.intField", 100);
		found = finder.find(params);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getStringField() == "A");
		

	}

	@Test
	public void testFindAll() {
		List<TestObject> found = finder.findAll();
		assertEquals(found.size(), 8);
	}

	@Test
	public void testFindStringArrayObjectArray() {
		List<TestObject> found = finder.find(new String[] {"intField","stringField","nestedObject.intField"}, new Object[] {1, "a", 2});
		assertEquals(found.size(), 1);
	}

	@Test
	public void testFindStringObject() {
		List<TestObject> found = finder.find("intField", 5);
		assertEquals(found.size(), 1);
	}

	@Test
	public void testFindOneStringObject() {
		TestObject to = finder.findOne("intField", 5);
		assertEquals(to.getStringField(), "e");
	}

	@Test
	public void testFindOneStringArrayObjectArray() {
		TestObject to = finder.findOne(new String[] {"intField","stringField","nestedObject.intField"}, new Object[] {5, "e", 6});
		assertEquals(to.getStringField(), "e");
	}

	@Test
	public void testFindOneMapOfStringObject() {
		Map<String, Object> params = new HashMap<String, Object>(0);

		params.put("intField", 3);
		params.put("stringField", "c");
		TestObject to = finder.findOne(params);
		assertEquals(to.getStringField(), "c");
	}

	@Test
	public void testFindOneMultipleHits() {
		TestObject to = finder.findOne("intField", 201);
		assertEquals(to.getStringField(), "bb1"); // Should find the first one
	}
	
	@Test
	public void testFindContains() {
		FinderSpec specs = new FinderSpec()
				.addCriterion("stringField", FinderOperator.CONTAINS, "bb");
		List<TestObject> found = finder.find(specs);
		assertEquals(found.size(), 2);
	}


	@Test
	public void testFindContainsAtEnd() {
		FinderSpec specs = new FinderSpec()
				.addCriterion("stringField", FinderOperator.CONTAINS, "b1");
		List<TestObject> found = finder.find(specs);
		assertEquals(found.size(), 1);
	}


	@Test
	public void testFindContainsNotFound() {
		FinderSpec specs = new FinderSpec()
				.addCriterion("stringField", FinderOperator.CONTAINS, "AB");
		List<TestObject> found = finder.find(specs);
		assertEquals(found.size(), 0);
	}



}
