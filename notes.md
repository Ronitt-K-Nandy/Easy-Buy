
-------------------------------------------------------------------------------------------------------
## jakarta.validation.constraints (dependency -> "spring-boot-starter-validation")

1)for strings

@Size


2)for numbers

@Min				- whole numbers, write as a number, inclusive by default, rejects fractions like 1.81
@DecimalMin			- string, write within a string, inclusive = true or inclusive = false, accepts fractions like "1.81"
@Max				- whole numbers, write as a number, incluisve by default, rejects fractions like 1.81
@DecimalMax			- string, write within a string, inclusive = true or inclusive = false, accepts fractions like "1.81"
@Digits				- size of numbers before and after decimals. Ex - @Digits(integer = 3, fraction = 2).Valid- 10.2,3.56,173.86, Invalid - 1234.56, 123.456
@Positive
@PositiveOrZero
@Negative
@NegativeOrZero


3)for dates
(LocalDate, LocalDateTime, ZonedDateTime, Instant, etc.), java.util.Date, and java.util.Calendar)

@Future				- @Future(message = "Card expiration date must be in the future")
@FutureOrPresent	- @FutureOrPresent(message = "Booking date cannot be in the past")
@Past
@PastOrPresent


4)for patterns

@Email
@Pattern			- @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "Username must be alphanumeric and 6-12 characters long")


5)for all types

@NotNull 				- Not allowed - null; allow - size 0 string , whitespaces
@NotEmpty 				- Not allowed - null, size 0 string, allow - whitespaces
@NotBlank 				- Not allowed - null, size 0 string, whitespaces


6)specialized type

@package-info			- A special source file used to define package-level annotations and Javadoc documentation for an entire package.

-------------------------------------------------------------------------------------------------------
## @ElementCollection
@ElementCollection - in JPA/Hibernate maps a collection of basic types (e.g., String, Integer)
or classes (e.g., Address or PhoneNumber object) without needing a
full-fledged independent entity.

No Independent Identity: The collection items do not have their own @Id primary key
or standalone repository. They are strictly owned by the parent entity
and share its lifecycle (if the parent is deleted, the collection items go with it)

-------------------------------------------------------------------------------------------------------
## MappedBy
@ManyToMany(mappedBy = "products")

• The Owning Side: The entity that does not use mappedBy (@ManyToOne,@JoinColumn,@JoinTable).
It is responsible for updating the database foreign key.
• The Inverse Side: The entity marked with mappedBy (@OneToMany,@OneToOne,@ManyToMany)
tells Hibernate: “Do not create a separate foreign key or join table for this side; look at the product field in the other entity to see how this relationship is mapped.”

-------------------------------------------------------------------------------------------------------
## ImageKit
Signup, then Login into image kit with appropriate credentials, and go to developer's option to get the
keys and url endpoint.

-------------------------------------------------------------------------------------------------------
## Load-Balancing
1. "spring-cloud-starter-netflix-eureka-client" dependency automatically registers the service with Eureka server and
also enables load balancing for the service.

2. "spring-cloud-starter-openfeign" dependency automatically registers the service with Eureka server and
also enables load balancing for the service.

3. "spring-cloud-loadbalancer" is automatically included in
the <spring-cloud-starter-netflix-eureka-client> and <spring-cloud-starter-openfeign> dependency,
so you don't need to add it separately.

4. RestTemplate / RestClient - For @Bean you need @LoadBalanced. You can then make requests using the service's Eureka application name (e.g., http://ORDER-SERVICE/api/orders).

5. Spring Cloud Gateway - use lb://SERVICE-NAME in your route URIs, the gateway automatically uses the bundled load balancer to resolve and distribute traffic.

-------------------------------------------------------------------------------------------------------

## @EnableJpaAuditing

Enables us to use :-

• @CreatedDate — Automatically populates the timestamp when an entity is first saved.
• @LastModifiedDate — Automatically updates the timestamp on every modification.
• @CreatedBy — Populates the user/auditor who created the entity (requires an AuditorAware bean).
• @LastModifiedBy — Populates the user/auditor who last modified the entity (requires an AuditorAware bean).

• @MappedSuperclass - designates a base class whose mapping information (fields and JPA annotations)
is inherited by its subclasses, without creating a separate database table for the base class itself.

• Step 1 :- Annotate main application file with "@EnableJpaAuditing".
• Step 2 :- Annotate file with "@EntityListeners(AuditingEntityListener.class)" where the date fields with 
            above annotations are used.

-------------------------------------------------------------------------------------------------------

## RestClient vs RestTemplate

1. Introduced In

RestClient: Introduced in Spring Framework 6.1 and Spring Boot 3.2.
RestTemplate: Introduced in Spring Framework 3.0.

2. Current Status

RestClient: Actively maintained and recommended for new synchronous HTTP client implementations.
RestTemplate: In maintenance mode, with no new features planned.

3. API Style

RestClient: Provides a modern, fluent API similar to WebClient, making HTTP requests more readable and easier to write.
RestTemplate: Uses a legacy API with overloaded methods such as getForObject(), postForObject(), and exchange().

4. Execution Model

RestClient: Synchronous and blocking execution.
RestTemplate: Synchronous and blocking execution.

5. Dependencies

RestClient: Available through spring-boot-starter-web. No need to include Spring WebFlux.
RestTemplate: Also available through spring-boot-starter-web.

6. Under the Hood

RestClient: Shares the same underlying HTTP infrastructure as RestTemplate, including ClientHttpRequestFactory, interceptors, and HTTP message converters.
RestTemplate: Uses the same underlying HTTP infrastructure and supports different HTTP engines, such as JDK HTTP Client, Apache HttpClient, and OkHttp (depending on the configured implementation).

-------------------------------------------------------------------------------------------------------

## Communication between Services

1. Register all services into Service Registry
2. RestTemplate can be used in this case, either directly use ulr with hostname and port name
   like localhost:8080, and create a bean object for RestTemplate with @Bean
3. Or use service names like
   "PRODUCT-SERVICE", @LoadBalanced needs to be added ith @Bean for RestTemplate.

Ex - @GetMapping("/client/{productId}")
    public ResponseEntity<ProductSnapshot> getProductForProductClient(@PathVariable("productId") UUID productId){
    ResponseEntity<ProductSnapshot> forEntity = restTemplate.getForEntity("http://PRODUCT-SERVICE/api/products/" + productId, ProductSnapshot.class);
    return forEntity;
    }

and,

@Configuration
public class ClientServiceImpl {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

-------------------------------------------------------------------------------------------------------

## Spring Cloud OpenFeign

@FeignClient(name = "productClient", url = "${product.service.id}")
    if, ${product.service.id} contains a URL like http://localhost:8081
@FeignClient(name = "${product.service.id}")
    if, ${product.service.id} is a name like "product-service"

Ex- @EnableFeignClients in main application file after adding dependency

and,

    @GetMapping("/client/{productId}")
    public ProductSnapshot getProductForProductClient1(@PathVariable("productId") UUID productId){
    return productClient.fetchProductsForInventory(productId);
    }

and,

    @FeignClient(name="PRODUCT-SERVICE")
    public interface ProductClient{
	@GetMapping("/api/products/{productId}")
	ProductSnapshot fetchProductsForInventory(@PathVariable("productId") UUID productId);
}

-------------------------------------------------------------------------------------------------------

##  Directly passwords cannot be used for git. Ex- in Config Server application.yml

1. Config Server git repository is supposed to be  private to hide all configurations, and 
    to get the access , we need username and password.
2. In case of passwords, you have to go to Settings > developer's settings > Personal access tokens >
   Fine-grained tokens > Generate new token
3. Only select repositories: <Select ConfigServer>, then, Permissions>Repositories>
   Contents:Read-Only, Metadata:Read-Only

-------------------------------------------------------------------------------------------------------

##  Migrating all .properties to Config Server

Inspite of "encrypt.key= ${ENCRYPT_KEY}" in application.properties in Config Server, we have to use 
"encrypt.key= ${ENCRYPT_KEY}" in Config Server also, keeping in mind no file is made in Config Server as
"config-server.properties".

encrypt a password - access http://localhost:8079/encrypt in "POST" method,
it produces  password in postman  for raw, text where you input a password, and it produces
the password in encrypted form, use that encrypted password in your .properties
or .yml as {cipher}(write_the_encrypted_passord_here).

Also, use "/decrypt" to decrypt and see the actual password.

-------------------------------------------------------------------------------------------------------

##  Refreshing environement values

1) Manual restart
2) Automatic update
 a) actuator/refresh [POST] - hit this in postman
 b) Spring Cloud Bus
 c) Monitor

* Remember - to add "@RefreshScope" in Controller, both "@RefreshScope" and "actuator/refresh" is required.

2a) In ProductController, after hitting "/checkRefreshValue" , the initial value is displayed
    After hitting "actuator/refresh" the valu gets updated, after hitting "/checkRefreshValue" again,
    we get the refreshed value.

-------------------------------------------------------------------------------------------------------

##  Circuit Breaker Pattern

    #Api Gateway
1) For API Gateway - add dependency, and properties, and make changes in the file.
2) For testing you need to access "actuator/circuitbreakers", to check the failure rates,
    no. of attempts, then, State(open,closed, half-open)

Initially it has status "CLOSED", after hitting the request, you will get no failures,
then turn-off the service, and keep on hitting the request, so that it crosses the 
failure threshold, you will see that the status becomes "OPEN", based on the properties value
it becomes "HALF-OPEN" after some time. Then keep on hitting the request after turning the service on.
it lowers down failure rate and makes the service status "CLOSED" again.


    #Feign Client

1) Add dependency , properties make a class to handle fallback method, and link that to the 
   interface with @FeignClient it is implementing.
2) 