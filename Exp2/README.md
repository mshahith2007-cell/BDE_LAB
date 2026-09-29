# Experiment 2 — Weather Data Analysis using MapReduce

## 1. Start Hadoop

```cmd
start-dfs.cmd
start-yarn.cmd
jps
```

You should see:
```text
NameNode
DataNode
ResourceManager
NodeManager
```

## 2. Create the Java Program

Create `WeatherAnalysis.java` and enter the Weather Analysis program.

Create the input folder and file:

```cmd
mkdir input
notepad input\weather.txt
```

Enter the sample weather data:

```text
202001011234567891020
202001021234567891015
202101011234567891030
202101021234567891027
```

## 3. Compile the Program

```cmd
javac -classpath "%HADOOP_HOME%\share\hadoop\common\*;%HADOOP_HOME%\share\hadoop\common\lib\*;%HADOOP_HOME%\share\hadoop\hdfs\*;%HADOOP_HOME%\share\hadoop\mapreduce\*;%HADOOP_HOME%\share\hadoop\mapreduce\lib\*" WeatherAnalysis.java
```

Compiles the Java program into `.class` files.

## 4. Create the JAR

```cmd
jar -cvf weatheranalysis.jar *.class
```

Packages the compiled files into a JAR file for Hadoop.

## 5. Upload Input to HDFS

```cmd
hdfs dfs -mkdir /weatherinput
hdfs dfs -put "FULL_PATH_TO_Exp2\input\weather.txt" /weatherinput
```

Copies the weather data from Windows to HDFS.

## 6. Run MapReduce

```cmd
hadoop jar weatheranalysis.jar WeatherAnalysis /weatherinput /weatheroutput
```

Finds the maximum temperature for each year.

## 7. Display Output

```cmd
hdfs dfs -cat /weatheroutput/*
```

Displays the result produced by the Reducer.

**Expected output for the sample data:**
```text
2020    7891
2021    7891
```

### If Running Again

```cmd
hdfs dfs -rm -r /weatherinput
hdfs dfs -rm -r /weatheroutput
```

Then repeat the HDFS upload and MapReduce steps.
