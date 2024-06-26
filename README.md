# ticketmaster-challenge

## Run in the Command Line:
Navigate to the project root directory and build the application by: 

    mvn package

This will create the jar file under the `/target` directory `ticketmaster-challenge-0.0.1-SNAPSHOT.jar`

Navigate to the `/target` directory and execute the command:

    java -jar ticketmaster-challenge-0.0.1-SNAPSHOT.jar

The application will be running in default 8080 port and the reactive API to get the Artist's info by ID can be accessed by:
http://localhost:8080/artist/21 `../artist/{id}`

# Development Approach
Followed the standard TDD approach to develop the functionality.

## Pojo - DTO Classes
Due to the simple structure of the json schema, here I have created the files manually, but this can be dynamically created during the compile time using `org.jsonschema2pojo:jsonschema2pojo-maven-plugin` maven plugin. 

## Exception and Error Handling
Created a ResponseHandler using `@RestControllerAdvice` and custom `ErrorResponse` class to handle the error/failure response, providing a custom response message, even when the data is not present for the given request.

## Client and Response
Given the following endpoints:
1. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/events.json` -> contains data for events. It links to artists and venues via ids
2. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/artists.json` -> contains data for artists.
3. `https://iccp-interview-data.s3-eu-west-1.amazonaws.com/78656681/venues.json` -> contains data for venues

Used the `RestClient` to consume the data from S3 - json files and publish it as a reactive non-blocking response, using Mono. This response type could have been Flux also, but since this is for a single Artist always, hence Mono.
Could have used `WebClient`, if the requirement was to simply filter the existing Artist/Event/Vanue lists and publish it, without modifying or creating a different response. Because in that case, we will have to block the `WebClient` response and we wont be able to create a readable Mono or Flux response. 

