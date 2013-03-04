SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `brockbutler` DEFAULT CHARACTER SET latin1 COLLATE latin1_general_ci ;
USE `brockbutler` ;

-- -----------------------------------------------------
-- Table `brockbutler`.`courses`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`courses` (
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `desc` VARCHAR(45) NULL ,
  `instructor` VARCHAR(45) NULL ,
  `instructor_email` VARCHAR(45) NULL ,
  PRIMARY KEY (`subj`, `code`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`courses`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`courses` (
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `desc` VARCHAR(45) NULL ,
  `instructor` VARCHAR(45) NULL ,
  `instructor_email` VARCHAR(45) NULL ,
  PRIMARY KEY (`subj`, `code`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`offerings`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`offerings` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `type` CHAR(3) NULL ,
  `sec` INT NULL ,
  INDEX `fk_offerings_courses_idx` (`subj` ASC, `code` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_offerings_courses`
    FOREIGN KEY (`subj` , `code` )
    REFERENCES `brockbutler`.`courses` (`subj` , `code` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`offering_times`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`offering_times` (
  `id` INT NOT NULL ,
  `day` CHAR(1) NOT NULL ,
  `time_start` CHAR(4) NULL ,
  `time_end` CHAR(4) NULL ,
  `location` VARCHAR(16) NULL ,
  PRIMARY KEY (`id`, `day`) ,
  INDEX `fk_offering_times_offerings1_idx` (`id` ASC) ,
  CONSTRAINT `fk_offering_times_offerings1`
    FOREIGN KEY (`id` )
    REFERENCES `brockbutler`.`offerings` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`tasks`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`tasks` (
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `assign` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `mark` INT NULL ,
  `base` INT NULL ,
  `weight` FLOAT NULL ,
  `due` DATETIME NULL ,
  `create_date` DATETIME NULL ,
  `priority` INT NULL ,
  PRIMARY KEY (`assign`, `subj`, `code`) ,
  INDEX `fk_marks_courses1_idx` (`subj` ASC, `code` ASC) ,
  CONSTRAINT `fk_marks_courses1`
    FOREIGN KEY (`subj` , `code` )
    REFERENCES `brockbutler`.`courses` (`subj` , `code` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`contacts`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`contacts` (
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `cid` INT NOT NULL ,
  `fname` VARCHAR(45) NULL ,
  `lname` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NULL ,
  INDEX `fk_contacts_courses1_idx` (`subj` ASC, `code` ASC) ,
  PRIMARY KEY (`subj`, `cid`, `code`) ,
  CONSTRAINT `fk_contacts_courses1`
    FOREIGN KEY (`subj` , `code` )
    REFERENCES `brockbutler`.`courses` (`subj` , `code` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`task_contacts`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`task_contacts` (
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `cid` INT NOT NULL ,
  `assign` INT NOT NULL ,
  PRIMARY KEY (`subj`, `code`, `cid`, `assign`) ,
  INDEX `fk_task_contacts_contacts1_idx` (`cid` ASC) ,
  INDEX `fk_task_contacts_tasks1_idx` (`assign` ASC) ,
  INDEX `fk_task_contacts_courses1_idx` (`subj` ASC, `code` ASC) ,
  CONSTRAINT `fk_task_contacts_contacts1`
    FOREIGN KEY (`cid` )
    REFERENCES `brockbutler`.`contacts` (`cid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_contacts_tasks1`
    FOREIGN KEY (`assign` )
    REFERENCES `brockbutler`.`tasks` (`assign` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_task_contacts_courses1`
    FOREIGN KEY (`subj` , `code` )
    REFERENCES `brockbutler`.`courses` (`subj` , `code` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
