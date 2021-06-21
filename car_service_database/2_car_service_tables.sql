-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema car_service_database
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema car_service_database
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `car_service_database` DEFAULT CHARACTER SET utf8 ;
USE `car_service_database` ;

-- -----------------------------------------------------
-- Table `car_service_database`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`users` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `enabled` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This is a USERS table for Spring Security in Car Service App';

CREATE UNIQUE INDEX `username_UNIQUE` ON `car_service_database`.`users` (`username` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`authorities`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`authorities` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`authorities` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `authority` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_authorities_users`
    FOREIGN KEY (`username`)
    REFERENCES `car_service_database`.`users` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a AUTHORITIES table for Spring Security in Car Service App';


-- -----------------------------------------------------
-- Table `car_service_database`.`customers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`customers` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `picture` VARCHAR(200) NULL,
  `name` VARCHAR(60) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(30) NOT NULL,
  `is_deleted` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This is a CUSTOMERS table in Car Service App';

CREATE UNIQUE INDEX `email_UNIQUE` ON `car_service_database`.`customers` (`email` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`cars_makers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`cars_makers` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`cars_makers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `make` VARCHAR(60) NOT NULL,
  `country` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This is a CAR MAKES table in Car Service App';


-- -----------------------------------------------------
-- Table `car_service_database`.`cars_models`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`cars_models` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`cars_models` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `make_id` INT NOT NULL,
  `model` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cars_models_cars_makers`
    FOREIGN KEY (`make_id`)
    REFERENCES `car_service_database`.`cars_makers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a CARS MODELS table in Car Service App';

CREATE INDEX `fk_cars_models_cars_makers_idx` ON `car_service_database`.`cars_models` (`make_id` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`manufacture_years`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`manufacture_years` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`manufacture_years` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `year` INT NOT NULL,
  `model_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_manufacture_years_cars_models`
    FOREIGN KEY (`model_id`)
    REFERENCES `car_service_database`.`cars_models` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a MANUFACTURE YEARS table in Car Service App';

CREATE INDEX `fk_manufacture_years_cars_models_idx` ON `car_service_database`.`manufacture_years` (`model_id` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`customers_cars`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`customers_cars` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`customers_cars` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `year_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `car_vin` VARCHAR(17) NOT NULL,
  `car_registration_plate` VARCHAR(20) NOT NULL,
  `is_deleted` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_customers_cars_customers`
    FOREIGN KEY (`customer_id`)
    REFERENCES `car_service_database`.`customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_customers_cars_manufacture_years`
    FOREIGN KEY (`year_id`)
    REFERENCES `car_service_database`.`manufacture_years` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a CUSTOMERS CARS table in Car Service App';

CREATE INDEX `fk_customers_cars_customers_idx` ON `car_service_database`.`customers_cars` (`customer_id` ASC);

CREATE INDEX `fk_customers_cars_manufacture_years_idx` ON `car_service_database`.`customers_cars` (`year_id` ASC);

CREATE UNIQUE INDEX `car_vin_UNIQUE` ON `car_service_database`.`customers_cars` (`car_vin` ASC);

CREATE UNIQUE INDEX `car_registration_plate_UNIQUE` ON `car_service_database`.`customers_cars` (`car_registration_plate` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`cars_visits`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`cars_visits` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`cars_visits` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP(6) NULL,
  `customer_car_id` INT NOT NULL,
  `total_price` DOUBLE NOT NULL,
  `pdf_generated` TINYINT(4) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cars_visits_customers_cars`
    FOREIGN KEY (`customer_car_id`)
    REFERENCES `car_service_database`.`customers_cars` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a CARS VISITS table in Car Service App';

CREATE INDEX `fk_cars_visits_customers_cars_idx` ON `car_service_database`.`cars_visits` (`customer_car_id` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`service_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`service_categories` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`service_categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'This is a SERVICE_CATEGORIES table in Car Service App';


-- -----------------------------------------------------
-- Table `car_service_database`.`services`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`services` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`services` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category_id` INT NOT NULL,
  `service` VARCHAR(100) NOT NULL,
  `price` DOUBLE NOT NULL,
  `is_deleted` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_services_service_categories`
    FOREIGN KEY (`category_id`)
    REFERENCES `car_service_database`.`service_categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a SERVICES table in Car Service App';

CREATE INDEX `fk_services_service_categories_idx` ON `car_service_database`.`services` (`category_id` ASC);


-- -----------------------------------------------------
-- Table `car_service_database`.`cars_visits_services`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `car_service_database`.`cars_visits_services` ;

CREATE TABLE IF NOT EXISTS `car_service_database`.`cars_visits_services` (
  `car_visit_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  CONSTRAINT `fk_cars_visits_services_cars_visits`
    FOREIGN KEY (`car_visit_id`)
    REFERENCES `car_service_database`.`cars_visits` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cars_visits_services_services`
    FOREIGN KEY (`service_id`)
    REFERENCES `car_service_database`.`services` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'This is a CARS VISITS SERVICES inner table in Car Service App';

CREATE INDEX `fk_cars_visits_services_cars_visits_idx` ON `car_service_database`.`cars_visits_services` (`car_visit_id` ASC);

CREATE INDEX `fk_cars_visits_services_services_idx` ON `car_service_database`.`cars_visits_services` (`service_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
