package au.id.lagod.dm.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import au.id.lagod.dm.base.DomainObjectCollectionManager;
import au.id.lagod.dm.test.AssociationPersistenceTests;
import au.id.lagod.dmexample.model.ExampleModel;
import au.id.lagod.dmexample.model.customer.folder.Folder;
import au.id.lagod.dmexample.model.group.Group;
import au.id.lagod.dmexample.model.group.folder.GroupFolder;
import au.id.lagod.dmexample.model.group.folder.GroupFolderManager;

@ContextConfiguration(classes = {au.id.lagod.dmexample.config.AppConfig.class})
public class GroupFolderTest extends AssociationPersistenceTests<GroupFolder, Group, Folder> {
	private static final String CUSTOMER_NAME = "GroupFolderTest";
	@Autowired
	protected ExampleModel model;
	
	@Override
	protected void doSetupBeforeTransaction(){
		model.getCustomers().create(CUSTOMER_NAME);
		getParent1Manager().create(parent1Name);
		getParent2Manager().create(parent2Name);
		getParent2Manager().create(parent2InTestName);
		super.doSetupBeforeTransaction();
	}
	
	@Override
	protected void doTeardownAfterTransaction(){
		super.doTeardownAfterTransaction();
		model.getCustomers().remove(model.customers(CUSTOMER_NAME));
	}


	@Override
	protected DomainObjectCollectionManager<Group> getParent1Manager() {
		return model.customers(CUSTOMER_NAME).getGroups();
	}

	@Override
	protected DomainObjectCollectionManager<Folder> getParent2Manager() {
		return model.customers(CUSTOMER_NAME).getFolders();
	}

	@Override
	protected GroupFolderManager getChildObjectManager() {
		return getParent1().getGroupFolders();
	}

	@Override
	public Group getParent1() {
		return getParent1Manager().get(parent1Name);
	}

	@Override
	protected Folder getParent2() {
		return getParent2Manager().get(parent2Name);
	}

	@Override
	protected Folder getParent2InTest() {
		return getParent2Manager().get(parent2InTestName);
	}

	@Override
	protected String getFindKey() {
		return "folder." + Folder.getTextKeyField(Folder.class);
	}

	@Override
	protected Object getFindValue() {
		return getParent2().getTextKey();
	}
	
	@Override
	@Test
	public void testGetAttached() {
		sf.getCurrentSession().update(domainObject);
		sf.getCurrentSession().update(((GroupFolder) domainObject).getFolder());
		
		GroupFolder domainObject2 = getChildObject();
		
		assertEquals(domainObject.getId(), domainObject2.getId());
		assertEquals(domainObject, domainObject2);

		checkCreatedObject(domainObject2);
	}





}
