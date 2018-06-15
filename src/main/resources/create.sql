-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.1
-- PostgreSQL version: 9.4
-- Project Site: pgmodeler.io
-- Model Author: ---

-- -- object: magic | type: ROLE --
-- -- DROP ROLE IF EXISTS magic;
-- CREATE ROLE magic WITH 
-- 	SUPERUSER
-- 	CREATEDB
-- 	CREATEROLE
-- 	LOGIN
-- 	UNENCRYPTED PASSWORD 'magic';
-- -- ddl-end --
-- 

-- Database creation must be done outside a multicommand file.
-- These commands were put in this file only as a convenience.
-- -- object: magic | type: DATABASE --
-- -- DROP DATABASE IF EXISTS magic;
-- CREATE DATABASE magic;
-- -- ddl-end --
-- 

-- object: public.card | type: TABLE --
-- DROP TABLE IF EXISTS public.card CASCADE;
CREATE TABLE public.card(
	id serial NOT NULL,
	name varchar(127) NOT NULL,
	rarity varchar(20) NOT NULL,
	image_url varchar(255) NOT NULL,
	code_set varchar(10) NOT NULL,
	CONSTRAINT pk_card PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.card OWNER TO magic;
-- ddl-end --

-- object: public.set | type: TABLE --
-- DROP TABLE IF EXISTS public.set CASCADE;
CREATE TABLE public.set(
	code varchar(10) NOT NULL,
	name varchar(50) NOT NULL,
	type varchar(50),
	release_date date,
	CONSTRAINT pk_set PRIMARY KEY (code)

);
-- ddl-end --
ALTER TABLE public.set OWNER TO magic;
-- ddl-end --

-- object: set_fk | type: CONSTRAINT --
-- ALTER TABLE public.card DROP CONSTRAINT IF EXISTS set_fk CASCADE;
ALTER TABLE public.card ADD CONSTRAINT set_fk FOREIGN KEY (code_set)
REFERENCES public.set (code) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: public.comment | type: TABLE --
-- DROP TABLE IF EXISTS public.comment CASCADE;
CREATE TABLE public.comment(
	id serial NOT NULL,
	type varchar(20) NOT NULL,
	value decimal NOT NULL,
	description text NOT NULL,
	id_card integer NOT NULL,
	CONSTRAINT pk_comment PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.comment OWNER TO magic;
-- ddl-end --

-- object: card_fk | type: CONSTRAINT --
-- ALTER TABLE public.comment DROP CONSTRAINT IF EXISTS card_fk CASCADE;
ALTER TABLE public.comment ADD CONSTRAINT card_fk FOREIGN KEY (id_card)
REFERENCES public.card (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


