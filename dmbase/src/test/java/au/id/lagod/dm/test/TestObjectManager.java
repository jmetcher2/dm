package au.id.lagod.dm.test;

import com.objective.dm.base.DomainObjectCollectionManager;

public class TestObjectManager extends DomainObjectCollectionManager<TestObject> {

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
