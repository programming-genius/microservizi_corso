--
-- PostgreSQL database dump
--

-- Dumped from database version 11.11
-- Dumped by pg_dump version 11.11

-- Started on 2021-12-15 11:33:42

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

SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE public.product (
    id integer NOT NULL,
    name character varying,
    version integer
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 17838)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 3
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 17815)
-- Name: sim; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sim (
    id integer NOT NULL,
    msisdn character varying,
    imsi character varying,
    userid integer,
    version integer
);


ALTER TABLE public.sim OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 17813)
-- Name: sim_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sim_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 3
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sim_id_seq OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 17833)
-- Name: sim_product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sim_product (
    sim_id integer NOT NULL,
    product_id integer NOT NULL,
    version integer
);


ALTER TABLE public.sim_product OWNER TO postgres;


ALTER TABLE ONLY public.sim
    ADD CONSTRAINT imsi_unique UNIQUE (imsi) INCLUDE (imsi);

ALTER TABLE ONLY public.sim
    ADD CONSTRAINT msisdn_unique UNIQUE (msisdn) INCLUDE (msisdn);


ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.sim
    ADD CONSTRAINT sim_pkey PRIMARY KEY (id);


ALTER TABLE ONLY public.sim_product
    ADD CONSTRAINT sim_product_pkey PRIMARY KEY (sim_id, product_id);

ALTER TABLE ONLY public.sim_product
    ADD CONSTRAINT product_fk FOREIGN KEY (product_id) REFERENCES public.product(id) NOT VALID;

ALTER TABLE ONLY public.sim_product
    ADD CONSTRAINT sim_fk FOREIGN KEY (sim_id) REFERENCES public.sim(id) NOT VALID;


-- Completed on 2021-12-15 11:33:42

--
-- PostgreSQL database dump complete
--


