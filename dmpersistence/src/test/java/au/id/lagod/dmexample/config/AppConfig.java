package au.id.lagod.dmexample.config;

import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import au.id.lagod.dm.collections.FinderFactory;
import au.id.lagod.dmexample.model.ExampleFinderFactory;
import au.id.lagod.dmexample.model.ExampleModel;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public class AppConfig extends au.id.lagod.dm.persistence.config.AppConfig {
	
	@Override
	protected List<String> getPackagesToScan() {
		return Arrays.asList(new String[] { "au.id.lagod.dmexample.model.**.*" });
	}
	
	@Primary
	@Bean
	public FinderFactory getFinderFactory(SessionFactory sf) {
		return new ExampleFinderFactory(sf);
	}
	
    @Bean(name="model")
    public ExampleModel getModel(SessionFactory sf) {
    	return ExampleModel.getModel(new InMemoryBootstrapper(sf));
    }

	@Override
	protected void configureSessionFactoryBean(LocalSessionFactoryBean arg0) {
		// TODO Auto-generated method stub
		
	}
    

}
