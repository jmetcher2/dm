package au.id.lagod.dm.test;

import java.util.HashSet;

import au.id.lagod.dm.base.DomainObjectCollectionManager;

public class TestObjectManager extends DomainObjectCollectionManager<TestObject> {

	public TestObjectManager() {
		super(new HashSet<TestObject>());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected TestObject instantiate(String name) {
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
