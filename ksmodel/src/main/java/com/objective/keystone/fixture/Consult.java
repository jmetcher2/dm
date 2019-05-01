package com.objective.keystone.fixture;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.customer.CustomerType;
import com.objective.keystone.model.domain.Domain;
import com.objective.keystone.model.domain.DomainType;
import com.objective.keystone.model.event.Event;
import com.objective.keystone.model.folder.ConsultFolder;

public class Consult extends Basic {

	public static final String CONSULTFOLDER1 = "consultfolder1";
	public static final String CONSULTFOLDER2 = "consultfolder2";
	public static final String EVENT1 = "event1";
	public static final String EVENT2 = "event2";


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
		c.setType(CustomerType.consult);
		Domain domain = c.getDomains().create("https://consult." + c.getIdentifier() + ".objective.com");
		domain.setType(DomainType.consult);
		setupFolders(c);
	}

	private void setupFolders(Customer c) {
		ConsultFolder f1 = c.getFolders().createConsult(prefix + CONSULTFOLDER1);
		ConsultFolder f2 = c.getFolders().createConsult(prefix + CONSULTFOLDER2);
		setupFolder(f1);
	}

	private void setupFolder(ConsultFolder f) {
		Event e1 = f.getCustomer().getEvents().create(prefix + EVENT1);
		Event e2 = f.getCustomer().getEvents().create(prefix + EVENT2);
		e1.setFolder(f);
		e2.setFolder(f);
		setupEvent(e1);
		setupEvent(e2);
	}

	private void setupEvent(Event e) {
		e.setStartEvent(LocalDateTime.now().minusDays(1));
		e.setStartReading(LocalDateTime.now().minusDays(1));
		e.setEndEvent(LocalDateTime.now().plusYears(1));
	}
	
}
