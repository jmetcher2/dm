package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.jersey_poc.services.CustomerService;
import au.id.lagod.jersey_poc.services.PersonService;

public class CustomerPersonDTO extends BaseDTO {

	public String type;
	public List<GroupDTO> groups = new ArrayList<GroupDTO>();
	public Long customerId;
	
	public CustomerPersonDTO() {}

	public CustomerPersonDTO(PersonService service, CustomerPerson cp, Boolean embed) {
		super(embed, service);
		this.type = cp.getType().toString();
		this.customerId = cp.getCustomer().getId();
		
		for (CustomerPersonGroup cpg: cp.getCustomerPersonGroups()) {
			groups.add(service.getGroupService().getGroupDTO(cpg.getGroup(), true));
		}
		
		_links.put("customer", link(CustomerService.class, "getCustomer", cp.getCustomer()));
		_links.put("folders", link("getFolders", cp.getCustomer(), cp.getPerson()));
		_links.put("self", link("getCustomerPerson", cp.getCustomer(), cp.getPerson()));
		
		if (!embed) {
			_links.put("parent", link("getCustomerPersons", cp.getPerson()));
		}
	}

}
