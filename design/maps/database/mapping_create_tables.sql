SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `brockbutler`.`node_information`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`node_information` (
  `node_number` VARCHAR(10) NOT NULL ,
  `node_name` VARCHAR(30) NOT NULL ,
  `x_position` INT NOT NULL ,
  `y_position` INT NOT NULL ,
  PRIMARY KEY (`node_number`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `brockbutler`.`node_data`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `brockbutler`.`node_data` (
  `node_number` VARCHAR(10) NOT NULL ,
  `mac_address` VARCHAR(20) NOT NULL ,
  `signal_strength` INT NOT NULL ,
  `frequency` INT NULL ,
  INDEX `fk_node_data_idx` (`node_number` ASC) ,
  CONSTRAINT `fk_node_data`
    FOREIGN KEY (`node_number`)
    REFERENCES `brockbutler`.`node_information` (`node_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;