package au.id.lagod.dmexample;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import au.id.lagod.dmexample.collections.CompanyManager;
import au.id.lagod.dmexample.collections.Model;
import au.id.lagod.dmexample.di.ModelFactory;
import au.id.lagod.dmexample.di.ModelInjector;
import au.id.lagod.dmexample.entities.Company;
import au.id.lagod.dmexample.entities.User;
import au.id.lagod.dmexample.entities.Venue;

public class ModelTest  extends BaseTest{

	@Test
	public void testGetCompanies() {
		Company company = model.getCompanies().create("test");
		Company company2 = model.companies("test");
		assertTrue(company.equals(company2));
	}
	
	@Test(expected=java.lang.Error.class)
	public void testDuplicateModel() {
		new Model();
	}
	
	@Test(expected=java.lang.Error.class)
	public void testSetModel() {
		Model model2 = null;
		ModelInjector injector = ModelInjector.aspectOf();
		injector.setModel(model2);
	}
	
	@Test
	public void testFlatten() {
		CompanyManager cm = ModelFactory.getModel().getCompanies(); 
		Company c = cm.create("test");
		Venue v1 = c.getVenues().create("venue1");
		Venue v2 = c.getVenues().create("venue2");
		
		v1.getExperiences().create("v1exp1");
		v1.getExperiences().create("v1exp2");
		v2.getExperiences().create("v2exp1");
		v2.getExperiences().create("v2exp2");
		
		
		List<Venue> venues = cm.flatten("venues.experiences");
		
		System.out.println(venues.toString());
	}

	@Test
	public void testGetUsers() {
		User user = model.getUsers().createByString("test");
		User user2 = model.users(user.getUid());
		assertTrue(user.equals(user2));
	}
	
}
