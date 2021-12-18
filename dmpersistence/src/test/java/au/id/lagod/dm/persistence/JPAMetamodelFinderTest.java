package au.id.lagod.dm.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.base.finders.BaseFinder;
import au.id.lagod.dm.test.BasePersistenceTests;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.customer.Customer_;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.customer.folder.Folder_;
import au.id.lagod.dmexample.model.group.Group;
import au.id.lagod.dmexample.model.group.Group_;
import au.id.lagod.dmexample.model.group.folder.GroupFolder;
import au.id.lagod.dmexample.model.group.folder.GroupFolder_;

@ContextConfiguration(classes = {au.id.lagod.dmexample.config.AppConfig.class})
public class JPAMetamodelFinderTest extends BasePersistenceTests<Customer>{
	
	private static final String CUSTOMER_NAME = "JPAFinderTest";
	private static final String CUSTOMER_NAME2 = "JPAFinderTest2";
	private static final String GROUP_NAME = "JPAFinderGroup";
	private static final String GROUP_NAME2 = "JPAFinderGroup2";
	private static final String FOLDER_NAME = "JPAFinderFolder";
	private static final String FOLDER_NAME2 = "JPAFinderFolder2";
	
	@Autowired
	protected ExampleModel model;

	@Test
	public void testFindTopLevel() {
		JPAFinder<Customer> finder = new JPAFinder<Customer>(sf, Customer.class);
		
		Customer c = finder.findOne(Customer_.NAME, CUSTOMER_NAME);
		assertEquals(model.customers(CUSTOMER_NAME), c);
	}

	@Test
	public void testFindMissing() {
		JPAFinder<Customer> finder = new JPAFinder<Customer>(sf, Customer.class);
		
		Customer c = finder.findOne("name", "bogus");
		assertEquals(null, c);
	}
	
	@Test
	public void testFindMultipleCriteria() {
		JPAFinder<Customer> finder = new JPAFinder<Customer>(sf, Customer.class);
		Customer c = finder.findOne(new String[] { Customer_.NAME, Customer_.CODE }, new Object[] { CUSTOMER_NAME, CUSTOMER_NAME });

		assertEquals(model.customers(CUSTOMER_NAME), c);
	}
	
	@Test
	public void testFindSecondLevel() { 
		Customer c = model.customers(CUSTOMER_NAME);
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);

		List<Group> groups = finder.find(BaseFinder.propPath(Group_.CUSTOMER, Customer_.NAME), CUSTOMER_NAME);
		assertEquals(2, groups.size());
		assertTrue(groups.contains(c.groups(GROUP_NAME)));
		
	}

	@Test
	public void testFindThirdLevel() { 
		Customer c = model.customers(CUSTOMER_NAME);
		JPAFinder<GroupFolder> finder = new JPAFinder<GroupFolder>(sf, GroupFolder.class);
		
		List<GroupFolder> groupFolders = finder.find("group.customer.name", CUSTOMER_NAME);
		assertEquals(2, groupFolders.size());
		assertTrue(groupFolders.contains(c.groups(GROUP_NAME).getGroupFolders().getDefault()));
		
		assertTrue(finder.find(BaseFinder.propPath(GroupFolder_.FOLDER, Folder_.CUSTOMER,Customer_.NAME), CUSTOMER_NAME).containsAll(groupFolders));
		
	}

	@Test
	public void testFindMap() { 
		Customer c = model.customers(CUSTOMER_NAME);
		JPAFinder<Group> finder = new JPAFinder<Group>(sf, Group.class);
		
		Map<String, Object> nestedCriteria = new HashMap<String, Object>();
		nestedCriteria.put(Customer_.NAME, CUSTOMER_NAME);
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put(Group_.CUSTOMER, nestedCriteria);
		
		
		List<Group> groups = finder.find(criteria);
		assertEquals(2, groups.size());
		assertTrue(groups.contains(c.groups(GROUP_NAME)));
		
	}

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
	
	

}
