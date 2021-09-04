package au.id.lagod.dm.test;

import java.util.HashSet;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class TestObjectManager extends DomainObjectCollectionManager<TestObject> {

	public TestObjectManager() {
		super(new HashSet<TestObject>());
	}

	@Override
	protected TestObject instantiate(String name) {
		return new TestObject(0, name, null);
	}

	@Override
	public Class<TestObject> getManagedObjectClass() {
		return TestObject.class;
	}


}
