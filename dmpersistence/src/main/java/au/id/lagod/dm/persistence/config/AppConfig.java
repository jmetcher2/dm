package au.id.lagod.dm.persistence.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

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

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public abstract class AppConfig {
	
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
			ds.setTestConnectionOnCheckout(true);
		} catch (PropertyVetoException e) {
			throw new Error(e);
		}
		
		return ds;
	}


	@Bean
	@Autowired
    public org.springframework.orm.hibernate5.LocalSessionFactoryBean getSessionFactory()
    {
		org.springframework.orm.hibernate5.LocalSessionFactoryBean lsfb = new org.springframework.orm.hibernate5.LocalSessionFactoryBean();
        lsfb.setDataSource(getDataSource());
        lsfb.setHibernateProperties(getHibernateProperties());  
        lsfb.setPackagesToScan(
        		getPackagesToScan()
        );
        configureSessionFactoryBean(lsfb);

        return lsfb;
    }
	
	protected String [] getPackagesToScan() {
		return new String[0];
	};
	protected void configureSessionFactoryBean(org.springframework.orm.hibernate5.LocalSessionFactoryBean lsfb) {
	  // Do nothing
	}



    @Bean(name="transactionManager")
    @Autowired
    public PlatformTransactionManager getTransactionManager() {
    	//JpaTransactionManager jtm = new JpaTransactionManager();
    	//jtm.setEntityManagerFactory(sf);
    	HibernateTransactionManager htm = new HibernateTransactionManager();
    	htm.setSessionFactory(getSessionFactory().getObject());
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
		
		// system properties
		if (System.getProperty("db.user") != null)
			properties.put("db.user", System.getProperty("db.user"));
		if (System.getProperty("db.password") != null)
			properties.put("db.password", System.getProperty("db.password"));
		if (System.getProperty("dburl") != null)
			properties.put("dburl", System.getProperty("dburl"));
		
		// hardcoded properties
		properties.putIfAbsent("driver","net.sourceforge.jtds.jdbc.Driver");

        
        return properties;
    }
    

}
