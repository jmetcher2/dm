package au.id.lagod.dmexample;

import static org.junit.Assert.*;

import org.junit.Test;

import au.id.lagod.dm.base.Utility;
import au.id.lagod.dmexample.entities.Company;

public class CompanyTest extends BaseTest {

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testBlankCode() {
		model.getCompanies().create("");
	}

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testNullCode() {
		model.getCompanies().create(null);
	}

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testDuplicateCode() {
		model.getCompanies().create("");
		model.getCompanies().create("");
	}
	
	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testDescriptionTooLong() {
		Company c = model.getCompanies().create("test");
		c.setDescription(Utility.string256);
		Utility.validate(c);
	}
}
