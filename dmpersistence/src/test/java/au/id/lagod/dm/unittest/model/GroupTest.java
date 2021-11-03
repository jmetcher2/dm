package au.id.lagod.dm.unittest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;

import au.id.lagod.dm.config.NullBootstrap;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;
import au.id.lagod.dmexample.model.group.Group;
import au.id.lagod.dmexample.model.group.GroupManager;

public class GroupTest {
	
	private ExampleModel model;
	private Customer customer;
	private Group group1;
	private Group group2;
	private GroupManager mgr;

	@Before
	public void setup() {
		model = ExampleModel.getModel(new NullBootstrap());
		customer = model.getCustomers().create("testCustomer");
		mgr = customer.getGroups();
		group1 = mgr.create("group1");
		group2 = mgr.create("group2");
	}
	
	@Test
	public void testSetNullCodes() {
		mgr.setCode(group1, null);
		mgr.setCode(group2, null);
		assertEquals(null, group1.getCode());
		assertEquals(null, group2.getCode());
	}
	
	@Test
	public void testSetDifferentCodes() {
		mgr.setCode(group1, "code1");
		mgr.setCode(group2, "code2");
		assertEquals("code1", group1.getCode());
		assertEquals("code2", group2.getCode());
	}
	
	@Test
	public void testSetDuplicateCodes() {
		try {
			mgr.setCode(group1, "code");
			mgr.setCode(group2, "code");
			fail("Expected constraint violation on duplicate group code");
		}
		catch (ConstraintViolationException e) {
			assertTrue(e.getMessage().contains("codeIsUnique"));
		}
	}

}
