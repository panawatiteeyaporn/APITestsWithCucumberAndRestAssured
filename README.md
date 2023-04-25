# Cucumber and RestAssured Test Framework
This is an API testing framework that uses the power of Cucumber-jvm, Rest-Assured and Java 17. The project showcase some common use cases of Gherkin features and how to serialize/deserialize json object for testing. 

Test scenarios are based on the API specification from Swagger PetStore - https://petstore.swagger.io/. The sample PetStore server is for public use so you can run these tests locally to try them out.   

## Get the code

Git:
```
git clone https://github.com/panawatiteeyaporn/APITestsWithCucumberAndRestAssured.git
```
Or [download a zip](https://github.com/panawatiteeyaporn/APITestsWithCucumberAndRestAssured/archive/refs/heads/master.zip) file.

## Installation note

The project is setup using Maven project structure and all require libraries are in the pom.xml.

If you open the project with Intellij or Eclipse the IDE will detect Maven project and will prompt you to download require libraries.

Most IDE will have Gherkin plugin available. It is recommended that user download the plugin from IDE plugin store to help working with feature files.


## Running tests

If you have Maven installed on your machine you can run the tests from command line/terminal with
```
mvn test
```
Otherwise, open the feature file in your IDE and run the tests from there.

