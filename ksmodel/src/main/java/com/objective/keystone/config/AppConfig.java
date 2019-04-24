package com.objective.keystone.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.JtaAnnotationTransactionAspect;

import au.id.lagod.dm.base.Utility;
import com.objective.keystone.model.Model;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public class AppConfig extends au.id.lagod.dm.persistence.config.AppConfig {
	

	protected String [] getPackagesToScan() {
		return new String [] { "com.objective.keystone.model.**.*" };
	}

    @Bean(name="model")
    @Autowired
    public Model getModel(SessionFactory sf) {
    	return Model.getModel(new DBBootstrapper(sf));
    }
    

}
