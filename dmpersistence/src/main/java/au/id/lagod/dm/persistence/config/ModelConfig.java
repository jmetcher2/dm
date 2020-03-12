package au.id.lagod.dm.persistence.config;

import java.util.List;

/**
 * Implement this interface to get a opportunity to configure the session factory
 * 
 * @author owner
 *
 */
public interface ModelConfig {

	/**
	 * 
	 * @return a list of package names to be passed to 
	 * org.springframework.orm.hibernate5.LocalSessionFactoryBean.setPackagesToScan(String... packagesToScan)
	 */
	public List<String> getPackagesToScan();

	/**
	 * Call any configurations method (e.g. setHibernateProperties) you need to 
	 * on the session factory
	 * Keep in mind that there may be other configurers, so do your configuration additively.
	 * Assume that a shared datasource, transaction manager, etc, are already configured, so limit your configuration
	 * to setting properties and the like.  
	 * 
	 * @param lsfb The session factory bean to be configured
	 */
	public void configureSessionFactoryBean(org.springframework.orm.hibernate5.LocalSessionFactoryBean lsfb);

}
