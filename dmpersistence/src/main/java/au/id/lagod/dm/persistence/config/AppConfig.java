package au.id.lagod.dm.persistence.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.aspectj.JtaAnnotationTransactionAspect;

import au.id.lagod.dm.base.Utility;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public abstract class AppConfig {
	
	@Autowired
	Environment environment;
	
	@Bean
	public com.mchange.v2.c3p0.ComboPooledDataSource getDataSource() {
		com.mchange.v2.c3p0.ComboPooledDataSource ds =  new com.mchange.v2.c3p0.ComboPooledDataSource();
		Properties prop = getDatasourceProperties();
		
		try {
			ds.setDriverClass(prop.getProperty("driver"));
			ds.setJdbcUrl(prop.getProperty("dburl"));
			ds.setPreferredTestQuery("SELECT 1");
			ds.setTestConnectionOnCheckout(true);
			ds.setUser(prop.getProperty("db.user"));
			ds.setPassword(prop.getProperty("db.password"));
			ds.setTestConnectionOnCheckout(false);
		} catch (PropertyVetoException e) {
			throw new Error(e);
		}
		
		return ds;
	}


	@Bean
	@Autowired
    public org.springframework.orm.hibernate5.LocalSessionFactoryBean getSessionFactory(List<ModelConfig> modelConfigs)
    {
		org.springframework.orm.hibernate5.LocalSessionFactoryBean lsfb = new org.springframework.orm.hibernate5.LocalSessionFactoryBean();
        lsfb.setDataSource(getDataSource());
        lsfb.setHibernateProperties(getHibernateProperties());  
        
        List<String> packages = getPackagesToScan();
        for (ModelConfig mc: modelConfigs) {
        	packages.addAll(mc.getPackagesToScan());
        	mc.configureSessionFactoryBean(lsfb);
        }
        lsfb.setPackagesToScan(
        		packages.toArray(new String[] {})
        );

        return lsfb;
    }
	
	protected List<String> getPackagesToScan() {
		return new ArrayList<String>();
	};
	protected void configureSessionFactoryBean(org.springframework.orm.hibernate5.LocalSessionFactoryBean lsfb) {
	  // Do nothing
	}



    @Bean(name="transactionManager")
    @Autowired
    public PlatformTransactionManager getTransactionManager(org.springframework.orm.hibernate5.LocalSessionFactoryBean sf) {
    	//JpaTransactionManager jtm = new JpaTransactionManager();
    	//jtm.setEntityManagerFactory(sf);
    	HibernateTransactionManager htm = new HibernateTransactionManager();
    	htm.setSessionFactory(sf.getObject());
    	JtaAnnotationTransactionAspect.aspectOf().setTransactionManager(htm);
    	return htm;
    }
    
    public Properties getHibernateProperties() {
		Properties properties;
		
		// externalized properties
		try {
			properties = Utility.loadProperties("/hibernate.properties");
		} catch (IOException e) {
			throw new Error(e);
		}
		
		// hardcoded properties
        properties.put("hibernate.format_sql", true);
        
        return properties;
    }
    
    public Properties getDatasourceProperties() {
		Properties properties;
		
		// externalized properties
		try {
			properties = Utility.loadProperties("/db.properties");
		} catch (IOException e) {
			throw new Error(e);
		}
		
		// spring context properties
		// TODO: this is a breach of layering
		if (environment.containsProperty("ksemodel.db.user"))
			properties.put("db.user",  environment.getProperty("ksemodel.db.user"));
		if (environment.containsProperty("ksemodel.db.password"))
			properties.put("db.password",  environment.getProperty("ksemodel.db.password"));
		if (environment.containsProperty("ksemodel.dburl"))
			properties.put("dburl",  environment.getProperty("ksemodel.dburl"));
		if (environment.containsProperty("ksemodel.driver"))
			properties.put("driver",  environment.getProperty("ksemodel.driver"));
		
		// system properties
		if (System.getProperty("db.user") != null)
			properties.put("db.user", System.getProperty("db.user"));
		if (System.getProperty("db.password") != null)
			properties.put("db.password", System.getProperty("db.password"));
		if (System.getProperty("dburl") != null)
			properties.put("dburl", System.getProperty("dburl"));
		if (System.getProperty("driver") != null) 
			properties.put("driver",  System.getProperty("driver"));
		
		// hardcoded properties
		properties.putIfAbsent("driver","net.sourceforge.jtds.jdbc.Driver");

        
        return properties;
    }
    

}
