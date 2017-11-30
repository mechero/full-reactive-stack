# AngularReactive

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.4.6.

## Running the application (from code)

You need to install Node.js on your computer. After that, you can execute:

- `npm install`: To download and install the dependencies.
- `npm start`: To start the application. You can navigate with your browser to `http://localhost:4200`.
Alternatively, you can also run `ng serve` if you downloaded Angular CLI.

Make sure you start first the Spring Boot backend app, otherwise this one won't work properly.

## Running the application with Docker

First, you need to download and install Angular CLI to build the application. 

`npm install -g @angular/cli`

Then you need to build it. From this same folder (`angular-reactive`), execute:

`ng build`

A folder called `dist` should have been generated if everything goes well. Now you can build the Docker image, running:

`docker build -t angular-reactive-tpd .`

Finally, you can run the Docker image by executing:

`docker run -p 8080:80 angular-reactive-tpd`

## More details about functionality

See [parent README file](../README.md) for more details about this application.
