# Welcome!

This is testing task for new Java BE candidates.
The goal of this task is to understand the way of thinking and problem-solving of a candidate.
Partially such skills like composing new service, database design, SQL composing, multi-threading understanding
and unit-testing.

We are expecting that this task should take no more than 2 hours of implementation.
Any result is important for us, if you feel that something is not solved it is not a problem.
TODOs and comments in the source code are welcome.

Please create a REST API service which handles data about airports, flights and passengers.
No need to think about scaling and multiple instances of this service. 
Lets imaging that it will be a single node only. 

## You need:
* use Java 17, Spring Boot 2.6+, Maven 3.5+ (if you prefer Gradle, let it be)
* select SQL DB like H2, PostgreSQL, etc
* create tables for airports, flights and passengers (and another DB entities if needed)
* create DAO, services and controllers
* add business logic
* test coverage at least 80%
* you can change initial design to get better performance, etc
* the Maven build should be successful
* the final JAR file should be runnable
                       
## Database description

* It should be SQL database. 
  * For example, H2 in memory with embedded console https://www.baeldung.com/spring-boot-h2-database
* Airport table should have at least:
  * `name` column as varchar(255)
  * `code` column as varchar(20)
  * `country` column as varchar(60)
* Flight table should have at least:
  * `code` column as varchar(20)
  * `originAirportCode` column as varchar(20)
  * `destinationAirportCode` column as varchar(20)
  * `departureDateTime` column as <your choice type>
  * `arrivalDateTime` column as <your choice type>
* Passenger table should have at least:
  * `name` column as varchar(1024)
  * `flightCode` column as varchar(20)

During the DB design, please think about additional constraints and type of `id` field and way of its generation 
for the better performance and security. It will be great to get your opinion about.

If you feel that some another tables or columns is needed then you can add them and change the structure of tables above.

## REST API description 

We would like to see these endpoints:
* Get all airports with pagination
* Get the one airport by its id
* Add new airport with validation for fields and unique code and name
* Add new passenger with validation for fields and unique name per flight
* Get statistics info about some airport per some day:
  * number of arriving flights
  * number of departing flights
* Get statistical data based on time buckets as a response:
  * request has non-empty start date time as a parameter
  * request has non-empty end date time as a parameters
  * request has `time bucket` length (in minutes), by default it is 20 minutes
    * value should not be less than 5 minutes
  * result data should have structure similar to table with two columns:
    * 1st column is date and time of time-bucket
    * 2nd column is amount of flights in this time bucket 
  * please do not forget to validate input parameters
  * if some time bucket has no flights then this item can be skipped or 0/null flights can be shown (as you wish)
  * it will be great to get this result directly from database with SQL query without additional business logic
  * example of response with time bucket size as 10 minutes:
    * 2023-11-16 10:00 | 14    
    * 2023-11-16 10:10 | 8
    * 2023-11-16 10:20 | 24
    * 2023-11-16 10:30 | 1
  * example of JSON for this response (structure of JSON can differ)
    * ```
      [
         {
           "dt": "2023-11-16 10:00",
           "value": 14
         },
         ...
         {
           "dt": "2023-11-16 10:30",
           "value": 1
         }
      ]  
      ```
  * Meaning: there were 14 flights from 2023-11-16 10:00 (inclusive) till 2023-11-16 10:10 (exclusive) and so on. 
      
## Business logic

Additional functionality needed: 
* On each "Add new passenger" REST request, service should calculate amount of passengers per flight.
  * If amount of passengers per some flight became 150 or more, new flight should be added into DB
  * New flight can have null origin and destination airports, also arrival time as well 
  * New flight should have departure datetime of the moment of last passenger was added
  * It will be great to have max amount of passengers configurable from application.yml file 
  * Note: Passenger.flightCode field will be helpful

## Notes
* Please implement all part of the service with best performance
* Do not forget that service can receive many requests at the same moment
* Remember that good tests cover negative scenarios as well
* You have any questions fell free to ask them, we are here to help you
* Do not forget to re-read the task to be sure every topic is covered
* Good luck!
