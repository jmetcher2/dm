package au.id.lagod.jersey_poc.fixture;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.objective.keystone.config.AppConfig;
import com.objective.keystone.model.Model;

public class Runner {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		Model model = context.getBean(Model.class);
		SessionFactory sf = context.getBean(SessionFactory.class);
		
		Basic fixture = new Basic();
		
		fixture.teardown(model);
		fixture.setup(model, sf);
		
		context.close();
	}

}
