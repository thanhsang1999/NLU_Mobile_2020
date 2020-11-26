--clear
DROP TABLE IF EXISTS tblaccount;
drop TRIGGER if EXISTS trigger_insert_tblaccount




CREATE TABLE IF NOT EXISTS  tblaccount(
	
	username varchar(255),
	fullname varchar(255),
	email varchar(255)	PRIMARY KEY,
	password varchar(255)
	
);


CREATE TRIGGER IF NOT EXISTS trigger_insert_tblaccount BEFORE INSERT ON tblaccount FOR EACH ROW
BEGIN
	 IF (EXISTS (SELECT * FROM tblaccount where new.username = username )) THEN		
					SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Not insert, username is exists';				
		end if;
END
 
		
INSERT into
tblaccount(username, fullname, email, password) values ("1", "1","1","1");
