package au.id.lagod.dm.test;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.UUID;

import org.springframework.orm.hibernate5.HibernateTransactionManager;

import au.id.lagod.dm.base.Utility;

public class DoInTransaction {

	public static void doAction(HibernateTransactionManager txManager, Runnable action) {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setName(UUID.randomUUID().toString());
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		
			TransactionStatus status = txManager.getTransaction(def);
			
			try {
				action.run();
			
				txManager.commit(status);
				
			}
			catch (javax.validation.ConstraintViolationException e) {
				Utility.printMessages(e);
				
				txManager.rollback(status);
				
				throw new Error (e);
			}
			catch (java.lang.Throwable e) {
				txManager.rollback(status);
				
				throw new Error(e);
			}
			
		}

}