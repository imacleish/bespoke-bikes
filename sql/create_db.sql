-- Database: bespoked_bikes

DROP DATABASE IF EXISTS bespoked_bikes;

CREATE DATABASE bespoked_bikes
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
\connect bespoked_bikes;
	
-- SEQUENCE: public.customer_id_seq

DROP SEQUENCE IF EXISTS public.customer_id_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS public.customer_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.customer_id_seq
    OWNER TO postgres;
	
-- Table: public.customer

DROP TABLE IF EXISTS public.customer CASCADE;

CREATE TABLE IF NOT EXISTS public.customer
(
    id integer NOT NULL DEFAULT nextval('customer_id_seq'::regclass),
    first_name text COLLATE pg_catalog."default" NOT NULL,
    last_name text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default" NOT NULL,
    phone text COLLATE pg_catalog."default" NOT NULL,
    start_date date NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customer
    OWNER to postgres;
	
-- SEQUENCE: public.discount_id_seq

DROP SEQUENCE IF EXISTS public.discount_id_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS public.discount_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.discount_id_seq
    OWNER TO postgres;
	
-- Table: public.discount

DROP TABLE IF EXISTS public.discount CASCADE;

CREATE TABLE IF NOT EXISTS public.discount
(
    id integer NOT NULL DEFAULT nextval('discount_id_seq'::regclass),
    product integer NOT NULL,
    begin_date date NOT NULL,
    end_date date NOT NULL,
    discount_percent double precision NOT NULL,
    CONSTRAINT discount_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.discount
    OWNER to postgres;
	
	
-- SEQUENCE: public.product_id_seq

DROP SEQUENCE IF EXISTS public.product_id_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS public.product_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.product_id_seq
    OWNER TO postgres;
	
-- Table: public.product

DROP TABLE IF EXISTS public.product CASCADE;

CREATE TABLE IF NOT EXISTS public.product
(
    id integer NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    manufacturer text COLLATE pg_catalog."default" NOT NULL,
    style text COLLATE pg_catalog."default" NOT NULL,
    purchase_price double precision NOT NULL,
    sale_price double precision NOT NULL,
    quantity integer NOT NULL,
    commission double precision NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.product
    OWNER to postgres;
	
-- SEQUENCE: public.sale_id_seq

DROP SEQUENCE IF EXISTS public.sale_id_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS public.sale_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.sale_id_seq
    OWNER TO postgres;
	
-- Table: public.sale

DROP TABLE IF EXISTS public.sale CASCADE;

CREATE TABLE IF NOT EXISTS public.sale
(
    id integer NOT NULL DEFAULT nextval('sale_id_seq'::regclass),
    product integer NOT NULL,
    salesperson integer NOT NULL,
    customer integer NOT NULL,
    sale_date date NOT NULL,
    CONSTRAINT sale_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sale
    OWNER to postgres;
	

-- SEQUENCE: public.salesperson_id_seq

DROP SEQUENCE IF EXISTS public.salesperson_id_seq CASCADE;

CREATE SEQUENCE IF NOT EXISTS public.salesperson_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER SEQUENCE public.salesperson_id_seq
    OWNER TO postgres;
	
-- Table: public.salesperson

DROP TABLE IF EXISTS public.salesperson CASCADE;

CREATE TABLE IF NOT EXISTS public.salesperson
(
    id integer NOT NULL DEFAULT nextval('salesperson_id_seq'::regclass),
    first_name text COLLATE pg_catalog."default" NOT NULL,
    last_name text COLLATE pg_catalog."default" NOT NULL,
    address text COLLATE pg_catalog."default" NOT NULL,
    phone text COLLATE pg_catalog."default" NOT NULL,
    start_date date NOT NULL,
    term_date date,
    manager text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT salesperson_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.salesperson
    OWNER to postgres;
	
-- Populate tables


INSERT INTO public.customer (first_name, last_name, address, phone, start_date)
VALUES ('Christina', 'John', '100 Main Street', '823-555-2983', '2021-07-11');

INSERT INTO public.customer (first_name, last_name, address, phone, start_date)
VALUES ('Philippa', 'Price', '200 North Street', '100-555-9836', '2021-03-20');

INSERT INTO public.customer (first_name, last_name, address, phone, start_date)
VALUES ('Milburn', 'Beckett', '300 South Street', '953-555-1820', '2022-02-07');

INSERT INTO public.customer (first_name, last_name, address, phone, start_date)
VALUES ('Christa', 'Rexanne', '400 East Street', '519-555-8921', '2020-11-29');

INSERT INTO public.customer (first_name, last_name, address, phone, start_date)
VALUES ('Zeph', 'Tessie', '500 West Street', '843-555-2348', '2022-05-01');




INSERT INTO public.product (name, manufacturer, style, purchase_price, sale_price, quantity, commission)
VALUES ('CYBIC 2022 Mountain Bike', 'CYBIC', 'Mountain', 69.99, 109.99, 15, 0.2);

INSERT INTO public.product (name, manufacturer, style, purchase_price, sale_price, quantity, commission)
VALUES ('Legion Freestyle', 'Mongoose', 'BMX', 199.99, 329.99, 8, 0.15);

INSERT INTO public.product (name, manufacturer, style, purchase_price, sale_price, quantity, commission)
VALUES ('Wayfarer', 'Schwinn', 'Cruiser', 429.99, 739.99, 5, 0.1);

INSERT INTO public.product (name, manufacturer, style, purchase_price, sale_price, quantity, commission)
VALUES ('High Timber', 'Schwinn', 'Mountain', 129.99, 279.99, 12, 0.25);



	
INSERT INTO public.salesperson (first_name, last_name, address, phone, start_date, term_date, manager) 
VALUES ('Peter', 'Venkman', '100 Peachtree Street', '111-111-1111', '2022-01-15', NULL, 'Gozer');

INSERT INTO public.salesperson (first_name, last_name, address, phone, start_date, term_date, manager) 
VALUES ('Raymond', 'Stantz', '200 Peachtree Lane', '222-222-2222', '2021-08-09', NULL, 'The Mayor');

INSERT INTO public.salesperson (first_name, last_name, address, phone, start_date, term_date, manager) 
VALUES ('Egon', 'Spengler', '300 Peachtree Drive', '333-333-3333', '2020-04-28', '2022-06-06', 'Slimer');

INSERT INTO public.salesperson (first_name, last_name, address, phone, start_date, term_date, manager) 
VALUES ('Winston', 'Zeddemore', '400 Peachtree Parkway', '444-444-4444', '2021-12-31', NULL, 'Marshmallow Man');

