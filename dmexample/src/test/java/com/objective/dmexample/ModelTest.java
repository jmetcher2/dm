package com.objective.dmexample;

import org.junit.Test;

import com.objective.dm.config.Bootstrapper;
import com.objective.dm.config.NullBootstrap;
import com.objective.keystone.model.Model;

public class ModelTest  extends BaseTest{

//	@Test
//	public void testGetCompanies() {
//		Company company = model.getCompanies().create("test");
//		Company company2 = model.companies("test");
//		assertTrue(company.equals(company2));
//	}
	
	@Test(expected=java.lang.Error.class)
	public void testDuplicateModel() {
		Model m = new Model();
		Bootstrapper b = new NullBootstrap();
		b.bootstrap(m);
		b.bootstrap(m);
	}
	
	
//	@Test
//	public void testFlatten() {
//		CompanyManager cm = model.getCompanies();
//		
//		Company c = cm.create("test");
//		Department d1 = c.getDepartments().create("test dept1");
//		Department d2 = c.getDepartments().create("test dept2");
//		
//		Company c2 = cm.create("test2");
//		Department d3 = c2.getDepartments().create("test dept1");
//		Department d4 = c2.getDepartments().create("test dept2");
//
//		List<Department> depts = cm.flatten("departments");
//		
//		assertEquals(depts.size(), 4);
//		assertTrue(depts.contains(d1));
//		assertTrue(depts.contains(d2));
//		assertTrue(depts.contains(d3));
//		assertTrue(depts.contains(d4));
//		
//		//System.out.println(venues.toString());
//	}

}
