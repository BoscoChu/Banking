DROP USER if exists 'root'@'localhost' ;

CREATE USER 'root'@'localhost' IDENTIFIED BY '';

GRANT ALL PRIVILEGES ON * . * TO 'root'@'localhost';









CREATE DATABASE  IF NOT EXISTS `bank`;
USE `bank`;


-- Table structure for table `user`
--



CREATE TABLE `user` (`Name` VARCHAR(50) NOT NULL , 
`ID` VARCHAR(50) NOT NULL , `MobileNo` VARCHAR(50) NOT NULL ,
 `Gender` VARCHAR(50) NOT NULL , `MartialStatus` VARCHAR(50) NOT NULL , 
 `Nationality` VARCHAR(50) NOT NULL , `DOB` VARCHAR(50) NOT NULL , `Address` VARCHAR(50) NOT NULL ,
 `AccountNo` VARCHAR(50) NOT NULL , `PIN` VARCHAR(50) NOT NULL , `AccountType` VARCHAR(50) NOT NULL , 
 `Balance` VARCHAR(50) NOT NULL , `SecurityQuestion` VARCHAR(50) NOT NULL , `Answer` VARCHAR(50) NOT NULL , 
 `ProfilePic` LONGBLOB NOT NULL ) ENGINE = InnoDB;


--
-- Table structure for table `withdraw ``
--

CREATE TABLE `bank`.`withdraw` (`AccountNo` VARCHAR(50) NOT NULL , 
`WithdrawAmount` VARCHAR(50) NOT NULL , `RemainingAmount` VARCHAR(50) NOT NULL , 
`Date` VARCHAR(50) NOT NULL , `Time` VARCHAR(50) NOT NULL  ) ENGINE = InnoDB;

--
-- Table structure for table `transfer ``
--
CREATE TABLE `bank`.`transfer` (`AccountNo` VARCHAR(50) NOT NULL , `TransferAmount` VARCHAR(50) NOT NULL , 
`Sendto` VARCHAR(50) NOT NULL , `Date` VARCHAR(50) NOT NULL ,
 `Time` VARCHAR(50) NOT NULL  ) ENGINE = InnoDB;



--
-- Table structure for table `receive``
--

CREATE TABLE `bank`.`receive` (`AccountNo` VARCHAR(50) NOT NULL , `TransferAmount` VARCHAR(50) NOT NULL , 
`ReceivefFrom` VARCHAR(50) NOT NULL , `Date` VARCHAR(50) NOT NULL , 
`Time` VARCHAR(50) NOT NULL  ) ENGINE = InnoDB;





--
-- Table structure for table `deposit``
--


CREATE TABLE `bank`.`deposit` (`AccountNo` VARCHAR(50) NOT NULL , `DepositAmount` VARCHAR(50) NOT NULL , 
`NewAmount` VARCHAR(50) NOT NULL , `Date` VARCHAR(50) NOT NULL , 
`Time` VARCHAR(50) NOT NULL  ) ENGINE = InnoDB;

