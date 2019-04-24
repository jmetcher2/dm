package au.id.lagod.dm.base;

public abstract class BaseAssociationDomainObject<A extends BaseDomainObject, B extends BaseDomainObject>  extends BaseDomainObject {

	// Here, the second type parameter refers to the type of whatever subclass we're in
	// At runtime, we'd just say this.getClass(), but java has no compile-time equivalent
	// Hence ? extends BaseAssociationDomainObject<A, B> is as close as we can get, and we end up with some unchecked
	// conversion warnings later on
	public abstract AssociationParents<A, ? extends BaseAssociationDomainObject<A, B>, B> getAssociationParents();
	
}
