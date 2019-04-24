package dm.test;

import com.objective.keystone.config.HardcodedBootstrap;
import com.objective.keystone.model.Model;

public class BaseUnitTest {
	
	protected Model model = Model.getModel(new HardcodedBootstrap());

}
