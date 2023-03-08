package com.example.sa;

import jakarta.ws.rs.GET;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9081/data/hello")
public interface ServiceBClient {

    @GET
    public String sayHello();
}
