-- Create a database (optional)
CREATE DATABASE IF NOT EXISTS company;
-- Use the database
USE company;
-- Create table
CREATE TABLE IF NOT EXISTS employee (
    id INT,
    name STRING,
    dept STRING,
    salary INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;
-- Load data
LOAD DATA LOCAL INPATH 'employee_data.txt' INTO TABLE employee;
-- Query: Show all records
SELECT * FROM employee;
-- Query: Find average salary per department
SELECT dept, AVG(salary) as avg_salary FROM employee GROUP BY dept;
-- Query: Highest salary employee
SELECT * FROM employee ORDER BY salary DESC LIMIT 1;

