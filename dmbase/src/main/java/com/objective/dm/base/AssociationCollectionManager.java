package com.objective.dm.base;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;

import com.objective.dm.validators.Refused;

/*
 * Adds methods for collection managers that are managing an association
 * between two domain objects.
 * If we are managing an association between A & B on behalf of A,
 * we can add an item to the association simply by specifying the value for B, plus any
 * association properties.
 * Similarly for getting and removing items.
 * Thus we end up with a few more options for create(), get() and remove().
 * 
 * T is the type of the association object itself.
 * 
 * Example:
 * If we are modelling an many-to-many association between users and roles, we would have objects:
 * 
 * User - UserRole - Role
 * 
 * User.userRoles would be a collection of type:
 * 
 * 		User_UserRoles extends DomainObjectCollectionManager<UserRole> implements AssociationManager<UserRole, Role>
 * 
 * and Role.userRoles would be:
 * 
 * 		Role_UserRoles extends DomainObjectCollectionManager<UserRole> implements AssociationManager<UserRole, User>
 * 
 * We can then add a role to a user by saying:
 * 
 * 		user.getUserRoles().create(role)
 * or
 * 		role.getUserRoles().create(user)
 * 
 * get(String) and create(String) are then defined relative to the foreign (B) end of the association. So,
 * user.getUserRoles().create(aString) means "add a role to this user where the role name is 'aString'.
 * 
 * This is different to the usual collection manager semantics, where aString would name the collection entry itself.
 * Here, aString names the object that the collection entry links to.  So, the implementation of User_UserRoles.get(String) would be:
 * 
 *  	get("role.name", aString)
 * 
 */
public abstract class AssociationCollectionManager<A extends BaseDomainObject,T extends BaseAssociationDomainObject,B extends BaseDomainObject> extends
		DomainCollectionManager<T> 
		implements AssociationManager<T, B> {

	protected A parent;
	
	public AssociationCollectionManager(A parent) {
		super(new HashSet<T>());
		this.parent = parent;
	}
	

	public AssociationCollectionManager(Collection<T> c) {
		super(c);
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
	public abstract String getAssociateName();
	
	protected abstract Class<B> getAssociateClass();

	/**
	 * Return a new association object.  We assume the implementing class has a reference to the "A" end of the association
	 * 
	 * In general, this is the only method that is allowed to call the constructor of the association object.
	 * 
	 * @param associate
	 * @return a new association object
	 */
	protected abstract T newAssociationObject(BaseDomainObject associate);
	
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
	
	protected abstract B getAssociate(T ao);
	
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