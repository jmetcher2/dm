package au.id.lagod.dmexample.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import au.id.lagod.dm.config.NullBootstrap;
import au.id.lagod.dmexample.model.Model;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public class AppConfig extends au.id.lagod.dm.persistence.config.AppConfig {
	
    @Bean(name="model")
    public Model getModel() {
    	return Model.getModel(new NullBootstrap());
    }
    

}
