# Full Reactive Stack [![Build Status](https://travis-ci.org/mechero/full-reactive-stack.svg?branch=master)](https://travis-ci.org/mechero/full-reactive-stack)

This repository contains backend and frontend projects that make use of **Reactive Web** patterns,
as explained in the mini book [Full Reactive Stack with Spring Boot 2, WebFlux, MongoDB and Angular](https://leanpub.com/full-reactive) and also in the [Full Reactive Stack series of posts](https://thepracticaldeveloper.com/full-reactive-stack).

![Full Reactive Stack Overview](resources/reactive_overview.png)

## Get the mini-book

You can get a copy of the mini-book on LeanPub: [Full Reactive Stack with Spring Boot 2, WebFlux, MongoDB and Angular](https://leanpub.com/full-reactive).

## Components

### Spring Boot Reactive Web

This is a Spring Boot 2.0 application that retrieves data using **Spring Reactive Web (WebFlux)**,
instead of using the standard synchronous MVC framework. It connects to a MongoDB database in a reactive
way too.

Check [this blog post](https://thepracticaldeveloper.com/2017/11/04/full-reactive-stack-with-spring-webflux-and-angularjs/)
for the complete description of the implementation.

### Angular Reactive

This simple Angular application consumes the controller on the backend side using a reactive approach,
 Server-Sent Events, so data is loaded on screen as soon as it's available.

[This blog post](https://thepracticaldeveloper.com/2017/11/04/full-reactive-stack-ii-the-angularjs-client/)
has the full instructions on how it has been implemented.

### Docker

The docker folder contains a `docker-compose` file that runs the Mongo database, the backend application
and the Angular application. It also contains a simplified version, `docker-compose-mongo-only.yml`, which
runs only the MongoDB instance, in case you want to run the applications without docker.

## Running the applications with Docker

Make sure to build the applications first:

* For the Angular application, run first `npm install` and then `npm run ng build`
in the `angular-reactive` folder. It generates a `dist` folder.
* For the Spring Boot application, execute `mvnw clean package` in the `spring-boot-reactive-web` folder.
That command generates the jar file in the `target` folder.

Then you need to run `docker-compose up` from the `docker` folder. After the services are executed, you
can navigate to `localhost:8900` to see the applications running. If you're running Docker in a different
machine (like when using a VM in Windows), replace `localhost` for the Docker machine IP.
