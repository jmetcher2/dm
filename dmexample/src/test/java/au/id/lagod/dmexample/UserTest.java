package au.id.lagod.dmexample;

import static org.junit.Assert.*;

import org.junit.Test;

import au.id.lagod.dm.base.Utility;
import au.id.lagod.dmexample.entities.User;

public class UserTest extends BaseTest {

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testBlankCode() {
		model.getUsers().create("");
	}

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testNullCode() {
		model.getUsers().create(null);
	}

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testDuplicateCode() {
		model.getUsers().create("");
		model.getUsers().create("");
	}
	
	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testDescriptionTooLong() {
		User c = model.getUsers().create("test");
		c.setDescription(Utility.string256);
		Utility.validate(c);
	}
	
	@Test
	public void testCreate() {
		User c = model.getUsers().create("test");
		User c2 = model.users("test");
		assertFalse(c2 == null);
		assertEquals(c2, c);
	}
}
