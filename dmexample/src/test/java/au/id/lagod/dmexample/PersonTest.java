package au.id.lagod.dmexample;

import static org.junit.Assert.*;

import org.junit.Test;

import com.objective.keystone.model.person.Person;

public class PersonTest extends BaseTest {

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testBlankCode() {
		model.getPersons().create("");
	}

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testNullCode() {
		model.getPersons().create(null);
	}

	@Test(expected=javax.validation.ConstraintViolationException.class)
	public void testDuplicateCode() {
		model.getPersons().create("");
		model.getPersons().create("");
	}
	
	@Test
	public void testCreate() {
		Person c = model.getPersons().create("test");
		Person c2 = model.persons("test");
		assertFalse(c2 == null);
		assertEquals(c2, c);
	}
}
