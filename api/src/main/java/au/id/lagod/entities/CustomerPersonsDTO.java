package au.id.lagod.entities;

import java.util.ArrayList;
import java.util.List;

import com.objective.keystone.model.person.customer.CustomerPerson;
import com.objective.keystone.model.person.customer.PersonCustomerManager;
import com.objective.keystone.model.person.customer.group.CustomerPersonGroup;

import au.id.lagod.jersey_poc.services.PersonService;

public class CustomerPersonsDTO extends BaseDTO {
	public List<CustomerPersonDTO> customerPersons = new ArrayList<CustomerPersonDTO>();
	private PersonService service;
	
	public CustomerPersonsDTO() {}
	
	public CustomerPersonsDTO(PersonService service, PersonCustomerManager personCustomerManager) {
		this.service = service;
		for (CustomerPerson cp: personCustomerManager) {
			this.customerPersons.add(new CustomerPersonDTO(cp));
		}
		
		String userName = personCustomerManager.getParent().getUserName();
		_links.put("parent", service.getPerson(userName));
	}
	
	public class CustomerPersonDTO extends BaseEmbedDTO {

		public String type;
		public List<GroupDTO> groups = new ArrayList<GroupDTO>();
		public Long customerId;
		
		public CustomerPersonDTO() {}

		public CustomerPersonDTO(CustomerPerson cp) {
			this.type = cp.getType().toString();
			this.customerId = cp.getCustomer().getId();
			
			for (CustomerPersonGroup cpg: cp.getCustomerPersonGroups()) {
				groups.add(service.getGroupService().getGroupDTO(cpg.getGroup(), true));
			}
			
			_links.put("self", service.getCustomerPerson(cp));

			
		}
	}

}
