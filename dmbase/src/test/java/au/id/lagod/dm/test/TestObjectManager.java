package au.id.lagod.dm.test;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class TestObjectManager extends DomainObjectCollectionManager<TestObject> {

	@Override
	public TestObject create(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestObject get(String textID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<TestObject> getManagedObjectClass() {
		return TestObject.class;
	}


}
