package com.objective.keystone.persistence;

import org.hibernate.FlushMode;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;

import com.objective.keystone.model.customer.Customer;

/*
 * This listener is necessary to undo interactions of cascading deletes with database triggers, e.g:
 * 1. You delete a customer
 * 2. Hibernate cascades the delete to folders, groups etc
 * 3. Folder delete has a trigger which creates an audit entry with a FK reference to the customer
 * 4. The customer delete then fails with a FK constraint violation
 * 
 * We have some options for fixing this:
 * 1. Remove the FK constraint on the audit record.  Audit records should be decoupled anyway.  
 * 		Rejected this as I don't want to change the schema if possible.
 * 2. Map the audit records into the domain model so cascade will take them out as well - only it might not, as
 * hibernate might not be aware of the inserted records.  Plus audit records grow large and are useless for the model, 
 * so rejected this approach.
 * 3. Add another trigger, this time on customer delete, to clean up any mess made by downstream deletes.  Rejected,
 * 	high yuck factor plus schema change required.
 * 4. This approach, which is essentially to put an equivalent of a delete trigger into the ORM.
 * 
 *  Ultimately I want to go for approach #1, fixing bad design is better than working around it.
 */
public class ReplicationDeleteEventListener 
    implements PreDeleteEventListener {

	private static final long serialVersionUID = 1L;
	public static final ReplicationDeleteEventListener INSTANCE =
	    new ReplicationDeleteEventListener();
	
	@Override
	public boolean onPreDelete(
	        PreDeleteEvent event) {
	    final Object entity = event.getEntity();
	
	    if(entity instanceof Customer) {
	    	Customer customer = (Customer) entity;
	
	        event.getSession().createNativeQuery(
	            "delete from publisher_group_audit where group_audit_customer_id = :id")
	        .setParameter("id", customer.getId())
	        .setFlushMode(FlushMode.MANUAL)
	        .executeUpdate();
	    }
	
	    return false;
	}

}
