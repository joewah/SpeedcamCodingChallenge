# SpeedcamCodingChallenge
a small sample project as part of a coding challenge

frameworks and libraries used: Spring(Boot), Swagger/OpenAPI, Mockito/JUnit

The project employs an API-first approach, defining the OpenAPI specifications in the openapi.yml file 
Currently, no real database is used and the data is persisted using an in-memory H2 database. 

The project can be started using ./gradlew bootRun (starting the service on localhost:8080), the tests can be run with ./gradlew test

To successfully store the images on S3, you would need to provide your own credentials in the application.properties file, however, better would be to expose the S3 bucket via an AWS GateWay, omitting the need for storing credentials. 


