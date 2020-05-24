package mvcrest;

import mvcrest.filters.CorsFilter;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

// Jersey ce 'rest' da prevede u '/rest/*'
@ApplicationPath("rest")
public class RestApp extends ResourceConfig {

    public RestApp() {
        // Ovde se navodi paket u kome smo definisali servlet-mapping
         packages("mvcrest.avioni");
         register(CorsFilter.class);
    }
}
