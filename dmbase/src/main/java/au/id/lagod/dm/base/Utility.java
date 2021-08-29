package au.id.lagod.dm.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class Utility {

	public static String string256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	protected static String string51 = "012345678901234567890123456789012345678901234567890";
	protected static String string512 = string256 + string256;

	private Utility() {
		
	}
	
	public static Map<String,Object> arraysToMap(String[] paramNames, Object[] values) {
		Map<String,Object> params = new HashMap<String,Object>();
		for (int i = 0; i < paramNames.length; i++) {
			params.put(paramNames[i], values[i]);
		}
		return params;
	}
	
	public static <T> String printMessages(Set<ConstraintViolation<T>> violations) {
		String ret = "";
		for (ConstraintViolation<T> v: violations) {
			ret = ret.concat(v.getPropertyPath() + ":" + v.getMessage() + " | ");
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static String printMessages(javax.validation.ConstraintViolationException e) {
		Set<ConstraintViolation<BaseDomainObject>> cvs = new HashSet<ConstraintViolation<BaseDomainObject>>(0);
		for (ConstraintViolation<?> cv: e.getConstraintViolations()) {
			cvs.add((ConstraintViolation<BaseDomainObject>) cv);
		}
		return e.getMessage().concat(" " + printMessages(cvs));
	}

	/*
	 * Utility method to help in writing tests.  In normal use, validation is triggered by context, not 
	 * explicitly by calling a method like this.
	 */
	public static void validate(BaseDomainObject bdo) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BaseDomainObject>> constraintViolations = validator.validate(bdo);
		
		if (!constraintViolations.isEmpty()) {
			System.out.println(Utility.printMessages(constraintViolations));
			throw new ConstraintViolationException(bdo.getMessage() ,constraintViolations);
		}
	}
	
    public static Properties loadProperties(String fileName) throws IOException {
		Properties prop = new Properties();
		 
		InputStream inputStream = Utility.class.getResourceAsStream(fileName);
		
		if (inputStream != null)
			prop.load(inputStream);
		
		return prop;
	}    



}
