package com.objective.keystone.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.objective.keystone.model.customer.Customer;

import dm.test.BaseUnitTest;

public class CustomerUnitTest extends BaseUnitTest {
	
	@Test
	public void testCreateCustomer() {
		Customer c = model.getCustomers().create("test1");
		assertEquals("Consultation home", c.getFolders().getConsultRoot().getName());
		assertEquals("Root", c.getFolders().getRoot().getName());
	}

}
