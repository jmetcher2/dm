package au.id.lagod.dm.base;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;

import au.id.lagod.dm.collections.Association;

/*
 * Adds methods for collection managers that are managing a many to many association
 * between two domain objects via an intermediate "join" object.
 * 
 * If we are managing an association between A & B on behalf of A:
 *   A is the parent
 *   T is the association object (or join object)
 *   B is the associate (or foreign end of the association)
 *   
 * Association managers come in pairs, one managing each side of the two one-to-many
 * relationships that makeup the many to many, and the association object knows about both managers
 * (it has the two many-to-one relationships after all), so we can automatically maintain both sides.
 *  
 * For example, if we are modelling an many-to-many association between users and roles, we would have objects:
 * 
 * User - UserRole - Role
 * 
 * To manage this association, we would have one manager whose parent is User, with a collection of UserRole objects 
 * representing all the roles associated with that user.
 * We might call that user.getUserRoles(), and we can add a role to a user by saying user.getUserRoles().create(role).
 * So far this is not much different to our normal parent/child relationship.
 * We'd also have a symmetrical manager whose parent is Role, with a collection of UserRole objects representing all
 * the users associated with that role.
 * What makes this different is that calling user.getRoles().create(role) will automatically also call role.getUsers().create(user),
 * thus managing the other end of the many-to-many.  Similarly remove a userrole from either end will automatically maintain
 * both collections.
 * The final difference is that you can retrieve a UserRole from a user's point of view by specifying the Role, and from a role's point 
 * of view by specifying the User.  In contrast, with normal parent/child relationship you retrieve the child by specifying
 * the child.
 * In practice, this means:
 *   We have an extra create method, create(Role) or create(User)
 *   Ditto for remove.
 *   Ditto for get
 *   The normal create(String) actually refers to the name of the foreign object, not the name of the child object.  So
 *   user.getUserRoles().create("admin") means "create a UserRole which associates this user with the role called admin". 
 * 		 
 *   
 */
public class AssociationCollectionManager<A extends BaseDomainObject,T extends BaseAssociationDomainObject,B extends BaseDomainObject> extends
		DomainCollectionManager<T> 
		implements AssociationManager<T, B> {

	protected A parent;
	private Association<A,T,B> spec;
	
	public AssociationCollectionManager(A parent, Collection<T> c, Association<A,T,B> association) {
		super(c);
		this.parent = parent;
		this.spec = association;
	}
	

	public AssociationCollectionManager(Collection<T> c) {
		super(c);
	}
	
	public A getParent() {
		return parent;
	}
	
	@Override
	public Class<T> getManagedObjectClass() {
		return spec.getAssociationClazz();
	}



	/* ******************************************************************************
	 * 
	 * PRIMITIVES (template method pattern)
	 * 
	 ****************************************************************************** */

	/**
	 * Get the field name of the associate within the association object
	 * 
	 * E.g. if UserRole has fields user and role, this would return the string "role"
	 */
	public String getAssociateName() {
		return spec.getAssociateFieldName();
	};
	
	protected Class<B> getAssociateClass() {
		return spec.getAssociateClazz();
	};

	/**
	 * Return a new association object.  We assume the implementing class has a reference to the "A" end of the association
	 * 
	 * In general, this is the only method that is allowed to call the constructor of the association object.
	 * 
	 * @param associate
	 * @return a new association object
	 */
	protected T newAssociationObject(B associate) {
		return spec.getCreateAssociationObject().apply(parent).apply(associate);
	}
	
	/* ******************************************************************************
	 * 
	 * TEMPLATE METHODS (template method pattern)
	 * 
	 ****************************************************************************** */

	/**
	 * Create an association object by specifying the foreign end of the association.
	 * This will add the association object to both ends of the association.
	 * Where the association object has mandatory extra metadata, you should implement 
	 * a create() method with more arguments to allow the caller to specify the extra data.
	 */
	 // See the comment on getAssociationParents();
	@SuppressWarnings("unchecked")
	public T create(B associate) {
		T ao = newAssociationObject(associate);
		if (ao == null) {
			throw new java.lang.Error("Failed to create an association object from a given associate.  You may need to use a create() method with more arguments.");
		}
		
		AssociationParents<A, T, B> ap = ao.getAssociationParents();
		ap.add(ao);
		return ao;
	}

	/**
	 * Get an association object by specifying the foreign end of the the association
	 */
	public T getAssociationWith(B associate) {
		return findOne(getAssociateName(), associate);
	}

	/**
	 * Remove an association object by specifying the foreign end of the the association
	 */
	public boolean removeAssociate(B associate) {
		return remove(getAssociationWith(associate));
	}
	
	/**
	 * Return true if this collection contains an association with the given object
	 */
	public boolean hasAssociate(B associate) {
		return getAssociationWith(associate) != null;
	}

	/**
	 * Remove the association object from both ends of the association
	 */
	@SuppressWarnings("unchecked") // See the comment on getAssociationParents();
	public boolean remove(Object o) {
		if (super.remove(o)) {
			T ao = getManagedObjectClass().cast(o);
			ao.getAssociationParents().remove(ao);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Set<B> getAssociates() {
		return collection.stream().map(e -> getAssociate(e)).collect(Collectors.toSet());
	}
	
	protected  B getAssociate(T ao) {
		return (B) spec.getGetAssociate().apply(ao);
	};
	
	/** 
	 * Add an 
	 */
	public boolean add(T ao) {
		new AddAssociation(ao).execute();
		return true;
	}
	
	protected class AddAssociation extends ValidatedCommand<Boolean> {
		
		@Valid 		private T ao;
		@AssertFalse private boolean associateAlreadyLinked;

		public AddAssociation(T ao) {
			this.ao = ao;
			
			this.associateAlreadyLinked = contains(ao);
		}
		
		@Override
		public Boolean doCommand() {
			return addnocheck(ao);
		}
		
	}
	
}