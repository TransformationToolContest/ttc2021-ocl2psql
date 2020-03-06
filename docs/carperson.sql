DROP TABLE IF EXISTS Car_ownedCars_owners_Person;
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

CREATE TABLE Car_ownedCars_owners_Person (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Car_ownedCars_owners_Person 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);