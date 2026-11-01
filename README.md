# Motor Vehicle Service and Repair Management System

## Student Information

- **Student Name:** Luqman NKUNDIMANA
- **Student ID:** 21967
- **Course Code:** INSY 8322
- **Course Name:** Web Technology and Internet
- **Academic Year:** 2026–2027

---

## 1. Project Description

The Motor Vehicle Service and Repair Management System is a Java-based web application developed using Spring Boot. It is designed to simplify the management of vehicle owners, vehicles, mechanics, repair jobs, spare parts, and repair reports.

The system provides RESTful APIs that allow users to manage vehicle service operations, assign mechanics to repair jobs, monitor repair progress, record spare parts used, and maintain repair history.

The application follows a layered architecture consisting of Controller, Service, Repository, and Entity layers to ensure separation of responsibilities and maintainable code.

## 2. Technologies Used

The project was developed using the following technologies:

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Backend application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database operations |
| Hibernate | ORM and entity management |
| PostgreSQL | Relational database |
| Maven | Dependency and project management |
| Postman | API testing |
| Visual Studio Code | Development environment |
| Git and GitHub | Version control and source code hosting |

## 3. System Architecture

The application follows a four-layer architecture:

### Controller Layer
Handles incoming HTTP requests, receives request data, calls service methods, and returns appropriate HTTP responses.

### Service Layer
Contains the application's business logic, validation rules, and operations involving multiple repositories.

### Repository Layer
Communicates with the PostgreSQL database through Spring Data JPA. It provides CRUD operations, derived queries, and custom JPQL queries.

### Entity Layer
Contains the Java classes representing the database entities and their relationships.

## 4. Main System Modules

### 4.1 Vehicle Owner Management
- Register vehicle owners.
- Retrieve all vehicle owners.
- Retrieve a vehicle owner by ID.
- Update vehicle owner information.
- Delete vehicle owners.
- Prevent duplicate phone numbers.
- Retrieve owners through vehicle and mechanic relationships.

### 4.2 Vehicle Management
- Register vehicles.
- Associate vehicles with their respective owners.
- Retrieve all vehicles.
- Retrieve vehicles by ID.
- Retrieve vehicles by owner.
- Search vehicles by make.
- Update vehicle information.
- Delete vehicles.
- Retrieve vehicles without repair jobs.

### 4.3 Mechanic Management
- Register mechanics.
- Retrieve all mechanics.
- Retrieve mechanics by ID.
- Update mechanic information.
- Delete mechanics.
- Prevent duplicate mechanic phone numbers.
- Retrieve mechanics without assigned repair jobs.

### 4.4 Repair Job Management
- Create repair jobs.
- Assign vehicles and mechanics to repair jobs.
- Retrieve all repair jobs.
- Retrieve repair jobs by ID.
- Retrieve repair jobs by vehicle or mechanic.
- Search repair jobs by status.
- Retrieve repair jobs within a date range.
- Update repair job information.
- Delete repair jobs subject to report-history restrictions.
- Update repair job status through controlled status transitions.

### 4.5 Spare Parts Management
- Record spare parts used in repair jobs.
- Associate spare parts with repair jobs.
- Retrieve all spare parts.
- Retrieve spare parts by ID.
- Retrieve spare parts by repair job.
- Update spare part information.
- Delete spare parts.

### 4.6 Repair Report Management
- Create repair reports for completed repair jobs.
- Automatically record the report creation date.
- Retrieve all repair reports.
- Retrieve repair reports by ID.
- Retrieve repair history by vehicle owner.
- Prevent deletion of repair jobs that have associated reports.

## 5. Business Rules and Validations

The application implements the following business rules:

1. Vehicle owners cannot register using an already registered phone number.
2. Vehicles must be associated with an existing vehicle owner.
3. Mechanics cannot register using an already registered phone number.
4. Repair jobs cannot have a start date in the past.
5. A mechanic cannot be assigned multiple active repair jobs on the same date.
6. New repair jobs automatically receive the `PENDING` status.
7. Repair job status changes must follow the permitted transition flow:
   - `PENDING` → `IN_PROGRESS`
   - `PENDING` → `CANCELLED`
   - `IN_PROGRESS` → `COMPLETED`
   - `IN_PROGRESS` → `CANCELLED`
8. Repair reports can only be created for completed repair jobs.
9. Repair report dates are automatically set to the current date.
10. Repair jobs with existing reports cannot be deleted.

## 6. Database Design

The application uses PostgreSQL as its relational database.

The main database tables are:

| Table | Description |
|---|---|
| `vehicle_owners` | Stores vehicle owner information |
| `vehicles` | Stores vehicle information and owner relationships |
| `mechanics` | Stores mechanic information |
| `repair_jobs` | Stores repair jobs, assigned vehicles, mechanics, dates, and statuses |
| `spare_parts` | Stores spare parts associated with repair jobs |
| `repair_reports` | Stores reports associated with completed repair jobs |

### Entity Relationships

- One Vehicle Owner can own multiple Vehicles.
- Each Vehicle belongs to one Vehicle Owner.
- One Vehicle can have multiple Repair Jobs.
- Each Repair Job belongs to one Vehicle.
- One Mechanic can be assigned multiple Repair Jobs.
- Each Repair Job has one assigned Mechanic.
- One Repair Job can have multiple Spare Parts.
- Each Spare Part belongs to one Repair Job.
- One Repair Job can have multiple Repair Reports.
- Each Repair Report belongs to one Repair Job.

All main entities use UUIDs as their primary keys.

## 7. Advanced Database Queries

The project demonstrates Spring Data JPA derived queries and custom JPQL queries.

### Derived Queries
- Search vehicles by make, ignoring case.
- Retrieve repair jobs by status ordered by start date.
- Retrieve repair jobs between two dates.

### JPQL Queries
- Retrieve vehicle owners based on vehicle make.
- Retrieve vehicles without repair jobs using `NOT EXISTS`.
- Retrieve mechanics without repair jobs using `IS EMPTY`.
- Retrieve distinct vehicle owners associated with a mechanic.
- Retrieve repair reports belonging to a vehicle owner.
- Count repair jobs grouped by status using `GROUP BY`.

### Bulk Update
The application uses a JPQL bulk update to cancel pending repair jobs assigned to a specific mechanic on a specified date.

The operation uses `@Modifying` and `@Transactional` to execute the update directly in the database without iterating through records in Java.

## 8. Dashboard and Pagination

The project includes additional features:

### Dashboard
Provides summary information using `Map<String, Long>`, including:
- Total vehicle owners.
- Total vehicles.
- Total mechanics.
- Total repair jobs.
- Pending repair jobs.
- In-progress repair jobs.
- Completed repair jobs.
- Cancelled repair jobs.

### Pagination
Uses Spring Data JPA `Page`, `Pageable`, and `PageRequest` to retrieve vehicle owners in pages.

The pagination response includes records and metadata such as total elements, total pages, current page, and page size.

## 9. API Endpoints

All endpoints use the base URL:

`http://localhost:8080`

| Module | Base Endpoint |
|---|---|
| Vehicle Owners | `/api/owners` |
| Vehicles | `/api/vehicles` |
| Mechanics | `/api/mechanics` |
| Repair Jobs | `/api/repair-jobs` |
| Spare Parts | `/api/spare-parts` |
| Repair Reports | `/api/repair-reports` |
| Dashboard | `/api/dashboard` |

The application supports standard HTTP methods including GET, POST, PUT, PATCH, and DELETE.

Additional endpoints support searching, filtering, reporting, bulk updates, and pagination.

## 10. Installation and Setup

### Prerequisites

Before running the application, ensure that you have installed:

- Java Development Kit (JDK) 21.
- PostgreSQL.
- Maven (or use the included Maven Wrapper).
- Visual Studio Code or another Java IDE.
- Postman.

### Step 1: Clone the Repository

```bash
git clone https://github.com/Luqman011/motor-service-management-backend.git
```

### Step 2: Navigate to the Project Directory

```bash
cd motor-vehicle-service
```

### Step 3: Create the PostgreSQL Database

Create a PostgreSQL database for the application.

For example:

```sql
CREATE DATABASE motor_vehicle_service;
```

### Step 4: Configure Database Connection

Open:

`src/main/resources/application.properties`

Configure the PostgreSQL connection using your local database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/motor_vehicle_service
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Replace the username and password with your PostgreSQL credentials.

### Step 5: Run the Application

Using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

The application will start at:

`http://localhost:8080`

## 11. API Testing with Postman

Postman is used to test and validate the REST API endpoints.

The testing process covers:

- Creating records.
- Retrieving records.
- Updating records.
- Deleting records.
- Testing invalid requests.
- Testing duplicate records.
- Validating business rules.
- Testing JPQL queries.
- Testing bulk updates.
- Testing dashboard summaries.
- Testing pagination.

The exported Postman collection is included in the project repository.

Import the collection into Postman to access the API requests.

## 12. Project Structure

```text
motor-vehicle-service/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── motorservice/
│   │   │           └── motor_vehicle_service/
│   │   │               │
│   │   │               ├── controller/
│   │   │               ├── entity/
│   │   │               ├── repository/
│   │   │               ├── service/
│   │   │               ├── enums/
│   │   │               │
│   │   │               └── MotorVehicleServiceApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── mvnw
├── mvnw.cmd
├── README.md
└── Motor_Vehicle_Service_Management.postman_collection.json
```

## 13. Conclusion

The Motor Vehicle Service and Repair Management System demonstrates the practical application of Java, Spring Boot, RESTful API development, relational database management, and object-relational mapping.

The project provides a structured approach to managing vehicle servicing operations while demonstrating CRUD operations, entity relationships, business logic, derived queries, JPQL, bulk database updates, reporting, dashboard summaries, and pagination.
