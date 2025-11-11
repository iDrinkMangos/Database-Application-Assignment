# Database-Application-Assignment

Name: Krishna Adhikari
Student NO: 101182873

Student Management Application 

This project implements a simple Student Management system demonstrating standard CRUD (Create, Read, Update, Delete) operations using Java and the PostgreSQL JDBC driver.

 Video Demonstration Link

** https://youtu.be/lxf_mNYB41w **

---

Setup and Prerequisites

1.  **PostgreSQL Server:** Ensure PostgreSQL 18 is installed and running locally on port 5432.
2.  **Database Credentials:** This application connects as the PostgreSQL superuser. The password is set to: **`noob123`**.
3.  **JDBC Driver:** The required PostgreSQL JDBC driver (`postgresql-42.7.8.jar`) must be located in the root directory of this project.

---

 Database Setup Instructions

Before running the Java application, the database and tables must be created and populated.

1.  **Connect to PostgreSQL:** 
    ```bash
    & "C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres
    ```

2.  **Create Database:** 
    ```sql
    CREATE DATABASE student_management;
    \c student_management
    ```

3.  **Run SQL Scripts:** 
    ```sql
    \i 'C:/Users/Krishna/Documents/ASSIGNMENTS FOLDER/Github/create_db.sql'
    \i 'C:/Users/Krishna/Documents/ASSIGNMENTS FOLDER/Github/data_db.sql'
    ```

4.  **Exit psql:**
    ```sql
    \q
    ```

---

Compilation and Execution

 1. Compile the Java Code

Compile the application, including the JDBC driver in the classpath:

```bash
javac -cp .;"postgresql-42.7.8.jar" StudentApp.java
