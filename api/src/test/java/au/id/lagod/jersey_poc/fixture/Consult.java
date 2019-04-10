package au.id.lagod.jersey_poc.fixture;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.event.Event;
import com.objective.keystone.model.folder.ConsultFolder;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.AuthoringFolder;

public class Consult extends Basic {

	private static final String CONSULTFOLDER1 = "consultfolder1";
	private static final String CONSULTFOLDER2 = "consultfolder2";
	private static final String EVENT1 = "event1";
	private static final String EVENT2 = "event2";


	public Consult(String prefix) {
		super(prefix);
	}

	@Transactional
	public void setup(Model model, SessionFactory sf) {
		super.setup(model, sf);
		setupCustomers();
	}
	
	private void setupCustomers() {
		setupCustomer(model.customers(prefix + CUSTOMER1));
		setupCustomer(model.customers(prefix + CUSTOMER2));
	}

	private void setupCustomer(Customer c) {
		setupFolders(c);
	}


	private void setupFolders(Customer c) {
		Folder f1 = c.getFolders().createConsult(prefix + CONSULTFOLDER1);
		Folder f2 = c.getFolders().createConsult(prefix + CONSULTFOLDER2);
		setupFolder(f1);
		setupFolder(f2);
	}

	private void setupFolder(Folder f) {
		//Event e1 = f.getCustomer().getEvents().create(prefix + EVENT1, f);
		//Event e2 = f.getCustomer().getEvents().create(prefix + EVENT1, f);
	}
	
}
