package au.id.lagod.dm.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.base.finders.FinderSpec;
import au.id.lagod.dm.test.BasePersistenceTests;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.customer.folder.Folder;

@ContextConfiguration(classes = {au.id.lagod.dmexample.config.AppConfig.class})
public class FinderPagingTest extends BasePersistenceTests<Customer> {
	private static final String CUSTOMER_NAME = "JPAFinderTest";
	private static final int FOLDER_COUNT = 10;
	private static final String FOLDER_CODE_A = "A";
	private static final String FOLDER_CODE_B = "B";
	
	@Autowired
	protected ExampleModel model;

	@Override
	protected void doSetupBeforeTransaction() {
		Customer c = model.getCustomers().create(CUSTOMER_NAME);
		for (int i = 0; i < FOLDER_COUNT; i++) {
			Folder f = c.getFolders().create("folder" + i);
			if (i < FOLDER_COUNT / 2) {
				f.setDescription(FOLDER_CODE_A);
			}
			else {
				f.setDescription(FOLDER_CODE_B);
			}
		}
	}

	@Override
	protected void doTeardownAfterTransaction() {
		model.getCustomers().remove(model.customers(CUSTOMER_NAME));
	}
	
	@Test
	public void testFindAll() {
		Customer c = model.customers(CUSTOMER_NAME);
		List<Folder> folders = c.getFolders().find(new FinderSpec());
		assertEquals(FOLDER_COUNT, folders.size());
	}

	@Test
	public void testFindPage() {
		Customer c = model.customers(CUSTOMER_NAME);
		List<Folder> folders = c.getFolders().find(new FinderSpec().paging(true,3,1));
		assertEquals(3, folders.size());
		groupContains(folders, "folder0", "folder1", "folder2");
	}

	@Test
	public void testFindPage2() {
		Customer c = model.customers(CUSTOMER_NAME);
		List<Folder> folders = c.getFolders().find(new FinderSpec().paging(true,3,2));
		assertEquals(3, folders.size());
		groupContains(folders, "folder3", "folder4", "folder5");
	}

	@Test
	public void testFindLastPage() {
		Customer c = model.customers(CUSTOMER_NAME);
		List<Folder> folders = c.getFolders().find(new FinderSpec().paging(true,3,4));
		assertEquals(1, folders.size());
		groupContains(folders, "folder9");
	}

	@Test
	public void testFindOversizedPage() {
		Customer c = model.customers(CUSTOMER_NAME);
		List<Folder> groups = c.getFolders().find(new FinderSpec().paging(true,20,1));
		assertEquals(10, groups.size());
	}

	@Test
	public void testFindOvershootPage() {
		Customer c = model.customers(CUSTOMER_NAME);
		List<Folder> folders = c.getFolders().find(new FinderSpec().paging(true,3,10));
		assertTrue(folders.isEmpty());
	}

	private void groupContains(List<Folder> groups, String... codes) {
		List<String> codeList = groups.stream().map(g -> g.getName()).collect(Collectors.toList());
		for (String s: codes) {
			assertTrue(codeList.contains(s));
		}
	}

}
