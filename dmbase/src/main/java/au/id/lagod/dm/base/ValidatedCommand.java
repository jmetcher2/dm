package au.id.lagod.dm.base;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.AssertTrue;


public class ValidatedCommand<T> {
	
	@AssertTrue
	private Boolean testResult;
	
	private Supplier<T> command;

	private Function<Set<ConstraintViolation<ValidatedCommand<T>>>, String> message;

	public ValidatedCommand() {
		this.message = this::getMessage;
	}

	/**
	 * If test evaluates to true, execute command, else return a
	 * ConstraintViolationException with the result of the given message function
	 * 
	 *  In this use case this is just a handy way to construct the correct exception,
	 *  but it also keeps constraint checking consistent with the extended use case
	 *  where we subclass ValidatedCommand and provide declarative validation of the
	 *  subclass's fields.
	 *  
	 * @param test
	 * @param command
	 * @param message
	 */
	public ValidatedCommand(Supplier<Boolean> test, Supplier<T> command, 
			Function<Set<ConstraintViolation<ValidatedCommand<T>>>, String> message) {
		this.testResult = test.get();
		this.command = command;
		setMessage(message);
	}

	public ValidatedCommand(boolean test, Supplier<T> command, Function<Set<ConstraintViolation<ValidatedCommand<T>>>, String> message) {
		this.testResult = test;
		this.command = command;
		setMessage(message);
	}

	public T execute() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ValidatedCommand<T>>> constraintViolations = validator.validate(this);
		
		if (constraintViolations.isEmpty()) {
			return doCommand();
		}
		else {
			System.out.println(message.apply(constraintViolations));
			throw new ConstraintViolationException(message.apply(constraintViolations),constraintViolations);
		}
		
	}
	
	public T doCommand() {
		return command.get();
	}
	
	public String getMessage(Set<ConstraintViolation<ValidatedCommand<T>>> constraintViolations) {
		return Utility.printMessages(constraintViolations);
	}
	
	private void setMessage( Function<Set<ConstraintViolation<ValidatedCommand<T>>>, String> message) {
		if (message == null) {
			this.message = this::getMessage;
		}
		else {
			this.message = message;
		}

	}
	
}
