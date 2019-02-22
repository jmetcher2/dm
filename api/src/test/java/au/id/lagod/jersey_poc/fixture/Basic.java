package au.id.lagod.jersey_poc.fixture;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.group.Group;
import com.objective.keystone.model.person.Person;

public class Basic {
	
	private Model model;
	private Customer c1;
	private Customer c2;
	private SessionFactory sf;

	@Transactional
	public void setup(Model model, SessionFactory sf) {
		this.model =  model;
		this.sf  = sf;
		setupCustomers();
		setupPersons();
	}
	
	@Transactional
	public void teardown(Model model) {
		model.getCustomers().remove(model.customers("test1"));
		model.getCustomers().remove(model.customers("test2"));
		model.getPersons().remove(model.persons("testPerson1"));
		model.getPersons().remove(model.persons("testPerson2"));
	}
	
	private void setupCustomers() {
		c1 = model.getCustomers().create("test1");
		c2 = model.getCustomers().create("test2");
		setupCustomer(c2);
		setupCustomer(c1);
	}

	private void setupCustomer(Customer c) {
		setupGroups(c);
		setupFolders(c);
	}

	private void setupFolders(Customer c) {
		Folder f1 = c.getFolders().create("testFolder1");
		Folder f2 = c.getFolders().create("testFolder2");
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
		f.getGroups().create(f.getCustomer().groups("testGroup1"));
		f.getGroups().create(f.getCustomer().groups("testGroup2"));
	}

	private void setupGroups(Customer c) {
		Group g1 = c.getGroups().create("testGroup1");
		Group g2 = c.getGroups().create("testGroup2");
	}

	private void setupPersons() {
		Person p1 = model.getPersons().create("testPerson1");
		Person p2 = model.getPersons().create("testPerson2");
		setupPerson(p1);
		setupPerson(p2);
	}

	private void setupPerson(Person p) {
		p.getPersonCustomers().create(c1);
		p.getPersonCustomers().create(c2);
	}

}
