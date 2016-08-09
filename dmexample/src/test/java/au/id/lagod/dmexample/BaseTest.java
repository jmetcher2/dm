package au.id.lagod.dmexample;

import org.junit.Before;

import au.id.lagod.dmexample.collections.Model;
import au.id.lagod.dmexample.config.HardcodedBootstrap;

public class BaseTest {

	protected Model model;

	public BaseTest() {
		super();
	}
	
	@Before
	public void beforeTest() {
		Model.resetModel();
		model = Model.getModel();
		new HardcodedBootstrap().bootstrap(model);
	}

}