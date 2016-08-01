package au.id.lagod.dm;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dm.base.Utility;
import au.id.lagod.dm.collections.Model;
import au.id.lagod.dm.config.NullBootstrap;
import au.id.lagod.dm.test.TestModelFactory;

public class BaseTest {

	protected static String string256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	protected static String string51 = "012345678901234567890123456789012345678901234567890";
	protected static String string512 = string256 + string256;
	
	protected Model model;

	public BaseTest() {
		super();
	}
	
	@Before
	public void beforeTest() {
		TestModelFactory.resetModel();
		TestModelFactory.initModel(new NullBootstrap());
		model = TestModelFactory.getModel();
	}

	protected void validate(BaseDomainObject bdo) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BaseDomainObject>> constraintViolations = validator.validate(bdo);
		
		if (!constraintViolations.isEmpty()) {
			System.out.println(Utility.printMessages(constraintViolations));
			throw new ConstraintViolationException(getMessage(bdo) ,constraintViolations);
		}
	}
	
	
	public String getMessage(BaseDomainObject bdo) {
		return " BaseDomainObject = "+bdo.getClass()+"  Text Key Field = "+Utility.getTextKeyField(bdo.getClass())+" Text Key = "+ bdo.getTextKey();
	}

}