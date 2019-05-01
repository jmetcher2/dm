package com.objective.keystone.fixture;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.Person;
import com.objective.keystone.model.person.customer.CustomerPerson;

public class Basic {
	
	public static final String GROUP2 = "testGroup2";
	public static final String GROUP1 = "testGroup1";
	public static final String FOLDER2 = "testFolder2";
	public static final String FOLDER1 = "testFolder1";
	public static final String PERSON2 = "testPerson2";
	public static final String PERSON1 = "testPerson1";
	public static final String CUSTOMER2 = "test2";
	public static final String CUSTOMER1 = "test1";
	
	protected Model model;
	private Customer c1;
	private Customer c2;
	private SessionFactory sf;
	protected String prefix;

	public Basic(String prefix) {
		this.prefix = prefix;
	}

	@Transactional
	public void setup(Model model, SessionFactory sf) {
		this.model =  model;
		this.sf  = sf;
		setupCustomers();
		setupPersons();
	}
	
	@Transactional
	public void teardown(Model model) {
		model.getCustomers().remove(model.customers(prefix + CUSTOMER1));
		model.getCustomers().remove(model.customers(prefix + CUSTOMER2));
		model.getPersons().remove(model.persons(prefix + PERSON1));
		model.getPersons().remove(model.persons(prefix + PERSON2));
	}
	
	private void setupCustomers() {
		c1 = model.getCustomers().create(prefix + CUSTOMER1);
		c2 = model.getCustomers().create(prefix + CUSTOMER2);
		setupCustomer(c2);
		setupCustomer(c1);
	}

	private void setupCustomer(Customer c) {
		c.getDomains().create(c.getIdentifier() + ".objective.com");
		c.getMetadata().put("v5Enabled", true);
		setupGroups(c);
		setupFolders(c);
	}

	private void setupFolders(Customer c) {
		Folder f1 = c.getFolders().create(prefix + FOLDER1);
		Folder f2 = c.getFolders().create(prefix + FOLDER2);
		setupFolder(f1);
		setupFolder(f2);
	}

	private void setupFolder(Folder f) {
		// This flush is a code smell caused by having entities with no independent primary key
		// Because the PK of the join entity is a compound key consisting of the PKs of the two 
		// joined entities, the cascaded save flows "downhill" from one of the joined entities to the join 
		// entity, and then back "uphill" from the join entity to the foreign 
		// end of the join.  Considering that it then flows downhill from that entity to other join entities,
		// which themselves may do the same thing, and it becomes clear that this turns into an arbitrarily complex
		// and possibly circular graph traversal.  Hibernate relies on being able to order operations in order
		// of when things need to exist in the database, but with this kind of graph traversal there is no general way
		// to calculate ordering.  So this flush before creating a join entity chops off the potential for 
		// uphill cascade.
		sf.getCurrentSession().flush();
		f.getGroups().create(f.getCustomer().groups(prefix + GROUP1));
		f.getGroups().create(f.getCustomer().groups(prefix + GROUP2));
	}

	private void setupGroups(Customer c) {
		Group g1 = c.getGroups().create(prefix + GROUP1);
		Group g2 = c.getGroups().create(prefix + GROUP2);
	}

	private void setupPersons() {
		Person p1 = model.getPersons().create(prefix + PERSON1);
		Person p2 = model.getPersons().create(prefix + PERSON2);
		setupPerson(p1);
		setupPerson(p2);
	}

	private void setupPerson(Person p) {
		CustomerPerson pc1 = p.getPersonCustomers().create(c1);
		CustomerPerson pc2 = p.getPersonCustomers().create(c2);
		pc1.getCustomerPersonGroups().create(c1.groups(prefix + GROUP1));
		pc1.getCustomerPersonGroups().create(c1.groups(prefix + GROUP2));
		pc2.getCustomerPersonGroups().create(c1.groups(prefix + GROUP1));
		pc2.getCustomerPersonGroups().create(c1.groups(prefix + GROUP2));
	}

}
