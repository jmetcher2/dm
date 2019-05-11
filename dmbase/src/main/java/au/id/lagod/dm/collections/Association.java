package au.id.lagod.dm.collections;

import java.util.function.Function;

public class Association<A, T, B> {
	String associateFieldName;
	Class<B> associateClazz;
	Class<T> associationClazz;
	Function<T, B> getAssociate;
	private Function<A, Function<B, T>> createAssociationObject;
	
	public Association(String name, Class<B> clazz, 
			Class<T> associationClazz, Function<T, B> getAssociate,
			Function<A, Function<B, T>> createAssociationObject) {
		super();
		this.associateFieldName = name;
		this.associateClazz = clazz;
		this.associationClazz = associationClazz;
		this.getAssociate = getAssociate;
		this.createAssociationObject = createAssociationObject;
	}

	public String getAssociateFieldName() {
		return associateFieldName;
	}

	public Class<B> getAssociateClazz() {
		return associateClazz;
	}

	public Function<T, B> getGetAssociate() {
		return getAssociate;
	}

	public Function<A, Function<B, T>> getCreateAssociationObject() {
		return createAssociationObject;
	}
	
	public Class<T> getAssociationClazz() {
		return associationClazz;
	}
	
}

