package au.id.lagod.jersey_poc;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.objective.keystone.config.AppConfig;

import au.id.lagod.jersey_poc.links.LinkInterceptor;

@ApplicationPath("resources")
public class Application extends ResourceConfig {
    public Application() {
        packages("au.id.lagod.jersey_poc.services");
        register(JacksonFeature.class);
        register(DeclarativeLinkingFeature.class);
        register(LinkInterceptor.class);
        //property("contextConfig", new AnnotationConfigApplicationContext(AppConfig.class));
    }
}
