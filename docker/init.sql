DROP DATABASE IF EXISTS cardb1;

CREATE DATABASE cardb1;

USE cardb1;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

DROP DATABASE IF EXISTS cardb2;

CREATE DATABASE cardb2;
USE cardb2;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

INSERT INTO Car (Car_id, color) VALUES (1, 'black');

DROP DATABASE IF EXISTS cardb3;

CREATE DATABASE cardb3;

USE cardb3;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

INSERT INTO Car (Car_id, color) VALUES (1, 'black');
INSERT INTO Car (Car_id, color) VALUES (2, 'red');
INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');

DROP DATABASE IF EXISTS cardb4;

CREATE DATABASE cardb4;

USE cardb4;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

INSERT INTO Car (Car_id, color) VALUES (1, 'black');
INSERT INTO Car (Car_id, color) VALUES (2, 'red');
INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');
INSERT INTO Ownership (ownedCars, owners) VALUES (1,1);

DROP DATABASE IF EXISTS cardb5;

CREATE DATABASE cardb5;

USE cardb5;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

INSERT INTO Car (Car_id, color) VALUES (1, 'black');
INSERT INTO Car (Car_id, color) VALUES (2, 'red');
INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');
INSERT INTO Person (Person_id, name) VALUES (2, 'Mary');
INSERT INTO Ownership (ownedCars, owners) VALUES (1,1);

DROP DATABASE IF EXISTS cardb6;

CREATE DATABASE cardb6;

USE cardb6;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

INSERT INTO Car (Car_id, color) VALUES (1, 'black');
INSERT INTO Car (Car_id, color) VALUES (2, 'red');
INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');
INSERT INTO Person (Person_id, name) VALUES (2, 'Mary');
INSERT INTO Ownership (ownedCars, owners) VALUES (1,1);
INSERT INTO Ownership (ownedCars, owners) VALUES (1,2);

DROP DATABASE IF EXISTS cardb7;

CREATE DATABASE cardb7;
USE cardb7;

DROP TABLE IF EXISTS Ownership;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Person;

CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);

CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);

CREATE TABLE Ownership (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Ownership 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

INSERT INTO Car (Car_id, color) VALUES (1, 'black');
INSERT INTO Car (Car_id, color) VALUES (2, 'red');
INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');
INSERT INTO Person (Person_id, name) VALUES (2, 'Mary');
INSERT INTO Ownership (ownedCars, owners) VALUES (1,1);
INSERT INTO Ownership (ownedCars, owners) VALUES (1,2);
INSERT INTO Ownership (ownedCars, owners) VALUES (2,2);