\i clear.sql


create type prod_class_enum as ENUM('Drinks', 'Solids', 'Species');
create type species_taste_enum as ENUM('Sweet', 'Salty', 'Bitter', 'Sour', 'Burning', 'Spicy');
create sequence id_for_tag start with 1 increment by 1;

create or replace function good(tag varchar)
	returns boolean as
$$
	declare
		i integer;
		lst char;
		c char;
	begin
		lst=',';
		for i in 0..length(tag)-1 loop
			c = (select substring(tag, i + 1, 1)::char);		
			if ((c >= 'a' and c <= 'z') = false and (c = '-') = false) then
				return false;
			end if;
			if (c = '-' and lst = '-') then
				return false;
			end if;
			lst = c;
		end loop;
		return true;
	end;
$$ language plpgsql;


create table products (
                          id_prod integer constraint pk_prod primary key,
                          product_group varchar(60) not null,
                          product_class prod_class_enum not null,
                          name varchar(30) unique not null,
                          description varchar(400),
                          calories numeric(5) not null check(calories >= 0)
);
create table products_tags(
	id_prod int not null references products(id_prod),
	id_tag int not null references tags(id_tag),
	primary key(id_prod, id_tag)
);
create table drinks_info (
                             id_prod integer not null primary key constraint fk_prod_dk references products(id_prod),
                             sugar boolean not null,
                             colour varchar(15)
);
create table products_nutrient(
                                  id_prod integer not null unique constraint fk_nut_main references products(id_prod),
                                  fat smallint not null check(fat >= 0 AND fat <= 100),
                                  saturated_fat smallint default 0.00 check(saturated_fat is null or saturated_fat <= fat),
                                  protein smallint not null check(protein >= 0 AND protein <= 100),
                                  carbo smallint not null check(carbo >= 0 AND carbo <= 100),
                                  sugar_total smallint check(sugar_total is null OR (sugar_total >= 0 AND sugar_total <= carbo)),
                                  zinc real default 0.00 check(zinc >= 0.00 AND zinc <= 100000.00),
                                  iron real default 0.00 check(iron >= 0.00 AND iron <= 100000.00),
                                  calcium real default 0.00 check(calcium >= 0.00 AND iron <= 100000.00),
                                  magnesium real default 0.00 check(magnesium >= 0.00 AND magnesium <= 100000.00),
                                  vitamin_A real default 0.00 check(vitamin_A >= 0.00 AND vitamin_A <= 100000.00),
                                  vitamin_B6 real default 0.00 check(vitamin_B6 >= 0.00 AND vitamin_B6 <= 100000.00),
                                  vitamin_B12 real default 0.00 check(vitamin_B12 >= 0.00 AND vitamin_B12 <= 100000.00),
                                  vitamin_C real default 0.00 check(vitamin_C >= 0.00 AND vitamin_C <= 100000.00),
                                  vitamin_E real default 0.00 check(vitamin_E >= 0.00 AND vitamin_E <= 100000.00),
                                  vitamin_K real default 0.00 check(vitamin_K >= 0.00 AND vitamin_K <= 100000.00)
                                      check (vitamin_A + vitamin_B6 + vitamin_B12 + vitamin_C + vitamin_E + vitamin_K <= 100000.00),
                                  check(carbo + fat + protein <= 100),
                                  taste species_taste_enum default null
);
create table recipes (
                         id_rec integer constraint pk_reci primary key,
                         name varchar(200) not null,
                         prep_time smallint default 0 check(prep_time >= 0 AND prep_time <= 30000),
                         calories numeric(7) not null,
                         description varchar(1000),
                         instruction varchar(1500),
                         check(calories >= 0)
);
create table recipes_tags(
	id_rec int not null references recipes(id_rec),
	id_tag int not null references tags(id_tag),
	primary key(id_rec, id_tag)
);

create table recipes_content_products (
                                          id_rec integer not null constraint fk_rec_cont references recipes(id_rec),
                                          id integer not null constraint fk_prod references products(id_prod),
                                          weight integer not null check(weight >= 0),
                                          weight_type char(2) not null check(weight_type in ('g', 'ml'))
);
create table recipes_content_recipes (
                                         id_rec integer not null constraint fk_rec_cont references recipes(id_rec),
                                         id integer not null constraint fk_prod references recipes(id_rec) check(id != id_rec),
                                         weight integer not null check(weight >= 0),
                                         weight_type char(2) not null check(weight_type in ('g', 'ml'))
);


create table restaurants_main(
                                 id integer not null primary key,
                                 "name" varchar(50) not null,
                                 address varchar(100),
                                 geoposition point,
                                 open_weekdays time,
                                 close_weekdays time,
                                 open_saturday time,
                                 close_saturday time,
                                 open_sunday time,
                                 close_sunday time,
                                 stars integer check ((stars >= 0 and stars <= 5) OR stars is null),
                                 description varchar(100),
                                 food_delivery boolean not null
);

create table restaurants_group_meals(
                                        id_restaurant integer not null references restaurants_main(id),
                                        id_group integer not null primary key,
                                        price numeric(10) not null
);
create table group_meals_content(
                                    id_group integer not null references restaurants_group_meals(id_group),
                                    id_rec integer not null references recipes(id_rec)
);
create table tags(
	id_tag int not null default nextval('id_for_tag') primary key,
	tag varchar(50) check(good(tag))
);

create table shops_main(
                           id integer not null primary key,
                           "name" varchar(50) not null,
                           address varchar(100),
                           geoposition point,
                           open_weekdays time,
                           close_weekdays time,
                           open_saturday time,
                           close_saturday time,
                           open_sunday time,
                           close_sunday time,
                           stars integer check ((stars >= 0 and stars <= 5) OR stars is null),
                           description varchar(100),
                           food_delivery boolean not null
);

create table shops_content_products(
                                       id_shop integer not null references shops_main(id),
                                       id_prod integer not null references products(id_prod),
                                       price numeric(10) not null,
                                       count integer not null check(count >= 0)
);
create table shops_content_recipes(
                                      id_shop integer not null references shops_main(id),
                                      id_rec integer not null references recipes(id_rec),
                                      price numeric(10) not null,
                                      count integer not null check(count >= 0)
);

create table restaurant_orders(
                                  id_order integer not null,
                                  id_restaurant integer not null references restaurants_main(id),
                                  id_rec integer not null references recipes(id_rec),
                                  price numeric(10) not null,
                                  date timestamp not null,
                                  count integer,
                                  primary key(id_order,id_restaurant,id_rec,date)
);
create table restaurant_content_recipes(
                                           id_restaurant integer not null references restaurants_main(id),
                                           id_rec integer not null references recipes(id_rec),
                                           price numeric(10) not null,
                                           count integer not null check(count >= 0)
);

create sequence for_id_products start with 1 increment by 2 maxvalue 100000;
create sequence for_id_recipes start with 2 increment by 2 maxvalue 100000;
create sequence for_id_shop start with 1 increment by 2 maxvalue 100000;
create sequence for_id_restaurants start with 2 increment by 2 maxvalue 100000;
create sequence for_id_shopOrders start with 1 increment by 2 maxvalue 100000;
create sequence for_id_restaurantsOrders start with 1 increment by 2 maxvalue 100000;


/*

for diagram


*/
create table shopsOrderRec(
                              id_order integer not null,
                              id_rec integer references recipes(id_rec),
                              id_shop integer not null references shops_main(id),
                              price numeric(10) not null,
                              date timestamp not null,
							  primary key(id_order, id_rec, id_shop, date)
);
create table shopsOrderProd(
                               id_order integer not null,
                               id_prod integer references products(id_prod),
                               id_shop integer not null references shops_main(id),
                               price numeric(10) not null,
                               date timestamp not null,
							   primary key(id_order, id_prod, id_shop, date)
);

ALTER TABLE ONLY restaurants_group_meals
    ADD CONSTRAINT fsese2222 FOREIGN KEY (id_restaurant) references restaurants_main(id);
ALTER TABLE ONLY shops_content_recipes
    ADD CONSTRAINT da2isc_1 FOREIGN KEY (id_shop) references shops_main(id);
ALTER TABLE ONLY shops_content_products
    ADD CONSTRAINT dai23sc_12 FOREIGN KEY (id_shop)references shops_main(id);
ALTER TABLE ONLY shops_content_products
    ADD CONSTRAINT dai23sc_122 FOREIGN KEY (id_prod)references products(id_prod);
ALTER TABLE ONLY drinks_info
    ADD CONSTRAINT fk_pwrod_dk FOREIGN KEY (id_prod) references products(id_prod);
ALTER TABLE ONLY restaurant_orders
    ADD CONSTRAINT fsese22222 FOREIGN KEY (id_restaurant) references restaurants_main(id);	
ALTER TABLE ONLY restaurant_content_recipes
    ADD CONSTRAINT dai23sc_1222 FOREIGN KEY (id_restaurant)references restaurants_main(id);
ALTER TABLE ONLY restaurant_orders
    ADD CONSTRAINT dai23sc_12222 FOREIGN KEY (id_restaurant)references restaurants_main(id);
ALTER TABLE ONLY shopsOrderRec
    ADD CONSTRAINT dai23sc_1222 FOREIGN KEY (id_shop)references shops_main(id);
ALTER TABLE ONLY shopsOrderProd
    ADD CONSTRAINT dai23sc_1222 FOREIGN KEY (id_shop)references shops_main(id);


	
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

/*create or replace view Species as
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
);*/


create or replace view solids_full(id_prod, product_group, product_class, name, description, calories, fat, saturated_fat, protein, carbo, sugar, zinc, iron, calcium, magnesium, vitamin_A, vitamin_B6, vitamin_B12, vitamin_C, vitamin_E, vitamin_K)
as
select * from products
                  natural join products_nutrient;

create or replace function solids_full_insert(i record)
    returns void as
$$
begin
    INSERT INTO PRODUCTS
    values (i.id_prod, i.product_group, i.product_class, i.name, i.description, i.calories);
    INSERT INTO products_nutrient
    values (i.id_prod, i.fat, i.saturated_fat, i.protein, i.carbo, i.sugar, i.zinc, i.iron, i.calcium, i.magnesium, i.vitamin_A, i.vitamin_B6, i.vitamin_B12, i.vitamin_C, i.vitamin_E, i.vitamin_K);
end;
$$ language plpgsql;

create or replace rule solids_full_insert as
    on insert to solids_full
    do instead(
    select solids_full_insert(new);
    );



create or replace view shopOrders as
select id_order, id_shop, id_prod as id, (select price from shops_content_products s where s.id_prod = p.id_prod),date from shopsOrderProd p
union
select id_order, id_shop, id_rec as id, (select price from shops_content_recipes s where s.id_rec = q.id_rec),date from shopsOrderRec q;

create or replace function order_insert(id_order integer, id_shop integer, id integer,price numeric, date timestamp)
    returns void as
$$
begin
    if (id % 2 = 1) then
        insert into shopsOrderProd
        values
        (id_order, id, id_shop,price,date);
    else
        insert into shopsOrderRec
        values
        (id_order, id, id_shop, price,date);
    end if;
end;
$$ language plpgsql;

create or replace rule rule_insert as
    on insert to shopOrders
    do instead(
    select order_insert(new.id_order, new.id_shop, new.id, new.price ,new.date);
    );



\i utils/solidsInsert.sql
\i utils/speciesInsert.sql
\i utils/recipesInsert.sql
\i utils/restaurantsInsert.sql
\i utils/shopsInsert.sql




insert into restaurants_group_meals(id_restaurant,id_group,cena) values (10,5,3424);
insert into group_meals_content(id_group,id_rec) values (5,12);
INSERT INTO shops_main(id, name, address, geoposition, open_weekdays, close_weekdays, open_saturday, close_saturday, open_sunday, close_sunday, stars, description, food_delivery) VALUES (nextval('for_id_shop'), 'Zabka', 'al. 4 maja', '0,0', '9:0', '17:0', '9:0', '17:0', '9:0', '17:0', 3, 'Green', false);
INSERT INTO restaurants_main(id, name, address, geoposition, open_weekdays, close_weekdays, open_saturday, close_saturday, open_sunday, close_sunday, stars, description, food_delivery) VALUES (nextval('for_id_restaurants'), 'Lewiatanka', 'al. 4 maja', '0,0', '9:0', '17:0', '9:0', '17:0', '9:0', '17:0', 3, 'Green', false);