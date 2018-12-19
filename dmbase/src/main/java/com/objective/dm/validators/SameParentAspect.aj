package com.objective.dm.validators;

import com.objective.dm.base.ChildDomainObject;

public aspect SameParentAspect {
	/* TODO: Not sure if this is the right way to go, or whether to do a big-bang validation at some point
	 * (e.g. on save in the persistence-enabled case).
	 * 
	 *  If we decide not to go with this approach we can just compile without this aspect
	*/
	pointcut annotatedIntegrityCheck(ChildDomainObject cdo):  target(cdo) && execution(@SameParent * *(..));

	before(ChildDomainObject cdo) : annotatedIntegrityCheck(cdo) {
		Object[] args = thisJoinPoint.getArgs();
		ChildDomainObject associate = (ChildDomainObject) args[0];
		
		if (!associate.getParent().equals(cdo.getParent())) {
			throw new java.lang.Error("Mismatched parent object");
		}
		else {
			System.out.println("Matched parent");
		}
	}
}
