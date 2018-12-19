package com.objective.dm.base;

import javax.validation.Valid;

public class ObjectValidator<T> extends ValidatedCommand<T> {

	@Valid 		private T baseDomainObject;
	
	public ObjectValidator(T baseDomainObject) {
		this.baseDomainObject = baseDomainObject;
	
	}
	
	@Override
	public T doCommand() {
		return baseDomainObject;
	}

}
