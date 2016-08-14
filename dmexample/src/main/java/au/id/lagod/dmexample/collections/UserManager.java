package au.id.lagod.dmexample.collections;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dmexample.entities.Company;
import au.id.lagod.dmexample.entities.User;

public class UserManager extends DomainObjectCollectionManager<User> {

	@Override
	public User create(String name) {
		User u = new User(name);
		add(u);
		return u;
	}

	@Override
	public Class<User> getManagedObjectClass() {
		return User.class;
	}
	

}
