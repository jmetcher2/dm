package au.id.lagod.dm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(mode=AdviceMode.ASPECTJ)
@EnableSpringConfigured
public abstract class AppConfig {
	
	@Autowired
	Environment environment;
	

}
