db.createUser(
    {
        user: "santa",
        pwd: "mishasanta",
        roles: [
            {
                role: "readWrite",
                db: "santafe"
            }
        ]
    }
);