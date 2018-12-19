package com.objective.dm.base;

public interface AssociationManager<T extends BaseDomainObject,B extends BaseDomainObject> {
	
	
	public T create(B associate);
	
	public T get (B associate);
	
	public boolean removeAssociate(B associate);

	public abstract String getAssociateName();

}
