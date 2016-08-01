package au.id.lagod.dm.base;

public interface AssociationManager<T,B> extends DomainObjectManager<T>{
	
	
	public T create(B associate);
	
	public T get (B associate);
	
	public boolean removeAssociate(B associate);

}
