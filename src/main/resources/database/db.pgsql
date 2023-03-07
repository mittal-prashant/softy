-- Drop and recreate schema
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- Create departments table
CREATE TABLE departments (
id VARCHAR(20) PRIMARY KEY,
name VARCHAR(50)
);

-- Create batch table with foreign key referencing departments
CREATE TABLE batch (
id VARCHAR(20) PRIMARY KEY,
year VARCHAR(20),
dep_id VARCHAR(20) REFERENCES departments(id)
);

-- Create courses table with foreign key referencing departments
CREATE TABLE courses (
id VARCHAR(20) PRIMARY KEY,
name VARCHAR(100),
dep_id VARCHAR(20) REFERENCES departments(id),
lec INTEGER,
tut INTEGER,
pra INTEGER,
credits INTEGER
);

-- Create ug_curriculum table with foreign keys referencing courses and batch
CREATE TABLE ug_curriculum (
course_id VARCHAR(20) REFERENCES courses(id),
batch_id VARCHAR(20) REFERENCES batch(id),
course_type VARCHAR(100)
);

-- Create prereq table with foreign keys referencing courses twice
CREATE TABLE prereq (
course_id VARCHAR(20) REFERENCES courses(id),
prereq_id VARCHAR(20) REFERENCES courses(id)
);

-- Create students table with foreign key referencing batch
CREATE TABLE students (
id VARCHAR(20) PRIMARY KEY,
name VARCHAR(100),
batch_id VARCHAR(20) REFERENCES batch(id),
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(100),
phone_number VARCHAR(20),
credits INTEGER
);

-- Create admins table
CREATE TABLE admins (
username VARCHAR(50) PRIMARY KEY,
name VARCHAR(100),
email VARCHAR(100),
password VARCHAR(100),
phone_number VARCHAR(20)
);

-- Create faculty table with foreign key referencing departments
CREATE TABLE faculties (
id VARCHAR(20) PRIMARY KEY,
name VARCHAR(20),
email VARCHAR(100) UNIQUE NOT NULL,
dep_id VARCHAR(20) REFERENCES departments(id),
password VARCHAR(100),
phone_number VARCHAR(20)
);

-- Create grades table with foreign keys referencing students, faculties, and courses
CREATE TABLE grades (
student_id VARCHAR(20) REFERENCES students(id),
faculty_id VARCHAR(20) REFERENCES faculties(id),
course_id VARCHAR(20) REFERENCES courses(id),
grade VARCHAR(5),
semester VARCHAR(100),
academic_year VARCHAR(100)
);

CREATE TABLE semester (
academic_year VARCHAR(20),
semester VARCHAR(20)
);

-- Insert data into departments table
INSERT INTO departments (id, name) VALUES
('CS', 'Computer Science'),
('EE', 'Electrical Engineering'),
('ME', 'Mechanical Engineering');

-- Insert data into batch table
INSERT INTO batch (id, year, dep_id) VALUES
('2020EEB', '2020', 'EE'),
('2019CSB', '2019', 'CS'),
('2020MEB', '2020', 'ME');

-- Insert data into students table
INSERT INTO students (id, name, batch_id, email, password, phone_number) VALUES
('2020EEB1048', 'Prashant Mittal', '2020EEB', '2020eeb1048@iitrpr.ac.in', 'hello', '8920770053'),
('2019CSB1110', 'Rajesh Runiwal', '2019CSB', '2019csb1110@iitrpr.ac.in', 'hello', '8010870396'),
('2020MEB1110', 'Shubham Mittal', '2020MEB', '2020meb1110@iitrpr.ac.in', 'hello', '8054525906');

-- Insert data into faculties table
INSERT INTO faculties (id, name, email, dep_id, password, phone_number) VALUES
('1113', 'Apurva Mudgal', 'apurva@iitrpr.ac.in', 'EE', 'apurva', '8920770053'),
('1114', 'Balwinder Sodhi', 'balwinder@iitrpr.ac.in', 'CS', 'balwinder', '8527997951');

-- Insert data into admins table
INSERT INTO admins (username, name, email, password, phone_number) VALUES
('dean_ug', 'Dean UG', 'deanug@iitrpr.ac.in', 'admin', '8920770053'),
('admin', 'Academic Office', 'academicoffice@iitrpr.ac.in', 'iitropar', '9868700502');
