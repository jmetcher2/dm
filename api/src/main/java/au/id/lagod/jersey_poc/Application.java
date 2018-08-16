package au.id.lagod.jersey_poc;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.objective.keystone.config.AppConfig;

@ApplicationPath("resources")
public class Application extends ResourceConfig {
    public Application() {
        packages("au.id.lagod.jersey_poc.services");
        register(JacksonFeature.class);
        //property("contextConfig", new AnnotationConfigApplicationContext(AppConfig.class));
    }
}
