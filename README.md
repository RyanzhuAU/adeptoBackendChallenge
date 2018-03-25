# Adepto Backend Developer Challenge

How to install:

1. Download or clone from this repository

2. The required database is MySQL. Run the sql script which is under the "SQL" folder to create the schema.

3. Go to {application folder}\src\main\resources\, modify the file "application.properties". Change the database username/password.

4. Use maven to build this application. Go to the application folder and type mvn install.

5. Go to the target folder, and start the application by "java -jar challenge-0.1.0.jar".

6. Copay the shifts.csv and staff.csv files to target folder. 

7. Endpoints are:
   - loadShift: load shift/staff csv file data into database
   - generateSchedule: generate schedule based on the input data and save into "shift_schedule" table.
