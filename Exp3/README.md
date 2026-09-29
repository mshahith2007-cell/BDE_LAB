# Experiment 3 — MongoDB Sharding and Replication

## Part A — Replication

### 1. Create folders

```cmd
mkdir "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3"
cd "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3"
mkdir rs1\db
mkdir rs2\db
mkdir rs3\db
```

### 2. Start MongoDB servers

Open 3 separate CMD windows.

**CMD 1**
```cmd
mongod --replSet rs0 --port 27017 --dbpath "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\rs1\db"
```

**CMD 2**
```cmd
mongod --replSet rs0 --port 27018 --dbpath "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\rs2\db"
```

**CMD 3**
```cmd
mongod --replSet rs0 --port 27019 --dbpath "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\rs3\db"
```

Keep all 3 windows open.

### 3. Initiate replica set

```cmd
mongosh --port 27017
```

```javascript
rs.initiate({
  _id: "rs0",
  members: [
    { _id: 0, host: "localhost:27017" },
    { _id: 1, host: "localhost:27018" },
    { _id: 2, host: "localhost:27019" }
  ]
})
```

Check:

```javascript
rs.status()
```

Expected: **1 PRIMARY and 2 SECONDARY**.

### 4. Insert sample data

```javascript
use college

db.students.insertMany([
  { rollno: 101, name: "Raja", mark: 95 },
  { rollno: 102, name: "Pooja", mark: 85 },
  { rollno: 103, name: "Roja", mark: 75 }
])
```

### 5. Verify replication

```javascript
.exit
```

```cmd
mongosh --port 27018
```

```javascript
use college
rs.secondaryOk()
db.students.find()
```

The same records should appear.

---

## Part B — Sharding

### 6. Create folders

```cmd
mkdir "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\config\db"
mkdir "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\shard1\db"
mkdir "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\shard2\db"
```

### 7. Start Config Server

```cmd
mongod --configsvr --replSet configReplSet --port 26050 --dbpath "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\config\db"
```

### 8. Initialize Config Server

```cmd
mongosh --port 26050
```

```javascript
rs.initiate({
  _id: "configReplSet",
  configsvr: true,
  members: [{ _id: 0, host: "localhost:26050" }]
})
```

### 9. Start Shards

**Shard 1**
```cmd
mongod --shardsvr --replSet shard1RS --port 27020 --dbpath "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\shard1\db"
```

**Shard 2**
```cmd
mongod --shardsvr --replSet shard2RS --port 27021 --dbpath "C:\Users\SAI\Desktop\Lab\Big Data\Exp\Exp3\shard2\db"
```

### 10. Initialize the shards

**Shard 1**
```cmd
mongosh --port 27020
```

```javascript
rs.initiate({
  _id: "shard1RS",
  members: [{ _id: 0, host: "localhost:27020" }]
})
```

**Shard 2**
```cmd
mongosh --port 27021
```

```javascript
rs.initiate({
  _id: "shard2RS",
  members: [{ _id: 0, host: "localhost:27021" }]
})
```

### 11. Start MongoDB Router

```cmd
mongos --configdb configReplSet/localhost:26050 --port 27022
```

### 12. Add shards

Open another CMD:

```cmd
mongosh --port 27022
```

```javascript
sh.addShard("shard1RS/localhost:27020")
sh.addShard("shard2RS/localhost:27021")
```

### 13. Enable sharding

```javascript
sh.enableSharding("college")
sh.shardCollection("college.students", { rollno: 1 })
```

### 14. Verify

```javascript
sh.status()
```

Check for:

```text
shard1RS
shard2RS
college.students
shardKey: { rollno: 1 }
```

## Result

Successfully implemented MongoDB Replication using a replica set and Sharding using multiple shards and the `mongos` router.
