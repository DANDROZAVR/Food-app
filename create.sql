drop table if exists products cascade;
create table products (
	id_prod numeric(5) constraint pk_prod primary key,
	product_type varchar(10) not null,
	name varchar(20) not null,
	description varchar(100), 
	area varchar(40)
);
drop table if exists receipts cascade;
create table receipts (
	id_rec numeric(5) constraint pk_rec primary key,
	id_prod varchar(10) not null,
	weight numeric(6), 
	weight_type numeric(5) --add g/ml check
);
