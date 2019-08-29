package au.id.lagod.dmexample.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import au.id.lagod.dmexample.model.ExampleModel;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public class AppConfig extends au.id.lagod.dm.persistence.config.AppConfig {
	
	@Override
	protected String[] getPackagesToScan() {
		return new String[] { "au.id.lagod.dmexample.model.**.*" };
	}
	
    @Bean(name="model")
    public ExampleModel getModel(SessionFactory sf) {
    	return ExampleModel.getModel(new InMemoryBootstrapper(sf));
    }
    

}
