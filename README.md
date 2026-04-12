BOX SERVICE API

Overview
This project is a Spring Boot REST API for managing delivery boxes and items.

Features
- Create a box
- Load items into a box
- Retrieve loaded items
- Get available boxes for loading
- Check battery level of a box

Base URL
http://localhost:8080/api

Endpoints

1. Create Box
POST /boxes/create

2. Load Box with Items
POST /boxes/{txRef}/load

3. Get Loaded Items
GET /boxes/{txRef}/items

4. Get Available Boxes
GET /boxes/available

5. Get Battery Level
GET /boxes/{txRef}/battery

Build & Run Instructions

1. Install Java 17
2. Install Maven

Run:
mvn clean install
mvn spring-boot:run

Access:
http://localhost:8080

H2 Console:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:boxdb
Username: sa
Password: (leave blank)

Dependencies
- Spring Boot 3.3.5
- Spring Data JPA
- H2 Database
- Lombok
