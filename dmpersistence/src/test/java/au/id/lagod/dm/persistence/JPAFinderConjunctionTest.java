package au.id.lagod.dm.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.base.finders.ConjunctionOperator;
import au.id.lagod.dm.base.finders.FinderConjunction;
import au.id.lagod.dm.base.finders.FinderCriterion;
import au.id.lagod.dm.base.finders.FinderOperator;
import au.id.lagod.dm.base.finders.FinderSpec;
import au.id.lagod.dm.test.BasePersistenceTests;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.group.Group;

@ContextConfiguration(classes = {au.id.lagod.dmexample.config.AppConfig.class})
public class JPAFinderConjunctionTest  extends BasePersistenceTests<Customer> {

	private static final String CUSTOMER_NAME = "JPAFinderTest";
	private static final String CUSTOMER_NAME2 = "JPAFinderTest2";
	private static final String GROUP_NAME = "JPAFinderGroup";
	private static final String GROUP_NAME2 = "JPAFinderGroup2";
	private static final String FOLDER_NAME = "JPAFinderFolder";
	private static final String FOLDER_NAME2 = "JPAFinderFolder2";
	
	@Autowired
	protected ExampleModel model;

	@Override
	protected void doSetupBeforeTransaction() {
		{
			Customer c = model.getCustomers().create(CUSTOMER_NAME);
			{
				Group g = c.getGroups().create(GROUP_NAME);
				Folder f = c.getFolders().create(FOLDER_NAME);
				g.getGroupFolders().create(f);
			}
			{
				Group g = c.getGroups().create(GROUP_NAME2);
				Folder f = c.getFolders().create(FOLDER_NAME2);
				g.getGroupFolders().create(f);
			}
		}
		
		{
			Customer c = model.getCustomers().create(CUSTOMER_NAME2);
			{
				Group g = c.getGroups().create(GROUP_NAME);
				Folder f = c.getFolders().create(FOLDER_NAME);
				g.getGroupFolders().create(f);
			}
			{
				Group g = c.getGroups().create(GROUP_NAME2);
				Folder f = c.getFolders().create(FOLDER_NAME2);
				g.getGroupFolders().create(f);
			}
		}
		
	}

	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers(CUSTOMER_NAME));
		model.getCustomers().remove(model.customers(CUSTOMER_NAME2));
	}

	@Test
	public void testAnd() {
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);
		
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("label", FinderOperator.EQUALS, GROUP_NAME),
				ConjunctionOperator.AND,
				new FinderCriterion("customer.code", FinderOperator.EQUALS, CUSTOMER_NAME)
		));
		
		List<Group> found = finder.find(fs);
		
		assertEquals(1, found.size());
		assertTrue(found.contains(model.customers(CUSTOMER_NAME).groups(GROUP_NAME)));
	}
	
	@Test
	public void testAnd2() {
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);

		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("label", FinderOperator.EQUALS, GROUP_NAME),
				ConjunctionOperator.AND,
				new FinderCriterion("description", FinderOperator.EQUALS, GROUP_NAME)
		));
		
		List<Group> found = finder.find(fs);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(model.customers(CUSTOMER_NAME).groups(GROUP_NAME)));
		assertTrue(found.contains(model.customers(CUSTOMER_NAME2).groups(GROUP_NAME)));
	}
	
	@Test
	public void testOr() {
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);
		
		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("label", FinderOperator.CONTAINS, "2"),
				ConjunctionOperator.OR,
				new FinderCriterion("customer.code", FinderOperator.EQUALS, CUSTOMER_NAME2)
		));
		
		List<Group> found = finder.find(fs);
		
		assertEquals(3, found.size());
		assertTrue(found.contains(model.customers(CUSTOMER_NAME).groups(GROUP_NAME2)));
		assertTrue(found.contains(model.customers(CUSTOMER_NAME2).groups(GROUP_NAME)));
		assertTrue(found.contains(model.customers(CUSTOMER_NAME2).groups(GROUP_NAME2)));
	}
	
	@Test
	public void testContains() {
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);
		
		FinderSpec fs = new FinderSpec();
		fs.addCriterion("label", FinderOperator.CONTAINS, "2");
		
		List<Group> found = finder.find(fs);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(model.customers(CUSTOMER_NAME).groups(GROUP_NAME2)));
		assertTrue(found.contains(model.customers(CUSTOMER_NAME2).groups(GROUP_NAME2)));
	}
	
	@Test
	public void testImplicitAnd() {
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);

		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
				new FinderCriterion("label", FinderOperator.EQUALS, GROUP_NAME),
				ConjunctionOperator.AND,
				new FinderCriterion("description", FinderOperator.EQUALS, GROUP_NAME)
		));
		// second criterion is and-ed with the first
		fs.addCriterion("customer.code", FinderOperator.CONTAINS, CUSTOMER_NAME2);
		
		List<Group> found = finder.find(fs);
		
		assertEquals(1, found.size());
		assertTrue(found.contains(model.customers(CUSTOMER_NAME2).groups(GROUP_NAME)));
	}
	
	@Test
	public void testNestedConjunctions() {
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);

		FinderSpec fs = new FinderSpec();
		fs.addCriterion(new FinderConjunction(
			new FinderConjunction(
					new FinderCriterion("label", FinderOperator.EQUALS, GROUP_NAME),
					ConjunctionOperator.AND,
					new FinderCriterion("customer.code", FinderOperator.EQUALS, CUSTOMER_NAME)
			),
			ConjunctionOperator.OR,
			new FinderConjunction(
					new FinderCriterion("label", FinderOperator.EQUALS, GROUP_NAME2),
					ConjunctionOperator.AND,
					new FinderCriterion("customer.code", FinderOperator.EQUALS, CUSTOMER_NAME2)
			)
		));

		List<Group> found = finder.find(fs);
		
		assertEquals(2, found.size());
		assertTrue(found.contains(model.customers(CUSTOMER_NAME).groups(GROUP_NAME)));
		assertTrue(found.contains(model.customers(CUSTOMER_NAME2).groups(GROUP_NAME2)));
	}


}
