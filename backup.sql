--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE daniil;
ALTER ROLE daniil WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'md506143c1b52e1e6f840eb1f006526edd3';
CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS;
CREATE ROLE test;
ALTER ROLE test WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'md5efae8f7fd2532386dc2643932a3cde48';






--
-- Database creation
--

CREATE DATABASE daniil WITH TEMPLATE = template0 OWNER = daniil;
REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;
CREATE DATABASE test WITH TEMPLATE = template0 OWNER = test;


\connect daniil

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: prod_class_enum; Type: TYPE; Schema: public; Owner: daniil
--

CREATE TYPE public.prod_class_enum AS ENUM (
    'Drinks',
    'Solids',
    'Species'
);


ALTER TYPE public.prod_class_enum OWNER TO daniil;

--
-- Name: species_taste_enum; Type: TYPE; Schema: public; Owner: daniil
--

CREATE TYPE public.species_taste_enum AS ENUM (
    'Sweet',
    'Salty',
    'Bitter',
    'Sour'
);


ALTER TYPE public.species_taste_enum OWNER TO daniil;

--
-- Name: add_czasopisma(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.add_czasopisma() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  begin
  new.id = nextval('for_id_czasopisma');
  return new; 
  end;
$$;


ALTER FUNCTION public.add_czasopisma() OWNER TO daniil;

--
-- Name: add_konferencje(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.add_konferencje() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  begin
  new.id = nextval('for_id_konferencje');
  return new; 
  end;
$$;


ALTER FUNCTION public.add_konferencje() OWNER TO daniil;

--
-- Name: add_naukowcy(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.add_naukowcy() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  begin
  new.id = nextval('for_id_naukowcy');
  return new; 
  end;
$$;


ALTER FUNCTION public.add_naukowcy() OWNER TO daniil;

--
-- Name: add_visit(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.add_visit() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
      IF (NEW.data_zakonczenia - NEW.data_rozpoczecia) > (interval '1 hour') OR
      EXISTS (SELECT * FROM wizyty a where a.lekarz = NEW.lekarz AND
              a.data_rozpoczecia < NEW.data_zakonczenia AND a.data_zakonczenia >
              NEW.data_rozpoczecia) then
              NEW = NULL;
      END IF;
      RETURN NEW;
 
END;
$$;


ALTER FUNCTION public.add_visit() OWNER TO daniil;

--
-- Name: array_intersect(anyarray, anyarray); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.array_intersect(t1 anyarray, t2 anyarray) RETURNS anyarray
    LANGUAGE plpgsql
    AS $$
    begin
        return array(select unnest(t1) intersect select unnest(t2) order by 1);
    end;
    $$;


ALTER FUNCTION public.array_intersect(t1 anyarray, t2 anyarray) OWNER TO daniil;

--
-- Name: array_sort(anyarray); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.array_sort(a anyarray) RETURNS anyarray
    LANGUAGE plpgsql
    AS $$
    begin
         return array(select * from unnest(a) order by 1);
    end;
    $$;


ALTER FUNCTION public.array_sort(a anyarray) OWNER TO daniil;

--
-- Name: check_add(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.check_add() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
     if (new.data_zakonczenia-new.data_rozpoczecia = interval '1h') then
       if (select count(*) from (select * from wizyty where not (data_zakonczenia<=new.data_rozpoczecia or data_rozpoczecia>=new.data_zakonczenia)) as cos) > 0 then
          new = null;
          return null;
       end if;
       return new;
      end if;
      new = null;
      return new;
end;
$$;


ALTER FUNCTION public.check_add() OWNER TO daniil;

--
-- Name: check_del(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.check_del() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE act_date timestamp := CURRENT_TIMESTAMP;
DECLARE bef_date timestamp := act_date - (interval '5 year');
 
BEGIN
      IF OLD.data_zakonczenia > bef_date then
              OLD = NULL;
      END IF;
      RETURN OLD;
END;
$$;


ALTER FUNCTION public.check_del() OWNER TO daniil;

--
-- Name: check_vis(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.check_vis() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
  if new.data_zakonczenia - new.data_rozpoczecia > '1 hour' then
    new = null;
    return null;
  end if;
  if (select count(*) from (select * from wizyty where not (data_zakonczenia < new.data_rozpoczecia or data_rozpoczecia > new.data_zakonczenia)) as aa) > 0 then
    new = null;
    return null;
  end if;
  return new;
end;
$$;


ALTER FUNCTION public.check_vis() OWNER TO daniil;

--
-- Name: data(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.data() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
  if new.data_zakonczenia is null then
    new.data_zakonczenia=new.data_rozpoczecia + interval '30m';
  end if;
  return new;
end;
$$;


ALTER FUNCTION public.data() OWNER TO daniil;

--
-- Name: del_check(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.del_check() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
  if curdate() - old.data_rozpoczecia < interval '5y' then
    return null;
  else
  return old;
  end if;
end;
$$;


ALTER FUNCTION public.del_check() OWNER TO daniil;

--
-- Name: nulls(anyarray); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.nulls(VARIADIC temp anyarray) RETURNS integer
    LANGUAGE plpgsql
    AS $$
    declare res integer;
    declare it integer;
    begin
      res = 0;
      for it in 1..array_length(temp, 1)
            loop
            if temp[it] is null then
            res = res + 1;
            end if;
        end loop;
    return res;
    end;
    $$;


ALTER FUNCTION public.nulls(VARIADIC temp anyarray) OWNER TO daniil;

--
-- Name: pesel(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.pesel() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin 
      IF length(new.pesel) != 11 or ((substring(new.pesel, 1,1)::integer + substring(new.pesel, 2,1)::integer*3 +
             substring(new.pesel, 3,1)::integer*7 + substring(new.pesel, 4,1)::integer*9 + 
             substring(new.pesel, 5,1)::integer + substring(new.pesel, 6,1)::integer*3 + 
             substring(new.pesel, 7,1)::integer * 7 + substring(new.pesel, 8,1)::integer*9+
             substring(new.pesel, 9,1)::integer + substring(new.pesel, 10,1)::integer*3 + 
             substring(new.pesel, 11,1)::integer)%10 !=0) then
               raise exception 'Niepoprawny PESEL';
      end if;
      return new;
end;
$$;


ALTER FUNCTION public.pesel() OWNER TO daniil;

--
-- Name: update_naukowcy(); Type: FUNCTION; Schema: public; Owner: daniil
--

CREATE FUNCTION public.update_naukowcy() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  begin
  new.id = old.id;
  return new;
  end;
$$;


ALTER FUNCTION public.update_naukowcy() OWNER TO daniil;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: lekarze; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.lekarze (
    id integer NOT NULL,
    imie character varying(100) NOT NULL,
    nazwisko character varying(100) NOT NULL
);


ALTER TABLE public.lekarze OWNER TO daniil;

--
-- Name: specjalizacje; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.specjalizacje (
    id_lekarza integer NOT NULL,
    specjalizacja character varying(80) NOT NULL
);


ALTER TABLE public.specjalizacje OWNER TO daniil;

--
-- Name: chirurdzy; Type: VIEW; Schema: public; Owner: daniil
--

CREATE VIEW public.chirurdzy AS
 SELECT lekarze.id,
    lekarze.imie,
    lekarze.nazwisko
   FROM (public.lekarze
     JOIN public.specjalizacje ON ((lekarze.id = specjalizacje.id_lekarza)))
  WHERE ((specjalizacje.specjalizacja)::text = 'Chirurgia'::text);


ALTER TABLE public.chirurdzy OWNER TO daniil;

--
-- Name: czasopisma; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.czasopisma (
    krotka_nazwa character varying(100) NOT NULL,
    pelna_nazwa character varying(100) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.czasopisma OWNER TO daniil;

--
-- Name: discounts; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.discounts (
    id_shop integer NOT NULL,
    sum_for_discount numeric(10,0) NOT NULL,
    discount numeric,
    CONSTRAINT discounts_discount_check CHECK (((discount > (0)::numeric) AND (discount <= (100)::numeric)))
);


ALTER TABLE public.discounts OWNER TO daniil;

--
-- Name: drinks_taste; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.drinks_taste (
    id integer NOT NULL,
    sugar boolean NOT NULL,
    colour character varying(15)
);


ALTER TABLE public.drinks_taste OWNER TO daniil;

--
-- Name: egzaminy; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.egzaminy (
    id_studenta integer NOT NULL,
    ocena integer NOT NULL,
    CONSTRAINT egzaminy_ocena_check CHECK ((ocena = ANY (ARRAY[2, 3, 4, 5])))
);


ALTER TABLE public.egzaminy OWNER TO daniil;

--
-- Name: for_id_czasopisma; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_czasopisma
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.for_id_czasopisma OWNER TO daniil;

--
-- Name: for_id_konferencje; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_konferencje
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.for_id_konferencje OWNER TO daniil;

--
-- Name: for_id_naukowcy; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_naukowcy
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.for_id_naukowcy OWNER TO daniil;

--
-- Name: for_id_products; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_products
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_products OWNER TO daniil;

--
-- Name: for_id_recipes; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_recipes
    START WITH 2
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_recipes OWNER TO daniil;

--
-- Name: for_id_restaurants; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_restaurants
    START WITH 2
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_restaurants OWNER TO daniil;

--
-- Name: for_id_shop; Type: SEQUENCE; Schema: public; Owner: daniil
--

CREATE SEQUENCE public.for_id_shop
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_shop OWNER TO daniil;

--
-- Name: group_meals_content; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.group_meals_content (
    id_group integer NOT NULL,
    id_rec integer NOT NULL
);


ALTER TABLE public.group_meals_content OWNER TO daniil;

--
-- Name: konferencje; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.konferencje (
    nazwa character varying(100) NOT NULL,
    kraj character varying(100) NOT NULL,
    mejsce character varying(100) NOT NULL,
    rok numeric(4,0) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.konferencje OWNER TO daniil;

--
-- Name: naukowcy; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.naukowcy (
    imie character varying(100) NOT NULL,
    nazwisko character varying(100) NOT NULL,
    rok numeric(4,0),
    id integer NOT NULL
);


ALTER TABLE public.naukowcy OWNER TO daniil;

--
-- Name: nieobecnosci; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.nieobecnosci (
    id_studenta integer NOT NULL,
    data date NOT NULL
);


ALTER TABLE public.nieobecnosci OWNER TO daniil;

--
-- Name: notatki; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.notatki (
    id_studenta integer NOT NULL,
    notatka character(1)[] NOT NULL
);


ALTER TABLE public.notatki OWNER TO daniil;

--
-- Name: pacjenci; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.pacjenci (
    pesel character(11) NOT NULL,
    imie character varying(100) NOT NULL,
    nazwisko character varying(100) NOT NULL
);


ALTER TABLE public.pacjenci OWNER TO daniil;

--
-- Name: pediatrzy; Type: VIEW; Schema: public; Owner: daniil
--

CREATE VIEW public.pediatrzy AS
 SELECT lekarze.id,
    lekarze.imie,
    lekarze.nazwisko
   FROM (public.lekarze
     JOIN public.specjalizacje ON ((lekarze.id = specjalizacje.id_lekarza)))
  WHERE ((specjalizacje.specjalizacja)::text = 'Pediatria'::text);


ALTER TABLE public.pediatrzy OWNER TO daniil;

--
-- Name: products; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.products (
    id_prod integer NOT NULL,
    product_group character varying(10) NOT NULL,
    product_class public.prod_class_enum NOT NULL,
    name character varying(20) NOT NULL,
    description character varying(200),
    calories numeric(5,0) NOT NULL,
    CONSTRAINT products_calories_check CHECK ((calories >= (0)::numeric))
);


ALTER TABLE public.products OWNER TO daniil;

--
-- Name: products_areatag; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.products_areatag (
    id integer,
    area character varying(40) NOT NULL
);


ALTER TABLE public.products_areatag OWNER TO daniil;

--
-- Name: publikacje; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.publikacje (
    nazwa character varying(100) NOT NULL,
    typ integer NOT NULL,
    zrodlo integer NOT NULL,
    rok numeric(4,0) NOT NULL,
    numer integer NOT NULL,
    od integer NOT NULL,
    "do" integer NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.publikacje OWNER TO daniil;

--
-- Name: recipes; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.recipes (
    id_rec integer NOT NULL,
    name character varying(30) NOT NULL,
    sum_weight numeric(7,0) NOT NULL,
    sum_calories numeric(7,0) NOT NULL,
    description character varying(300),
    links character varying(300),
    CONSTRAINT recipes_sum_calories_check CHECK ((sum_calories >= (0)::numeric)),
    CONSTRAINT recipes_sum_weight_check CHECK ((sum_weight >= (0)::numeric))
);


ALTER TABLE public.recipes OWNER TO daniil;

--
-- Name: recipes_areatag; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.recipes_areatag (
    id integer,
    area character varying(40) NOT NULL
);


ALTER TABLE public.recipes_areatag OWNER TO daniil;

--
-- Name: recipes_content; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.recipes_content (
    id_rec integer NOT NULL,
    id_prod integer NOT NULL,
    weight numeric(6,0) NOT NULL,
    weight_type character(4) NOT NULL,
    CONSTRAINT recipes_content_weight_check CHECK ((weight >= (0)::numeric)),
    CONSTRAINT recipes_content_weight_type_check CHECK ((weight_type = ANY (ARRAY['g'::bpchar, 'ml'::bpchar])))
);


ALTER TABLE public.recipes_content OWNER TO daniil;

--
-- Name: recipes_tag; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.recipes_tag (
    id integer,
    area character varying(30) NOT NULL
);


ALTER TABLE public.recipes_tag OWNER TO daniil;

--
-- Name: restaurants_group_meals; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.restaurants_group_meals (
    id_restaurant integer NOT NULL,
    id_group integer NOT NULL,
    cena numeric(10,0) NOT NULL,
    min_cena numeric(10,0) NOT NULL,
    max_cena numeric(10,0) NOT NULL,
    CONSTRAINT con_min_max CHECK ((max_cena >= min_cena))
);


ALTER TABLE public.restaurants_group_meals OWNER TO daniil;

--
-- Name: restaurants_info; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.restaurants_info (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    stars integer,
    description character varying(100),
    food_delivery boolean NOT NULL,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open))),
    CONSTRAINT restaurants_info_stars_check CHECK (((stars <= 5) OR (stars IS NULL)))
);


ALTER TABLE public.restaurants_info OWNER TO daniil;

--
-- Name: restaurants_main; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.restaurants_main (
    id integer NOT NULL,
    name character varying(10) NOT NULL,
    geoposition character varying(30) NOT NULL,
    adres character varying(100)
);


ALTER TABLE public.restaurants_main OWNER TO daniil;

--
-- Name: shop_cards; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shop_cards (
    id_shop integer NOT NULL,
    is_accumulative boolean NOT NULL
);


ALTER TABLE public.shop_cards OWNER TO daniil;

--
-- Name: shops_content_products; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shops_content_products (
    id_shop integer NOT NULL,
    id_prod integer NOT NULL,
    cena numeric(10,0) NOT NULL,
    count integer NOT NULL,
    CONSTRAINT shops_content_products_count_check CHECK ((count >= 1))
);


ALTER TABLE public.shops_content_products OWNER TO daniil;

--
-- Name: shops_content_recipes; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shops_content_recipes (
    id_shop integer NOT NULL,
    id_rec integer NOT NULL,
    cena numeric(10,0) NOT NULL,
    count integer NOT NULL,
    CONSTRAINT shops_content_recipes_count_check CHECK ((count >= 1))
);


ALTER TABLE public.shops_content_recipes OWNER TO daniil;

--
-- Name: shops_discounts_products; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shops_discounts_products (
    id_shop integer NOT NULL,
    id_prod integer NOT NULL,
    min_count integer
);


ALTER TABLE public.shops_discounts_products OWNER TO daniil;

--
-- Name: shops_discounts_recipes; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shops_discounts_recipes (
    id_shop integer NOT NULL,
    id_rec integer NOT NULL,
    discount_val numeric(4,0) NOT NULL,
    min_count integer,
    CONSTRAINT shops_discounts_recipes_discount_val_check CHECK (((discount_val > (0)::numeric) AND (discount_val <= (100)::numeric)))
);


ALTER TABLE public.shops_discounts_recipes OWNER TO daniil;

--
-- Name: shops_info; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shops_info (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    description character varying(100),
    food_delivery boolean NOT NULL,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.shops_info OWNER TO daniil;

--
-- Name: shops_main; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.shops_main (
    id integer NOT NULL,
    name character varying(10) NOT NULL,
    geoposition character varying(30) NOT NULL,
    adres character varying(100)
);


ALTER TABLE public.shops_main OWNER TO daniil;

--
-- Name: species_taste; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.species_taste (
    id integer NOT NULL,
    taste public.species_taste_enum NOT NULL
);


ALTER TABLE public.species_taste OWNER TO daniil;

--
-- Name: studenci; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.studenci (
    id integer NOT NULL,
    imie character varying(20) NOT NULL,
    nazwisko character varying(30) NOT NULL
);


ALTER TABLE public.studenci OWNER TO daniil;

--
-- Name: tab; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.tab (
    a integer,
    b boolean NOT NULL
);


ALTER TABLE public.tab OWNER TO daniil;

--
-- Name: test; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.test (
    a integer,
    b integer,
    c integer,
    CONSTRAINT test_check CHECK ((public.nulls(VARIADIC ARRAY[a, b, c]) <= 1))
);


ALTER TABLE public.test OWNER TO daniil;

--
-- Name: typy; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.typy (
    typ integer NOT NULL,
    opis character varying(100) NOT NULL
);


ALTER TABLE public.typy OWNER TO daniil;

--
-- Name: wizyty; Type: TABLE; Schema: public; Owner: daniil
--

CREATE TABLE public.wizyty (
    lekarz integer NOT NULL,
    pacjent character(11) NOT NULL,
    data_rozpoczecia timestamp without time zone NOT NULL,
    data_zakonczenia timestamp without time zone NOT NULL,
    CONSTRAINT ck_data CHECK ((data_rozpoczecia < data_zakonczenia))
);


ALTER TABLE public.wizyty OWNER TO daniil;

--
-- Data for Name: czasopisma; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.czasopisma (krotka_nazwa, pelna_nazwa, id) FROM stdin;
dfdsfa	afdsf	1
\.


--
-- Data for Name: discounts; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.discounts (id_shop, sum_for_discount, discount) FROM stdin;
\.


--
-- Data for Name: drinks_taste; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.drinks_taste (id, sugar, colour) FROM stdin;
\.


--
-- Data for Name: egzaminy; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.egzaminy (id_studenta, ocena) FROM stdin;
5	3
2	2
\.


--
-- Data for Name: group_meals_content; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.group_meals_content (id_group, id_rec) FROM stdin;
\.


--
-- Data for Name: konferencje; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.konferencje (nazwa, kraj, mejsce, rok, id) FROM stdin;
dfdsfa	afdsf	fasdfa	2341	1
\.


--
-- Data for Name: lekarze; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.lekarze (id, imie, nazwisko) FROM stdin;
0	Abel	Spencer
1	Erick	Leonard
2	Janice	Montes
3	Gretchen	Proctor
4	Lawanda	Velazquez
5	Robbie	Wilkins
6	Carla	Randall
7	Heath	Dickson
8	Kendra	Rodgers
9	Brandie	Finley
\.


--
-- Data for Name: naukowcy; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.naukowcy (imie, nazwisko, rok, id) FROM stdin;
sfasdf	asdfasff	1234	1
sfasdf	asdfasff	1234	2
sfasdf	asdfasff	1256	3
\.


--
-- Data for Name: nieobecnosci; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.nieobecnosci (id_studenta, data) FROM stdin;
1	2015-10-29
1	2015-12-03
2	2016-01-13
1	2016-01-20
3	2016-01-20
1	2016-01-27
\.


--
-- Data for Name: notatki; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.notatki (id_studenta, notatka) FROM stdin;
1	{2,3,+,+,2}
2	{5,5,+,4}
4	{+,2,-,-,2,2,3}
1	{+,4}
3	{+,+,-}
\.


--
-- Data for Name: pacjenci; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.pacjenci (pesel, imie, nazwisko) FROM stdin;
55121400927	Keri	Brennan
67042513037	Cameron	Bass
46062718514	Roberta	Morse
68122519338	Colby	Boone
56062118986	Cornelius	Herring
92060614291	Teddy	Martin
39051200886	Lillian	Huerta
30092104661	Dianna	Chapman
72123101403	Geoffrey	Pacheco
74030811158	Melissa	Calderon
18091210888	Moses	Patel
24092005633	Franklin	Mills
78082506945	Arlene	Mcgrath
81121615959	Kathleen	Huynh
97091307785	Sheldon	Lamb
60122611096	Ivan	Edwards
93061202003	Rose	Escobar
41042504097	Ernest	Robinson
43080817599	Randal	Larsen
46052712258	Ismael	Dickerson
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.products (id_prod, product_group, product_class, name, description, calories) FROM stdin;
\.


--
-- Data for Name: products_areatag; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.products_areatag (id, area) FROM stdin;
\.


--
-- Data for Name: publikacje; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.publikacje (nazwa, typ, zrodlo, rok, numer, od, "do", id) FROM stdin;
\.


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.recipes (id_rec, name, sum_weight, sum_calories, description, links) FROM stdin;
\.


--
-- Data for Name: recipes_areatag; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.recipes_areatag (id, area) FROM stdin;
\.


--
-- Data for Name: recipes_content; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.recipes_content (id_rec, id_prod, weight, weight_type) FROM stdin;
\.


--
-- Data for Name: recipes_tag; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.recipes_tag (id, area) FROM stdin;
\.


--
-- Data for Name: restaurants_group_meals; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.restaurants_group_meals (id_restaurant, id_group, cena, min_cena, max_cena) FROM stdin;
\.


--
-- Data for Name: restaurants_info; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.restaurants_info (id, open, close, stars, description, food_delivery) FROM stdin;
\.


--
-- Data for Name: restaurants_main; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.restaurants_main (id, name, geoposition, adres) FROM stdin;
\.


--
-- Data for Name: shop_cards; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shop_cards (id_shop, is_accumulative) FROM stdin;
\.


--
-- Data for Name: shops_content_products; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shops_content_products (id_shop, id_prod, cena, count) FROM stdin;
\.


--
-- Data for Name: shops_content_recipes; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shops_content_recipes (id_shop, id_rec, cena, count) FROM stdin;
\.


--
-- Data for Name: shops_discounts_products; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shops_discounts_products (id_shop, id_prod, min_count) FROM stdin;
\.


--
-- Data for Name: shops_discounts_recipes; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shops_discounts_recipes (id_shop, id_rec, discount_val, min_count) FROM stdin;
\.


--
-- Data for Name: shops_info; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shops_info (id, open, close, description, food_delivery) FROM stdin;
\.


--
-- Data for Name: shops_main; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.shops_main (id, name, geoposition, adres) FROM stdin;
\.


--
-- Data for Name: species_taste; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.species_taste (id, taste) FROM stdin;
\.


--
-- Data for Name: specjalizacje; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.specjalizacje (id_lekarza, specjalizacja) FROM stdin;
0	Dermatologia
0	Alergologia
1	Ginekologia
4	Ginekologia
2	Chirurgia
9	Chirurgia
3	Psychologia
3	Psychiatria
5	Kardiologia
6	Okulistyka
7	Pediatria
7	Alergologia
8	Pediatria
9	Reumatologia
0	Medycyna rodzinna
5	Medycyna rodzinna
8	Medycyna rodzinna
\.


--
-- Data for Name: studenci; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.studenci (id, imie, nazwisko) FROM stdin;
1	Jan	Kowalski
2	Anna	Nowak
3	Jerzy	Robak
4	Maurycy	Poleski
5	Amelia	Brzyska
\.


--
-- Data for Name: tab; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.tab (a, b) FROM stdin;
1	t
2	f
3	f
4	t
5	t
\.


--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.test (a, b, c) FROM stdin;
\N	2	1
\N	2	1
\.


--
-- Data for Name: typy; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.typy (typ, opis) FROM stdin;
1	czasopisma
2	konferencja
\.


--
-- Data for Name: wizyty; Type: TABLE DATA; Schema: public; Owner: daniil
--

COPY public.wizyty (lekarz, pacjent, data_rozpoczecia, data_zakonczenia) FROM stdin;
7	97091307785	2008-07-24 11:00:00	2008-07-24 11:30:00
8	46062718514	2008-07-22 14:00:00	2008-07-22 14:30:00
2	72123101403	2008-07-25 08:30:00	2008-07-25 09:00:00
6	72123101403	2008-07-21 08:00:00	2008-07-21 08:30:00
6	56062118986	2008-07-23 11:00:00	2008-07-23 11:30:00
3	55121400927	2008-07-25 12:00:00	2008-07-25 12:30:00
5	39051200886	2008-07-25 13:30:00	2008-07-25 14:00:00
1	81121615959	2008-07-21 11:30:00	2008-07-21 12:00:00
5	46052712258	2008-07-25 09:30:00	2008-07-25 10:00:00
7	97091307785	2008-07-25 08:00:00	2008-07-25 08:30:00
3	55121400927	2008-07-25 13:00:00	2008-07-25 13:30:00
2	68122519338	2008-07-23 13:30:00	2008-07-23 14:00:00
\.


--
-- Name: for_id_czasopisma; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_czasopisma', 1, true);


--
-- Name: for_id_konferencje; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_konferencje', 2, true);


--
-- Name: for_id_naukowcy; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_naukowcy', 3, true);


--
-- Name: for_id_products; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_products', 1, false);


--
-- Name: for_id_recipes; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_recipes', 2, false);


--
-- Name: for_id_restaurants; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_restaurants', 2, false);


--
-- Name: for_id_shop; Type: SEQUENCE SET; Schema: public; Owner: daniil
--

SELECT pg_catalog.setval('public.for_id_shop', 1, false);


--
-- Name: czasopisma czasopisma_krotka_nazwa_key; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.czasopisma
    ADD CONSTRAINT czasopisma_krotka_nazwa_key UNIQUE (krotka_nazwa);


--
-- Name: czasopisma czasopisma_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.czasopisma
    ADD CONSTRAINT czasopisma_pkey PRIMARY KEY (id);


--
-- Name: discounts discounts_id_shop_key; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_id_shop_key UNIQUE (id_shop);


--
-- Name: drinks_taste drinks_taste_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.drinks_taste
    ADD CONSTRAINT drinks_taste_pkey PRIMARY KEY (id);


--
-- Name: egzaminy egzaminy_id_studenta_key; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.egzaminy
    ADD CONSTRAINT egzaminy_id_studenta_key UNIQUE (id_studenta);


--
-- Name: specjalizacje idx_specjalizacje; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.specjalizacje
    ADD CONSTRAINT idx_specjalizacje PRIMARY KEY (id_lekarza, specjalizacja);


--
-- Name: wizyty idx_wizyty; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.wizyty
    ADD CONSTRAINT idx_wizyty UNIQUE (lekarz, pacjent, data_rozpoczecia, data_zakonczenia);


--
-- Name: konferencje konferencje_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.konferencje
    ADD CONSTRAINT konferencje_pkey PRIMARY KEY (id);


--
-- Name: naukowcy naukowcy_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.naukowcy
    ADD CONSTRAINT naukowcy_pkey PRIMARY KEY (id);


--
-- Name: nieobecnosci nieobecnosci_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.nieobecnosci
    ADD CONSTRAINT nieobecnosci_pkey PRIMARY KEY (id_studenta, data);


--
-- Name: lekarze pk_lekarze; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.lekarze
    ADD CONSTRAINT pk_lekarze PRIMARY KEY (id);


--
-- Name: pacjenci pk_pacjenci; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.pacjenci
    ADD CONSTRAINT pk_pacjenci PRIMARY KEY (pesel);


--
-- Name: products pk_prod; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT pk_prod PRIMARY KEY (id_prod);


--
-- Name: recipes pk_reci; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT pk_reci PRIMARY KEY (id_rec);


--
-- Name: products_areatag products_areatag_id_key; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.products_areatag
    ADD CONSTRAINT products_areatag_id_key UNIQUE (id);


--
-- Name: publikacje publikacje_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.publikacje
    ADD CONSTRAINT publikacje_pkey PRIMARY KEY (id);


--
-- Name: restaurants_group_meals restaurants_group_meals_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.restaurants_group_meals
    ADD CONSTRAINT restaurants_group_meals_pkey PRIMARY KEY (id_group);


--
-- Name: restaurants_info restaurants_info_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.restaurants_info
    ADD CONSTRAINT restaurants_info_pkey PRIMARY KEY (id);


--
-- Name: restaurants_main restaurants_main_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.restaurants_main
    ADD CONSTRAINT restaurants_main_pkey PRIMARY KEY (id);


--
-- Name: shop_cards shop_cards_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shop_cards
    ADD CONSTRAINT shop_cards_pkey PRIMARY KEY (id_shop);


--
-- Name: shops_info shops_info_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_info
    ADD CONSTRAINT shops_info_pkey PRIMARY KEY (id);


--
-- Name: shops_main shops_main_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_main
    ADD CONSTRAINT shops_main_pkey PRIMARY KEY (id);


--
-- Name: species_taste species_taste_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.species_taste
    ADD CONSTRAINT species_taste_pkey PRIMARY KEY (id);


--
-- Name: studenci studenci_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.studenci
    ADD CONSTRAINT studenci_pkey PRIMARY KEY (id);


--
-- Name: typy typy_pkey; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.typy
    ADD CONSTRAINT typy_pkey PRIMARY KEY (typ);


--
-- Name: konferencje un_nazwa_rok; Type: CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.konferencje
    ADD CONSTRAINT un_nazwa_rok UNIQUE (nazwa, rok);


--
-- Name: lekarze chir; Type: RULE; Schema: public; Owner: daniil
--

CREATE RULE chir AS
    ON DELETE TO public.lekarze
   WHERE ('Chirurgia'::text IN ( SELECT specjalizacje.specjalizacja
           FROM public.specjalizacje
          WHERE (specjalizacje.id_lekarza = old.id))) DO INSTEAD NOTHING;


--
-- Name: chirurdzy chir; Type: RULE; Schema: public; Owner: daniil
--

CREATE RULE chir AS
    ON DELETE TO public.chirurdzy DO INSTEAD NOTHING;


--
-- Name: lekarze del_chr; Type: RULE; Schema: public; Owner: daniil
--

CREATE RULE del_chr AS
    ON DELETE TO public.lekarze
   WHERE ('Chirurgia'::text IN ( SELECT specjalizacje.specjalizacja
           FROM public.specjalizacje
          WHERE (specjalizacje.id_lekarza = old.id))) DO INSTEAD NOTHING;


--
-- Name: pediatrzy ped; Type: RULE; Schema: public; Owner: daniil
--

CREATE RULE ped AS
    ON INSERT TO public.pediatrzy DO INSTEAD ( INSERT INTO public.lekarze (id, imie, nazwisko)
  VALUES (new.id, new.imie, new.nazwisko);
 INSERT INTO public.specjalizacje (id_lekarza, specjalizacja)
  VALUES (new.id, 'Pediatria'::character varying);
);


--
-- Name: czasopisma add_naukowcy; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER add_naukowcy BEFORE INSERT ON public.czasopisma FOR EACH ROW EXECUTE PROCEDURE public.add_czasopisma();


--
-- Name: konferencje add_naukowcy; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER add_naukowcy BEFORE INSERT ON public.konferencje FOR EACH ROW EXECUTE PROCEDURE public.add_konferencje();


--
-- Name: naukowcy add_naukowcy; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER add_naukowcy BEFORE INSERT ON public.naukowcy FOR EACH ROW EXECUTE PROCEDURE public.add_naukowcy();


--
-- Name: wizyty add_visit; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER add_visit BEFORE INSERT ON public.wizyty FOR EACH ROW EXECUTE PROCEDURE public.add_visit();


--
-- Name: wizyty check_add; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER check_add BEFORE INSERT ON public.wizyty FOR EACH ROW EXECUTE PROCEDURE public.check_add();


--
-- Name: wizyty check_del; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER check_del BEFORE DELETE ON public.wizyty FOR EACH ROW EXECUTE PROCEDURE public.check_del();


--
-- Name: wizyty data; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER data BEFORE INSERT OR UPDATE ON public.wizyty FOR EACH ROW EXECUTE PROCEDURE public.data();


--
-- Name: wizyty del_check; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER del_check BEFORE DELETE ON public.wizyty FOR EACH ROW EXECUTE PROCEDURE public.del_check();


--
-- Name: pacjenci pesel; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER pesel BEFORE INSERT OR UPDATE ON public.pacjenci FOR EACH ROW EXECUTE PROCEDURE public.pesel();


--
-- Name: naukowcy update_naukowcy; Type: TRIGGER; Schema: public; Owner: daniil
--

CREATE TRIGGER update_naukowcy BEFORE UPDATE ON public.naukowcy FOR EACH ROW EXECUTE PROCEDURE public.update_naukowcy();


--
-- Name: shops_content_recipes da2isc_1; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_recipes
    ADD CONSTRAINT da2isc_1 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_products dai23sc_12; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT dai23sc_12 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_products dai23sc_122; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT dai23sc_122 FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_discounts_recipes daisc_1; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT daisc_1 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_recipes daisc_12; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT daisc_12 FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: shop_cards disc_1223; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shop_cards
    ADD CONSTRAINT disc_1223 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_products disc_12233; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT disc_12233 FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_discounts_products disc_12253; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT disc_12253 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: discounts disc_123; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT disc_123 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: discounts discounts_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: egzaminy egzaminy_id_studenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.egzaminy
    ADD CONSTRAINT egzaminy_id_studenta_fkey FOREIGN KEY (id_studenta) REFERENCES public.studenci(id);


--
-- Name: recipes_content fk_prod; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes_content
    ADD CONSTRAINT fk_prod FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: products_areatag fk_prod_area; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.products_areatag
    ADD CONSTRAINT fk_prod_area FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: drinks_taste fk_prod_dk; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.drinks_taste
    ADD CONSTRAINT fk_prod_dk FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: species_taste fk_prod_ss; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.species_taste
    ADD CONSTRAINT fk_prod_ss FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: species_taste fk_proed_ss; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.species_taste
    ADD CONSTRAINT fk_proed_ss FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: drinks_taste fk_pwrod_dk; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.drinks_taste
    ADD CONSTRAINT fk_pwrod_dk FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: recipes_areatag fk_rec_area; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes_areatag
    ADD CONSTRAINT fk_rec_area FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_content fk_rec_cont; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes_content
    ADD CONSTRAINT fk_rec_cont FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_tag fk_rec_tag; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes_tag
    ADD CONSTRAINT fk_rec_tag FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: shops_info fk_shoep_des; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_info
    ADD CONSTRAINT fk_shoep_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: restaurants_info fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.restaurants_info
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.restaurants_main(id);


--
-- Name: shops_info fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_info
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: specjalizacje fk_specjalizacje_lekarze; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.specjalizacje
    ADD CONSTRAINT fk_specjalizacje_lekarze FOREIGN KEY (id_lekarza) REFERENCES public.lekarze(id);


--
-- Name: wizyty fk_wizyty_lekarze; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.wizyty
    ADD CONSTRAINT fk_wizyty_lekarze FOREIGN KEY (lekarz) REFERENCES public.lekarze(id);


--
-- Name: wizyty fk_wizyty_pacjenci; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.wizyty
    ADD CONSTRAINT fk_wizyty_pacjenci FOREIGN KEY (pacjent) REFERENCES public.pacjenci(pesel);


--
-- Name: restaurants_group_meals fsese2222; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.restaurants_group_meals
    ADD CONSTRAINT fsese2222 FOREIGN KEY (id_restaurant) REFERENCES public.restaurants_main(id);


--
-- Name: group_meals_content group_meals_content_id_group_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.group_meals_content
    ADD CONSTRAINT group_meals_content_id_group_fkey FOREIGN KEY (id_group) REFERENCES public.restaurants_group_meals(id_group);


--
-- Name: group_meals_content group_meals_content_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.group_meals_content
    ADD CONSTRAINT group_meals_content_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: nieobecnosci nieobecnosci_id_studenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.nieobecnosci
    ADD CONSTRAINT nieobecnosci_id_studenta_fkey FOREIGN KEY (id_studenta) REFERENCES public.studenci(id);


--
-- Name: notatki notatki_id_studenta_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.notatki
    ADD CONSTRAINT notatki_id_studenta_fkey FOREIGN KEY (id_studenta) REFERENCES public.studenci(id);


--
-- Name: products_areatag prod_area_ee; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.products_areatag
    ADD CONSTRAINT prod_area_ee FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: publikacje publikacje_typ_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.publikacje
    ADD CONSTRAINT publikacje_typ_fkey FOREIGN KEY (typ) REFERENCES public.typy(typ);


--
-- Name: recipes_areatag rec_area_ee2; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes_areatag
    ADD CONSTRAINT rec_area_ee2 FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_tag rec_tag; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.recipes_tag
    ADD CONSTRAINT rec_tag FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: restaurants_group_meals restaurants_group_meals_id_restaurant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.restaurants_group_meals
    ADD CONSTRAINT restaurants_group_meals_id_restaurant_fkey FOREIGN KEY (id_restaurant) REFERENCES public.restaurants_main(id);


--
-- Name: shop_cards shop_cards_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shop_cards
    ADD CONSTRAINT shop_cards_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_products shops_content_products_id_prod_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT shops_content_products_id_prod_fkey FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_content_products shops_content_products_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT shops_content_products_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_recipes shops_content_recipes_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_recipes
    ADD CONSTRAINT shops_content_recipes_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: shops_content_recipes shops_content_recipes_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_content_recipes
    ADD CONSTRAINT shops_content_recipes_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_products shops_discounts_products_id_prod_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT shops_discounts_products_id_prod_fkey FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_discounts_products shops_discounts_products_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT shops_discounts_products_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_recipes shops_discounts_recipes_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT shops_discounts_recipes_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: shops_discounts_recipes shops_discounts_recipes_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: daniil
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT shops_discounts_recipes_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- PostgreSQL database dump complete
--

\connect postgres

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE postgres; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE postgres IS 'default administrative connection database';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- PostgreSQL database dump complete
--

\connect template1

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- PostgreSQL database dump complete
--

\connect test

SET default_transaction_read_only = off;

--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.16 (Ubuntu 10.16-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: prod_class_enum; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.prod_class_enum AS ENUM (
    'Drinks',
    'Solids',
    'Species'
);


ALTER TYPE public.prod_class_enum OWNER TO test;

--
-- Name: solid_taste_enum; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.solid_taste_enum AS ENUM (
    'Sweet',
    'Sour',
    'Salty',
    'Bitter',
    'None'
);


ALTER TYPE public.solid_taste_enum OWNER TO test;

--
-- Name: species_taste_enum; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.species_taste_enum AS ENUM (
    'Sweet',
    'Salty',
    'Bitter',
    'Sour',
    'Burning',
    'Spicy'
);


ALTER TYPE public.species_taste_enum OWNER TO test;

--
-- Name: fib(integer); Type: FUNCTION; Schema: public; Owner: test
--

CREATE FUNCTION public.fib(n integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
  begin
  if n>1 then
  return fib(n-1) + fib(n-2);
  else
  return n;
  end if; 
  end;
$$;


ALTER FUNCTION public.fib(n integer) OWNER TO test;

--
-- Name: getproductareatags(integer); Type: FUNCTION; Schema: public; Owner: test
--

CREATE FUNCTION public.getproductareatags(item integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
	begin
		return (select array_agg(area) 
		from products_areatag
		where id_prod = item);
	end;
$$;


ALTER FUNCTION public.getproductareatags(item integer) OWNER TO test;

--
-- Name: getproducttags(integer); Type: FUNCTION; Schema: public; Owner: test
--

CREATE FUNCTION public.getproducttags(item integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
	begin
		return (select array_agg(tag) 
		from products_tag
		where id_prod = item);
	end;
$$;


ALTER FUNCTION public.getproducttags(item integer) OWNER TO test;

--
-- Name: getrecipecontentproducts(integer); Type: FUNCTION; Schema: public; Owner: test
--

CREATE FUNCTION public.getrecipecontentproducts(id_rec_find integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
	begin
		return (select array_agg(array[id::varchar, weight::varchar, weight_type]) 
		from recipes_content_products
		where id_rec = id_rec_find);
	end;
$$;


ALTER FUNCTION public.getrecipecontentproducts(id_rec_find integer) OWNER TO test;

--
-- Name: getrecipecontentrecipes(integer); Type: FUNCTION; Schema: public; Owner: test
--

CREATE FUNCTION public.getrecipecontentrecipes(id_rec_find integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
	begin
		return (select array_agg(array[id::varchar, weight::varchar, weight_type]) 
		from recipes_content_recipes
		where id_rec = id_rec_find);
	end;
$$;


ALTER FUNCTION public.getrecipecontentrecipes(id_rec_find integer) OWNER TO test;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: discounts; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.discounts (
    id_shop integer NOT NULL,
    sum_for_discount numeric(10,0) NOT NULL,
    discount numeric,
    CONSTRAINT discounts_discount_check CHECK (((discount > (0)::numeric) AND (discount <= (100)::numeric)))
);


ALTER TABLE public.discounts OWNER TO test;

--
-- Name: drinks_info; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.drinks_info (
    id_prod integer NOT NULL,
    sugar boolean NOT NULL,
    colour character varying(15)
);


ALTER TABLE public.drinks_info OWNER TO test;

--
-- Name: drinks_taste; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.drinks_taste (
    id numeric(7,0) NOT NULL,
    sugar boolean NOT NULL,
    colour character varying(15)
);


ALTER TABLE public.drinks_taste OWNER TO test;

--
-- Name: for_id_products; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.for_id_products
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_products OWNER TO test;

--
-- Name: for_id_recipes; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.for_id_recipes
    START WITH 2
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_recipes OWNER TO test;

--
-- Name: for_id_restaurants; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.for_id_restaurants
    START WITH 2
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_restaurants OWNER TO test;

--
-- Name: for_id_shop; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.for_id_shop
    START WITH 1
    INCREMENT BY 2
    NO MINVALUE
    MAXVALUE 100000
    CACHE 1;


ALTER TABLE public.for_id_shop OWNER TO test;

--
-- Name: group_meals_content; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.group_meals_content (
    id_group integer NOT NULL,
    id_rec integer NOT NULL
);


ALTER TABLE public.group_meals_content OWNER TO test;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.orders (
    id_order integer NOT NULL,
    id_restaurant integer NOT NULL,
    id_rec integer NOT NULL,
    date date NOT NULL
);


ALTER TABLE public.orders OWNER TO test;

--
-- Name: products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products (
    id_prod integer NOT NULL,
    product_group character varying(60) NOT NULL,
    product_class public.prod_class_enum NOT NULL,
    name character varying(30) NOT NULL,
    description character varying(400),
    calories numeric(5,0) NOT NULL,
    CONSTRAINT products_calories_check CHECK ((calories >= (0)::numeric))
);


ALTER TABLE public.products OWNER TO test;

--
-- Name: products_areatag; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products_areatag (
    id_prod integer,
    area character varying(30) NOT NULL
);


ALTER TABLE public.products_areatag OWNER TO test;

--
-- Name: products_nutrient_additional; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products_nutrient_additional (
    id_prod integer NOT NULL,
    zinc real DEFAULT 0.00,
    iron real DEFAULT 0.00,
    calcium real DEFAULT 0.00,
    magnesium real DEFAULT 0.00,
    CONSTRAINT products_nutrient_additional_check CHECK (((calcium >= (0.00)::double precision) AND (iron <= (100000.00)::double precision))),
    CONSTRAINT products_nutrient_additional_iron_check CHECK (((iron >= (0.00)::double precision) AND (iron <= (100000.00)::double precision))),
    CONSTRAINT products_nutrient_additional_magnesium_check CHECK (((magnesium >= (0.00)::double precision) AND (magnesium <= (100000.00)::double precision))),
    CONSTRAINT products_nutrient_additional_zinc_check CHECK (((zinc >= (0.00)::double precision) AND (zinc <= (100000.00)::double precision)))
);


ALTER TABLE public.products_nutrient_additional OWNER TO test;

--
-- Name: products_nutrient_main; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products_nutrient_main (
    id_prod integer NOT NULL,
    fat smallint NOT NULL,
    saturated_fat smallint,
    protein smallint NOT NULL,
    carbo smallint NOT NULL,
    sugar smallint,
    CONSTRAINT products_nutrient_main_carbo_check CHECK (((carbo >= 0) AND (carbo <= 100))),
    CONSTRAINT products_nutrient_main_check CHECK (((saturated_fat IS NULL) OR (saturated_fat <= fat))),
    CONSTRAINT products_nutrient_main_check1 CHECK (((sugar IS NULL) OR ((sugar >= 0) AND (sugar <= carbo)))),
    CONSTRAINT products_nutrient_main_check2 CHECK ((((carbo + fat) + protein) <= 100)),
    CONSTRAINT products_nutrient_main_fat_check CHECK (((fat >= 0) AND (fat <= 100))),
    CONSTRAINT products_nutrient_main_protein_check CHECK (((protein >= 0) AND (protein <= 100)))
);


ALTER TABLE public.products_nutrient_main OWNER TO test;

--
-- Name: products_tag; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products_tag (
    id_prod integer,
    tag character varying(40) NOT NULL
);


ALTER TABLE public.products_tag OWNER TO test;

--
-- Name: products_vitamins; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.products_vitamins (
    id_prod integer NOT NULL,
    vitamin_a real DEFAULT 0.00,
    vitamin_b6 real DEFAULT 0.00,
    vitamin_b12 real DEFAULT 0.00,
    vitamin_c real DEFAULT 0.00,
    vitamin_e real DEFAULT 0.00,
    vitamin_k real DEFAULT 0.00,
    CONSTRAINT products_vitamins_check CHECK (((((((vitamin_a + vitamin_b6) + vitamin_b12) + vitamin_c) + vitamin_e) + vitamin_k) <= (100000.00)::double precision)),
    CONSTRAINT products_vitamins_vitamin_a_check CHECK (((vitamin_a >= (0.00)::double precision) AND (vitamin_a <= (100000.00)::double precision))),
    CONSTRAINT products_vitamins_vitamin_b12_check CHECK (((vitamin_b12 >= (0.00)::double precision) AND (vitamin_b12 <= (100000.00)::double precision))),
    CONSTRAINT products_vitamins_vitamin_b6_check CHECK (((vitamin_b6 >= (0.00)::double precision) AND (vitamin_b6 <= (100000.00)::double precision))),
    CONSTRAINT products_vitamins_vitamin_c_check CHECK (((vitamin_c >= (0.00)::double precision) AND (vitamin_c <= (100000.00)::double precision))),
    CONSTRAINT products_vitamins_vitamin_e_check CHECK (((vitamin_e >= (0.00)::double precision) AND (vitamin_e <= (100000.00)::double precision))),
    CONSTRAINT products_vitamins_vitamin_k_check CHECK (((vitamin_k >= (0.00)::double precision) AND (vitamin_k <= (100000.00)::double precision)))
);


ALTER TABLE public.products_vitamins OWNER TO test;

--
-- Name: receipts; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.receipts (
    id_rec numeric(7,0) NOT NULL,
    id_of_restaurants numeric(7,0),
    name character varying(30) NOT NULL,
    sum_weight numeric(7,0) NOT NULL,
    sum_calories numeric(7,0) NOT NULL,
    description character varying(300),
    links character varying(300),
    CONSTRAINT receipts_sum_calories_check CHECK ((sum_calories >= (0)::numeric)),
    CONSTRAINT receipts_sum_weight_check CHECK ((sum_weight >= (0)::numeric))
);


ALTER TABLE public.receipts OWNER TO test;

--
-- Name: receipts_content; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.receipts_content (
    id_rec numeric(7,0) NOT NULL,
    id_prod numeric(7,0) NOT NULL,
    weight numeric(6,0) NOT NULL,
    weight_type character(4) NOT NULL,
    CONSTRAINT receipts_content_weight_check CHECK ((weight >= (0)::numeric)),
    CONSTRAINT receipts_content_weight_type_check CHECK ((weight_type = ANY (ARRAY['g'::bpchar, 'ml'::bpchar])))
);


ALTER TABLE public.receipts_content OWNER TO test;

--
-- Name: recipes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes (
    id_rec integer NOT NULL,
    name character varying(200) NOT NULL,
    prep_time smallint DEFAULT 0,
    sum_weight numeric(7,0) NOT NULL,
    sum_calories numeric(7,0) NOT NULL,
    description character varying(1000),
    instruction character varying(1500),
    CONSTRAINT recipes_prep_time_check CHECK (((prep_time >= 0) AND (prep_time <= 1000))),
    CONSTRAINT recipes_sum_calories_check CHECK ((sum_calories >= (0)::numeric)),
    CONSTRAINT recipes_sum_weight_check CHECK ((sum_weight >= (0)::numeric))
);


ALTER TABLE public.recipes OWNER TO test;

--
-- Name: recipes_areatag; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes_areatag (
    id integer,
    area character varying(40) NOT NULL
);


ALTER TABLE public.recipes_areatag OWNER TO test;

--
-- Name: recipes_content; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes_content (
    id_rec integer NOT NULL,
    id_prod integer NOT NULL,
    weight numeric(6,0) NOT NULL,
    weight_type character(4) NOT NULL,
    CONSTRAINT recipes_content_weight_check CHECK ((weight >= (0)::numeric)),
    CONSTRAINT recipes_content_weight_type_check CHECK ((weight_type = ANY (ARRAY['g'::bpchar, 'ml'::bpchar])))
);


ALTER TABLE public.recipes_content OWNER TO test;

--
-- Name: recipes_content_products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes_content_products (
    id_rec integer NOT NULL,
    id integer NOT NULL,
    weight numeric(6,0) NOT NULL,
    weight_type character(4) NOT NULL,
    CONSTRAINT recipes_content_products_weight_check CHECK ((weight >= (0)::numeric)),
    CONSTRAINT recipes_content_products_weight_type_check CHECK ((weight_type = ANY (ARRAY['g'::bpchar, 'ml'::bpchar])))
);


ALTER TABLE public.recipes_content_products OWNER TO test;

--
-- Name: recipes_content_recipes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes_content_recipes (
    id_rec integer NOT NULL,
    id integer NOT NULL,
    weight numeric(6,0) NOT NULL,
    weight_type character(4) NOT NULL,
    CONSTRAINT recipes_content_recipes_check CHECK ((id <> id_rec)),
    CONSTRAINT recipes_content_recipes_weight_check CHECK ((weight >= (0)::numeric)),
    CONSTRAINT recipes_content_recipes_weight_type_check CHECK ((weight_type = ANY (ARRAY['g'::bpchar, 'ml'::bpchar])))
);


ALTER TABLE public.recipes_content_recipes OWNER TO test;

--
-- Name: recipes_nutrient_main; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes_nutrient_main (
    id_rec integer NOT NULL,
    fat smallint NOT NULL,
    protein smallint NOT NULL,
    carbo smallint NOT NULL,
    sugar smallint,
    CONSTRAINT carbo1 CHECK (((carbo >= 0) AND (carbo <= 100))),
    CONSTRAINT fat1 CHECK (((fat >= 0) AND (fat <= 100))),
    CONSTRAINT protein1 CHECK (((protein >= 0) AND (protein <= 100))),
    CONSTRAINT sugar1 CHECK (((sugar IS NULL) OR ((sugar >= 0) AND (sugar <= carbo)))),
    CONSTRAINT sum_check1 CHECK ((((carbo + fat) + protein) <= 100))
);


ALTER TABLE public.recipes_nutrient_main OWNER TO test;

--
-- Name: recipes_tag; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recipes_tag (
    id integer,
    tag character varying(30) NOT NULL
);


ALTER TABLE public.recipes_tag OWNER TO test;

--
-- Name: restaurants_geoposition; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_geoposition (
    id integer NOT NULL,
    geoposition character varying(30) NOT NULL
);


ALTER TABLE public.restaurants_geoposition OWNER TO test;

--
-- Name: restaurants_group_meals; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_group_meals (
    id_restaurant integer NOT NULL,
    id_group integer NOT NULL,
    cena numeric(10,0) NOT NULL,
    min_cena numeric(10,0) NOT NULL,
    max_cena numeric(10,0) NOT NULL,
    CONSTRAINT con_min_max CHECK ((max_cena >= min_cena))
);


ALTER TABLE public.restaurants_group_meals OWNER TO test;

--
-- Name: restaurants_info; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_info (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    stars integer,
    description character varying(100),
    food_delivery boolean NOT NULL,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open))),
    CONSTRAINT restaurants_info_stars_check CHECK (((stars <= 5) OR (stars IS NULL)))
);


ALTER TABLE public.restaurants_info OWNER TO test;

--
-- Name: restaurants_main; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_main (
    id integer NOT NULL,
    name character varying(10) NOT NULL,
    geoposition character varying(30) NOT NULL,
    adres character varying(100)
);


ALTER TABLE public.restaurants_main OWNER TO test;

--
-- Name: restaurants_plan_saturday; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_plan_saturday (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.restaurants_plan_saturday OWNER TO test;

--
-- Name: restaurants_plan_sunday; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_plan_sunday (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.restaurants_plan_sunday OWNER TO test;

--
-- Name: restaurants_plan_weekdays; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.restaurants_plan_weekdays (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.restaurants_plan_weekdays OWNER TO test;

--
-- Name: shop_cards; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shop_cards (
    id_shop integer NOT NULL,
    is_accumulative boolean NOT NULL
);


ALTER TABLE public.shop_cards OWNER TO test;

--
-- Name: shop_goods; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shop_goods (
    id_shop numeric(7,0) NOT NULL,
    id_group numeric(7,0) NOT NULL,
    id_item numeric(7,0) NOT NULL
);


ALTER TABLE public.shop_goods OWNER TO test;

--
-- Name: shops_content_products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_content_products (
    id_shop integer NOT NULL,
    id_prod integer NOT NULL,
    cena numeric(10,0) NOT NULL,
    count integer NOT NULL,
    CONSTRAINT shops_content_products_count_check CHECK ((count >= 1))
);


ALTER TABLE public.shops_content_products OWNER TO test;

--
-- Name: shops_content_recipes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_content_recipes (
    id_shop integer NOT NULL,
    id_rec integer NOT NULL,
    cena numeric(10,0) NOT NULL,
    count integer NOT NULL,
    CONSTRAINT shops_content_recipes_count_check CHECK ((count >= 1))
);


ALTER TABLE public.shops_content_recipes OWNER TO test;

--
-- Name: shops_discounts_products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_discounts_products (
    id_shop integer NOT NULL,
    id_prod integer NOT NULL,
    min_count integer
);


ALTER TABLE public.shops_discounts_products OWNER TO test;

--
-- Name: shops_discounts_recipes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_discounts_recipes (
    id_shop integer NOT NULL,
    id_rec integer NOT NULL,
    discount_val numeric(4,0) NOT NULL,
    min_count integer,
    CONSTRAINT shops_discounts_recipes_discount_val_check CHECK (((discount_val > (0)::numeric) AND (discount_val <= (100)::numeric)))
);


ALTER TABLE public.shops_discounts_recipes OWNER TO test;

--
-- Name: shops_geoposition; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_geoposition (
    id integer NOT NULL,
    geoposition character varying(30) NOT NULL
);


ALTER TABLE public.shops_geoposition OWNER TO test;

--
-- Name: shops_groups; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_groups (
    id_of_restaurant numeric(7,0) NOT NULL,
    id_of_group numeric(7,0) NOT NULL,
    cena numeric(10,0) NOT NULL,
    min_cena numeric(10,0) NOT NULL,
    max_cena numeric(10,0) NOT NULL,
    CONSTRAINT con_min_max CHECK ((max_cena >= min_cena))
);


ALTER TABLE public.shops_groups OWNER TO test;

--
-- Name: shops_info; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_info (
    id integer NOT NULL,
    description character varying(100),
    food_delivery boolean NOT NULL
);


ALTER TABLE public.shops_info OWNER TO test;

--
-- Name: shops_main; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_main (
    id integer NOT NULL,
    name character varying(10) NOT NULL,
    adres character varying(100)
);


ALTER TABLE public.shops_main OWNER TO test;

--
-- Name: shops_plan_saturday; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_plan_saturday (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.shops_plan_saturday OWNER TO test;

--
-- Name: shops_plan_sunday; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_plan_sunday (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.shops_plan_sunday OWNER TO test;

--
-- Name: shops_plan_weekdays; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.shops_plan_weekdays (
    id integer NOT NULL,
    open time without time zone,
    close time without time zone,
    CONSTRAINT con_open_close CHECK ((((open IS NULL) AND (close IS NULL)) OR (close > open)))
);


ALTER TABLE public.shops_plan_weekdays OWNER TO test;

--
-- Name: solids_full; Type: VIEW; Schema: public; Owner: test
--

CREATE VIEW public.solids_full AS
 SELECT products.id_prod,
    products.product_group,
    products.product_class,
    products.name,
    products.description,
    products.calories,
    products_nutrient_main.fat,
    products_nutrient_main.saturated_fat,
    products_nutrient_main.protein,
    products_nutrient_main.carbo,
    products_nutrient_main.sugar,
    products_nutrient_additional.zinc,
    products_nutrient_additional.iron,
    products_nutrient_additional.calcium,
    products_nutrient_additional.magnesium,
    products_vitamins.vitamin_a,
    products_vitamins.vitamin_b6,
    products_vitamins.vitamin_b12,
    products_vitamins.vitamin_c,
    products_vitamins.vitamin_e,
    products_vitamins.vitamin_k
   FROM (((public.products
     JOIN public.products_nutrient_main USING (id_prod))
     JOIN public.products_nutrient_additional USING (id_prod))
     JOIN public.products_vitamins USING (id_prod));


ALTER TABLE public.solids_full OWNER TO test;

--
-- Name: solids_taste; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.solids_taste (
    id numeric(7,0) NOT NULL,
    taste public.solid_taste_enum
);


ALTER TABLE public.solids_taste OWNER TO test;

--
-- Name: species_taste; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.species_taste (
    id_prod integer NOT NULL,
    taste public.species_taste_enum NOT NULL
);


ALTER TABLE public.species_taste OWNER TO test;

--
-- Name: species; Type: VIEW; Schema: public; Owner: test
--

CREATE VIEW public.species AS
 SELECT species_taste.id_prod,
    species_taste.taste,
    products.product_group,
    products.product_class,
    products.name,
    products.description,
    products.calories
   FROM (public.species_taste
     JOIN public.products USING (id_prod));


ALTER TABLE public.species OWNER TO test;

--
-- Name: water_products; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.water_products (
    id numeric(10,0),
    has_sugar boolean
);


ALTER TABLE public.water_products OWNER TO test;

--
-- Data for Name: discounts; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.discounts (id_shop, sum_for_discount, discount) FROM stdin;
\.


--
-- Data for Name: drinks_info; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.drinks_info (id_prod, sugar, colour) FROM stdin;
\.


--
-- Data for Name: drinks_taste; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.drinks_taste (id, sugar, colour) FROM stdin;
\.


--
-- Data for Name: group_meals_content; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.group_meals_content (id_group, id_rec) FROM stdin;
5	12
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.orders (id_order, id_restaurant, id_rec, date) FROM stdin;
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products (id_prod, product_group, product_class, name, description, calories) FROM stdin;
1	fruits	Solids	apple	A very useful product, pleasant	47
3	fruits	Solids	orange	Orange has an orange colour	43
5	meat	Solids	beef	One of the most common types of meat	187
7	minerals	Species	salt	An indispensable product both in the Middle Ages and today	0
\.


--
-- Data for Name: products_areatag; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products_areatag (id_prod, area) FROM stdin;
1	Polska
1	Włochy
7	Worldwide
\.


--
-- Data for Name: products_nutrient_additional; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products_nutrient_additional (id_prod, zinc, iron, calcium, magnesium) FROM stdin;
\.


--
-- Data for Name: products_nutrient_main; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products_nutrient_main (id_prod, fat, saturated_fat, protein, carbo, sugar) FROM stdin;
\.


--
-- Data for Name: products_tag; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products_tag (id_prod, tag) FROM stdin;
1	Healthy food
\.


--
-- Data for Name: products_vitamins; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.products_vitamins (id_prod, vitamin_a, vitamin_b6, vitamin_b12, vitamin_c, vitamin_e, vitamin_k) FROM stdin;
\.


--
-- Data for Name: receipts; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.receipts (id_rec, id_of_restaurants, name, sum_weight, sum_calories, description, links) FROM stdin;
\.


--
-- Data for Name: receipts_content; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.receipts_content (id_rec, id_prod, weight, weight_type) FROM stdin;
\.


--
-- Data for Name: recipes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes (id_rec, name, prep_time, sum_weight, sum_calories, description, instruction) FROM stdin;
12	asfds	234	234	234234	sdfdsf	sfdsfsd
\.


--
-- Data for Name: recipes_areatag; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes_areatag (id, area) FROM stdin;
\.


--
-- Data for Name: recipes_content; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes_content (id_rec, id_prod, weight, weight_type) FROM stdin;
\.


--
-- Data for Name: recipes_content_products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes_content_products (id_rec, id, weight, weight_type) FROM stdin;
\.


--
-- Data for Name: recipes_content_recipes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes_content_recipes (id_rec, id, weight, weight_type) FROM stdin;
\.


--
-- Data for Name: recipes_nutrient_main; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes_nutrient_main (id_rec, fat, protein, carbo, sugar) FROM stdin;
\.


--
-- Data for Name: recipes_tag; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.recipes_tag (id, tag) FROM stdin;
\.


--
-- Data for Name: restaurants_geoposition; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_geoposition (id, geoposition) FROM stdin;
\.


--
-- Data for Name: restaurants_group_meals; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_group_meals (id_restaurant, id_group, cena, min_cena, max_cena) FROM stdin;
10	5	3424	1213	4535
\.


--
-- Data for Name: restaurants_info; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_info (id, open, close, stars, description, food_delivery) FROM stdin;
\.


--
-- Data for Name: restaurants_main; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_main (id, name, geoposition, adres) FROM stdin;
10	Andrew	CS	Dust
\.


--
-- Data for Name: restaurants_plan_saturday; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_plan_saturday (id, open, close) FROM stdin;
\.


--
-- Data for Name: restaurants_plan_sunday; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_plan_sunday (id, open, close) FROM stdin;
\.


--
-- Data for Name: restaurants_plan_weekdays; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.restaurants_plan_weekdays (id, open, close) FROM stdin;
\.


--
-- Data for Name: shop_cards; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shop_cards (id_shop, is_accumulative) FROM stdin;
\.


--
-- Data for Name: shop_goods; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shop_goods (id_shop, id_group, id_item) FROM stdin;
\.


--
-- Data for Name: shops_content_products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_content_products (id_shop, id_prod, cena, count) FROM stdin;
\.


--
-- Data for Name: shops_content_recipes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_content_recipes (id_shop, id_rec, cena, count) FROM stdin;
\.


--
-- Data for Name: shops_discounts_products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_discounts_products (id_shop, id_prod, min_count) FROM stdin;
\.


--
-- Data for Name: shops_discounts_recipes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_discounts_recipes (id_shop, id_rec, discount_val, min_count) FROM stdin;
\.


--
-- Data for Name: shops_geoposition; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_geoposition (id, geoposition) FROM stdin;
\.


--
-- Data for Name: shops_groups; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_groups (id_of_restaurant, id_of_group, cena, min_cena, max_cena) FROM stdin;
\.


--
-- Data for Name: shops_info; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_info (id, description, food_delivery) FROM stdin;
\.


--
-- Data for Name: shops_main; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_main (id, name, adres) FROM stdin;
\.


--
-- Data for Name: shops_plan_saturday; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_plan_saturday (id, open, close) FROM stdin;
\.


--
-- Data for Name: shops_plan_sunday; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_plan_sunday (id, open, close) FROM stdin;
\.


--
-- Data for Name: shops_plan_weekdays; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.shops_plan_weekdays (id, open, close) FROM stdin;
\.


--
-- Data for Name: solids_taste; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.solids_taste (id, taste) FROM stdin;
\.


--
-- Data for Name: species_taste; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.species_taste (id_prod, taste) FROM stdin;
7	Salty
\.


--
-- Data for Name: water_products; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.water_products (id, has_sugar) FROM stdin;
\.


--
-- Name: for_id_products; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.for_id_products', 7, true);


--
-- Name: for_id_recipes; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.for_id_recipes', 2, false);


--
-- Name: for_id_restaurants; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.for_id_restaurants', 2, false);


--
-- Name: for_id_shop; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.for_id_shop', 1, false);


--
-- Name: discounts discounts_id_shop_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_id_shop_key UNIQUE (id_shop);


--
-- Name: drinks_info drinks_info_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.drinks_info
    ADD CONSTRAINT drinks_info_pkey PRIMARY KEY (id_prod);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id_order, id_restaurant, id_rec, date);


--
-- Name: products pk_prod; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT pk_prod PRIMARY KEY (id_prod);


--
-- Name: receipts pk_rec; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.receipts
    ADD CONSTRAINT pk_rec PRIMARY KEY (id_rec);


--
-- Name: recipes pk_reci; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes
    ADD CONSTRAINT pk_reci PRIMARY KEY (id_rec);


--
-- Name: products_nutrient_additional products_nutrient_additional_id_prod_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_nutrient_additional
    ADD CONSTRAINT products_nutrient_additional_id_prod_key UNIQUE (id_prod);


--
-- Name: products_nutrient_main products_nutrient_main_id_prod_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_nutrient_main
    ADD CONSTRAINT products_nutrient_main_id_prod_key UNIQUE (id_prod);


--
-- Name: products_vitamins products_vitamins_id_prod_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_vitamins
    ADD CONSTRAINT products_vitamins_id_prod_key UNIQUE (id_prod);


--
-- Name: recipes_nutrient_main recipes_nutrient_main_id_rec_key; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_nutrient_main
    ADD CONSTRAINT recipes_nutrient_main_id_rec_key UNIQUE (id_rec);


--
-- Name: restaurants_geoposition restaurants_geoposition_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_geoposition
    ADD CONSTRAINT restaurants_geoposition_pkey PRIMARY KEY (id);


--
-- Name: restaurants_group_meals restaurants_group_meals_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_group_meals
    ADD CONSTRAINT restaurants_group_meals_pkey PRIMARY KEY (id_group);


--
-- Name: restaurants_info restaurants_info_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_info
    ADD CONSTRAINT restaurants_info_pkey PRIMARY KEY (id);


--
-- Name: restaurants_main restaurants_main_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_main
    ADD CONSTRAINT restaurants_main_pkey PRIMARY KEY (id);


--
-- Name: restaurants_plan_saturday restaurants_plan_saturday_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_plan_saturday
    ADD CONSTRAINT restaurants_plan_saturday_pkey PRIMARY KEY (id);


--
-- Name: restaurants_plan_sunday restaurants_plan_sunday_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_plan_sunday
    ADD CONSTRAINT restaurants_plan_sunday_pkey PRIMARY KEY (id);


--
-- Name: restaurants_plan_weekdays restaurants_plan_weekdays_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_plan_weekdays
    ADD CONSTRAINT restaurants_plan_weekdays_pkey PRIMARY KEY (id);


--
-- Name: shop_cards shop_cards_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shop_cards
    ADD CONSTRAINT shop_cards_pkey PRIMARY KEY (id_shop);


--
-- Name: shops_geoposition shops_geoposition_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_geoposition
    ADD CONSTRAINT shops_geoposition_pkey PRIMARY KEY (id);


--
-- Name: shops_groups shops_groups_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_groups
    ADD CONSTRAINT shops_groups_pkey PRIMARY KEY (id_of_group);


--
-- Name: shops_info shops_info_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_info
    ADD CONSTRAINT shops_info_pkey PRIMARY KEY (id);


--
-- Name: shops_main shops_main_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_main
    ADD CONSTRAINT shops_main_pkey PRIMARY KEY (id);


--
-- Name: shops_plan_saturday shops_plan_saturday_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_plan_saturday
    ADD CONSTRAINT shops_plan_saturday_pkey PRIMARY KEY (id);


--
-- Name: shops_plan_sunday shops_plan_sunday_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_plan_sunday
    ADD CONSTRAINT shops_plan_sunday_pkey PRIMARY KEY (id);


--
-- Name: shops_plan_weekdays shops_plan_weekdays_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_plan_weekdays
    ADD CONSTRAINT shops_plan_weekdays_pkey PRIMARY KEY (id);


--
-- Name: solids_taste solids_taste_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.solids_taste
    ADD CONSTRAINT solids_taste_pkey PRIMARY KEY (id);


--
-- Name: species_taste species_taste_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.species_taste
    ADD CONSTRAINT species_taste_pkey PRIMARY KEY (id_prod);


--
-- Name: shops_content_recipes da2isc_1; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_recipes
    ADD CONSTRAINT da2isc_1 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_products dai23sc_12; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT dai23sc_12 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_products dai23sc_122; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT dai23sc_122 FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_discounts_recipes daisc_1; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT daisc_1 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_recipes daisc_12; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT daisc_12 FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: shop_cards disc_1223; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shop_cards
    ADD CONSTRAINT disc_1223 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_products disc_12233; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT disc_12233 FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_discounts_products disc_12253; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT disc_12253 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: discounts disc_123; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT disc_123 FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: discounts discounts_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.discounts
    ADD CONSTRAINT discounts_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: products_nutrient_main fk_nut_main; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_nutrient_main
    ADD CONSTRAINT fk_nut_main FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: products_nutrient_additional fk_nut_main; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_nutrient_additional
    ADD CONSTRAINT fk_nut_main FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: recipes_nutrient_main fk_nut_main; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_nutrient_main
    ADD CONSTRAINT fk_nut_main FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_content_products fk_prod; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_content_products
    ADD CONSTRAINT fk_prod FOREIGN KEY (id) REFERENCES public.products(id_prod);


--
-- Name: recipes_content_recipes fk_prod; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_content_recipes
    ADD CONSTRAINT fk_prod FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: products_areatag fk_prod_area; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_areatag
    ADD CONSTRAINT fk_prod_area FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: products_tag fk_prod_area; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_tag
    ADD CONSTRAINT fk_prod_area FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: drinks_info fk_prod_dk; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.drinks_info
    ADD CONSTRAINT fk_prod_dk FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: species_taste fk_prod_ss; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.species_taste
    ADD CONSTRAINT fk_prod_ss FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: species_taste fk_proed_ss; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.species_taste
    ADD CONSTRAINT fk_proed_ss FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: drinks_info fk_pwrod_dk; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.drinks_info
    ADD CONSTRAINT fk_pwrod_dk FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: recipes_areatag fk_rec_area; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_areatag
    ADD CONSTRAINT fk_rec_area FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: receipts_content fk_rec_cont; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.receipts_content
    ADD CONSTRAINT fk_rec_cont FOREIGN KEY (id_rec) REFERENCES public.receipts(id_rec);


--
-- Name: recipes_content_products fk_rec_cont; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_content_products
    ADD CONSTRAINT fk_rec_cont FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_content_recipes fk_rec_cont; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_content_recipes
    ADD CONSTRAINT fk_rec_cont FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_tag fk_rec_tag; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_tag
    ADD CONSTRAINT fk_rec_tag FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: shops_info fk_shoep_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_info
    ADD CONSTRAINT fk_shoep_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: restaurants_info fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_info
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.restaurants_main(id);


--
-- Name: restaurants_plan_weekdays fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_plan_weekdays
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.restaurants_main(id);


--
-- Name: restaurants_plan_saturday fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_plan_saturday
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.restaurants_main(id);


--
-- Name: restaurants_plan_sunday fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_plan_sunday
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.restaurants_main(id);


--
-- Name: shops_info fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_info
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: shops_plan_weekdays fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_plan_weekdays
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: shops_plan_saturday fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_plan_saturday
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: shops_plan_sunday fk_shop_des; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_plan_sunday
    ADD CONSTRAINT fk_shop_des FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: products_vitamins fk_vit_main; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_vitamins
    ADD CONSTRAINT fk_vit_main FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: restaurants_group_meals fsese2222; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_group_meals
    ADD CONSTRAINT fsese2222 FOREIGN KEY (id_restaurant) REFERENCES public.restaurants_main(id);


--
-- Name: group_meals_content group_meals_content_id_group_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.group_meals_content
    ADD CONSTRAINT group_meals_content_id_group_fkey FOREIGN KEY (id_group) REFERENCES public.restaurants_group_meals(id_group);


--
-- Name: group_meals_content group_meals_content_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.group_meals_content
    ADD CONSTRAINT group_meals_content_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: orders orders_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: orders orders_id_restaurant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_id_restaurant_fkey FOREIGN KEY (id_restaurant) REFERENCES public.restaurants_main(id);


--
-- Name: products_areatag prod_area_ee; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.products_areatag
    ADD CONSTRAINT prod_area_ee FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: recipes_areatag rec_area_ee2; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_areatag
    ADD CONSTRAINT rec_area_ee2 FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: recipes_tag rec_tag; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recipes_tag
    ADD CONSTRAINT rec_tag FOREIGN KEY (id) REFERENCES public.recipes(id_rec);


--
-- Name: restaurants_geoposition restaurants_geoposition_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_geoposition
    ADD CONSTRAINT restaurants_geoposition_id_fkey FOREIGN KEY (id) REFERENCES public.restaurants_main(id);


--
-- Name: restaurants_group_meals restaurants_group_meals_id_restaurant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.restaurants_group_meals
    ADD CONSTRAINT restaurants_group_meals_id_restaurant_fkey FOREIGN KEY (id_restaurant) REFERENCES public.restaurants_main(id);


--
-- Name: shop_cards shop_cards_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shop_cards
    ADD CONSTRAINT shop_cards_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shop_goods shop_goods_id_group_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shop_goods
    ADD CONSTRAINT shop_goods_id_group_fkey FOREIGN KEY (id_group) REFERENCES public.shops_groups(id_of_group);


--
-- Name: shops_content_products shops_content_products_id_prod_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT shops_content_products_id_prod_fkey FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_content_products shops_content_products_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_products
    ADD CONSTRAINT shops_content_products_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_content_recipes shops_content_recipes_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_recipes
    ADD CONSTRAINT shops_content_recipes_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: shops_content_recipes shops_content_recipes_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_content_recipes
    ADD CONSTRAINT shops_content_recipes_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_products shops_discounts_products_id_prod_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT shops_discounts_products_id_prod_fkey FOREIGN KEY (id_prod) REFERENCES public.products(id_prod);


--
-- Name: shops_discounts_products shops_discounts_products_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_products
    ADD CONSTRAINT shops_discounts_products_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_discounts_recipes shops_discounts_recipes_id_rec_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT shops_discounts_recipes_id_rec_fkey FOREIGN KEY (id_rec) REFERENCES public.recipes(id_rec);


--
-- Name: shops_discounts_recipes shops_discounts_recipes_id_shop_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_discounts_recipes
    ADD CONSTRAINT shops_discounts_recipes_id_shop_fkey FOREIGN KEY (id_shop) REFERENCES public.shops_main(id);


--
-- Name: shops_geoposition shops_geoposition_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.shops_geoposition
    ADD CONSTRAINT shops_geoposition_id_fkey FOREIGN KEY (id) REFERENCES public.shops_main(id);


--
-- Name: water_products water_products_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.water_products
    ADD CONSTRAINT water_products_id_fkey FOREIGN KEY (id) REFERENCES public.solids_taste(id);


--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

