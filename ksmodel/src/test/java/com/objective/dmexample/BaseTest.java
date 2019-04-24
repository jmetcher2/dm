package au.id.lagod.dmexample;

import org.junit.Before;

import com.objective.keystone.config.HardcodedBootstrap;
import com.objective.keystone.model.Model;

public class BaseTest {

	protected Model model;

	public BaseTest() {
		super();
	}
	
	@Before
	public void beforeTest() {
		Model.resetModel();
		model = Model.getModel(new HardcodedBootstrap());
	}

}