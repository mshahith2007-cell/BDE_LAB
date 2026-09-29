# Experiment 4 — MongoDB Data Retrieval

## 1. Create the bulk insertion file

Create:

```text
mongo_bulk_insert.js
```

Add:

```javascript
use college

let bulk = []

for (let i = 1; i <= 100000; i++) {
    bulk.push({
        rollno: i,
        name: "Student_" + i,
        mark: Math.floor(Math.random() * 100)
    })
}

db.students.insertMany(bulk)
```

## 2. Insert 100000 student records

Open Command Prompt in the Exp4 folder:

```cmd
mongosh --port 27022 < mongo_bulk_insert.js
```

This inserts 100000 student records into MongoDB.

---

## 3. Create the query file

Create:

```text
mongo_query_test.js
```

Add:

```javascript
use college

db.students.createIndex({ rollno: 1 })

db.students.find({ rollno: 50000 })

db.students.find(
    {},
    { name: 1, mark: 1, _id: 0 }
).limit(5)
```

## 4. Run the queries

```cmd
mongosh --port 27022 < mongo_query_test.js
```

Expected output:

```text
rollno: 50000
name: Student_50000
mark: <value>
```

Five student names and marks will also be displayed.

## Result

Successfully performed fast data retrieval from MongoDB using an index and queries.
