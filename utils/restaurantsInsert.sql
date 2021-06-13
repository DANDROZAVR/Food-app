

insert into restaurants_main values (nextval('for_id_restaurants'), 'KFC 1', 'Floriańska 33, 31-425 Kraków','50.06509,19.93986','8:00', '23:30','8:00', '23:30','8:00', '23:30',4, '', true);

insert into restaurants_main values (nextval('for_id_restaurants'), 'KFC 2', 'Pawia 5, 31-154 Kraków','50.06785,19.94587','8:30', '22:30','8:30', '22:30','8:30', '22:30',4, '', true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'KFC 3', 'Opolska 60A, 31-355 Kraków','50.09296,19.92063','8:30', '22:30','8:30', '22:30','8:30', '22:30', 4, '', true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'KFC 4', 'Wadowicka 2, 30-415 Kraków', '50.03662,19.94050', '8:00', '23:00','8:00', '23:00','8:00', '23:00',4,'',true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'KFC 5', 'Krowoderskich Zuchów 21, 31-271 Kraków','50.09167,19.92668','8:30', '23:00','8:30', '23:00','8:30', '23:00',4,'',true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Good Lood Blonia', 'Krowoderskich Zuchów 21, 31-271 Kraków','50.059408179645104,19.921864931621894', '11:00', '22:00', '11:00', '22:00', '11:00', '22:00', 5,'',true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Good Lood Basztowa', 'Basztowa 10, 31-141 Kraków','50.066976126796106,19.93850241684209','11:00', '22:00','11:00', '22:00','11:00', '22:00',5,'',true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Good Lood Matejki', 'plac Jana Matejki 9, 31-157 Kraków','50.068057164637565,19.942432129608825','11:00', '21:00','11:00', '21:00','11:00', '21:00',5,'',true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Good Lood Krowodrza', 'plac Axentowicza, 30-034 Kraków','50.072413827733925,19.926202926234524','11:00', '21:00','11:00', '22:00','11:00', '22:00',5,'',false);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Good Lood Kazimierz', 'plac Wolnica 11, 31-060 Kraków','50.049531975233606,19.944669933375426','11:00', '21:00','11:00', '21:00','11:00', '21:00',5,'',false);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Cafe Camelot', 'Świętego Tomasza 17, 33-332 Kraków','50.063096528036546,19.9391680134084','9:00', '23:59','9:00', '23:59','9:00', '23:59',5,'',false);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Mleczarnia', 'Rabina, Beera Meiselsa 20, 31-058 Kraków','50.05148675720475,19.943828684572754','10:00', '23:59','10:00', '23:59','10:00', '23:59',5,'',false);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Blossom', 'Apartamenty Novum, Rakowicka 20, 31-510 Kraków','50.06928032606181,19.952713426901465','08:00', '16:00','08:00', '16:00','08:00', '16:00',5,'',true);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Cafe Szafe', 'Felicjanek 10, 31-104 Kraków','50.057843945898476,19.929687469230185','09:00', '23:00','09:00', '23:00','09:00', '23:00',5,'',false);

insert into restaurants_main values(nextval('for_id_restaurants'), 'Costa Coffee', 'Rynek Główny 41, 31-013 Kraków','50.06268386692502,19.93789928642227','07:00', '21:00','07:00', '21:00','07:00', '21:00',5,'',true);








/*insert into restaurants_main(id, name, address, geoposition, stars, description, food_delivery, open_weekdays, close_weekdays, open_saturday, close_saturday, open_sunday, close_sunday) values 
(101, 'KFC 1', 'Floriańska 33, 31-425 Kraków', '50.06509,19.93986', 4, '', true, '8:00', '23:30', '8:00', '23:30', '8:00', '21:30');

insert into restaurants_main(id, name, adres) values (102, 'KFC 2', 'Pawia 5, 31-154 Kraków');
insert into restaurants_geoposition(id, geoposition) values(102, '50.06785,19.94587');
insert into restaurants_info(id, stars, description, food_delivery) values(102, 4, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(102, '8:30', '22:30');
insert into restaurants_plan_saturday(id, open, close) values(102, '8:30', '22:30');
insert into restaurants_plan_sunday(id, open, close) values(102, '8:30', '22:30');

insert into restaurants_main(id, name, adres) values(103, 'KFC 3', 'Opolska 60A, 31-355 Kraków');
insert into restaurants_geoposition(id, geoposition) values(103, '50.09296,19.92063');
insert into restaurants_info(id, stars, description, food_delivery) values(103, 4, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(103, '8:30', '22:30');
insert into restaurants_plan_saturday(id, open, close) values(103, '8:30', '22:30');
insert into restaurants_plan_sunday(id, open, close) values(103, '8:30', '22:30');

insert into restaurants_main(id, name, adres) values(104, 'KFC 4', 'Wadowicka 2, 30-415 Kraków');
insert into restaurants_geoposition(id, geoposition) values(104, '50.03662,19.94050');
insert into restaurants_info(id, stars, description, food_delivery) values(104, 4, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(104, '8:00', '23:00');
insert into restaurants_plan_saturday(id, open, close) values(104, '8:00', '23:00');
insert into restaurants_plan_sunday(id, open, close) values(104, '8:00', '23:00');

insert into restaurants_main(id, name, adres) values(105, 'KFC 5', 'Krowoderskich Zuchów 21, 31-271 Kraków');
insert into restaurants_geoposition(id, geoposition) values(105, '50.09167,19.92668');
insert into restaurants_info(id, stars, description, food_delivery) values(105, 4, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(105, '8:30', '23:00');
insert into restaurants_plan_saturday(id, open, close) values(105, '8:30', '23:00');
insert into restaurants_plan_sunday(id, open, close) values(105, '8:30', '23:00');

insert into restaurants_main(id, name, adres) values(106, 'Good Lood Blonia', 'Krowoderskich Zuchów 21, 31-271 Kraków');
insert into restaurants_geoposition(id, geoposition) values(106, '50.059408179645104,19.921864931621894');
insert into restaurants_info(id, stars, description, food_delivery) values(106, 5, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(106, '11:00', '22:00');
insert into restaurants_plan_saturday(id, open, close) values(106, '11:00', '22:00');
insert into restaurants_plan_sunday(id, open, close) values(106, '11:00', '22:00');

insert into restaurants_main(id, name, adres) values(107, 'Good Lood Basztowa', 'Basztowa 10, 31-141 Kraków');
insert into restaurants_geoposition(id, geoposition) values(107, '50.066976126796106,19.93850241684209');
insert into restaurants_info(id, stars, description, food_delivery) values(107, 5, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(107, '11:00', '22:00');
insert into restaurants_plan_saturday(id, open, close) values(107, '11:00', '22:00');
insert into restaurants_plan_sunday(id, open, close) values(107, '11:00', '22:00');

insert into restaurants_main(id, name, adres) values(108, 'Good Lood Matejki', 'plac Jana Matejki 9, 31-157 Kraków');
insert into restaurants_geoposition(id, geoposition) values(108, '50.068057164637565,19.942432129608825');
insert into restaurants_info(id, stars, description, food_delivery) values(108, 5, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(108, '11:00', '21:00');
insert into restaurants_plan_saturday(id, open, close) values(108, '11:00', '21:00');
insert into restaurants_plan_sunday(id, open, close) values(108, '11:00', '21:00');

insert into restaurants_main(id, name, adres) values(109, 'Good Lood Krowodrza', 'plac Axentowicza, 30-034 Kraków');
insert into restaurants_geoposition(id, geoposition) values(109, '50.072413827733925,19.926202926234524');
insert into restaurants_info(id, stars, description, food_delivery) values(109, 5, '', false);
insert into restaurants_plan_weekdays(id, open, close) values(109, '11:00', '21:00');
insert into restaurants_plan_saturday(id, open, close) values(109, '11:00', '22:00');
insert into restaurants_plan_sunday(id, open, close) values(109, '11:00', '22:00');

insert into restaurants_main(id, name, adres) values(110, 'Good Lood Kazimierz', 'plac Wolnica 11, 31-060 Kraków');
insert into restaurants_geoposition(id, geoposition) values(110, '50.049531975233606,19.944669933375426');
insert into restaurants_info(id, stars, description, food_delivery) values(110, 5, '', false);
insert into restaurants_plan_weekdays(id, open, close) values(110, '11:00', '21:00');
insert into restaurants_plan_saturday(id, open, close) values(110, '11:00', '21:00');
insert into restaurants_plan_sunday(id, open, close) values(110, '11:00', '21:00');

insert into restaurants_main(id, name, adres) values(111, 'Cafe Camelot', 'Świętego Tomasza 17, 33-332 Kraków');
insert into restaurants_geoposition(id, geoposition) values(111, '50.063096528036546,19.9391680134084');
insert into restaurants_info(id, stars, description, food_delivery) values(111, 5, '', false);
insert into restaurants_plan_weekdays(id, open, close) values(111, '9:00', '23:59');
insert into restaurants_plan_saturday(id, open, close) values(111, '9:00', '23:59');
insert into restaurants_plan_sunday(id, open, close) values(111, '9:00', '23:59');

insert into restaurants_main(id, name, adres) values(112, 'Mleczarnia', 'Rabina, Beera Meiselsa 20, 31-058 Kraków');
insert into restaurants_geoposition(id, geoposition) values(112, '50.05148675720475,19.943828684572754');
insert into restaurants_info(id, stars, description, food_delivery) values(112, 5, '', false);
insert into restaurants_plan_weekdays(id, open, close) values(112, '10:00', '23:59');
insert into restaurants_plan_saturday(id, open, close) values(112, '10:00', '23:59');
insert into restaurants_plan_sunday(id, open, close) values(112, '10:00', '23:59');

insert into restaurants_main(id, name, adres) values(113, 'Blossom', 'Apartamenty Novum, Rakowicka 20, 31-510 Kraków');
insert into restaurants_geoposition(id, geoposition) values(113, '50.06928032606181,19.952713426901465');
insert into restaurants_info(id, stars, description, food_delivery) values(113, 5, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(113, '08:00', '16:00');
insert into restaurants_plan_saturday(id, open, close) values(113, '08:00', '16:00');
insert into restaurants_plan_sunday(id, open, close) values(113, '08:00', '16:00');

insert into restaurants_main(id, name, adres) values(114, 'Cafe Szafe', 'Felicjanek 10, 31-104 Kraków');
insert into restaurants_geoposition(id, geoposition) values(114, '50.057843945898476,19.929687469230185');
insert into restaurants_info(id, stars, description, food_delivery) values(114, 5, '', false);
insert into restaurants_plan_weekdays(id, open, close) values(114, '09:00', '23:00');
insert into restaurants_plan_saturday(id, open, close) values(114, '09:00', '23:00');
insert into restaurants_plan_sunday(id, open, close) values(114, '09:00', '23:00');

insert into restaurants_main(id, name, adres) values(115, 'Costa Coffee', 'Rynek Główny 41, 31-013 Kraków');
insert into restaurants_geoposition(id, geoposition) values(115, '50.06268386692502,19.93789928642227');
insert into restaurants_info(id, stars, description, food_delivery) values(115, 5, '', true);
insert into restaurants_plan_weekdays(id, open, close) values(115, '07:00', '21:00');
insert into restaurants_plan_saturday(id, open, close) values(115, '07:30', '21:00');
insert into restaurants_plan_sunday(id, open, close) values(115, '07:30', '21:00');
*/