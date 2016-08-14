package au.id.lagod.dm.base;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

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
public abstract class AssociationCollectionManager<A extends BaseDomainObject,T extends BaseDomainObject,B extends BaseDomainObject> extends
		DomainObjectCollectionManager<T> 
		implements AssociationManager<T, B> {

	public AssociationCollectionManager(Collection<T> c) {
		super(c);
	}

	/* ******************************************************************************
	 * 
	 * PRIMITIVES (template method pattern)
	 * 
	 ****************************************************************************** */

	// Get the foreign end of the association (aka "the associate" out of the association object)
	// E.g. userRole.getRole()
	protected abstract B getAssociate(T ao);

	/** 
	 * Get the field name of the text key of the associate
	 * 
	 * E.g. if Role's textkey was the field Role.name, this would be "name"
	 * 
	 * @return field name, or null if the object has no text key
	 */
	protected String getAssociateKeyFieldName() {
		return BaseDomainObject.getTextKeyField(getManagedObjectClass());
	};

	/**
	 * Get the collection that manages all of the associate objects (not just the ones participating in this association)
	 * 
	 * This is required for the default implementation of create(String) to work.  However, many domain objects won't have a 
	 * single master collection.  For example, each User might have a collection of UserRoles, but there is no single collection
	 * containing all UserRoles for all Users.  In that case, this method should return null.  You may still be able to provide an 
	 * alternative implementation of create(String).
	 *  
	 * @return A collection manager, or null
	 */
	protected abstract DomainObjectCollectionManager<B> getAssociateMasterCollection();

	/**
	 * Get the field name of the associate within the association object
	 * 
	 * E.g. if UserRole has fields user and role, this would return the string "role"
	 */
	protected abstract String getAssociateName();

	/**
	 * Get the reverse association collection
	 * 
	 * E.g. if we are looking at a UserRole that is in the collection of UserRoles for a User, this method will give us
	 * the collection of UserRoles for a given Role that also contains that same UserRole.
	 * 
	 * @param associate
	 * @return an association collection manager
	 */
	protected abstract AssociationCollectionManager<B,T,A> getReverseCollection(T associationObject);

	/**
	 * Return a new association object.  We assume the implementing class has a reference to the "A" end of the association
	 * 
	 * In general, this is the only method that is allowed to call the constructor of the association object.
	 * 
	 * @param associate
	 * @return a new association object
	 */
	protected abstract T newAssociationObject(B associate);

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
	public T create(B associate) {
		T ao = newAssociationObject(associate);
		if (ao == null) {
			throw new java.lang.Error("Failed to create an association object from a given associate.  You may need to use a create() method with more arguments.");
		}
		add(ao);
		getReverseCollection(ao).add(ao);
		return ao;
	}

	/**
	 * Get an association object by specifying the foreign end of the the assocation
	 */
	public T get(B associate) {
		return findOne(getAssociateName(), associate);
	}

	/**
	 * Remove an association object by specifying the foreign end of the the assocation
	 */
	public boolean removeAssociate(B associate) {
		return remove(get(associate));
	}
	
	/**
	 * Return true if this collection contains an association with the given object
	 */
	public boolean hasAssociate(B associate) {
		return get(associate) != null;
	}

	/**
	 * Create an association object by specifying the name of the object at the foreign end of the association
	 * 
	 * There are two preconditions for this to work:
	 * 1. The foreign object must have a text key
	 * 2. There must be a single collection in which we can look up that text key (i.e. getAssociateMasterCollection() must not
	 *    return null.
	 *    
	 * If these preconditions are false, subclasses may override this method to make it clear that it's not supported. 
	 */
	public T create(String name) {
		return new CreateByString(name).execute();
	}

	/**
	 * Get an association object by specifying the name of the object at the foreign end of the association.
	 * The foreign object must have a text key.  If it doesn't, subclasses may override this method to make it clear that it's not supported.
	 */
	public T get(String textID) {
		if (getAssociateKeyFieldName() == null) {
			throw new java.lang.Error("Can't get association by name as associate has no text key field");
		}
		return findOne(getAssociateName() + "." + getAssociateKeyFieldName(), textID);
	}

	/**
	 * Remove the association object from both ends of the association
	 */
	public boolean remove(Object o) {
		if (super.remove(o)) {
			T ao = getManagedObjectClass().cast(o);
			getReverseCollection(ao).remove(ao);
			return true;
		}
		else {
			return false;
		}
	}
	
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

		public AddAssociation(T ees) {
			this.ao = ees;
			
			this.associateAlreadyLinked = get(getAssociate(ees)) != null;
		}
		
		@Override
		public Boolean doCommand() {
			return addnocheck(ao);
		}
		
	}
	
	protected class CreateByString extends ValidatedCommand<T> {
		
		@NotNull 		private DomainObjectCollectionManager<B> amc;
		@NotNull		private B associate;

		public CreateByString(String name) {
			this.amc = getAssociateMasterCollection();
			if (amc != null) {
				associate = getAssociateMasterCollection().get(name);
			}
		}
		
		@Override
		public T doCommand() {
			return create(associate);
		}
		
	}
	
}