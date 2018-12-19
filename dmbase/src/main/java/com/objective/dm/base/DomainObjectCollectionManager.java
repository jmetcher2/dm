package com.objective.dm.base;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;

public abstract class DomainObjectCollectionManager<T extends BaseDomainObject> extends DomainCollectionManager<T> implements 
		DomainObjectManager<T> {

	protected abstract T instantiate(String name);
	
	@Override
	public T create(String name) {
		T u = instantiate(name);
		u.setParentManager(this);
		add(u);
		return u;
	}
	
	public T get(String textID) {
		return findOne(BaseDomainObject.getTextKeyField(getManagedObjectClass()), textID);
	};
	
	public boolean exists(String textID) {
		return get(textID) != null;
	}
	
	public T getOrCreate(String name) {
		T t = get(name);
		if (t == null) {
			t = instantiate(name);
		}
		return t;
	}
	
	protected class AddDomainObject extends ValidatedCommand<Boolean> {
		
		@Valid 		private T domainObject;
		@AssertTrue private boolean nameIsUnique = true;

		public AddDomainObject(T domainObject) {
			this.domainObject = domainObject;
			
			if (domainObject.hasTextKey()) {
				this.nameIsUnique = get(domainObject.getTextKey()) == null;
			}
		}
		
		@Override
		public Boolean doCommand() {
			return addnocheck(domainObject);
		}
		
		@Override
		public String getMessage(Set<ConstraintViolation<ValidatedCommand<Boolean>>> constraintViolations) {
			return super.getMessage(constraintViolations) + " | " + domainObject.getMessage();
		}

		
	}
	
}
