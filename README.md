# Mini Backend Project
This is mini backend project exposes a REST API for a bank account.
It is written in an API first approach - 
it implements the REST API in the `api-spec` module.

It is created with spring boot and uses gradle for the build system.
An in-memory H2 database is used for testing and running the app.

## Setup
- Clone this project
- Run the following commands to download dependencies and build the application.
  ```commandline
    ./gradlew clean build
  ```
  
## Running
### `bootRun`
`./gradlew bootRun`

### `jar`
  ```commandline
    ./gradlew clean build
    java -jar build/libs/bank-account-backend-1.0-SNAPSHOT.jar
  ```

### `docker`
 - `./gradlew jibDockerBuild`
 - go to the local docker dashboard and search for `bank-account-backend` in `images`
 - run the image 
 
   ` docker run -i -p:8080:8080 --rm bank-account-backend:1.0-SNAPSHOT`
  
    
## Automated Tests
`./gradlew test`

To view the coverage reports, open `build/reports/jacoco/test/html/index.html`
  
## Functional Testing
The app has a default account which has the account number `2342`.
This account number can be used to make the tests.
Validations are carried out as specified in the requirements.

In postman or with curl, do the following:

### Balance
`curl --location --request GET 'localhost:8080/account/2342/balance'`

### Deposit
`curl --location --request POST 'localhost:8080/account/2342/deposit/1'`

### Withdrawal
`curl --location --request POST 'localhost:8080/account/2342/withdraw/10'`
