drop table if exists recipes cascade;
drop table if exists products cascade;
drop table if exists solids cascade;
drop table if exists drinks cascade;
drop table if exists species cascade;
drop table if exists recipes_content cascade;

drop table if exists solids_taste cascade;
drop table if exists drinks_taste cascade;
drop table if exists species_taste cascade;

drop type if exists prod_class_enum cascade;
drop type if exists solid_taste_enum cascade;
drop type if exists species_taste_enum cascade;


drop table if exists restaurants_info cascade;
drop table if exists restaurants_main cascade;
drop table if exists restaurants_groups cascade;


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
create type solid_taste_enum as ENUM('Sweet', 'Sour', 'Salty', 'Bitter', 'None');
create type species_taste_enum as ENUM('Sweet', 'Salty', 'Bitter', 'Species');


create table restaurants_main(
                                 id numeric(7) not null primary key,
                                 "name" varchar(10) not null,
                                 geoposition varchar(30) not null,
                                 adres varchar(100)
);

create table restaurants_info(
                                 id numeric(7) not null  constraint fk_shop_des references restaurants_main(id),
                                 open time,
                                 close time
                                     constraint con_open_close check((open is null and close is null) or (close>open)),
                                 stars numeric(1) check (stars <= 5 OR stars is null),
                                 description varchar(100),
                                 food_delivery boolean not null
);

create table shops_main(
                           id numeric(7) not null primary key,
                           "name" varchar(10) not null,
                           geoposition varchar(30) not null,
                           adres varchar(100)
);

create table shops_info(
                           id numeric(7) not null constraint fk_shop_des references restaurants_main(id),
                           open time,
                           close time
                               constraint con_open_close check((open is null and close is null) or (close>open)),
                           description varchar(100),
                           food_delivery boolean not null
);
create table products (
                          id_prod numeric(7) constraint pk_prod primary key,
                          product_group varchar(10) not null,
                          product_class prod_class_enum not null,
                          name varchar(20) not null,
                          description varchar(200),  --optional
                          area varchar(200), --optional
                          calories numeric(5) not null check(calories >= 0)
);
create table recipes (
                         id_rec numeric(7) constraint pk_reci primary key,
                         name varchar(30) not null,
                         sum_weight numeric(7) not null,
                         sum_calories numeric(7) not null,
                         description varchar(300), --optional
                         links varchar(300) --optional
                             check(sum_weight >= 0),
                         check(sum_calories >= 0)
);
create table recipes_content (
                                 id_rec numeric(7) not null constraint fk_rec_cont references recipes(id_rec),
                                 id_prod numeric(7) not null constraint fk_prod references products(id_prod),
                                 weight numeric(6) not null check(weight >= 0),
                                 weight_type char(4) not null check(weight_type in ('g', 'ml'))
);
create table solids_taste (
                              id numeric(7) not null constraint fk_prod_sd references products(id_prod),
                              taste solid_taste_enum,
                              primary key(id)
);
create table drinks_taste (
                              id numeric(7) not null constraint fk_prod_dk references products(id_prod),
                              sugar boolean not null,
                              colour varchar(15)
);
create table species_taste (
                               id numeric(7) not null constraint fk_prod_ss references products(id_prod),
                               taste species_taste_enum,
                               primary key(id)
);



create table restaurants_groups(
                                   id_restaurant numeric(7) not null references restaurants_main(id),
                                   id_group numeric(7) not null primary key,
                                   cena numeric(10) not null,
                                   min_cena numeric(10) not null,
                                   max_cena numeric(10) not null,
                                   constraint con_min_max check(max_cena >= min_cena)
);
create table shops_content_products(
                                       id_shop numeric(7) not null references shops_main(id),
                                       id_prod numeric(7) not null references products(id_prod),
                                       cena numeric(10) not null,
                                       count numeric(1000) not null check(count >= 1)
);
create table shops_content_recipes(
                                      id_shop numeric(7) not null references shops_main(id),
                                      id_rec numeric(7) not null references recipes(id_rec),
                                      cena numeric(10) not null,
                                      count numeric(1000) not null check(count >= 1)
);
create table shops_discounts_recipes(
                                        id_shop numeric(7) not null references shops_main(id),
                                        id_rec numeric(7) not null references recipes(id_rec),
                                        discount_val numeric(100) not null check(discount_val >0 and discount_val<=100),
                                        min_count numeric(1000)
);
create table shops_discounts_products(
                                         id_shop numeric(7) not null references shops_main(id),
                                         id_prod numeric(7) not null references products(id_prod),
                                         min_count numeric(1000)
);
create table shop_cards(
                           id_shop numeric(7) not null references shops_main(id),
                           is_accumulative boolean not null
);
create table discounts(
                          id_shop numeric(7) not null references shops_main(id),
                          sum_for_discount numeric(1000) not null,
                          discount numeric(7) check(discount >0 and discount<=100)
);
create sequence for_id_products start with 1 increment by 2 maxvalue 1000;
create sequence for_id_recipes start with 2 increment by 2 maxvalue 1000;
create sequence for_id_shop start with 1 increment by 2 maxvalue 1000;
create sequence for_id_restaurants start with 2 increment by 2 maxvalue 1000;


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
ALTER TABLE ONLY shops_content_recipes
    ADD CONSTRAINT daisc_12 FOREIGN KEY (id_rec) references recipes(id_rec);
ALTER TABLE ONLY shops_content_products
    ADD CONSTRAINT dai23sc_12 FOREIGN KEY (id_shop)references shops_main(id);
ALTER TABLE ONLY shops_content_products
    ADD CONSTRAINT dai23sc_122 FOREIGN KEY (id_prod)references products(id_prod);
ALTER TABLE ONLY restaurants_groups
    ADD CONSTRAINT dai23sc_122 FOREIGN KEY (id_restaurant) references restaurants_main(id);
ALTER TABLE ONLY recipes_content
    ADD CONSTRAINT fk_rec_coewnt FOREIGN KEY (id_rec) references recipes(id_rec);
ALTER TABLE ONLY recipes_content
    ADD CONSTRAINT fk_proed FOREIGN KEY (id_prod) references products(id_prod);
ALTER TABLE ONLY species_taste
    ADD CONSTRAINT fk_proed_ss FOREIGN KEY (id) references products(id_prod);
ALTER TABLE ONLY drinks_taste
    ADD CONSTRAINT fk_pwrod_dk FOREIGN KEY (id) references products(id_prod);
ALTER TABLE ONLY solids_taste
    ADD CONSTRAINT fk_preod_sd FOREIGN KEY (id) references products(id_prod);
ALTER TABLE ONLY shops_info
    ADD CONSTRAINT fk_shoep_des FOREIGN KEY (id) references restaurants_main(id);
ALTER TABLE ONLY restaurants_info
    ADD CONSTRAINT fk_shop_des2 FOREIGN KEY (id) references restaurants_main(id);
