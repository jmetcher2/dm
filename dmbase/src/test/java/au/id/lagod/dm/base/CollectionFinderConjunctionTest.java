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
import au.id.lagod.dm.base.finders.ConjunctionOperator;
import au.id.lagod.dm.base.finders.FinderConjunction;
import au.id.lagod.dm.base.finders.FinderOperator;
import au.id.lagod.dm.base.finders.FinderSpec;
import au.id.lagod.dm.base.finders.IFinderCriterion;
import au.id.lagod.dm.base.finders.FinderCriterion;
import au.id.lagod.dm.test.TestObject;

public class CollectionFinderConjunctionTest {
	List<TestObject> list;
	private CollectionFinder<TestObject> finder;
	
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<TestObject>(0);
		
		// Build a list
		list.add(new TestObject(1,"a", new TestObject(2, "b", null)));
		list.add(new TestObject(1,"a", new TestObject(22, "bb", null)));
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
	public void testAnd() {
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("intField", FinderOperator.EQUALS, 1),
				ConjunctionOperator.AND,
				new FinderCriterion("stringField", FinderOperator.EQUALS, "a")
		));
		
		List<TestObject> found = finder.find(fs);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(list.get(0)));
		assertTrue(found.contains(list.get(1)));
	}
	
	@Test
	public void testAnd2() {
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("intField", FinderOperator.EQUALS, 1),
				ConjunctionOperator.AND,
				new FinderCriterion("nestedObject.stringField", FinderOperator.EQUALS, "bb")
		));
		
		List<TestObject> found = finder.find(fs);
		
		assertEquals(1, found.size());
		assertTrue(found.contains(list.get(1)));
	}
	
	@Test
	public void testOr() {
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("intField", FinderOperator.EQUALS, 1),
				ConjunctionOperator.OR,
				new FinderCriterion("stringField", FinderOperator.EQUALS, "c")
		));
		
		List<TestObject> found = finder.find(fs);
		
		assertEquals(3, found.size());
		assertTrue(found.contains(list.get(0)));
		assertTrue(found.contains(list.get(1)));
		assertTrue(found.contains(list.get(2)));
	}
	
	@Test
	public void testContains() {
		FinderSpec fs = new FinderSpec();
		fs.addCriterion("stringField", FinderOperator.CONTAINS, "b");
		List<TestObject> found = finder.find(fs);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(list.get(6)));
		assertTrue(found.contains(list.get(7)));
	}
	
	@Test
	public void testImplicitAnd() {
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("intField", FinderOperator.EQUALS, 1),
				ConjunctionOperator.OR,
				new FinderCriterion("stringField", FinderOperator.EQUALS, "c")
		));
		// second criterion is and-ed with the first
		fs.addCriterion("nestedObject.stringField", FinderOperator.CONTAINS, "b");
		
		List<TestObject> found = finder.find(fs);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(list.get(0)));
		assertTrue(found.contains(list.get(1)));
	}
	
	@Test
	public void testNestedConjunctions() {
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
			new FinderConjunction(
					new FinderCriterion("intField", FinderOperator.EQUALS, 1),
					ConjunctionOperator.AND,
					new FinderCriterion("stringField", FinderOperator.EQUALS, "a")
			),
			ConjunctionOperator.OR,
			new FinderConjunction(
					new FinderCriterion("intField", FinderOperator.EQUALS, 5),
					ConjunctionOperator.AND,
					new FinderCriterion("stringField", FinderOperator.EQUALS, "e")
			)
		));

		List<TestObject> found = finder.find(fs);
		
		assertEquals(3, found.size());
		assertTrue(found.contains(list.get(0)));
		assertTrue(found.contains(list.get(1)));
		assertTrue(found.contains(list.get(3)));
	}


}
