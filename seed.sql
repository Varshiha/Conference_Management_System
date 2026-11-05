/*Reset the autoincrement id to one. Keeping it for now until all values have been inserted from all tables*/
DELETE FROM sqlite_sequence WHERE name = 'Conferences';
DELETE FROM sqlite_sequence WHERE name = 'Users';
DELETE FROM sqlite_sequence WHERE name = 'Registrations';

DELETE FROM Conferences;
DELETE FROM Registrations;
DELETE FROM Credentials;
DELETE FROM Users;

INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location)
VALUES("Computer Science", 10, "2026-12-16 09:00", "2026-12-16 12:00", "Vanier");

INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location)
VALUES("Web Development Workshop", 30, "2026-12-10 12:00", "2026-12-10 15:00", NULL);

INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location)
VALUES("AI Conference", 15, "2026-12-07 13:00", "2026-12-07 16:00", "Saint-Maxime");

INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location)
VALUES("Cybersecurity Seminar", 20, "2026-12-07 12:00", "2026-12-07 15:00", "Montmorency College");

INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location)
VALUES("Database Management Workshop", 25, "2027-01-01 09:00", "2027-01-01 14:00", "Vanier");

INSERT INTO Conferences (Title, Capacity, Start_DateTime, End_DateTime, Location)
VALUES("Advanced C# Course", 5, "2027-01-01 09:00", "2027-01-01 12:00", "Vanier");

--USERS
INSERT INTO Users(U_ID,Full_Name,Age,Role)
VALUES(1,'Shreeraj Patel',19,'ADMIN');

INSERT INTO Users(U_ID,Full_Name,Age,Role)
VALUES(2,'Var Char',67,'PARTICIPANT');

INSERT INTO Users(U_ID,Full_Name,Age,Role)
VALUES(3,'Daniel Ghosted',76,'PARTICIPANT');

--CREDENTIALS
INSERT INTO Credentials (U_ID,Email_Address,Password)
VALUES(1,'admin@gmail.com','Adminpass123!');

INSERT INTO Credentials (U_ID,Email_Address,Password)
VALUES(2,'varchar@gmail.com','Charpass123!');

INSERT INTO Credentials (U_ID,Email_Address,Password)
VALUES(3,'broo@gmail.com','Bropass123!');

--REGISTRATIONS
INSERT INTO Registrations(C_ID, U_ID, Registration_Date, Status, Waitlist_Position)
VALUES(5, 3, "2026-11-05 05:00", "CONFIRMED", null)

