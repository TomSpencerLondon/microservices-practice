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

https://12factor.net/

The Twelve Factors
I. Codebase
One codebase tracked in revision control, many deploys
II. Dependencies
Explicitly declare and isolate dependencies
III. Config
Store config in the environment
IV. Backing services
Treat backing services as attached resources
V. Build, release, run
Strictly separate build and run stages
VI. Processes
Execute the app as one or more stateless processes
VII. Port binding
Export services via port binding
VIII. Concurrency
Scale out via the process model
IX. Disposability
Maximize robustness with fast startup and graceful shutdown
X. Dev/prod parity
Keep development, staging, and production as similar as possible
XI. Logs
Treat logs as event streams
XII. Admin processes
Run admin/management tasks as one-off processes

https://www.youtube.com/watch?v=Ov3BbGl2iyQ

