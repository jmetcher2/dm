package com.objective.keystone.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.objective.keystone.model.Model;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public class AppConfig extends au.id.lagod.dm.persistence.config.AppConfig {
	

	protected String [] getPackagesToScan() {
		return new String [] { "com.objective.keystone.model.**.*" };
	}

	protected void configureSessionFactoryBean(org.springframework.orm.hibernate5.LocalSessionFactoryBean lsfb) {
	};

    @Bean(name="model")
    @Autowired
    public Model getModel(SessionFactory sf) {
    	return Model.getModel(new DBBootstrapper(sf));
    }
    

}
