CREATE SCHEMA IF NOT EXISTS `BANK_SYSTEM` DEFAULT CHARACTER SET utf8;
USE `BANK_SYSTEM`;
CREATE TABLE Person
(
IDPerson INT NOT NULL AUTO_INCREMENT,
FirstName VARCHAR(25) NOT NULL,
LastName VARCHAR(25) NOT NULL,
Nationality VARCHAR(20) NOT NULL,
Country VARCHAR(20) NOT NULL,
Email VARCHAR(30) NOT NULL,
PRIMARY KEY (IDPerson)
)ENGINE = InnoDB AUTO_INCREMENT=11111 ;

CREATE TABLE Location
(
IDLocation INT NOT NULL AUTO_INCREMENT,
Country VARCHAR(25) NOT NULL,
City VARCHAR(20) NOT NULL,
Street VARCHAR(30) NOT NULL,
  PRIMARY KEY (IDLocation)
)ENGINE = InnoDB AUTO_INCREMENT=1000;

CREATE TABLE Bank
(
IDBank INT NOT NULL AUTO_INCREMENT,
BankName VARCHAR(25) NOT NULL,
IDLocation INT NOT NULL,
PRIMARY KEY (IDBank, BankName),
CONSTRAINT  FOREIGN KEY (IDLocation)
    REFERENCES  Location (IDLocation)
)ENGINE = InnoDB AUTO_INCREMENT=12222;

CREATE TABLE Loan
(
IDLoan INT NOT NULL AUTO_INCREMENT,
IDBank INT NOT NULL,
IDClient INT NOT NULL,
Amount INT NOT NULL,
Percent DOUBLE NOT NULL,
Currency VARCHAR(10) NOT NULL,
Date_Issue DATE NOT NULL,
Date_Return DATE NOT NULL,
PRIMARY KEY (IDLoan),
CONSTRAINT  FOREIGN KEY (IDClient)
    REFERENCES  Person (IDPerson),
CONSTRAINT   FOREIGN KEY (IDClient)
    REFERENCES  Bank (IDBank),
CONSTRAINT   FOREIGN KEY (IDBank)
    REFERENCES  Bank (IDBank)
)ENGINE = InnoDB AUTO_INCREMENT=33331;

CREATE TABLE Deposit (
    IDDeposit INT NOT NULL AUTO_INCREMENT,
    IDBank INT NOT NULL,
    IDClient INT NOT NULL,
    Amount INT NOT NULL,
    Percent DOUBLE NOT NULL,
    Currency VARCHAR(10) NOT NULL,
    Date_Issue DATE NOT NULL,
    Date_Return DATE NOT NULL,
    PRIMARY KEY (IDDeposit),
    CONSTRAINT fk_deposit_person FOREIGN KEY (IDClient)
        REFERENCES Person(IDPerson),
    CONSTRAINT fk_deposit_bank FOREIGN KEY (IDClient)
        REFERENCES Bank(IDBank),
    CONSTRAINT FOREIGN KEY (IDBank)
        REFERENCES Bank (IDBank)
)  ENGINE=INNODB AUTO_INCREMENT=44441;


CREATE TABLE Investment
(
IDInvestment INT NOT NULL AUTO_INCREMENT,
IDBank INT NOT NULL,
IDClient INT NOT NULL,
Amount INT NOT NULL,
Percent DOUBLE NOT NULL,
Currency VARCHAR(10) NOT NULL,
Date_Issue DATE NOT NULL,
Date_Return DATE NOT NULL,
PRIMARY KEY (IDInvestment),
CONSTRAINT  FOREIGN KEY (IDClient)
    REFERENCES  Person (IDPerson),
CONSTRAINT   FOREIGN KEY (IDClient )
    REFERENCES  Bank (IDBank),
CONSTRAINT   FOREIGN KEY (IDBank)
    REFERENCES  Bank (IDBank)
)ENGINE = InnoDB AUTO_INCREMENT=55501;



DELIMITER //
CREATE PROCEDURE InsertPerson
(
IN FirstNamePersonIn varchar(25),
IN LastNameIn varchar(45)
)
BEGIN
	DECLARE msg varchar(40);
    
  -- checks for present 
  IF NOT EXISTS( SELECT * FROM Person WHERE FirstName=FirstNamePersonIn AND LastName=LastNameIn)
  THEN SET msg = 'This Person is absent';
  
    ELSEIF EXISTS( SELECT * FROM Person WHERE FirstName=FirstNamePersonIn AND LastName=LastNameIn)
  THEN SET msg = 'This Person already exists';

	END IF;

	SELECT msg AS msg;

END //

CREATE PROCEDURE InsertBank
(
IN BankNameIn varchar(25)
)
BEGIN
	DECLARE msg varchar(40);
	IF EXISTS( SELECT * FROM Bank WHERE BankName=BankNameIn)
	THEN SET msg = 'This bank already exists';
    ELSEIF NOT EXISTS(SELECT * FROM Bank WHERE BankName=BankNameIn)
    THEN SET msg = 'This bank is absent';
	END IF;
	SELECT msg AS msg;
END //

CREATE PROCEDURE TakeLoan
(
IN IDClientIN INT,
IN IDBankIN INT
)
BEGIN
	DECLARE msg varchar(60);
  -- checks if there are this combination already
	IF EXISTS( SELECT * FROM Loan l
		WHERE l.IDBank =IDBankIN AND l.IDClient = (SELECT IDPerson FROM Person WHERE IDPerson=IDClientIN) OR l.IDClient=(SELECT IDBank FROM Bank WHERE IDBank=IDClientIN))
       THEN SET msg = 'This Client already has LOAN in this Bank';
	ELSEIF NOT EXISTS( SELECT * FROM Loan l
		WHERE l.IDBank =IDBankIN AND l.IDClient = (SELECT IDPerson FROM Person WHERE IDPerson=IDClientIN) OR l.IDClient=(SELECT IDBank FROM Bank WHERE IDBank=IDClientIN))
	THEN SET msg = 'This Client can take LOAN in this Bank';
    END IF;
	SELECT msg AS msg;
END //


CREATE PROCEDURE LocationStatus
(
IN IDLocationIN INT
)
BEGIN
	DECLARE msg varchar(40);
  -- checks if Location FREE
   IF EXISTS( SELECT * FROM Bank WHERE IDLocation=IDLocationIn)
  THEN SET msg = 'This Location already has Bank';
  ELSEIF NOT EXISTS( SELECT * FROM Bank WHERE IDLocation=IDLocationIn)
  THEN SET msg = 'This Location FREE';

	END IF;
	SELECT msg AS msg;
END //
CREATE PROCEDURE LocationExists
(
IN CountryIN VARCHAR(20),
IN CityIN VARCHAR(20),
IN StreetIN VARCHAR(20)
)
BEGIN
	DECLARE msg varchar(50);
  -- checks if Location FREE
   IF EXISTS( SELECT * FROM Location WHERE  Country=CountryIN AND City=CityIN AND Street=StreetIN)
  THEN SET msg = 'This Location already exists';
  ELSEIF NOT EXISTS( SELECT * FROM Location WHERE  Country=CountryIN AND City=CityIN AND Street=StreetIN)
  THEN SET msg = 'This Location absent';
	END IF;
	SELECT msg AS msg;
END //
DELIMITER ;
SET FOREIGN_KEY_CHECKS=1;





