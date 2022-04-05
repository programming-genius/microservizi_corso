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

--
-- TOC entry 197 (class 1259 OID 17804)
-- Name: business_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.business_user (
    id integer NOT NULL,
    firstname character varying,
    lastname character varying,
    fiscalcode character varying,
    version integer
);


ALTER TABLE public.business_user OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 17802)
-- Name: business_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.business_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 3
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.business_user_id_seq OWNER TO postgres;

--
-- TOC entry 2735 (class 2606 OID 17812)
-- Name: business_user business_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.business_user
    ADD CONSTRAINT business_user_pkey PRIMARY KEY (id);

-- Completed on 2021-12-15 11:33:42

--
-- PostgreSQL database dump complete
--


