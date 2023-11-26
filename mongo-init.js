db.createUser(
    {
        user: "santa",
        pwd: "root",
        roles: [
            {
                role: "readWrite",
                db: "santafe"
            }
        ]
    }
);