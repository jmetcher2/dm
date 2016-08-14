package au.id.lagod.dmexample.associations;

import au.id.lagod.dm.base.BaseDomainObject;
import au.id.lagod.dmexample.entities.Company;
import au.id.lagod.dmexample.entities.User;

public class UserCompany extends BaseDomainObject {
	
	User user;
	Company company;
	
	public UserCompany (User u, Company c) {
		this.user = u;
		this.company = c;
	}

	public User getUser() {
		return user;
	}

	public Company getCompany() {
		return company;
	}
	
	

}
