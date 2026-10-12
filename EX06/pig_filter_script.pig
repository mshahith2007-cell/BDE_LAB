students = LOAD 'students.txt' USING PigStorage(',') 
           AS (rollno:int, name:chararray, mark:int);

high_scorers = FILTER students BY mark > 80;

DUMP high_scorers
;