# Dining review API




  ## Project Overview

The restaurant dining review app lets users rate restaurants based on their handling of three food allergies: peanut, egg, and dairy. Ratings range from 1 to 5, with 5 being the best.

### Features

- Review Ratings: Users rate how well restaurants manage peanut, egg, and dairy allergies.
-  Score Calculation: The app computes average ratings for each allergy category per restaurant. If no ratings exist, scores remain null. Overall restaurant scores are averages across all categories.
- Search Functionality: Users can search for restaurants based on allergy ratings, helping them find places that cater well to specific dietary needs.

  
### Project Objectives

  - Construct a RESTful web API with data persistence using Spring and Spring Data JPA.
  - Use Spring Initializr to generate the initial Java project.
  - Configure application properties for specific dependencies, including the H2 embedded database.
  - Define the entities that comprise this application scenario.
  - Define the repositories that enable creating, updating, and querying these different entities.
  - Define the API contracts that will enable this application scenario.
  - Leverage the convenience of Lombok for reducing boilerplate code.
  - Use curl to test the API scenarios.

## Prerequisites

  - Java
  - Spring
  - curl

## Getting Started

To get a local copy up and running, follow these simple steps:

 1. Clone the repository:

    

        git clone https://github.com/AKaraeva/dining-review-api.git

2. Navigate to the project directory:


        cd my-restful-web-api-project

3. Install dependencies and build the project:



        ./mvnw clean install

4. Run the application:


        ./mvnw spring-boot:run
