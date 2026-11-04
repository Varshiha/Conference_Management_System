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
