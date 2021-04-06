drop table if exists products cascade;
create table products (
	id_prod numeric(5) constraint pk_prod primary key,
	product_type varchar(10) not null,
	name varchar(20) not null,
	description varchar(200),  --optional
	area varchar(200), --optional
	calories numeric(5) not null
	
	check(calories >= 0)
);
drop table if exists receipts cascade;
create table receipts (
	id_rec numeric(5) constraint pk_rec primary key,
	name varchar(30) not null,
	sum_weight numeric(7) not null,
	sum_calories numeric(7) not null, 
	description varchar(300), --optional
	links varchar(300) --optional
	
	check(sum_weight >= 0),
	check(sum_calories >= 0)
);
drop table if exists receipts_content cascade;
create table receipts_content (
	id_rec numeric(5) not null constraint fk_rec_cont references receipts(id_rec),
	id_prod numeric(5) not null constraint fk_prod references products(id_prod),
	weight numeric(6) not null, 
	weight_type char(4) not null --add g/ml check
	
	check(weight >= 0),
	check(weight_type in ('g', 'ml'))
);
	

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
	