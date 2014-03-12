DROP TABLE IF EXISTS REPORT;

CREATE TABLE REPORT  (
	ID BIGINT (20),  
	SALES DECIMAL(4,2) ,
	QTY INT (10),
	STAFF_NAME VARCHAR(30),
	DATE DATETIME
) ;

INSERT INTO `REPORT` (`ID`, `SALES`, `QTY`, `STAFF_NAME`, `DATE`) VALUES('4','1.56','10','cowboy76','2014-03-12 12:11:01');
INSERT INTO `REPORT` (`ID`, `SALES`, `QTY`, `STAFF_NAME`, `DATE`) VALUES('2','2.40','15','cowboy95','2014-03-04 16:11:35');
INSERT INTO `REPORT` (`ID`, `SALES`, `QTY`, `STAFF_NAME`, `DATE`) VALUES('3','3.50','20','cowboy77','2014-03-12 16:11:57');
