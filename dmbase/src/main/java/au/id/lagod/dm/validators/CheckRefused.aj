package au.id.lagod.dm.validators;

/**
 * Enforces the rule that any method marked with @Refused must never be called.
 * 
 * Works by triggering a compile error at the offending call site, but only if the calling code is
 * woven with this aspect.  We would typically implement a runtime error into the method body as well.
 * 
 * This is just a handy way to get compile-time notification of "refused bequests" in subclasses.
 * 
 * @author Jaime Metcher
 *
 */
public aspect CheckRefused {

	public CheckRefused() {}
	
	
	pointcut refusedMethod() : call(@Refused * * (..));
	
	pointcut refusedConstructor() : call(@Refused au.id.lagod..*.new(..));

	declare error : refusedMethod() : "Refused bequest: call to unimplemented method";
	
	declare error : refusedConstructor() : "Refused bequest: call to unimplemented constructor";

}
