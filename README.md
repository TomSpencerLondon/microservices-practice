### Commands after download
- mvn clean install
- java -jar target/sa.jar
- go to
http://localhost:9080/data/hello

also for sb:

### Commands after download
- mvn clean install
- java -jar target/sb.jar
- go to
http://localhost:9081/data/hello
- 

![image](https://user-images.githubusercontent.com/27693622/223872390-572a95f0-9dda-4eb8-ba53-01ac17d87f12.png)

```bash

@Path("/hello")
@Singleton
public class HelloController {

    @Inject
    @RestClient ServiceBClient serviceBClient;
    @GET
    public String sayHello() {
        return "Hello from service A to " + serviceBClient.sayHello();
    }
}

```

```bash

package com.example.sa;

import jakarta.ws.rs.GET;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:9081/data/hello")
public interface ServiceBClient {

    @GET
    public String sayHello();
}

```

```bash

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
```

### How can we make the reference fault tolerant?
- Add @Retry!

```bash
package com.example.sa;

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
```



Now it doesn't break every three requests. Yay!

![image](https://user-images.githubusercontent.com/27693622/223878154-a20a1bb3-bd95-450a-9b51-27cae83b00a5.png)



