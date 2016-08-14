package au.id.lagod.dmexample.associations;

import java.util.Collection;
import java.util.HashSet;

import au.id.lagod.dm.base.AssociationCollectionManager;
import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dmexample.collections.Model;
import au.id.lagod.dmexample.entities.Company;
import au.id.lagod.dmexample.entities.User;

public class UserCompanyManager_User extends AssociationCollectionManager<User, UserCompany, Company> {
	
	User user;

	public UserCompanyManager_User(User u) {
		super(new HashSet<UserCompany>(0));
		this.user = u;
	}

	@Override
	protected Company getAssociate(UserCompany ao) {
		return ao.getCompany();
	}

	@Override
	protected DomainObjectCollectionManager<Company> getAssociateMasterCollection() {
		return Model.getModel().getCompanies();
	}

	@Override
	protected String getAssociateName() {
		return "company";
	}

	@Override
	protected AssociationCollectionManager<Company, UserCompany, User> getReverseCollection(
			UserCompany associationObject) {
		return associationObject.getCompany().getUserCompanies();
	}

	@Override
	protected UserCompany newAssociationObject(Company associate) {
		return new UserCompany(user, associate);
	}

	@Override
	public Class<UserCompany> getManagedObjectClass() {
		return UserCompany.class;
	}

}
