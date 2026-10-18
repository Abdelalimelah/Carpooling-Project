<div align="center">

# CarPooling — Web Application for Ride Sharing

**A full-featured Java EE / Jakarta EE web application for intercity carpooling, connecting drivers and passengers with role-based dashboards, ride management, booking workflows, and automated Jenkins CI/CD.**

[![Repository](https://img.shields.io/badge/GitHub-Abdelalimelah%2FCarpooling--Project-181717?style=for-the-badge&logo=github)](https://github.com/Abdelalimelah/Carpooling-Project)
[![Java 17](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta_EE-6.0-F80000?style=for-the-badge&logo=jakartaee&logoColor=white)](https://jakarta.ee/)
[![Maven](https://img.shields.io/badge/Apache_Maven-3.9+-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Jenkins](https://img.shields.io/badge/Jenkins-CI%2FCD-D24939?style=for-the-badge&logo=jenkins&logoColor=white)](https://www.jenkins.io/)
[![SonarQube](https://img.shields.io/badge/SonarQube-Analysis-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)](https://www.sonarsource.com/products/sonarqube/)

<br />

<p align="center">
  <a href="#overview">Overview</a> •
  <a href="#key-features">Features</a> •
  <a href="#role-based-capabilities">User Roles</a> •
  <a href="#architecture--design-patterns">Architecture</a> •
  <a href="#tech-stack">Tech Stack</a> •
  <a href="#database-schema">Database</a> •
  <a href="#getting-started">Getting Started</a> •
  <a href="#cicd-pipeline">CI/CD</a> •
  <a href="#servlet-reference">Servlets</a>
</p>

</div>

---

## Overview

**CarPooling** is a web-based ride-sharing platform developed in Java (Jakarta EE 6.0) designed to facilitate carpooling between drivers with available vehicle seats and passengers traveling along the same routes. The application automates trip publication, route searching, booking reservations, user notifications, and mutual reviews.

Built around standard Jakarta EE architectural patterns (MVC, DAO, Service Layer, and HTTP Filters), the platform enforces strict role-based access control across three user profiles: **Admin**, **Driver**, and **Passenger**. The project also includes an automated **Jenkins CI/CD pipeline** with integrated **SonarQube static code analysis** and unit tests written with **JUnit 5 and Mockito**.

---

## Key Features

- **Authentication & Security**:
  - Secure registration and login flows with hashed passwords via PasswordUtil.
  - HTTP Session authentication management with automated session invalidation upon logout.
  - Granular access enforcement using dedicated Jakarta Servlet HTTP Filters (AdminFilter, DriverFilter, PassengerFilter, LoginFilter).

- **Driver Management**:
  - Vehicle management: register, update, and remove vehicles with license plate, model, color, and seat capacity.
  - Trip publication: create intercity trips specifying departure/arrival cities, departure date and time, price per seat, and available capacity.
  - Booking management: accept, confirm, or cancel incoming reservation requests from passengers.
  - Trip status life cycle: schedule, activate, edit, and mark trips as completed.

- **Passenger Experience**:
  - Advanced trip search and filtering by departure location, destination city, date, and available seats.
  - Seamless reservation booking with automatic capacity decrement.
  - Trip tracking dashboard showing upcoming, completed, and canceled rides.
  - Booking cancellation with automatic seat restoration.

- **Reviews and Ratings**:
  - Post-trip mutual feedback system between drivers and passengers with numerical ratings and written comments.

- **Real-Time Notification System**:
  - In-app notification engine alerting users of booking requests, confirmations, trip updates, and cancellations.
  - Mark-as-read and bulk unread notification counters.

- **Administrator Portal**:
  - Centralized dashboard providing aggregate metrics on users, active trips, bookings, and revenue.
  - Complete user management (search, view, edit role, and delete users).
  - Trip and booking oversight with moderation and cancellation privileges.
  - System-wide feedback and complaint monitoring.

- **Continuous Integration & Quality**:
  - Declarative Jenkins pipeline automating Maven build, unit testing, and SonarQube code quality inspection.

---

## Role-Based Capabilities

| Feature | Administrator | Driver | Passenger |
|---|:---:|:---:|:---:|
| User Registration & Profile Edit | Yes | Yes | Yes |
| Browse & Search Trips | Yes | Yes | Yes |
| Book a Seat | - | - | Yes |
| Cancel Booking | Yes | - | Yes |
| Register & Manage Cars | - | Yes | - |
| Create & Manage Trips | Yes (Moderation) | Yes | - |
| Approve / Confirm Bookings | - | Yes | - |
| Complete Trip | - | Yes | - |
| Submit Reviews & Ratings | - | Yes | Yes |
| View System Notifications | Yes | Yes | Yes |
| Manage Users & Roles | Yes | - | - |
| View Admin KPI Dashboard | Yes | - | - |

---

## Architecture & Design Patterns

The application follows the classic enterprise three-tier Jakarta EE architecture:

`	ext
+-----------------------------------------------------------+
|                 Presentation Layer (Views)                |
|             JSP, JSTL 2.0, HTML5, CSS3, JavaScript        |
+-----------------------------------------------------------+
                             |
                             v
+-----------------------------------------------------------+
|                   Controller Layer (Web)                  |
|          Jakarta Servlets (HttpServlet) & HTTP Filters     |
|   (AdminFilter, DriverFilter, PassengerFilter, LoginFilter)|
+-----------------------------------------------------------+
                             |
                             v
+-----------------------------------------------------------+
|                   Business & Data Access                  |
|                 Java Beans (POJOs / DTOs)                 |
|             DAO Factory & DAO Implementations             |
+-----------------------------------------------------------+
                             |
                             v
+-----------------------------------------------------------+
|                     Persistence Layer                     |
|           MySQL 8.0 via JDBC (mysql-connector-j-9.4.0)    |
|                H2 In-Memory Database (Tests)              |
+-----------------------------------------------------------+
`

### Core Design Patterns Applied:
1. **Model-View-Controller (MVC)**: Clean separation of presentation (.jsp views), routing/processing (Servlets), and domain state (Beans).
2. **Data Access Object (DAO) Pattern**: Decouples business logic from persistence operations through interfaces (UserDAO, TripDAO, BookingDAO, CarDAO, FeedbackDAO, NotificationDAO).
3. **Abstract Factory Pattern**: Centralized connection management and DAO instantiation via DAOFactory driven by dao.properties.
4. **Intercepting Filter Pattern**: Jakarta Servlet Filters intercepting requests to validate authentication tokens and role authorization prior to servlet execution.

---

## Tech Stack

| Component | Technology | Version / Details |
|---|---|---|
| **Runtime / Language** | Java (OpenJDK) | 17 LTS |
| **Enterprise Platform** | Jakarta EE (Servlets & JSTL) | Jakarta Servlet API 6.0.0, JSTL 2.0.0 |
| **Web Server / Container** | Apache Tomcat | 10.1+ (Jakarta EE 9/10 compliant) |
| **Build & Dependency Tool** | Apache Maven | 3.8+ / packaging WAR |
| **Relational Database** | MySQL | 8.0+ via JDBC |
| **In-Memory Test Database**| H2 Database | 2.2.224 |
| **Unit Testing & Mocking** | JUnit 5 & Mockito | JUnit Jupiter 5.10.0, Mockito Core 5.2.0 |
| **CI/CD Automation** | Jenkins Pipeline | Declarative Jenkinsfile |
| **Code Quality / SAST** | SonarQube Scanner | sonar-maven-plugin |

---

## Database Schema

The persistence layer uses a normalized relational schema under the database name carpoolingdb:

- **User**: id, email, password_hash, irst_name, last_name, phone_number, ole, created_at, updated_at
- **Driver**: user_id (FK to User), license_number, io, ating
- **Passenger**: user_id (FK to User), ating
- **Car**: id, driver_id (FK to User), make, model, year, license_plate, color, seats
- **Trip**: id, driver_id (FK to User), car_id (FK to Car), departure_city, rrival_city, date_time, price, seats_available, created_at
- **Booking**: id, 	rip_id (FK to Trip), passenger_id (FK to User), seats_booked, 	otal_price, status, created_at
- **Feedback**: id, ooking_id (FK to Booking), rom_user_id (FK to User), 	o_user_id (FK to User), ating, comment, created_at
- **Notification**: id, user_id (FK to User), message, is_read, created_at

---

## Getting Started

### 1. Prerequisites
- **Java Development Kit (JDK)**: Version 17 or higher
- **Apache Maven**: Version 3.8+ ([Download](https://maven.apache.org/))
- **MySQL Server**: Version 8.0+ ([Download](https://dev.mysql.com/downloads/))
- **Servlet Container**: Apache Tomcat 10.1+ ([Download](https://tomcat.apache.org/download-10.cgi))

### 2. Clone the Repository
`ash
git clone https://github.com/Abdelalimelah/Carpooling-Project.git
cd Carpooling-Project
`

### 3. Database Setup
Create the MySQL database:
`sql
CREATE DATABASE carpoolingdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
`

Update your database credentials in the configuration file:
src/main/java/com/JAVA/DAO/Factory/dao.properties

`properties
url = jdbc:mysql://localhost:3306/carpoolingdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
driver = com.mysql.cj.jdbc.Driver
nomutilisateur = your_mysql_username
motdepasse = your_mysql_password
`

### 4. Build and Package
To compile source code, execute unit tests, and package the WAR archive:
`ash
mvn clean package
`
The compiled archive will be generated at:
	arget/CarPooling-1.0.war

### 5. Running the Tests
To run unit and integration tests with JUnit 5 and Mockito:
`ash
mvn clean test
`

### 6. Deployment

#### Deploy to Apache Tomcat 10+:
1. Copy the generated WAR file into your Tomcat webapps/ folder:
   `ash
   cp target/CarPooling-1.0.war <TOMCAT_HOME>/webapps/carpooling.war
   `
2. Start Tomcat:
   - On Linux/macOS: <TOMCAT_HOME>/bin/startup.sh
   - On Windows: <TOMCAT_HOME>\bin\startup.bat
3. Access the application in your browser:
   http://localhost:8080/carpooling/

#### Deploy in Eclipse or IntelliJ IDEA:
1. Open the project as an **Existing Maven Project**.
2. Configure an **Apache Tomcat 10.1+** runtime server in your IDE.
3. Add the project to the server deployment list and click **Run**.

---

## CI/CD Pipeline

The project includes an automated declarative pipeline defined in [Jenkinsfile](Jenkinsfile):

`groovy
pipeline {
    agent any
    tools {
        maven 'Maven-3.9.12'
        jdk 'JDK-17'
    }
    stages {
        stage('Clone') {
            steps {
                git branch: 'master', url: 'https://github.com/Abdelalimelah/Carpooling-Project.git'
            }
        }
        stage('Build & Test') {
            steps {
                bat 'mvn clean verify'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube-Server') {
                    bat 'mvn sonar:sonar'
                }
            }
        }
    }
}
`

The pipeline automatically triggers:
1. Source checkout from GitHub.
2. Clean compilation and execution of unit and integration test suites (mvn clean verify).
3. Static code analysis and security vulnerability scanning via SonarQube.

---

## Servlet Reference

| Servlet Name | URL Pattern | Required Role | Functionality |
|---|---|:---:|---|
| RegisterServlet | /register | Public | Account creation for Passengers and Drivers |
| LoginServlet | /login | Public | Session authentication & role-based dispatch |
| LogoutServlet | /logout | Authenticated | Session invalidation and redirect to login |
| ProfileServlet | /profile | Authenticated | View and edit user personal info |
| BrowseTripsServlet | /browseTrips | Public / Passenger | Search and filter available rides |
| CreateTripServlet | /createTrip | Driver | Publish a new intercity ride |
| EditTripServlet | /editTrip | Driver | Update departure, route, price, or seats |
| DeleteTripServlet | /deleteTrip | Driver | Cancel and delete scheduled trip |
| TripDetailsServlet | /tripDetails | Driver | View detailed trip passenger roster |
| AddCarServlet | /addCar | Driver | Register a new vehicle to driver profile |
| EditCarServlet | /editCar | Driver | Update vehicle information |
| DeleteCarServlet | /deleteCar | Driver | Remove a vehicle from driver profile |
| DriverDashServlet | /driverDashboard | Driver | Driver overview of trips, cars, and bookings |
| CreateBookingServlet| /createBooking | Passenger | Reserve seats on a selected trip |
| CancelBookingServlet| /cancelBooking | Passenger | Cancel an existing booking reservation |
| PassengerDashboardServlet | /passengerDashboard | Passenger | Passenger history of active and past bookings |
| TripDetailsPassengerServlet | /tripDetailsPassenger | Passenger | View itinerary and driver details |
| FeedbackServlet | /feedback | Authenticated | Post reviews and star ratings |
| ListUnreadNotificationsServlet | /notifications | Authenticated | Fetch list of unread user alerts |
| MarkAllReadServlet | /markAllRead | Authenticated | Acknowledge all pending notifications |
| AdminDashboardServlet | /admin/dashboard | Admin | High-level metrics, revenue, and stats |
| AdminUserServlet | /admin/users | Admin | User listing, role updates, and deletions |
| AdminTripServlet | /admin/trips | Admin | Trip supervision, search, and moderation |
| AdminBookingServlet| /admin/bookings | Admin | Reservation dispute resolution and oversight |
| AdminFeedbackServlet| /admin/feedbacks | Admin | Audit passenger and driver reviews |

---

## Repository Structure

`	ext
Carpooling-Project/
+-- .classpath               # Eclipse project classpath configuration
+-- .gitignore               # Ignored build artifacts, logs, and OS files
+-- .project                 # Eclipse project definition
+-- .settings/               # IDE configuration parameters
+-- Jenkinsfile              # Declarative CI/CD pipeline script
+-- pom.xml                  # Maven project descriptor, dependencies & plugins
+-- README.md                # Project documentation
¦
+-- src/
    +-- main/
    ¦   +-- java/
    ¦   ¦   +-- com/JAVA/
    ¦   ¦       +-- Beans/               # Entity models (User, Trip, Car, Booking, etc.)
    ¦   ¦       +-- DAO/                 # Data Access Object interfaces
    ¦   ¦       ¦   +-- Exception/       # Custom DAO exceptions
    ¦   ¦       ¦   +-- Factory/         # DAOFactory & dao.properties
    ¦   ¦       ¦   +-- Implement/       # JDBC implementations of DAOs
    ¦   ¦       +-- Filters/             # Authentication & role authorization filters
    ¦   ¦       +-- Servlet/             # Controller servlets for user actions
    ¦   ¦       +-- Utils/               # Password hashing & helper utilities
    ¦   ¦
    ¦   +-- webapp/
    ¦       +-- META-INF/
    ¦       +-- WEB-INF/
    ¦       ¦   +-- lib/                 # JSTL & MySQL JDBC runtime libraries
    ¦       +-- views/
    ¦           +-- admin/               # Admin dashboards & management JSPs
    ¦           +-- auth/                # Login & registration forms
    ¦           +-- driver/              # Driver dashboards, car & trip forms
    ¦           +-- feedback/            # Review & rating forms
    ¦           +-- home/                # Welcome & public home views
    ¦           +-- passenger/           # Ride search & booking management views
    ¦           +-- profile/             # User profile editing views
    ¦
    +-- test/
        +-- java/
            +-- com/JAVA/
                +-- Beans/               # Bean unit tests
                +-- Servlet/             # Servlet integration tests (Mockito)
                +-- Utils/               # Password utility unit tests
`

---

## License

This project is open-source and available under the standard MIT License.
