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
	public Class<Company> getManagedObjectClass() {
		return Company.class;
	}
	

}
