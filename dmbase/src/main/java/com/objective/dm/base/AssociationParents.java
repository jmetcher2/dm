package com.objective.dm.base;

public class AssociationParents<A extends BaseDomainObject, T extends BaseAssociationDomainObject<A,B>, B extends BaseDomainObject> {
	private AssociationCollectionManager<A, T, B> parent1;
	private AssociationCollectionManager<A, T, B> parent2;
	
	public AssociationParents(AssociationCollectionManager<A, T, B> parent1,
			AssociationCollectionManager<A, T, B> parent2) {
		super();
		this.parent1 = parent1;
		this.parent2 = parent2;
	}

	public AssociationCollectionManager<A, T, B> getParent1() {
		return parent1;
	}

	public AssociationCollectionManager<A, T, B> getParent2() {
		return parent2;
	}

	public void add(BaseAssociationDomainObject<A,B> ao) {
		parent1.add((T) ao);
		parent2.add((T) ao);
		// Cast here is necessary because java has no self types, which means that 
		// T here i.e. T extends BaseAssociationDomainObject<A,B> can not be made to be type compatible with
		// ? extends BaseAssociationDomainObject<A, B> from com.objective.dm.base.BaseAssociationDomainObject.getAssociationParents()
		// even though we can see the T and ? must logically always be the same type.
	}

	public void remove(BaseAssociationDomainObject<A,B> ao) {
		parent1.remove(ao);
		parent2.remove(ao);
	}

	
}
