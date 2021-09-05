package au.id.lagod.dm.test.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.TextKeyCollectionPersistenceTests;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.Customer;

@ContextConfiguration(classes = {au.id.lagod.dmexample.config.AppConfig.class})
public class CustomerTest extends TextKeyCollectionPersistenceTests<Customer>{
	@Autowired
	protected ExampleModel model;


	@Override
	protected DomainObjectCollectionManager<Customer> getChildObjectManager() {
		return model.getCustomers();
	}

}
