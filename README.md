
# room-occupancy-manager

## Enviorement
- Spring
- Java 17
-  Maven

## Running

**1. Ensure Java 17 and Maven are Installed.**

    java -version
    mvn -v



**2 .Navigate to Your Maven Project's Root Directory:**

To build our Maven project, use the following command:

    mvn clean install

**3. Run our Java Application:**


     java -jar target/room-occupancy-manager-0.0.1-SNAPSHOT.jar



## Tests

Already have controller and service tests, but if you would like to test the cases manually I prepared some curls for you.

## Test 1

● (input) Free Premium rooms: 3  
● (input) Free Economy rooms: 3  
● (output) Usage Premium: 3 (EUR 738)  
● (output) Usage Economy: 3 (EUR 167.99)

```
curl -X 'GET' \
'http://localhost:8080/api/revenue/available-rooms' \
-H 'Content-Type: application/json' \
-d '{
"rooms": {
"premium": 3,
"economy": 3
}
}'
```

## Test 2

● (input) Free Premium rooms: 7  
● (input) Free Economy rooms: 5  
● (output) Usage Premium: 6 (EUR 1054)  
● (output) Usage Economy: 4 (EUR 189.99)

```
curl -X 'GET' \
'http://localhost:8080/api/revenue/available-rooms' \
-H 'Content-Type: application/json' \
-d '{
"rooms": {
"premium": 7,
"economy": 5
}
}'
```





## Test 3

● (input) Free Premium rooms: 2  
● (input) Free Economy rooms: 7  
● (output) Usage Premium: 2 (EUR 583)  
● (output) Usage Economy: 4 (EUR 189.99)


```
curl -X 'GET' \
'http://localhost:8080/api/revenue/available-rooms' \
-H 'Content-Type: application/json' \
-d '{
"rooms": {
"premium": 2,
"economy": 7
}
}'
```



## Test 4

● (input) Free Premium rooms: 7  
● (input) Free Economy rooms: 1  
● (output) Usage Premium: 7 (EUR 1153)  
● (output) Usage Economy: 1 (EUR 45.99)

```
curl -X 'GET' \
'http://localhost:8080/api/revenue/available-rooms' \
-H 'Content-Type: application/json' \
-d '{
"rooms": {
"premium": 7,
"economy": 1
}
}'
```
