/*
link to diagram
https://github.com/DANDROZAVR/Food-app/blob/main/diagram.png
*/
drop table if exists recipes cascade;
drop table if exists products cascade;
drop table if exists products_tag cascade;
drop table if exists products_areatag cascade;
drop table if exists recipes_areatag cascade;
drop table if exists recipes_tag cascade;
drop table if exists recipes_content cascade;

drop table if exists drinks_taste cascade;
drop table if exists species_taste cascade;

drop type if exists prod_class_enum cascade;
drop type if exists species_taste_enum cascade;


drop table if exists restaurants_info cascade;
drop table if exists restaurants_main cascade;
drop table if exists restaurants_group_meals cascade;
drop table if exists group_meals_content cascade;
	

drop table if exists shop_description cascade;
drop table if exists shops_main cascade;
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
create type species_taste_enum as ENUM('Sweet', 'Salty', 'Bitter', 'Sour');


create table products (
	id_prod integer constraint pk_prod primary key,
	product_group varchar(10) not null, 
	product_class prod_class_enum not null,
	name varchar(20) not null,
	description varchar(200),
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


create table recipes (
	id_rec integer constraint pk_reci primary key,
	name varchar(30) not null,
	sum_weight numeric(7) not null,
	sum_calories numeric(7) not null, 
	description varchar(300), --optional
	links varchar(300) --optional	
	check(sum_weight >= 0),
	check(sum_calories >= 0)
);
create table recipes_areatag (
	id integer constraint fk_rec_area references recipes(id_rec),
	area varchar(40) not null
);
create table recipes_tag (
	id integer constraint fk_rec_tag references recipes(id_rec),
	tag varchar(30) not null
);
create table recipes_content (
	id_rec integer not null constraint fk_rec_cont references recipes(id_rec),
	id_prod integer not null constraint fk_prod references products(id_prod),
	weight numeric(6) not null check(weight >= 0),  
	weight_type char(4) not null check(weight_type in ('g', 'ml'))
);




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
        geoposition varchar(30) not null, 
		adres varchar(100)
);
create table shops_info(
       id integer not null primary key constraint fk_shop_des references shops_main(id), 
       open time,
       close time
       constraint con_open_close check((open is null and close is null) or (close>open)),
       description varchar(100),
       food_delivery boolean not null
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


create sequence for_id_products start with 1 increment by 2 maxvalue 100000;
create sequence for_id_recipes start with 2 increment by 2 maxvalue 100000;
create sequence for_id_shop start with 1 increment by 2 maxvalue 100000;
create sequence for_id_restaurants start with 2 increment by 2 maxvalue 100000;


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
ALTER TABLE ONLY drinks_taste
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

	
	
/*ALTER TABLE ONLY restaurants_info
    ADD CONSTRAINT fk_shop_des2 FOREIGN KEY (id) references restaurants_main(id);*/
	
/*
Przykład logiki produktów i przepisów
INSERT INTO
	products(id_prod, product_type, name, description, area, calories)
VALUES
	(2, 'fruits', 'apple', 'A very useful product', 'Worldwide', 47),
	(4, 'fruits', 'orange', 'Orange has an orange colour', 'South and North America, China, Italy, Iran, Egypt', 43),
	(6, 'meat', 'beef', 'One of the most common types of meat', 'Worldwide', 187),
	(8, 'meat', 'chiken', 'Meat that contains a lot of protein', 'Worldwide', 239);
INSERT INTO
	recipes(id_rec, name, sum_weight, sum_calories) 
VALUES
	(1, 'Simple fruit salad', 300, 137);
	
INSERT INTO
	recipes_content(id_rec, id_prod, weight, weight_type)
VALUES
	(1, 2, 200, 'g'),
	(1, 4, 100, 'g');
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
