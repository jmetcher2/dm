package com.objective.keystone.model;

import static org.junit.Assert.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.objective.keystone.model.customer.Customer;
import com.objective.keystone.model.folder.Folder;
import com.objective.keystone.model.folder.FolderType;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.BaseChildObjectPersistenceTests;

@ContextConfiguration(classes = {com.objective.keystone.config.AppConfig.class})
public class FolderTest extends BaseChildObjectPersistenceTests<Folder, Customer> {
	@Autowired
	protected Model model;

	@Override
	protected void doSetupBeforeTransaction() {
		model.getCustomers().create(getParentName());
		super.doSetupBeforeTransaction();
	}

	@Override
	protected void doTeardownAfterTransaction() {
		super.doTeardownAfterTransaction();
		removeParent();
	}

	@Override
	protected void removeParent() {
		model.getCustomers().remove(getParent());
	}

	@Override
	public Customer getParent() {
		return model.customers(getParentName());
	}

	@Override
	protected DomainObjectCollectionManager<Folder> getChildObjectManager() {
		return getParent().getFolders();
	}
	
	@Override
	public void checkCreatedObject(Folder f) {
		assertEquals(FolderType.folder.name(),  f.getType());
		assertEquals(LiveStatus.PUBLIC, f.getLiveStatus());
	}

}
