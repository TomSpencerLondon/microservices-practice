package com.example.sa;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {


    @Inject @ConfigProperty(name = "userName")
    String userName;

    @Inject
    @RestClient ServiceBClient serviceBClient;
    @Retry
    @GET
    public String sayHello() {
        return "Hello from " + userName + " to " + serviceBClient.sayHello();
    }
}
