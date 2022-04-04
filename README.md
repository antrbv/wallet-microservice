# Wallet Microservice

## Requirements
Java 11+

## Build
```
./gradlew clean build
```

## Run
```
java -jar build/libs/build/libs/wallet-microservice-*.jar
```
## Features of BD
There is afterMigrate flyway script with populating DB for demo run

## Api Endpoints

OpenApi (swagger): http://localhost:8080/swagger-ui.html

Http GET endpoints:
1. http://localhost:8080/api/v1/history/{id}
   Get transactions history by player id
```
[
  {
     "transId": string,
     "playerId": int,
     "type": [ "CREDIT", "DEBIT" ],
     "amount": number
  }
]
```
2. http://localhost:8080/api/v1/balance/{id}
Get balance by player id
```
{
  "id": int,
  "balance": number
}
```

Http POST endpoints:
1. http://localhost:8080/api/v1/debit
Debit transaction

2. http://localhost:8080/api/v1/credit
Credit transaction
   
Request:
```
{
  "transId": string,
  "playerId": int,
  "amount": number
}
```
Response:
```
{
  "transId": string,
  "playerId": int,
  "type": [ "CREDIT", "DEBIT" ],
  "amount": number
}
```