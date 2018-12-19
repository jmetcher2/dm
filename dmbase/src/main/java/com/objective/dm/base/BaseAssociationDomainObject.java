package com.objective.dm.base;

public abstract class BaseAssociationDomainObject<A extends BaseDomainObject, B extends BaseDomainObject>  extends BaseDomainObject {

	public abstract AssociationParents<A, ? extends BaseAssociationDomainObject<A, B>, B> getAssociationParents();
	
}
