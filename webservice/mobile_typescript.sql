
SET FOREIGN_KEY_CHECKS = 0;
-- clear
DROP TABLE IF EXISTS tblaccount;
DROP TABLE IF EXISTS tblpackage;
drop TRIGGER if EXISTS trigger_insert_tblaccount



CREATE TABLE IF NOT EXISTS  tblaccount(
	`id` int not null AUTO_INCREMENT,
	`username` varchar(255),
	`fullname` varchar(255),
	`email` varchar(255)	,
	`password` varchar(255),
	PRIMARY KEY (id)
	
);


DROP TRIGGER IF EXISTS `trigger_insert_tblaccount`;
delimiter ;;
CREATE TRIGGER `trigger_insert_tblaccount` BEFORE INSERT ON `tblaccount` FOR EACH ROW BEGIN 
	  IF ((EXISTS (SELECT * FROM tblaccount where new.username = username )) or (EXISTS (SELECT * FROM tblaccount where new.email = email ))) 	THEN
					SIGNAL SQLSTATE '45001' SET MESSAGE_TEXT = 'Not insert, username is exists';				
		end if;
END
;;
delimiter ;


		
INSERT into
tblaccount(id,username, fullname, email, password) values (null,"abc123", "123","tanhoang99.999@gmail.com","aA@123");


CREATE TABLE IF NOT EXISTS  tblpackage(
	id int not null AUTO_INCREMENT,
	id_account int not null,
	color varchar(255),
	title varchar(255),
	last_edit datetime,
	PRIMARY KEY (id) ,
	FOREIGN KEY(id_account) REFERENCES tblaccount(id)
);
-- INSERT into tblpackage(id,id_account, color, title, last_edit) values (null,"1","color_blue", "title","2009-01-10 18:38:02");
SET FOREIGN_KEY_CHECKS = 1;