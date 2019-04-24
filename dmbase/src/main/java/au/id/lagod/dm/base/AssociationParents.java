package au.id.lagod.dm.base;

// BaseAssociationDomainObject will be either BaseAssociationDomainObject<A, B> or BaseAssociationDomainObject<B, A>
// Unfortunately there's no way to express this in Java
public class AssociationParents<A extends BaseDomainObject, T extends BaseAssociationDomainObject, B extends BaseDomainObject> {
	
	private AssociationCollectionManager<A, T, B> parent1;
	private AssociationCollectionManager<B, T, A> parent2;
	
	public AssociationParents(AssociationCollectionManager<A, T, B> parent1,
			AssociationCollectionManager<B, T, A> parent2) {
		super();
		this.parent1 = parent1;
		this.parent2 = parent2;
	}

	public AssociationCollectionManager<A, T, B> getParent1() {
		return parent1;
	}

	public AssociationCollectionManager<B, T, A> getParent2() {
		return parent2;
	}

	public void add(T ao) {
		parent1.add(ao);
		parent2.add(ao);
	}

	public void remove(T ao) {
		parent1.remove(ao);
		parent2.remove(ao);
	}

	
}
