# Academic Information Management Portal

### Follow the following steps to run the program:

1. First of all run the build.gradle file.
2. Get all the dependencies in build.gradle.
3. Load the file db.pgsql from src/main/resources/database folder into the postgres database into a new database aims.
4. Change the password and username in ConnectDB.java file and provide your credentials.
5. Compile all the Java files before running.
6. Run the Main.java file to run the entire program.

### Following files are present in the Project:

1. db.pgsql in src/main/resources/database directory.
2. Main.java, ConnectDB.java in src/main/java/org/example package.
3. Student.java and StudentMenu.java in src/main/java/org/example/Student package.
4. Faculty.java and FacultyMenu.java in src/main/java/org/example/Faculty package.
5. AcademicOffice.java and AcademicOfficeMenu.java in src/main/java/org/example/AcademicOffice package.
6. grades.csv and Transcript_Login_Logout.txt file in src/main/resources folder.
7. And the units test files in the src/test/java/org/example directory.
8. UML diagram (static and dynamic) is also present in the root directory.
9. Jacoco report is present in the Jacoco_Report folder.
10. Students transcript is generated in src/main/resources/Transcript_Student folder.
11. Unit_Test_Plan.pdf is present in root directory.

### Stake Holder of Portal

1. AcademicOffice: dean_ug, admin
   1. admin can perform various operations
   2. dean_ug can perform all operations including options to update course catalogue and course curriculum (UG curriculum)
2. Faculty
3. Student