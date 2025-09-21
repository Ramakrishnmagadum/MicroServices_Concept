package com.MicroUserService.Controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MicroUserService.Entites.User;
import com.MicroUserService.Service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;



@RestController
@RequestMapping("/users")
public class UserController {
	
	@Value("${app.message}")
	private String message;

	@Autowired
	private UserService userService;
	
//	@RequestBody why used ??????convert Json to User Object
//	@RequestBody --How it works 
//Incoming request: When a client sends data in the body of an HTTP request (for example, JSON in a POST or PUT request), Spring MVC reads that body.
//Deserialization: Spring uses an HttpMessageConverter (like Jackson for JSON) to convert that body into the corresponding Java object.
//Binding to method parameter: The converted object is assigned to the method parameter annotated with @RequestBody.
	@PostMapping()
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1=userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	

//	@RateLimiter(name = "orderService")
	
	@Retry(name = "userService" , fallbackMethod = "orderFallback")
//	Stops sending requests to a failing service after detecting too many failures. Instead, it returns a fallback immediately until the service recovers.
	@CircuitBreaker(name = "userService")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUserById(@PathVariable("userId") String userId) {
        System.out.println("Retrying if service fails " + LocalTime.now());
        User user = userService.getUser(userId); // this must throw RuntimeException or configured exception
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<User> orderFallback(String userId, Throwable t) {
        System.out.println("User Service is unavailable. Please try again later.");
        User user = new User();
        user.setUserId("Dummy UserId");
        user.setName("Dummy Name");
        user.setEmail("Dummy@gmail.com");
        user.setAbout("User Service is unavailable. Please try again later.");
        return ResponseEntity.ok(user);
    }

	
	
	//all user get 
	@GetMapping
	public  ResponseEntity<List<User>> getAllUser(){
		List<User> user =userService.getAllUser();
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping("/message")
	public String getMessage() {
		return message;
	}
	
	
}

//---------------       circuit breaker   ----------------
//Question :
//1. If the circuit breaker is in Closed state, and the opposite (downstream) service suddenly goes down, how many calls will be made before the breaker changes to Open state?
//Config Recao :
//failureRateThreshold: 50      # if >= 50% calls fail → trip to OPEN
//slidingWindowSize: 10         # look at the last 10 calls
//slidingWindowType: COUNT_BASED
//----Case: Service suddenly goes down
//The first call → fails (failure rate = 100%, but only 1 call made, not enough data yet).
//By default, Resilience4j requires at least slidingWindowSize calls before evaluating failure rate.
//Here, that means 10 calls must be recorded before it checks.
//Once 10 calls are made → if 5 or more failed → circuit breaker switches to OPEN.
//
//
//How it works :-
//If Order Service responds → success.
//If Order Service fails (throws error or down):
//First few failures → retried normally.
//If failure threshold (50%) is crossed → Circuit Breaker opens.
//Then all calls return fallback immediately.
//After 5 seconds wait → Circuit goes Half-Open (lets a few test requests through).
//If success → circuit closes again.
//If fail → stays open.



//--------------------------Retry + Circuit Breaker together:-----------------------------------------------
//Use of Retry + Circuit Breaker :--
//Why we need both
//Retry without Circuit Breaker:-If service is down, retries just keep hammering it → waste of resources.
//Circuit Breaker without Retry:-If service has a small glitch (e.g., one packet drop), even 1 failed call gets counted, but the call could have succeeded on retry.
//Retry + Circuit Breaker together (best practice):-
//Retry fixes temporary hiccups at the request level.
//Circuit Breaker prevents cascading failures at the system level.


//---------------------------------
//Why we need this both :
//For the first time Retry will take care if any failure or Exception Comes:
//Example : In retry If I configured as it should run 3 times, then it will try 3 times if any minner exception or Network Exception it will fix else all three attempts will fail ---> then it will consider as 1 fail in Circuit Breakers-----and again Circuit Breakers will try for some times times(Mentioned times) and it will fail ..
//So Retry concept imp because it will Retry In time difference and if network issues so on it will fix it ...no need to go to the circuitbreakers...
//
//Example with Your Config
//Retry: maxAttempts: 3
//CircuitBreaker: slidingWindowSize: 10, failureRateThreshold: 50
//👉 Flow if service is down:
//Call 1 → fails, Retry tries 3 times → all fail → counts as 1 failed call in Circuit Breaker.
//This happens 10 times → breaker trips OPEN.
//From now on → no retries, just fallback immediately.
//---------------------------------
//-----------------------------------------------------------------------------------------------------------------------





//-----------------------------------------------------------------------------------------------------------------------
//What is a Rate Limiter?
//A Rate Limiter controls how many calls per second/minute are allowed to a service.
//If the limit is exceeded → calls are rejected (or delayed).
//Purpose: protect downstream services from being overwhelmed.
//Think of it like a traffic signal at a busy intersection — only let a certain number of cars pass per second.
//
//Why use a Rate Limiter?
//1. Protect downstream services:- If too many requests flood an external API (like payment gateway), the rate limiter ensures you don’t exceed their limits.
//2.  Stability under load:- During traffic spikes, rate limiter sheds extra load instead of crashing the system.
//
//
//
//How to do it 
//1. Depedencies
//<dependency>
//    <groupId>io.github.resilience4j</groupId>
//    <artifactId>resilience4j-ratelimiter</artifactId>
//</dependency>
//
//Step 2: Extend Configuration (application.yml)
// ratelimiter:
//    instances:
//      orderService:
//        limitForPeriod: 5          # allow 5 calls per refresh period
//        limitRefreshPeriod: 10s    # refresh every 10 seconds
//        timeoutDuration: 1s        # wait 1s if limit exceeded
//👉 Meaning:
//Allows 5 calls per 10 seconds.
//Extra calls wait up to 1s, then fail.
//Protects downstream from traffic burst.
//
//Step 3: Apply All Annotations in Service Layer
//  @RateLimiter(name = "orderService")
//    public String fetchOrder(String orderId) {
//        return restTemplate.getForObject("http://localhost:8082/orders/" + orderId, String.class);
//    }
//-----------------------------------------------------------------------------------------------------------------------





//-----------------------------------------------------------------------------------------------------------------------
//Bulkhead is another resilience pattern in Resilience4j (and microservices in general):--
//
//1. Semaphore Bulkhead:--
//Limits the number of concurrent calls to a service.
//If limit exceeded → new calls are rejected immediately (or wait for a bit if configured).
//👉 Example:
//maxConcurrentCalls = 5
//At most 5 threads can call the service.
//If a 6th request comes in → it fails fast.
//
//Why Bulkhead is useful
//1. Prevents resource exhaustion
//2. Improves system stability
//
//Step 1: Add Dependencies
//<dependency>
//    <groupId>io.github.resilience4j</groupId>
//    <artifactId>resilience4j-bulkhead</artifactId>
//</dependency>
//
//Step 2: Extend Configuration (application.yml)
//  bulkhead:
//    instances:
//      orderService:
//        maxConcurrentCalls: 5      # allow max 5 threads at once
//        maxWaitDuration: 2s        # wait before rejecting
//
//Step 3: Apply All Annotations in Service Layer		
//  @Bulkhead(name = "orderService", type = Bulkhead.Type.SEMAPHORE)
//    public String fetchOrder(String orderId) {
//        return restTemplate.getForObject("http://localhost:8082/orders/" + orderId, String.class);
//    
//-----------------------------------------------------------------------------------------------------------------------