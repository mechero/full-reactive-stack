# Full Reactive Stack [![Build Status](https://travis-ci.org/mechero/full-reactive-stack.svg?branch=master)](https://travis-ci.org/mechero/full-reactive-stack)

This repository contains a sample application, to show Full Stack Reactive capabilities, with a Spring Boot 3 WebFlux backend and a Angular 15 frontend.

On the `master` branch you will find a basic comparison between blocking & reactive requests (with both blocking / reactive controllers, repositories alternatives).
On the `reactive-quotes-creation-refresh` you will discover how to keep listening for new data coming from the server, making the app fully reactive using *Spring Events*, *FluxSink* *ServerSentEvent*s.

This repository originally comes from https://github.com/mechero/full-reactive-stack/ and was adapted for current versions and expanded to show more reactive features. This original repository linked to a mini book [Full Reactive Stack with Spring Boot 2, WebFlux, MongoDB and Angular](https://leanpub.com/full-reactive) and also [Full Reactive Stack series of posts](https://thepracticaldeveloper.com/full-reactive-stack).

This fork does not use a MongoDB anymore but rather a H2 database, to show how to link with a relational database using [Spring Data R2DBC](https://docs.spring.io/spring-data/r2dbc/docs/current-SNAPSHOT/reference/html).

The original schema:
![Full Reactive Stack Overview](resources/reactive-stack-logical.png)

## Components

### Spring Boot Reactive Web

A Spring Boot 3 application that retrieves data using **Spring Reactive Web (WebFlux)**, instead of using the standard Web MVC framework. It connects to a H2 database in a reactive way using using [Spring Data R2DBC](https://docs.spring.io/spring-data/r2dbc/docs/current-SNAPSHOT/reference/html).

Check [this blog post](https://thepracticaldeveloper.com/2017/11/04/full-reactive-stack-with-spring-webflux-and-angularjs/) for a short version of the original guide.

### Angular Reactive

A simple Angular application consumes the controller on the backend side using a reactive approach, Server-Sent Events and RxJS, so data is loaded on screen as soon as it's available.

[This blog post](https://thepracticaldeveloper.com/2017/11/04/full-reactive-stack-ii-the-angularjs-client/) contains a summary of the frontend's implementation.

### Docker

The docker folder contains a `docker-compose` file that runs containers.

## Running the applications with Docker

Make sure to build the applications first, from the `docker` folder:

`docker-compose build`

Then, you can run the set of containers with: 

`docker-compose up` 

After the services are executed, you can navigate to `localhost:4200` to see the applications running. If you're running Docker in a different machine (like when using a VM in Windows), replace `localhost` for the Docker machine IP.
