DROP TABLE IF EXISTS TestTable;
CREATE TABLE TestTable (
	status VARCHAR (100)
);
INSERT INTO TestTable (status) VALUES ('Connection successful');

DROP TABLE IF EXISTS Car;
CREATE TABLE Car (
	Car_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	color VARCHAR (100) 
);
DROP TABLE IF EXISTS Person;
CREATE TABLE Person (
	Person_id INT (11) NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR (100) 
);
DROP TABLE IF EXISTS Car_ownedCars_owners_Person;
CREATE TABLE Car_ownedCars_owners_Person (
	ownedCars INT (11), 
	owners INT (11)
);
ALTER TABLE Car_ownedCars_owners_Person 
	ADD FOREIGN KEY (ownedCars) REFERENCES Car (Car_id), 
	ADD FOREIGN KEY (owners) REFERENCES Person (Person_id);

DELIMITER //
CREATE PROCEDURE BuildScenario1()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE BuildScenario2()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
	INSERT INTO Car (Car_id, color) VALUES (1, 'black');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE BuildScenario3()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
	INSERT INTO Car (Car_id, color) VALUES (1, 'black'), (2, 'red');
	INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE BuildScenario4()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
	INSERT INTO Car (Car_id, color) VALUES (1, 'black'), (2, 'red');
	INSERT INTO Person (Person_id, name) VALUES (1, 'Peter');
	INSERT INTO Car_ownedCars_owners_Person (owners, ownedCars) VALUES (1, 1);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE BuildScenario5()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
	INSERT INTO Car (Car_id, color) VALUES (1, 'black'), (2, 'red');
	INSERT INTO Person (Person_id, name) VALUES (1, 'Peter'), (2, 'Jane');
	INSERT INTO Car_ownedCars_owners_Person (owners, ownedCars) VALUES (1, 1);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE BuildScenario6()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
	INSERT INTO Car (Car_id, color) VALUES (1, 'black'), (2, 'red');
	INSERT INTO Person (Person_id, name) VALUES (1, 'Peter'), (2, 'Jane');
	INSERT INTO Car_ownedCars_owners_Person (owners, ownedCars) VALUES (1, 1), (2, 1);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE BuildScenario7()
BEGIN
    DELETE FROM Car_ownedCars_owners_Person;
	DELETE FROM Car;
	ALTER TABLE Car AUTO_INCREMENT = 1;
	DELETE FROM Person;
	ALTER TABLE Person AUTO_INCREMENT = 1;
	INSERT INTO Car (Car_id, color) VALUES (1, 'black'), (2, 'red');
	INSERT INTO Person (Person_id, name) VALUES (1, 'Peter'), (2, 'Jane');
	INSERT INTO Car_ownedCars_owners_Person (owners, ownedCars) VALUES (1, 1), (2, 1), (2, 2);
END //
DELIMITER ;

