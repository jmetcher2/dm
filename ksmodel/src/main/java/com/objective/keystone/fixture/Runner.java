package com.objective.keystone.fixture;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.objective.keystone.config.AppConfig;
import com.objective.keystone.model.Model;
import com.objective.keystone.model.customer.Customer;

public class Runner {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		Model model = context.getBean(Model.class);
		SessionFactory sf = context.getBean(SessionFactory.class);
		
//		Basic fixture = new Basic("runner");
		
		Adhoc fixture = new Adhoc("runner");
		
		fixture.run(model);
//		
//		fixture.teardown(model);
//		fixture.setup(model, sf);
		
		context.close();
	}

}
