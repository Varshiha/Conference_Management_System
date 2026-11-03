BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Conferences" (
	"C_ID"	INTEGER,
	"Title"	TEXT NOT NULL,
	"Capacity"	INTEGER NOT NULL CHECK("Capacity" > 0),
	"Start_DateTime"	TEXT NOT NULL,
	"End_DateTime"	TEXT NOT NULL CHECK("End_DateTime" > "Start_DateTime"),
	"Location"	TEXT,
	PRIMARY KEY("C_ID" AUTOINCREMENT)
);
COMMIT;
