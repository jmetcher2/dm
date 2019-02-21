package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.jersey_poc.services.PersonService;

public class CustomerPersonDTO extends BaseDTO {

	public String type;
	public List<GroupDTO> groups = new ArrayList<GroupDTO>();
	public Long customerId;
	
	public CustomerPersonDTO() {}

	public CustomerPersonDTO(PersonService service, CustomerPerson cp) {
		super();
		this.type = cp.getType().toString();
		this.customerId = cp.getCustomer().getId();
		
		String customerIdf = cp.getCustomer().getIdentifier();
		String userName = cp.getPerson().getUserName();
		
		for (CustomerPersonGroup cpg: cp.getCustomerPersonGroups()) {
			groups.add(service.getGroupService().getGroupDTO(cpg.getGroup(), true));
		}
		
		_links.put("customer", service.getCustomerService().getCustomer(customerIdf));
		_links.put("folders", service.getFolders(userName, customerIdf));
		_links.put("self", service.getCustomerPerson(userName, customerIdf));
		
		_links.put("parent", service.getCustomerPersons(userName));
	}

}
