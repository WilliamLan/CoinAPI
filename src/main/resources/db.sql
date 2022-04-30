CREATE TABLE currency (
	id INT NOT NULL PRIMARY KEY auto_increment, 
	code VARCHAR(20) NOT NULL, 
	name VARCHAR(80) NOT NULL, 
	rate double not null,
	constraint code UNIQUE (code)
);

insert into currency (code,name,rate) values ('USD','美金',38633.5733);
insert into currency (code,name,rate) values ('GBP','英鎊',30727.2696);
insert into currency (code,name,rate) values ('EUR','歐元',36642.6603);