package au.id.lagod.dm.base;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


public abstract class ValidatedCommand<T> {

	public T execute() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ValidatedCommand<T>>> constraintViolations = validator.validate(this);
		
		if (constraintViolations.isEmpty()) {
			T o = doCommand();
			if (o == null) {
				throw new Error(this.getClass().getName() + ": super create failed");
			}
			return o;
		}
		else {
			System.out.println(Utility.printMessages(constraintViolations));
			throw new ConstraintViolationException(getMessage(),constraintViolations);
		}
		
	}
	
	abstract public T doCommand();
	
	public String getMessage() {
		return "";
	}
	
}
