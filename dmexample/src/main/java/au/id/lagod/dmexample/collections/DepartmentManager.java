package au.id.lagod.dmexample.collections;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dmexample.entities.Company;
import au.id.lagod.dmexample.entities.Department;

public class DepartmentManager extends DomainObjectCollectionManager<Department> {
	
	Company company;
	
	public DepartmentManager(Company c) {
		super();
		this.company = c;
	}

	@Override
	public Department create(String name) {
		Department c = new Department(company, name);
		add(c);
		return c;
	}

	@Override
	public Class<Department> getManagedObjectClass() {
		return Department.class;
	}
	

}
