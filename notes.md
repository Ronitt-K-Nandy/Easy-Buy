
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
## 