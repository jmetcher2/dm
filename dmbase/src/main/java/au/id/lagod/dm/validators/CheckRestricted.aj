package au.id.lagod.dm.validators;

/**
 * Enforces the rule that any method marked with @Restricted must only be called from within the domain model
 * 
 * Works by triggering a compile error at the offending call site, but only if the calling code is
 * woven with this aspect.  We could convert this to a runtime check if necessary, at the cost of increased complexity
 * and probably a performance hit.
 * 
 * This is a workaround for the lack of modularity mechanisms in Java.  The model has several methods with 
 * public accessibility that must not be called by client code.  However, they must remain public either to obey 
 * existing contracts or because of conflicting demands on the package structure.
 * 
 * TODO: mark domain model constructors with @Restricted
 * 
 * @author Jaime Metcher
 *
 */
public aspect CheckRestricted {

	public CheckRestricted() {}
	
	
	pointcut restrictedMethod() : call(@Restricted * * (..))  && !within(au.id.lagod.dm..*);
	
	pointcut restrictedConstructor() : call(@Restricted com.objective.dm..*.new(..)) && !within(au.id.lagod.dm..*);

	declare error : restrictedMethod() : "Modularity rule breach: call to restricted method";
	
	declare error : restrictedConstructor() : "Modularity rule breach: call to restricted constructor";

	/*
	 * In theory we could do something like this to parametrize the Restricted annotation.
	 * However, it seems that we can't use this technique on a "call" pointcut
	 * 
	 * We could possibly change this to an "execution" pointcut and then use reflection to find the package of the 
	 * caller.
	pointcut restricted(String packageSpec) : call(@Restricted * * (..)) && @annotation(Restricted(packageSpec));
	
	before(String packageSpec) : restricted(packageSpec) {
		
		throw new java.lang.Error("Modularity rules breach: method call available only within " + packageSpec);
	}
	 */
	
}
