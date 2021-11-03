package au.id.lagod.dmexample.model.group;

import java.util.Set;
import java.util.function.Supplier;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.base.ValidatedCommand;
import au.id.lagod.dmexample.model.customer.Customer;

public class GroupManager extends DomainObjectCollectionManager<Group> {
	
	private Customer customer;
	
	public GroupManager(Customer customer, Set<Group> g) {
		super(g);
		this.customer = customer;
	}

	@Override
	protected Group instantiate(String name) {
		return new Group(customer, name);
	}

	@Override
	public Class<Group> getManagedObjectClass() {
		return Group.class;
	}
	
	public String setCode(Group group, String code) {
		Group found = findOne("code", code);
		return new ValidatedCommand<String>(
				code == null || found == null || found.equals(group), 
				() -> group.setCode(code),
				(s) -> "codeIsUnique: code " + code + " has already been used"
		).execute();
	}

}
