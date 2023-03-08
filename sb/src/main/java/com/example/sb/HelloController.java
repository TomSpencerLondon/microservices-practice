package com.example.sb;

import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 *
 */
@Path("/hello")
@Singleton
public class HelloController {
    static int i = 0;
    @GET
    public String sayHello() {
        if (i++ % 3 == 0) {
            throw new RuntimeException("I know...");
        }
        return "Hello from service B!";
    }
}
