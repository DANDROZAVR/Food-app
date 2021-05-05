drop table if exists receipts cascade;
drop table if exists products cascade;
drop table if exists solids cascade;
drop table if exists drinks cascade;
drop table if exists species cascade;
drop table if exists solids_taste cascade;
drop table if exists drinks_taste cascade;
drop table if exists species_taste cascade;
drop type if exists prod_class_enum cascade;
drop type if exists solid_taste_enum cascade;
drop type if exists species_taste_enum cascade;
drop table if exists receipts_content cascade;
drop table if exists shops cascade;
drop table if exists shop_description cascade;

create type prod_class_enum as ENUM('drinks', 'solid', 'species');
create type solid_taste_enum as ENUM('Sweet', 'Sour', 'Salty', 'Bitter', 'None');
create type species_taste_enum as ENUM('Sweet', 'Salty', 'Bitter', 'Species');
create type restaurant_shop_choice as ENUM('Restaurant', 'Shop');

create table products (
	id_prod numeric(7) constraint pk_prod primary key,
	product_group varchar(10) not null, 
	product_class prod_class_enum not null,
	name varchar(20) not null,
	description varchar(200),  --optional
	area varchar(200), --optional
	calories numeric(5) not null check(calories >= 0)
);
create table receipts (
	id_rec numeric(7) constraint pk_rec primary key,
	name varchar(30) not null,
	sum_weight numeric(7) not null,
	sum_calories numeric(7) not null, 
	description varchar(300), --optional
	links varchar(300) --optional	
	check(sum_weight >= 0),
	check(sum_calories >= 0)
);

create table solids_taste (	
	id numeric(7) not null constraint fk_prod_sd references products(id_prod),
	taste solid_taste_enum
);
create table drinks_taste (	
	id numeric(7) not null constraint fk_prod_dk references products(id_prod),
	sugar boolean not null, 
	colour varchar(15)
);
create table species_taste (	
	id numeric(7) not null constraint fk_prod_ss references products(id_prod),
	taste species_taste_enum
);

create table receipts_content (
	id_rec numeric(7) not null constraint fk_rec_cont references receipts(id_rec),
	id_prod numeric(7) not null constraint fk_prod references products(id_prod),
	weight numeric(6) not null, 
	weight_type char(4) not null --add g/ml check
	
	check(weight >= 0),
	check(weight_type in ('g', 'ml'))
);
	
create table shops(
	id numeric(7) not null primary key,
	"name" varchar(40) not null,
	geoposition varchar(30) not null, 
	adres varchar(100)
);

create table shop_description(
	id numeric(7) constraint fk_shop_des references shops(id),
	working_time_start time not null,
	working_time_end time not null,
	stars numeric(1) check (stars <= 5 OR stars is null),
	description varchar(100),
	delivers_food boolean,
	building_type restaurant_shop_choice
);

create table shop_goods(
	id_shop numeric(7) not null constraint fk_shop_gs references shops(id_shop),
	id_item not null
)

/*
INSERT INTO
	products(id_prod, product_type, name, description, area, calories)
VALUES
	(2, 'fruits', 'apple', 'A very useful product', 'Worldwide', 47),
	(4, 'fruits', 'orange', 'Orange has an orange colour', 'South and North America, China, Italy, Iran, Egypt', 43),
	(6, 'meat', 'beef', 'One of the most common types of meat', 'Worldwide', 187),
	(8, 'meat', 'chiken', 'Meat that contains a lot of protein', 'Worldwide', 239);

INSERT INTO
	receipts(id_rec, name, sum_weight, sum_calories) 
VALUES
	(1, 'Simple fruit salad', 300, 137);
	
INSERT INTO
	receipts_content(id_rec, id_prod, weight, weight_type)
VALUES
	(1, 2, 200, 'g'),
	(1, 4, 100, 'g');
*/