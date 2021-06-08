/*
link to diagram
https://github.com/DANDROZAVR/Food-app/blob/main/diagram.png
*/
drop table if exists recipes cascade;
drop table if exists products cascade;
drop table if exists products_tag cascade;
drop table if exists products_areatag cascade;
drop table if exists products_nutrient_main cascade;
drop table if exists products_nutrient_additional cascade;
drop table if exists products_vitamins cascade;

drop table if exists recipes_areatag cascade;
drop table if exists recipes_tag cascade;
drop table if exists recipes_content_products cascade;
drop table if exists recipes_content_recipes cascade;
drop table if exists recipes_nutrient_main cascade;
drop table if exists recipes_nutrient_additional cascade;

drop table if exists drinks_info cascade;
drop table if exists species_taste cascade;

drop type if exists prod_class_enum cascade;
drop type if exists species_taste_enum cascade;

drop table if exists restaurants_info cascade;
drop table if exists orders cascade;
drop table if exists restaurants_main cascade;
drop table if exists restaurants_group_meals cascade;
drop table if exists group_meals_content cascade;

drop table if exists restaurants_geoposition cascade;
drop table if exists restaurants_plan_weekdays;
drop table if exists restaurants_plan_saturday;
drop table if exists restaurants_plan_sunday;


drop table if exists shop_description cascade;
drop table if exists shops_main cascade;
drop table if exists shops_geoposition cascade;

drop table if exists shops_plan_weekdays;
drop table if exists shops_plan_saturday;
drop table if exists shops_plan_sunday;

drop table if exists shops_content_recipes cascade;
drop table if exists shops_content_products cascade;
drop table if exists shops_discounts_recipes cascade;
drop table if exists shops_discounts_products cascade;
drop table if exists shop_cards cascade;
drop table if exists shops_info cascade;
drop table if exists discounts cascade;

drop sequence if exists for_id_products cascade;
drop sequence if exists for_id_recipes cascade;
drop sequence if exists for_id_restaurants cascade;
drop sequence if exists for_id_shop cascade;


create type prod_class_enum as ENUM('Drinks', 'Solids', 'Species');
create type species_taste_enum as ENUM('Sweet', 'Salty', 'Bitter', 'Sour', 'Burning', 'Spicy');


create table products (
	id_prod integer constraint pk_prod primary key,
	product_group varchar(60) not null,
	product_class prod_class_enum not null,
	name varchar(30) not null,
	description varchar(400),
	calories numeric(5) not null check(calories >= 0)
);
create table products_areatag (
	id_prod integer constraint fk_prod_area references products(id_prod),
	area varchar(30) not null
);
create table products_tag (
	id_prod integer constraint fk_prod_area references products(id_prod),
	tag varchar(40) not null
);
create table drinks_info (	
	id_prod integer not null primary key constraint fk_prod_dk references products(id_prod),
	sugar boolean not null, 
	colour varchar(15)
);
create table species_taste (	
	id_prod integer not null primary key constraint fk_prod_ss references products(id_prod),
	taste species_taste_enum not null
);	

create table products_nutrient_main(
	id_prod integer not null unique constraint fk_nut_main references products(id_prod),
	fat smallint not null check(fat >= 0 AND fat <= 100),
	saturated_fat smallint check(saturated_fat is null or saturated_fat <= fat),
	protein smallint not null check(protein >= 0 AND protein <= 100), 
	carbo smallint not null check(carbo >= 0 AND carbo <= 100),
	sugar smallint check(sugar is null OR (sugar >= 0 AND sugar <= carbo))
	check(carbo + fat + protein <= 100)
);
create table products_nutrient_additional(
	id_prod integer not null unique constraint fk_nut_main references products(id_prod),
	zinc real default 0.00 check(zinc >= 0.00 AND zinc <= 100000.00),
	iron real default 0.00 check(iron >= 0.00 AND iron <= 100000.00), 
	calcium real default 0.00 check(calcium >= 0.00 AND iron <= 100000.00), 
	magnesium real default 0.00 check(magnesium >= 0.00 AND magnesium <= 100000.00)
);
create table products_vitamins(
	id_prod integer not null unique constraint fk_vit_main references products(id_prod),
	vitamin_A real default 0.00 check(vitamin_A >= 0.00 AND vitamin_A <= 100000.00),
	vitamin_B6 real default 0.00 check(vitamin_B6 >= 0.00 AND vitamin_B6 <= 100000.00),
	vitamin_B12 real default 0.00 check(vitamin_B12 >= 0.00 AND vitamin_B12 <= 100000.00),
	vitamin_C real default 0.00 check(vitamin_C >= 0.00 AND vitamin_C <= 100000.00),
	vitamin_E real default 0.00 check(vitamin_E >= 0.00 AND vitamin_E <= 100000.00),
	vitamin_K real default 0.00 check(vitamin_K >= 0.00 AND vitamin_K <= 100000.00)
	check (vitamin_A + vitamin_B6 + vitamin_B12 + vitamin_C + vitamin_E + vitamin_K <= 100000.00)
); 
create table recipes (
	id_rec integer constraint pk_reci primary key,
	name varchar(200) not null,
	prep_time smallint default 0 check(prep_time >= 0 AND prep_time <= 1000),
	sum_weight numeric(7) not null,
	sum_calories numeric(7) not null, 
	description varchar(1000), 
	instruction varchar(1500), 
	check(sum_weight >= 0),
	check(sum_calories >= 0)
);
--check for products
create table recipes_nutrient_main(
	id_rec integer not null unique constraint fk_nut_main references recipes(id_rec),
	fat smallint not null constraint fat1 check(fat >= 0 AND fat <= 100),
	protein smallint not null constraint protein1 check(protein >= 0 AND protein <= 100), 
	carbo smallint not null constraint carbo1 check(carbo >= 0 AND carbo <= 100),
	sugar smallint constraint sugar1 check(sugar is null OR (sugar >= 0 AND sugar <= carbo)),
	constraint sum_check1 check(carbo + fat + protein <= 100)
);
create table recipes_areatag (
	id integer constraint fk_rec_area references recipes(id_rec),
	area varchar(40) not null
);
create table recipes_tag (
	id integer constraint fk_rec_tag references recipes(id_rec),
	tag varchar(30) not null
);

create table recipes_content_products (
	id_rec integer not null constraint fk_rec_cont references recipes(id_rec),
	id integer not null constraint fk_prod references products(id_prod),
	weight numeric(6) not null check(weight >= 0),  
	weight_type char(4) not null check(weight_type in ('g', 'ml'))
);







/*


weight


*/


create table recipes_content_recipes (
	id_rec integer not null constraint fk_rec_cont references recipes(id_rec),
	id integer not null constraint fk_prod references recipes(id_rec) check(id != id_rec), 
	weight numeric(6) not null check(weight >= 0),  
	weight_type char(4) not null check(weight_type in ('g', 'ml'))
);
--check for cycle


create table restaurants_main(
        id integer not null primary key,
        "name" varchar(10) not null,
        geoposition varchar(30) not null, 
		adres varchar(100)
);
create table restaurants_info(
       id integer not null primary key constraint fk_shop_des references restaurants_main(id), 
       open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open)),
       stars integer check (stars <= 5 OR stars is null),
       description varchar(100),
       food_delivery boolean not null
);
create table restaurants_geoposition(
		id integer not null primary key references restaurants_main(id),	
		geoposition varchar(30) not null
);
create table restaurants_plan_weekdays(
	   id integer not null primary key constraint fk_shop_des references restaurants_main(id), 
	   open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open))
);
create table restaurants_plan_saturday(
	   id integer not null primary key constraint fk_shop_des references restaurants_main(id), 
	   open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open))
);
create table restaurants_plan_sunday(
	   id integer not null primary key constraint fk_shop_des references restaurants_main(id), 
	   open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open))
);
create table restaurants_group_meals(
       id_restaurant integer not null references restaurants_main(id),
       id_group integer not null primary key,
       cena numeric(10) not null, 
       min_cena numeric(10) not null,
       max_cena numeric(10) not null,
       constraint con_min_max check(max_cena >= min_cena)
);
create table group_meals_content(
       id_group integer not null references restaurants_group_meals(id_group),
       id_rec integer not null references recipes(id_rec)
);



create table shops_main(
        id integer not null primary key,
        "name" varchar(10) not null, 
		adres varchar(100)
);
create table shops_info(
       id integer not null primary key constraint fk_shop_des references shops_main(id), 
       description varchar(100),
       food_delivery boolean not null
);
create table shops_geoposition(
		id integer not null primary key references shops_main(id),	
		geoposition varchar(30) not null
);
create table shops_plan_weekdays(
	   id integer not null primary key constraint fk_shop_des references shops_main(id), 
	   open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open))
);
create table shops_plan_saturday(
	   id integer not null primary key constraint fk_shop_des references shops_main(id), 
	   open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open))
);
create table shops_plan_sunday(
	   id integer not null primary key constraint fk_shop_des references shops_main(id), 
	   open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open))
);

create table shops_content_products(
       id_shop integer not null references shops_main(id),
       id_prod integer not null references products(id_prod),
       cena numeric(10) not null,
       count integer not null check(count >= 1)
);
create table shops_content_recipes(
       id_shop integer not null references shops_main(id),
       id_rec integer not null references recipes(id_rec),
       cena numeric(10) not null,
       count integer not null check(count >= 1)
);
create table shops_discounts_recipes(
       id_shop integer not null references shops_main(id),
       id_rec integer not null references recipes(id_rec),
       discount_val numeric(4) not null check(discount_val >0 and discount_val<=100),
       min_count integer
);
create table shops_discounts_products(
       id_shop integer not null references shops_main(id),
       id_prod integer not null references products(id_prod),
       min_count integer
);
create table shop_cards(
       id_shop integer not null primary key references shops_main(id),
       is_accumulative boolean not null 
);
create table discounts(
       id_shop integer not null unique references shops_main(id),
       sum_for_discount numeric(10) not null,
       discount numeric check(discount >0 and discount<=100)
);
create table orders(
       id_order integer not null,
       id_restaurant integer not null references restaurants_main(id),
       id_rec integer not null references recipes(id_rec),
       date date not null,
       primary key(id_order,id_restaurant,id_rec,date)
);


create sequence for_id_products start with 1 increment by 2 maxvalue 100000;
create sequence for_id_recipes start with 2 increment by 2 maxvalue 100000;
create sequence for_id_shop start with 1 increment by 2 maxvalue 100000;
create sequence for_id_restaurants start with 2 increment by 2 maxvalue 100000;

/*

for diagram


*/

ALTER TABLE ONLY products_areatag
    ADD CONSTRAINT prod_area_ee FOREIGN KEY (id_prod) references products(id_prod);
ALTER TABLE ONLY recipes_tag
    ADD CONSTRAINT rec_tag FOREIGN KEY (id) references recipes(id_rec);	
/*ALTER TABLE ONLY group_meals_content
    ADD CONSTRAINT fs1ese FOREIGN KEY (id_group) references restaurants_group_meals(id_group);*/
ALTER TABLE ONLY restaurants_group_meals
    ADD CONSTRAINT fsese2222 FOREIGN KEY (id_restaurant) references restaurants_main(id);
/*ALTER TABLE ONLY group_meals_content
    ADD CONSTRAINT fses2e FOREIGN KEY (id_rec) references recipes(id_rec);*/
ALTER TABLE ONLY recipes_areatag
    ADD CONSTRAINT rec_area_ee2 FOREIGN KEY (id) references recipes(id_rec);
ALTER TABLE ONLY discounts
    ADD CONSTRAINT disc_123 FOREIGN KEY (id_shop) references shops_main(id);
ALTER TABLE ONLY shop_cards
    ADD CONSTRAINT disc_1223 FOREIGN KEY (id_shop) references shops_main(id);
ALTER TABLE ONLY shops_discounts_products
    ADD CONSTRAINT disc_12253 FOREIGN KEY (id_shop) references shops_main(id);
ALTER TABLE ONLY shops_discounts_products
    ADD CONSTRAINT disc_12233 FOREIGN KEY (id_prod) references products(id_prod);
ALTER TABLE ONLY shops_discounts_recipes
    ADD CONSTRAINT daisc_1 FOREIGN KEY (id_shop) references shops_main(id);
ALTER TABLE ONLY shops_discounts_recipes
    ADD CONSTRAINT daisc_12 FOREIGN KEY (id_rec) references recipes(id_rec);
ALTER TABLE ONLY shops_content_recipes
    ADD CONSTRAINT da2isc_1 FOREIGN KEY (id_shop) references shops_main(id);	
/*ALTER TABLE ONLY shops_content_recipes
    ADD CONSTRAINT daisc_12 FOREIGN KEY (id_rec) references recipes(id_rec);*/
ALTER TABLE ONLY shops_content_products
    ADD CONSTRAINT dai23sc_12 FOREIGN KEY (id_shop)references shops_main(id);		
ALTER TABLE ONLY shops_content_products
    ADD CONSTRAINT dai23sc_122 FOREIGN KEY (id_prod)references products(id_prod);	
/*ALTER TABLE ONLY recipes_content
    ADD CONSTRAINT fk_rec_coewnt FOREIGN KEY (id_rec) references recipes(id_rec);*/	
ALTER TABLE ONLY species_taste
    ADD CONSTRAINT fk_proed_ss FOREIGN KEY (id_prod) references products(id_prod);		
ALTER TABLE ONLY drinks_info
    ADD CONSTRAINT fk_pwrod_dk FOREIGN KEY (id_prod) references products(id_prod);	
ALTER TABLE ONLY shops_info
    ADD CONSTRAINT fk_shoep_des FOREIGN KEY (id) references shops_main(id);	
	
create or replace function getProductAreaTags(item integer)
	returns varchar as 
$$
	begin
		return (select array_agg(area) 
		from products_areatag
		where id_prod = item);
	end;
$$ language plpgsql;

create or replace function getProductTags(item integer)
	returns varchar as 
$$
	begin
		return (select array_agg(tag) 
		from products_tag
		where id_prod = item);
	end;
$$ language plpgsql;

create or replace function getRecipeContentProducts(id_rec_find integer)
	returns varchar as 
$$
	begin
		return (select array_agg(array[id::varchar, weight::varchar, weight_type]) 
		from recipes_content_products
		where id_rec = id_rec_find);
	end;
$$ language plpgsql;

create or replace function getRecipeContentRecipes(id_rec_find integer)
	returns varchar as 
$$
	begin
		return (select array_agg(array[id::varchar, weight::varchar, weight_type]) 
		from recipes_content_recipes
		where id_rec = id_rec_find);
	end;
$$ language plpgsql;

create or replace view Species as
	select * from species_taste
	natural join products;

create or replace function spec_insert(i record)
	returns void as
$$
	begin
		INSERT INTO PRODUCTS(id_prod, product_group, product_class, name, description, calories)
			values (i.id_prod, i.product_group, i.product_class, i.name, i.description, i.calories);
		INSERT INTO SPECIES_TASTE(id_prod, taste)
			values (i.id_prod, i.taste);
	end;
$$ language plpgsql;

create or replace rule spec_insert as
on insert to Species
do instead(
	select spec_insert(new);
);


create or replace view solids_full(id_prod, product_group, product_class, name, description, calories, fat, saturated_fat, protein, carbo, sugar, zinc, iron, calcium, magnesium, vitamin_A, vitamin_B6, vitamin_B12, vitamin_C, vitamin_E, vitamin_K)
	as
	select * from products 
	natural join products_nutrient_main
	natural join products_nutrient_additional
	natural join products_vitamins;	

create or replace function solids_full_insert(i record)
	returns void as
$$
	begin
		INSERT INTO PRODUCTS
			values (i.id_prod, i.product_group, i.product_class, i.name, i.description, i.calories);
		INSERT INTO products_nutrient_main
			values (i.id_prod, i.fat, i.saturated_fat, i.protein, i.carbo, i.sugar);
		INSERT INTO products_nutrient_additional
			values (i.id_prod, i.zinc, i.iron, i.calcium, i.magnesium);
		INSERT INTO products_vitamins
			values (i.id_prod, i.vitamin_A, i.vitamin_B6, i.vitamin_B12, i.vitamin_C, i.vitamin_E, i.vitamin_K);
	end;
$$ language plpgsql;

create or replace rule spec_insert as
on insert to solids_full
do instead(
	select solids_full_insert(new);
);
	


/*



inserts


*/
	
insert into products(id_prod, product_group, product_class, name, description, calories)
	values
(nextval('for_id_products'), 'fruits', 'Solids', 'apple', 'A very useful product, pleasant', 47),
(nextval('for_id_products'), 'fruits', 'Solids', 'orange', 'Orange has an orange colour', 43),
(nextval('for_id_products'), 'meat', 'Solids', 'beef', 'One of the most common types of meat', 187),
(nextval('for_id_products'), 'minerals', 'Species', 'salt', 'An indispensable product both in the Middle Ages and today', 0);

insert into products_areatag(id_prod, area)
	values
(1, 'Polska'),
(1, 'Włochy'),
(7, 'Worldwide');

insert into products_tag(id_prod, tag)
	values
(1, 'Healthy food');

insert into species_taste(id_prod, taste)
	values 
(7, 'Salty');	

insert into restaurants_main(id,name,geoposition,adres) values (10, 'Andrew', 'CS', 'Dust');
--insert into recipes(id_rec, name, sum_weight,sum_calories,description,links) values (12,'sdfgsdgs',14,15,'csdfgsd','link');
insert into restaurants_group_meals(id_restaurant,id_group,cena,min_cena,max_cena) values (10,5,3424,1213,4535);
insert into group_meals_content(id_group,id_rec) values (5,12);