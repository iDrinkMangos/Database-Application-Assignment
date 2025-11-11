--Script to create the students table

--Drop the table if it exists 
DROP TABLE IF EXISTS students;

--Create the students table as per the schema
CREATE TABLE students (
    student_id SERIAL PRIMARY KEY, 
    first_name TEXT NOT NULL,      
    last_name TEXT NOT NULL,       
    email TEXT NOT NULL UNIQUE,    
    enrollment_date DATE           
);