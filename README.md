#Mini Backend Project
This is mini backend project exposes a REST API for a bank account.
It is written in an API first approach - 
it implements the REST API in the `api-spec` module.

## Setup
- Clone this project
- Run the following commands to download dependencies and start the application.
  ```commandline
    ./gradlew clean build
  
## Running
`./gradlew bootRun`

## Automated Tests
`./gradlew test`
  
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
