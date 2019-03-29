package au.id.lagod.jersey_poc.fixture;

import javax.transaction.Transactional;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

public class Adhoc {

	protected String prefix;

	public Adhoc(String prefix) {
		this.prefix = prefix;
	}
	
	@Transactional
	public void run(Model model) {
		Customer c = model.customers("test1");
		System.out.println(c.getConsultFolders().getRoot());
		System.out.println(c.getFolders().getAll());
		model.getCustomers().remove(c);
	}

}
