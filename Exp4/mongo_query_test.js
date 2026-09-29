use college

db.students.createIndex({ rollno: 1 })

db.students.find({ rollno: 50000 })

db.students.find(
    {},
    { name: 1, mark: 1, _id: 0 }
).limit(5)