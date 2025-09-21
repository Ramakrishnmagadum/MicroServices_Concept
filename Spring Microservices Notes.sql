Concept : 
1. API Gateway
2. Distributed Tracing
3. Server Config
4. Fault Tolerance (Circuit Breakers)

1. Microservices Basics
What are microservices?
Monolith vs Microservices
Advantages (scalability, agility) vs Challenges (complexity, communication).

2. Service Design
Domain-Driven Design (DDD)
Bounded Context
Database per service principle
Stateless vs Stateful services.

----🌐 Communication---
3 . Service-to-Service Communication
Synchronous (REST, gRPC)
Asynchronous (Kafka, RabbitMQ, messaging)
Event-driven architecture.

4 . API Gateway
Routing, Security, Load balancing, Aggregation.
Tools: Zuul, Spring Cloud Gateway, Kong, NGINX.

5. Service Discovery
Dynamic discovery of services (Eureka, Consul, Kubernetes Service).

----⚙️ Configuration & Management----

6. Centralized Configuration
Config Server (Spring Cloud Config, Consul, Vault).

7. Distributed Tracing & Logging
Correlation IDs
Tools: Zipkin, Jaeger, Sleuth, OpenTelemetry.

8. Observability
Monitoring (Prometheus, Grafana, Micrometer).
Logging (ELK Stack, Splunk).
Metrics & Health Checks.

---🔒 Security-----

9. Authentication & Authorization
OAuth2, OpenID Connect, JWT.
Identity providers (Okta, Keycloak, Auth0).
Secure communication (HTTPS, mTLS).

-----🛠️ Infrastructure & Deployment----------

10. Containers & Orchestration
Docker basics
Kubernetes (K8s) concepts (Pods, Services, Ingress, ConfigMaps, Secrets).

11. Service Mesh
Sidecar pattern
Istio, Linkerd → for traffic management, security, observability.

12. CI/CD Pipelines
Automated builds, testing, deployments.
Tools: Jenkins, GitHub Actions, GitLab CI, ArgoCD.

---📦 Data & Transactions------

13. Database per Microservice
Polyglot persistence (each service can use different DB).
Data consistency challenges.

15. Distributed Transactions
Saga Pattern
Event Sourcing
CQRS (Command Query Responsibility Segregation).

------Architecture & Patterns------

16.Microservices Design Patterns
API Gateway
Circuit Breaker (Resilience4j, Hystrix)
Retry, Bulkhead, Fallback
Strangler Fig (migration pattern).

17. Event-Driven Architecture
Publish/Subscribe
Event sourcing
Outbox pattern.

--------🌍 Cloud & Scalability------------

18.Cloud-Native Microservices
12-Factor App principles.
Cloud services (AWS, Azure, GCP).
Horizontal scaling.

19. Resilience & Fault Tolerance
Handling partial failures.
Chaos Engineering (Netflix Chaos Monkey).
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

API GateWay 
1. Create API Gateway Project
Add dependencies (pom.xml):
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
</dependencies>

2. Enable Discovery Client
@EnableDiscoveryClient   ---> On MainClass add this annotations....
-------What it does---------
Marks a Spring Boot application as a Discovery Client.
This means the service will register itself with a service registry (like Eureka, Consul, or Zookeeper) and can also discover other services from the registry.
--------Difference: @EnableDiscoveryClient vs @EnableEurekaClient----------
@EnableEurekaClient → Specific to Eureka only.
@EnableDiscoveryClient → More generic (works with Eureka, Consul, Zookeeper).
Most projects just use @EnableDiscoveryClient for flexibility.


3. Configure Routes (application.yml)
spring: 
  cloud: 
    gateway: 
	  routes: 
	     - id: user-service
		 uri: lb://USER-SERVICE 
		 predicates: - Path=/users/** 
		 
		 - id: order-service 
		 uri: lb://ORDER-SERVICE 
		 predicates: - Path=/orders/**
		
Imp ---> 
Config Explained

1. spring: cloud:gateway:routes:
This section defines routing rules for the API Gateway.
Each route tells the Gateway:“If the request matches this condition (predicate), forward it to this destination (uri).”
Example :- id: user-service
          uri: lb://USER-SERVICE
          predicates:- Path=/users/**
Explanation how it works :
id: user-service
→ A unique identifier for this route (used internally, good for debugging/logging).
uri: lb://USER-SERVICE
→ lb:// = LoadBalancer.
→ Tells Gateway to forward requests to the service named USER-SERVICE in the Eureka service registry.
→ If multiple instances of USER-SERVICE exist, Gateway will load balance between them.
predicates: - Path=/users/**
→ A predicate defines when this route is applied.
→ Here: if the request path starts with /users/ (like /users/123), it will be routed to USER-SERVICE.

Example in Action
Client calls:
http://localhost:8080/users/123
Gateway checks routes.
Matches /users/**.
Forwards request to USER-SERVICE (e.g., http://localhost:9001/users/123).

One more Question :
If I have 100 Microservices then no need to mention all the Predicates here ---we can tell do it auto 
Option 1: Manual Route Configuration (Static)  --we can register all the configuration seperately as well..

Option 2: Dynamic Routing with Discovery Locator
Spring Cloud Gateway can automatically create routes for every registered service.
Enable this in application.properties File 
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
		  
Now:If USER-SERVICE is registered in Eureka, Gateway auto-creates a route.
You can access it like:http://localhost:8080/USER-SERVICE/users/123
(or lowercase: /user-service/** if you enable lower-case-service-id).
✅ No need to define 100 routes manually.		  
		  
		  
-----------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------
Config Server

A config server in a microservices architecture centralizes the management of configuration properties for all services. Instead of each microservice having its own local configuration files, a config server acts as a single source of truth, providing configurations to microservices on demand.
Here's how it generally works and its benefits:
How it works:
Centralized Storage: 
Configuration files (e.g., properties, YAML) for all microservices are stored in a central repository, often a version control system like Git, accessible by the config server.
Config Server: 
A dedicated service (like Spring Cloud Config Server) exposes a REST API through which microservices can request their specific configurations.
Config Client: 
Each microservice includes a "config client" library that enables it to connect to the config server and retrieve its required configuration properties during startup or even dynamically at runtime.
Dynamic Updates: 
When configuration changes are made in the central repository, the config server can notify or be polled by microservices, allowing them to refresh their configurations without requiring a full application restart. Tools like Spring Cloud Bus can facilitate this by pushing updates to subscribed microservices.


Benefits:
Centralized Management: Simplifies the management of configurations across numerous microservices, providing a single location for updates and version control.

Consistency: Ensures all instances of a microservice use the same configuration, reducing inconsistencies and potential errors.

Dynamic Configuration: Allows for real-time updates of configurations without downtime or redeployments, crucial for agile environments.

Environment-Specific Configurations: Easily manage different configurations for various environments (development, testing, production) using profiles.

Enhanced Security: Sensitive credentials and configurations can be stored securely and retrieved by services, potentially with encryption.

Reduced Maintenance Overhead: Automates configuration management, reducing manual effort and potential for human error.

Scalability and Resilience: Config servers can be made highly available and load-balanced to prevent single points of failure in a distributed system.

------step-by-step setup of a Spring Cloud Config Server----------
-----Step 1: Create Config Server-------
1. Create a Spring Boot project (Maven or Gradle).
In pom.xml add:

<dependencies>
    <!-- Spring Cloud Config Server -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2023.0.3</version> <!-- check latest -->
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

2. In main class, enable config server:
@EnableConfigServer

What this annotation will do @EnableConfigServer
"This application is not just a normal Spring Boot app, it should act as a Config Server that serves configuration to other microservices."
What happens when you add @EnableConfigServer?
1. Turns your Spring Boot app into a Config Server
It activates Spring Cloud Config Server auto-configuration.
Your application starts listening for configuration requests from clients.

2. Exposes REST endpoints for config clients.

3. Loads configuration from a backend store
(depending on how you configure it in application.yml):
Git (most common) , File system , Vault , JDBC (database) so on

3.  Configure application.yml (point to Git repo for configs):
server:
  port: 8888

spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-username/config-repo
          clone-on-start: true
		  
spring.cloud.config.server.git.uri
This specifies the Git repository URL where your centralized configuration files live.
The Config Server will clone this repository on startup.
clone-on-start: true
Normally, Config Server clones the Git repo lazily (only when the first request comes in).
If true, it forces the server to clone the Git repo immediately at startup.


Step 2: Create Config Repository
In GitHub (or local Git repo), create files:
application.yml (common for all services)
app:
  message: Hello from Config Server (default)
order-service.yml (specific for order-service)
app:
  message: Order Service Config from Git
  
Step 3: Create Microservice Client
1.  Create another Spring Boot app (e.g., order-service).
Add dependency:
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Cloud Config Client -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
</dependencies>
  
 2. Configure bootstrap.yml (so it fetches config from server at startup):

spring:
  application:
    name: order-service

  cloud:
    config:
      uri: http://localhost:8888   
	  
Here we have two questions 
Instead of hardcoding this uri: http://localhost:8888
we can register Config Server project with Eureka ...
then no need to hardcode it ---- Instead, they use the service ID (e.g., CONFIG-SERVER) to locate it.
Spring Cloud Config Client + Eureka integration lets microservices fetch configs dynamically.
For That :
  cloud:
    config:
      discovery:
        enabled: true
        service-id: CONFIG-SERVER
		
2 .second questions is :
Example: 
In config Server (Git) has three .yml files
   1. application.yml          # Common config for ALL services
   2. order-service.yml        # Config only for Order Service
   3. payment-service.yml      # Config only for Payment Service

   
   If am hitting the Request from the order-service 
   (Means :-spring:application: name: order-service)
   then By default it will check order-service.yml and Merge with application.yml 
  ------------Config Server Merge Logic----------
  Config Server looks in Git and merges configs in order of priority:
application.yml → global defaults
order-service.yml → overrides the global ones
That means:
If a property exists only in application.yml, the service gets it.
If the same property exists in both, the value from order-service.yml wins (because service-specific configs have higher priority).
How it works :
When Order Service starts:
Config Server first loads application.yml (gets Hello from Config Server (default)).
Then it loads order-service.yml and overwrites app.message with "Order Service Config from Git".

-----------------------------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------------------------
---circuit Breakers-----

A circuit breaker in microservices is a pattern that prevents cascading failures by acting as a proxy between services, mimicking an electrical circuit breaker.
Summery :-  
 It detects failing remote service calls, opens the circuit to stop further requests to that service, and returns an immediate error or fallback response. After a timeout, it enters a half-open state to test if the service has recovered, returning to a closed state if calls succeed and staying open if they fail.  

How it Works 
1. Closed State: In the normal, closed state, the circuit breaker allows requests to pass through to the target microservice. It monitors the success and failure rates of these requests.
2. Open State: If the number of failures exceeds a configured threshold, the circuit breaker trips into the open state. In this state, it intercepts all calls to the failing service and returns an error immediately without making a request, preventing the caller from waiting for a timeout.
3. Half-Open State: After a pre-configured timeout period in the open state, the circuit breaker enters a half-open state. It allows a limited number of test calls to the service.
4. State Transition:
If the test calls in the half-open state are successful, the circuit breaker closes, indicating the service is healthy again.
If any test call in the half-open state fails, the circuit breaker immediately returns to the open state.

------------------------------------------------------
Tools and Frameworks for Implementing Circuit Breaker
Below are some tools and frameworks for implementing circuit breaker:

Hystrix: Developed by Netflix, Hystrix is one of the most well-known libraries for implementing the Circuit Breaker pattern in Java applications. It provides features like fallback mechanisms, metrics, and monitoring, helping to manage service calls effectively.
Resilience4j: This lightweight, modular library for Java is designed to work seamlessly with Spring Boot and other frameworks. Resilience4j includes a circuit breaker, along with other resilience patterns like retries and rate limiting, allowing for fine-tuned control over service interactions.
Spring Cloud Circuit Breaker: This project provides an abstraction for circuit breakers in Spring applications. It allows you to use different circuit breaker implementations (like Hystrix or Resilience4j) interchangeably, making it easy to switch between them based on your needs.
------------------------------------------------------
Difference between Circuit Breaker vs Retry
------Retry Pattern------------
What it does:
If a request to another service fails (e.g., due to network glitch, temporary overload), it automatically retries the request a few times before giving up.
Use case:
Good for transient failures that are likely to succeed if retried (like a momentary network timeout).
Example :
@Retry(name = "paymentService", fallbackMethod = "paymentFallback")
public String processPayment(String id) {
    return restTemplate.getForObject("http://payment-service/pay/" + id, String.class);
}

-----Circuit Breaker Pattern---
What it does:
Stops sending requests to a failing service after detecting too many failures. Instead, it returns a fallback immediately until the service recovers.
Use case:
Good for persistent failures where retrying won’t help (e.g., service is completely down).

Example:
@CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
public String getOrder(String id) {
    return restTemplate.getForObject("http://order-service/orders/" + id, String.class);
}

In short:
Retry = "Try again, maybe it was a glitch."
Circuit Breaker = "Stop trying, service is broken. Let’s wait and check later."
-----------------------------------------------------------------------------------

building  a sample project with Spring Cloud Circuit Breaker (Resilience4j):-
I’ll create a mini microservices setup with:
Order Service → a dummy downstream service.
API Gateway / Client Service → calls Order Service with Circuit Breaker protection.

This is Client Service ----
1. Project Dependencies (pom.xml)
 <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Actuator (to monitor health) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

    <!-- Spring Cloud Circuit Breaker Spring Cloud Circuit Breaker abstraction-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>
	
	<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-spring-boot3</artifactId>
</dependency>

	
2. Configuration (application.yml)
resilience4j:
  circuitbreaker:
    instances:
      orderService:                # name of the circuit breaker
        registerHealthIndicator: true
        failureRateThreshold: 50   # % of failures before opening circuit
        slowCallDurationThreshold: 2s   # calls taking longer than this are slow
        slowCallRateThreshold: 50
        waitDurationInOpenState: 5s   # how long to wait before half-open
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED

--------Properties in Detail------------
1. orderService:
This is the name of your circuit breaker instance.
Must match the name you use in @CircuitBreaker(name = "orderService").
2. registerHealthIndicator: true
Exposes the health of this circuit breaker via Spring Boot Actuator (/actuator/health).
Useful for monitoring dashboards like Prometheus/Grafana.
3. failureRateThreshold: 50
If 50% or more calls fail within the sliding window, the circuit will open.
Example: In a window of 10 calls → if 5 fail, breaker trips.
4.  slowCallDurationThreshold: 2s
Any call taking longer than 2 seconds is considered a “slow call.”
Helps detect latency issues (not just outright failures).
5.  slowCallRateThreshold: 50
If 50% or more of the calls are slow, circuit opens.
Example: If 10 calls are made and 5 took >2s → circuit breaker trips.
6.  aitDurationInOpenState: 5s
How long the circuit stays open before moving to half-open.
During these 5 seconds, all calls fail fast (go straight to fallback).
7.  permittedNumberOfCallsInHalfOpenState: 3
When moving to half-open, only 3 trial requests are allowed.
If they succeed → circuit closes again.
If they fail → circuit goes back to open.
8.  slidingWindowSize: 10
Number of calls to keep in the “sliding window” for calculating failure/slow rates.
Here, it checks the last 10 calls.
9.  slidingWindowType: COUNT_BASED
Defines how the window is measured.
COUNT_BASED: Tracks the last N calls (like last 10 requests).
TIME_BASED: Tracks calls in the last N seconds (e.g., last 60 seconds).
------------------------------------------------------------------------

3. Service Layer with Circuit Breaker
  @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    public String fetchOrder(String orderId) {
        return restTemplate.getForObject("http://localhost:8082/orders/" + orderId, String.class);
    }

    // Fallback method (must match signature + Throwable)
    public String orderFallback(String orderId, Throwable t) {
        return "Order Service is unavailable. Please try again later.";
    }

Question :
1. If the circuit breaker is in Closed state, and the opposite (downstream) service suddenly goes down, how many calls will be made before the breaker changes to Open state?
Config Recao :
failureRateThreshold: 50      # if >= 50% calls fail → trip to OPEN
slidingWindowSize: 10         # look at the last 10 calls
slidingWindowType: COUNT_BASED
----Case: Service suddenly goes down
The first call → fails (failure rate = 100%, but only 1 call made, not enough data yet).
By default, Resilience4j requires at least slidingWindowSize calls before evaluating failure rate.
Here, that means 10 calls must be recorded before it checks.
Once 10 calls are made → if 5 or more failed → circuit breaker switches to OPEN.


How it works :-
If Order Service responds → success.
If Order Service fails (throws error or down):
First few failures → retried normally.
If failure threshold (50%) is crossed → Circuit Breaker opens.
Then all calls return fallback immediately.
After 5 seconds wait → Circuit goes Half-Open (lets a few test requests through).
If success → circuit closes again.
If fail → stays open.

----Monitoring (Actuator)---------
http://localhost:8081/actuator/health
http://localhost:8081/actuator/metrics/resilience4j.circuitbreaker.calls
This shows how many calls were successful, failed, or short-circuited.

----------------------------------------------------------------------------------------------


Retry + Circuit Breaker together:
Use of Retry + Circuit Breaker :--
Why we need both
Retry without Circuit Breaker:-If service is down, retries just keep hammering it → waste of resources.
Circuit Breaker without Retry:-If service has a small glitch (e.g., one packet drop), even 1 failed call gets counted, but the call could have succeeded on retry.
Retry + Circuit Breaker together (best practice):-
Retry fixes temporary hiccups at the request level.
Circuit Breaker prevents cascading failures at the system level.

---------------------------------
Why we need this both :
For the first time Retry will take care if any failure or Exception Comes:
Example : In retry If I configured as it should run 3 times, then it will try 3 times if any minner exception or Network Exception it will fix else all three attempts will fail ---> then it will consider as 1 fail in Circuit Breakers-----and again Circuit Breakers will try for some times times(Mentioned times) and it will fail ..
So Retry concept imp because it will Retry In time difference and if network issues so on it will fix it ...no need to go to the circuitbreakers...

Example with Your Config
Retry: maxAttempts: 3
CircuitBreaker: slidingWindowSize: 10, failureRateThreshold: 50
👉 Flow if service is down:
Call 1 → fails, Retry tries 3 times → all fail → counts as 1 failed call in Circuit Breaker.
This happens 10 times → breaker trips OPEN.
From now on → no retries, just fallback immediately.
---------------------------------


Step 1: Add Dependencies
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-retry</artifactId>
</dependency>

Step 2: Configure Retry + Circuit Breaker in application.yml
resilience4j:
  circuitbreaker:
    instances:
      orderService:
        registerHealthIndicator: true
        failureRateThreshold: 50
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED

  retry:
    instances:
      orderService:
        maxAttempts: 3          # 1 initial + 2 retries
        waitDuration: 2s        # wait 2s before retry
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
Here Explanation is :



Step 3: Apply Both Annotations in Service Layer
   @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    @Retry(name = "orderService")
    public String fetchOrder(String orderId) {
        return restTemplate.getForObject("http://localhost:8082/orders/" + orderId, String.class);
    }

    // Fallback method if retries + circuit breaker fail
    public String orderFallback(String orderId, Throwable t) {
        return "⚠️ Order Service is unavailable. Please try again later.";
    }


-----------------------------------------------------------------------------------------------------------------------
What is a Rate Limiter?
A Rate Limiter controls how many calls per second/minute are allowed to a service.
If the limit is exceeded → calls are rejected (or delayed).
Purpose: protect downstream services from being overwhelmed.
Think of it like a traffic signal at a busy intersection — only let a certain number of cars pass per second.

Why use a Rate Limiter?
1. Protect downstream services:- If too many requests flood an external API (like payment gateway), the rate limiter ensures you don’t exceed their limits.
2.  Stability under load:- During traffic spikes, rate limiter sheds extra load instead of crashing the system.



How to do it 
1. Depedencies
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-ratelimiter</artifactId>
</dependency>

Step 2: Extend Configuration (application.yml)
 ratelimiter:
    instances:
      orderService:
        limitForPeriod: 5          # allow 5 calls per refresh period
        limitRefreshPeriod: 10s    # refresh every 10 seconds
        timeoutDuration: 1s        # wait 1s if limit exceeded
👉 Meaning:
Allows 5 calls per 10 seconds.
Extra calls wait up to 1s, then fail.
Protects downstream from traffic burst.

Step 3: Apply All Annotations in Service Layer
  @RateLimiter(name = "orderService")
    public String fetchOrder(String orderId) {
        return restTemplate.getForObject("http://localhost:8082/orders/" + orderId, String.class);
    }
-----------------------------------------------------------------------------------------------------------------------

Bulkhead is another resilience pattern in Resilience4j (and microservices in general):--

1. Semaphore Bulkhead:--
Limits the number of concurrent calls to a service.
If limit exceeded → new calls are rejected immediately (or wait for a bit if configured).
👉 Example:
maxConcurrentCalls = 5
At most 5 threads can call the service.
If a 6th request comes in → it fails fast.

Why Bulkhead is useful
1. Prevents resource exhaustion
2. Improves system stability

Step 1: Add Dependencies
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-bulkhead</artifactId>
</dependency>

Step 2: Extend Configuration (application.yml)
  bulkhead:
    instances:
      orderService:
        maxConcurrentCalls: 5      # allow max 5 threads at once
        maxWaitDuration: 2s        # wait before rejecting

Step 3: Apply All Annotations in Service Layer		
  @Bulkhead(name = "orderService", type = Bulkhead.Type.SEMAPHORE)
    public String fetchOrder(String orderId) {
        return restTemplate.getForObject("http://localhost:8082/orders/" + orderId, String.class);
    }
	
Monitoring (via Actuator):-
Circuit Breaker calls → http://localhost:8081/actuator/metrics/resilience4j.circuitbreaker.calls
Retry calls → http://localhost:8081/actuator/metrics/resilience4j.retry.calls
Bulkhead stats → http://localhost:8081/actuator/metrics/resilience4j.bulkhead.available.concurrent.calls
RateLimiter stats → http://localhost:8081/actuator/metrics/resilience4j.ratelimiter.available.permissions

Final Result:-
Small hiccups → Retry fixes them.
Persistent failure → Circuit Breaker prevents cascading issues.
Too many parallel calls → Bulkhead isolates resources.
Traffic spikes → Rate Limiter throttles requests.

-----------------------------------------------------------------------------------------------------------------------





