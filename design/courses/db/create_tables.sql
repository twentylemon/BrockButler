SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `brockbutler`.`courses`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`courses` (
  `subj` CHAR(4) NOT NULL ,
  `code` CHAR(4) NOT NULL ,
  `desc` VARCHAR(45) NULL ,
  `instructor` VARCHAR(45) NULL ,
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
  `timestart` CHAR(4) NULL ,
  `timeend` CHAR(4) NULL ,
  `location` VARCHAR(16) NULL ,
  PRIMARY KEY (`id`, `day`) ,
  INDEX `fk_offering_times_offerings1_idx` (`id` ASC) ,
  CONSTRAINT `fk_offering_times_offerings1`
    FOREIGN KEY (`id` )
    REFERENCES `brockbutler`.`offerings` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
