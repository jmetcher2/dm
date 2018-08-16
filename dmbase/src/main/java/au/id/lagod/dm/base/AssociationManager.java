package au.id.lagod.dm.base;

public interface AssociationManager<T extends BaseDomainObject,B extends BaseDomainObject> extends DomainObjectManager<T>{
	
	
	public T create(B associate);
	
	public T get (B associate);
	
	public boolean removeAssociate(B associate);

	public abstract DomainObjectCollectionManager<B> getAssociateMasterCollection();
	
	public abstract String getAssociateName();

}
