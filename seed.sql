/*Reset the autoincrement id to one. Keeping it for now until all values have been inserted from all tables*/
DELETE FROM sqlite_sequence
WHERE name = 'Conferences';

DELETE FROM Conferences;

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




/*
/*Participants for conference 1*/
/*INSERT INTO REGISTRATIONS (U_ID, C_ID, Registration_Date, Status, Waitinglist_Position)
VALUES (?, 1, "2026-11-28 13:00" "CONFRIMED", NULL);
INSERT INTO REGISTRATIONS (U_ID, C_ID, Registration_Date, Status, Waitinglist_Position)
VALUES (?, 1, "2026-12-12 09:00" "CONFRIMED", NULL);
INSERT INTO REGISTRATIONS (U_ID, C_ID, Registration_Date, Status, Waitinglist_Position)
VALUES (?, 1, "2026-11-11 08:00" "CONFRIMED", NULL);*/


/*Participants for conference 2*/
/*INSERT INTO REGISTRATIONS (U_ID, C_ID, Registration_Date, Status, Waitinglist_Position)
VALUES (?,2, "2026-12-08 13:00" "CONFRIMED", NULL);
INSERT INTO REGISTRATIONS (U_ID, C_ID, Registration_Date, Status, Waitinglist_Position)
VALUES (?, 2, "2026-12-01 16:00" "CONFRIMED", NULL);
INSERT INTO REGISTRATIONS (U_ID, C_ID, Registration_Date, Status, Waitinglist_Position)
VALUES (?, 2, "2026-11-11 16:00" "CONFRIMED", NULL);*/

