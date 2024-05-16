# A basic template API following Clean Architecture principles and DDD

## Table of Contents

- [Getting started](#getting-started)
- [Domain Driven Architectures](#domain-driven-architectures)
- [DDD and Clean Architecture](#ddd-and-clean-architecture)
    - [Clean Architecture layers](#clean-architecture-layers)
    - [Project anatomy](#project-anatomy)
    - [The Dependency Rule](#the-dependency-rule)
    - [Server, Routes and Plugins](#server-routes-and-plugins)
    - [Controllers](#controllers)
    - [Use Cases](#use-cases)

- [TDD](#tdd)
    - [What is TDD?](#what-is-tdd)
    - [How?](#how)
- [Pattern](#pattern)
    - [Bounded Context](#bounded-context)
    - [Mother Object](#mother-object)
    - [Value Object](#value-object)

## Getting started
Warning, for production environment do not load environment variables file based on profiles, please add in your .gitignore file the next line : */src/\*/resources/application-**

**NOTE: if you use Linux or Mac exec ./mvnw, for windows ./mvnw.cmd**
```
run: ./mvnw spring-boot:run
```

```
build: ./mvnw clean package -DskipTests
```

```
unit test: ./mvnw clean test
```

In a browser, open 
1. http://localhost:9006/api/v1/info
```json
{
  "msvc": "backend-template-api",
  "version": "0.1.1"
}
```
2. http://localhost:9006/api/v1/actuator/health
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP",
      "details": {
        "database": "MySQL",
        "validationQuery": "isValid()"
      }
    },
    "discoveryComposite": {
      "description": "Discovery Client not initialized",
      "status": "UNKNOWN",
      "components": {
        "discoveryClient": {
          "description": "Discovery Client not initialized",
          "status": "UNKNOWN"
        }
      }
    },
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 62671097856,
        "free": 43695169536,
        "threshold": 10485760,
        "path": "/app/.",
        "exists": true
      }
    },
    "livenessState": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    },
    "readinessState": {
      "status": "UP"
    },
    "refreshScope": {
      "status": "UP"
    }
  },
  "groups": [
    "liveness",
    "readiness"
  ]
}
```

## Domain Driven Architectures

Software design is a very hard thing. From years, a trend has appeared to put the business logic, a.k.a. the (Business) Domain, and with it the User, in the heart of the overall system. Based on this concept, different architectural patterns was imaginated.

One of the first and main ones was introduced by E. Evans in its [Domain Driven Design approach](http://dddsample.sourceforge.net/architecture.html).

![DDD Architecture](https://gcdnb.pbrd.co/images/tcFMXVyxKSs8.jpg?o=1)

Based on it or in the same time, other applicative architectures appeared like [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/) (by. J. Palermo), [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/) (by A. Cockburn) or [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) (by. R. Martin).

This repository is an exploration of this type of architecture, mainly based on DDD and Clean Architecture, on a concrete and modern JavaScript application.

## DDD and Clean Architecture

The application follows the Uncle Bob "[Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)" principles and project structure :

### Clean Architecture layers

![Schema of flow of Clean Architecture](https://1048636645-files.gitbook.io/~/files/v0/b/gitbook-legacy-files/o/assets%2F-MAffO8xa1ZWmgZvfeK2%2F-MBmS7EO8Fe7VVZVRc_Q%2F-MBmS9tX9OP1kMC9I4z6%2Fimage.png?alt=media&token=5aff66d7-0528-45ba-95d3-003b2b824ca0)

### Project anatomy

```
src
 └ context                          → Application sources
   └ categories                     → Categories product
    └ application                   → Application services layer
      └ use_cases                   → Application business rules
    └ domain                        → Enterprise core business layer such as domain model objects (Aggregates, Entities, Value Objects) and repository interfaces
    └ infrastructure                → Frameworks, drivers and tools such as Database, the Web Framework, mailing/logging/glue code etc.
```

### The Dependency Rule

> The overriding rule that makes this architecture work is The Dependency Rule. This rule says that source code dependencies can only point inwards. Nothing in an inner circle can know anything at all about something in an outer circle. In particular, the name of something declared in an outer circle must not be mentioned by the code in the an inner circle. That includes, functions, classes. variables, or any other named software entity.

src. https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html#the-dependency-rule

### Server, Routes and Plugins

Server, routes and plugins can be considered as "plumbery-code" that exposes the API to the external world, via an instance of Spring web dependency.

The role of the server is to intercept the HTTP request and match the corresponding route.

Routes are configuration objects whose responsibilities are to check the request format and params, and then to call the good controller (with the received request). They are registered as Plugins.

Plugins are configuration object that package an assembly of features (ex: authentication & security concerns, routes, pre-handlers, etc.) and are registered at the server startup.

### Controllers

Controllers are the entry points to the application context.

They have 3 main responsibilities :

1. Extract the parameters (query or body) from the request
2. Call the good Use Case (application layer)
3. Return an HTTP response (with status code and serialized data)

### Use Cases

A use case is a business logic unit.

It is a class that must have an `execute` method which will be called by controllers.

It may have a constructor to define its dependencies (concrete implementations - a.k.a. _adapters_ - of the _port_ objects) or its execution context.

**NOTE: A use case must have only one precise business responsibility!!!**

A use case can call objects in the same layer (such as data repositories) or in the domain layer, but not out...

## TDD

### What is TDD?

> Test-driven development (TDD) is an evolutionary approach to development which combines test-first development, where you write a test before you write just enough production code to fulfil that test, and refactoring. In other words, it’s one way to think through your requirements or design before your write your functional code.

### How?

The first thing you need to understand is that writing code following TDD (discipline) is a (slightly) different approach from simply diving into solving the problem (without a test).
When reading about TDD you will usually see the expression: "Red, Green, Refactor":

![TDD](https://www.xeridia.com/wp-content/uploads/drupal-files/contenidos/blog/test-driven-development.png)

What this means is that TDD follows a 3-step process:

1. Write a Failing Test - Understand the (user) requirements/story well enough to write a test for what you expect. (the test should fail initially - hence it being "Red")

2. Make the (failing) Test Pass - Write (only) the code you need to make the (failing) test pass, while ensuring your existing/previous tests all still pass (no regressions).

3. Refactor the code you wrote take the time to tidy up the code you wrote to make it simpler (for your future self or colleagues to understand) before you need to ship the current feature, do it.

## Pattern

> In software engineering, a design pattern is a general repeatable solution to a commonly occurring problem in software design. A design pattern isn't a finished design that can be transformed directly into code. It is a description or template for how to solve a problem that can be used in many different situations.

### Bounded Context

> Bounded Context is a central pattern in Domain-Driven Design. It is the focus of DDD's strategic design section which is all about dealing with large models and teams. DDD deals with large models by dividing them into different Bounded Contexts and being explicit about their interrelationships.

![Example](https://martinfowler.com/bliki/images/boundedContext/sketch.png)

### Mother Object

> An object mother is a kind of class used in testing to help create example objects that you use for testing.
> When you write tests in a reasonably sized system, you find you have to create a lot of example data. If I want to test a sick pay calculation on an employee, I need an employee. But this isn't just a simple object - I'll need the employee's marital status, number of dependents, some employment and payroll history. Potentially this can be a lot of objects to create. This set data is generally referred to as the test fixture.

It works as a kind of factory from where the objects for the tests are created. In this case we use faker.
You can see examples on test folder

```
import java.util.random.Math;

public class MotherCreator {
  public static int random() {
    return (int)Math.random();
  }
}
```

### Value Object

> Value Objects are a fundamental building block of Domain-Driven Design, and they’re used to model concepts of your Ubiquitous Language in code.

> The Value Object pattern reinforces a concept that is often forgotten in object-oriented principles, especially by those of us used to weakly typed languages: encapsulation.

```
public abstract class StringValueObject {
  private String value;

  public StringValueObject(String value) {
    this.value = value;
  }

  public boolean equalsTo(String anotherValue) {
    return this.value == anotherValue;
  }

  public boolean differentTo(String anotherValue) {
    return this.value != anotherValue;
  }

  public String toString() {
    return this.value;
  }
}
```
