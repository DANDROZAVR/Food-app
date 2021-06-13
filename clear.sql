drop table if exists recipes cascade;
drop table if exists products cascade;
drop table if exists products_tag cascade;
drop table if exists products_nutrient cascade;
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
drop table if exists shops_content_recipes cascade;
drop table if exists shops_content_orders cascade;
drop table if exists group_meals_content cascade;
drop table if exists shopsOrderRec cascade;
drop table if exists shopsOrderProd cascade;

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
drop table if exists shops_content_products cascade
drop table if exists shops_discounts_recipes cascade;
drop table if exists shops_discounts_products cascade;
drop table if exists shop_cards cascade;
drop table if exists shops_info cascade;
drop table if exists discounts cascade;

drop sequence if exists for_id_products cascade;
drop sequence if exists for_id_recipes cascade;
drop sequence if exists for_id_restaurants cascade;
drop sequence if exists for_id_shop cascade;


drop function if exists getProductAreaTags cascade; 
drop function if exists getProductTags cascade;  
drop function if exists getRecipeContentProducts cascade;  
drop function if exists getRecipeContentRecipes cascade;
drop function if exists spec_insert cascade; 
drop rule if exists spec_insert on Species;  
drop view if exists solids_full cascade;
drop view if exists Species cascade;  

drop rule if exists rule_insert on Solids_full; 
drop function if exists order_insert(id_order integer, id_shop integer, id integer, date timestamp)	 cascade;  
drop view if exists shopOrders;


drop table if exists shopsOrderRec cascade;
drop table if exists shopsOrderProd cascade;

drop function if exists solids_full_insert;
drop rule if exists solids_full_insert on solids_full;
