package au.id.lagod.dmexample;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import au.id.lagod.dm.config.Bootstrapper;
import au.id.lagod.dm.config.NullBootstrap;
import au.id.lagod.dmexample.collections.CompanyManager;
import au.id.lagod.dmexample.collections.Model;
import au.id.lagod.dmexample.config.HardcodedBootstrap;
import au.id.lagod.dmexample.entities.Company;

public class ModelTest  extends BaseTest{

	@Test
	public void testGetCompanies() {
		Company company = model.getCompanies().create("test");
		Company company2 = model.companies("test");
		assertTrue(company.equals(company2));
	}
	
	@Test(expected=java.lang.Error.class)
	public void testDuplicateModel() {
		Model m = new Model();
		Bootstrapper b = new NullBootstrap();
		b.bootstrap(m);
		b.bootstrap(m);
	}
	
	
	@Test
	public void testFlatten() {
		CompanyManager cm = model.getCompanies(); 
		Company c = cm.create("test");
		//System.out.println(venues.toString());
	}

}
