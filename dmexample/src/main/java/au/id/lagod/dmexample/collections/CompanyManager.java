package au.id.lagod.dmexample.collections;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dmexample.entities.Company;

public class CompanyManager extends DomainObjectCollectionManager<Company> {

	@Override
	public Company create(String name) {
		Company c = new Company(name);
		add(c);
		return c;
	}

	@Override
	public Company get(String textID) {
		// TODO Auto-generated method stub
		return null;
	}

}
