DROP DATABASE IF EXISTS heroDb;
CREATE DATABASE heroDb;

USE heroDb;

CREATE TABLE `location`(
    `location_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `description` VARCHAR(50) NOT NULL,
    `address` VARCHAR(50) NOT NULL,
    `latitude` VARCHAR(50) NOT NULL,
    `longitude` VARCHAR(50) NOT NULL
);
CREATE TABLE `superpower`(
    `superpower_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `description` VARCHAR(50) NOT NULL
   
);
CREATE TABLE `superhero`(
    `superhero_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    `description` VARCHAR(50) NOT NULL
);

CREATE TABLE `superpower_superhero`(
    `superpower_id` INT NOT NULL,
    `superhero_id` INT NOT NULL,
    PRIMARY KEY(`superpower_id`, `superhero_id`),
    FOREIGN KEY (`superhero_id`) REFERENCES superhero(superhero_id),
    FOREIGN KEY (`superpower_id`) REFERENCES superpower(superpower_id)
);





CREATE TABLE `sighting`(
	`sighting_id` INT PRIMARY KEY AUTO_INCREMENT,
    `superhero_id` INT NOT NULL,
    `location_id` INT NOT NULL,
    `date` DATE,
    FOREIGN KEY (`superhero_id`) REFERENCES superhero(superhero_id),
    FOREIGN KEY (`location_id`) REFERENCES location(location_id)
);

CREATE TABLE `organization`(
    `organization_id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(50),
    `address` VARCHAR(50) NOT NULL
);

CREATE TABLE `organization_superhero`(
    `superhero_id` INT NOT NULL,
    `organization_id` INT NOT NULL,
    PRIMARY KEY(`superhero_id`, `organization_id`),
    FOREIGN KEY (`superhero_id`) REFERENCES superhero(superhero_id),
    FOREIGN KEY (`organization_id`) REFERENCES organization(organization_id)
);

