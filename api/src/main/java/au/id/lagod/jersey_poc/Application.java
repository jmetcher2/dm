package au.id.lagod.jersey_poc;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("resources")
public class Application extends ResourceConfig {
    public Application() {
        packages("au.id.lagod.jersey_poc.services");
        register(JacksonFeature.class);
    }
}
