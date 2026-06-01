--
-- PostgreSQL database dump
--

\restrict dGjKbsbxzTXKAAAfNIuMRhRdvPLxVZPwDDCbBCSP4KWbh9LkVobOrqFfKEwOUna

-- Dumped from database version 15.17 (Debian 15.17-1.pgdg13+1)
-- Dumped by pg_dump version 16.13 (Ubuntu 16.13-0ubuntu0.24.04.1)

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
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: client_profiles; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.client_profiles (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    user_id uuid NOT NULL,
    company_name character varying(200),
    logo_url character varying(500),
    industry character varying(100),
    total_spent numeric(14,2) DEFAULT 0 NOT NULL,
    payment_verified boolean DEFAULT false NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    description text,
    website_url character varying(500),
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.client_profiles OWNER TO librework_user;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO librework_user;

--
-- Name: freelancer_profiles; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.freelancer_profiles (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    user_id uuid NOT NULL,
    title character varying(200),
    hourly_rate numeric(10,2),
    experience_level character varying(20) DEFAULT 'ENTRY'::character varying NOT NULL,
    availability character varying(20) DEFAULT 'FULL_TIME'::character varying NOT NULL,
    total_earned numeric(14,2) DEFAULT 0 NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    overview text,
    social_links jsonb,
    avatar_url character varying(255),
    CONSTRAINT freelancer_profiles_hourly_rate_check CHECK ((hourly_rate >= (0)::numeric))
);


ALTER TABLE public.freelancer_profiles OWNER TO librework_user;

--
-- Name: freelancer_skills; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.freelancer_skills (
    freelancer_id uuid NOT NULL,
    skill_id uuid NOT NULL,
    proficiency_level character varying(255) DEFAULT 'INTERMEDIATE'::character varying NOT NULL,
    years_of_experience integer,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT user_skills_years_of_experience_check CHECK ((years_of_experience >= 0))
);


ALTER TABLE public.freelancer_skills OWNER TO librework_user;

--
-- Name: job_attachments; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.job_attachments (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    entity_type character varying(20) NOT NULL,
    entity_id uuid NOT NULL,
    uploaded_by uuid,
    file_url character varying(500) NOT NULL,
    file_name character varying(255) NOT NULL,
    file_size integer,
    uploaded_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.job_attachments OWNER TO librework_user;

--
-- Name: job_categories; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.job_categories (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    parent_id uuid,
    name character varying(100) NOT NULL,
    slug character varying(100) NOT NULL,
    icon_url character varying(500),
    description character varying(255),
    sort_order integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.job_categories OWNER TO librework_user;

--
-- Name: job_skills; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.job_skills (
    job_id uuid NOT NULL,
    skill_id uuid NOT NULL,
    is_required boolean DEFAULT true NOT NULL
);


ALTER TABLE public.job_skills OWNER TO librework_user;

--
-- Name: jobs; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.jobs (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    client_id uuid NOT NULL,
    category_id uuid,
    title character varying(200) NOT NULL,
    description text NOT NULL,
    job_type character varying(20) NOT NULL,
    budget_type character varying(10) NOT NULL,
    budget_fixed numeric(12,2),
    budget_min numeric(12,2),
    budget_max numeric(12,2),
    duration character varying(20) NOT NULL,
    experience_level character varying(20) DEFAULT 'INTERMEDIATE'::character varying NOT NULL,
    status character varying(20) DEFAULT 'OPEN'::character varying NOT NULL,
    proposals_count integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    visibility character varying(20) DEFAULT 'PUBLIC'::character varying NOT NULL,
    is_urgent boolean DEFAULT false NOT NULL,
    freelancers_needed integer DEFAULT 1 NOT NULL,
    subcategory_id uuid,
    CONSTRAINT jobs_freelancers_needed_check CHECK ((freelancers_needed >= 1))
);


ALTER TABLE public.jobs OWNER TO librework_user;

--
-- Name: proposals; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.proposals (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    job_id uuid NOT NULL,
    freelancer_id uuid NOT NULL,
    cover_letter text NOT NULL,
    bid_type character varying(10) NOT NULL,
    bid_amount numeric(12,2) NOT NULL,
    bid_duration integer NOT NULL,
    status character varying(20) DEFAULT 'PENDING'::character varying NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    CONSTRAINT proposals_bid_amount_check CHECK ((bid_amount > (0)::numeric)),
    CONSTRAINT proposals_bid_duration_check CHECK ((bid_duration > 0))
);


ALTER TABLE public.proposals OWNER TO librework_user;

--
-- Name: skill_categories; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.skill_categories (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    icon_url character varying(255),
    sort_order integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.skill_categories OWNER TO librework_user;

--
-- Name: skills; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.skills (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    category_id uuid,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL,
    is_verified boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.skills OWNER TO librework_user;

--
-- Name: user_oauth_providers; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.user_oauth_providers (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    user_id uuid NOT NULL,
    provider character varying(20) NOT NULL,
    provider_user_id character varying(255) NOT NULL,
    access_token text,
    refresh_token text,
    expires_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.user_oauth_providers OWNER TO librework_user;

--
-- Name: user_settings; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.user_settings (
    user_id uuid NOT NULL,
    country character varying(100),
    timezone character varying(100) DEFAULT 'Asia/Ho_Chi_Minh'::character varying NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    language character varying(20),
    active_profile_type character varying(20)
);


ALTER TABLE public.user_settings OWNER TO librework_user;

--
-- Name: users; Type: TABLE; Schema: public; Owner: librework_user
--

CREATE TABLE public.users (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255),
    full_name character varying(150) NOT NULL,
    username character varying(50) NOT NULL,
    status character varying(20) DEFAULT 'ACTIVE'::character varying NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.users OWNER TO librework_user;

--
-- Data for Name: client_profiles; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.client_profiles (id, user_id, company_name, logo_url, industry, total_spent, payment_verified, updated_at, description, website_url, created_at) FROM stdin;
0b4c7e21-584d-4a23-af94-e9a195c583ed	112feb01-e7a1-4d85-93e3-1f27b713a34f	amazon	\N	\N	0.00	f	2026-04-21 21:46:33.518203	\N	\N	2026-04-25 01:47:33.894728
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	2	Move AvatarUrl	SQL	V2__Move_AvatarUrl.sql	1661401329	librework_user	2026-04-21 20:30:04.625393	11	t
2	3	Remove ClientId	SQL	V3__Remove_ClientId.sql	377537914	librework_user	2026-04-24 17:41:19.908643	11	t
3	4	Refactor FreelancerSkills	SQL	V4__Refactor_FreelancerSkills.sql	-971212992	librework_user	2026-04-25 01:47:33.882548	33	t
4	5	Seed Skill Data	SQL	V5__Seed_Skill_Data.sql	1237026202	librework_user	2026-04-25 17:54:07.158386	16	t
5	6	Convert to English	SQL	V6__Convert_to_English.sql	-1686073753	librework_user	2026-04-25 18:22:28.974566	12	t
6	7	Seed job categories	SQL	V7__Seed_job_categories.sql	-1280907932	librework_user	2026-05-05 00:33:20.485309	10	t
7	8	Modify Jobs	SCRIPT	V8__Modify_Jobs	0	librework_user	2026-05-23 18:41:22.805276	11	t
8	8	Modify jobs	SQL	V8__Modify_jobs.sql	-1426714639	librework_user	2026-05-23 19:07:45.932625	56	t
\.


--
-- Data for Name: freelancer_profiles; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.freelancer_profiles (id, user_id, title, hourly_rate, experience_level, availability, total_earned, updated_at, overview, social_links, avatar_url) FROM stdin;
6223a427-e162-4e42-a620-d04e68b428c6	112feb01-e7a1-4d85-93e3-1f27b713a34f	Senior Backend Developer	20.00	INTERMEDIATE	PART_TIME	0.00	2026-04-26 00:06:47.066882	I'm a software engineer developer, especially on java, pythonasdfasdf	\N	\N
\.


--
-- Data for Name: freelancer_skills; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.freelancer_skills (freelancer_id, skill_id, proficiency_level, years_of_experience, created_at) FROM stdin;
6223a427-e162-4e42-a620-d04e68b428c6	b1000000-0000-0000-0000-000000000004	EXPERT	\N	2026-04-25 23:28:37.517979
6223a427-e162-4e42-a620-d04e68b428c6	b1000000-0000-0000-0000-000000000012	EXPERT	\N	2026-04-26 18:30:23.201755
6223a427-e162-4e42-a620-d04e68b428c6	b4000000-0000-0000-0000-000000000008	BEGINNER	\N	2026-04-26 18:40:45.331994
\.


--
-- Data for Name: job_attachments; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.job_attachments (id, entity_type, entity_id, uploaded_by, file_url, file_name, file_size, uploaded_at) FROM stdin;
\.


--
-- Data for Name: job_categories; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.job_categories (id, parent_id, name, slug, icon_url, description, sort_order, created_at) FROM stdin;
a1000000-0000-0000-0000-000000000001	\N	Web Development	web-development	\N	Jobs related to web and application development	1	2026-05-05 00:33:20.497794
a1000000-0000-0000-0000-000000000002	\N	Design & Creative	design-creative	\N	Jobs related to graphic design, UI/UX, and creative work	2	2026-05-05 00:33:20.497794
a1000000-0000-0000-0000-000000000003	\N	Data & AI	data-ai	\N	Jobs related to data science, machine learning, and AI	3	2026-05-05 00:33:20.497794
a1000000-0000-0000-0000-000000000004	\N	Mobile Development	mobile-development	\N	Jobs related to iOS and Android development	4	2026-05-05 00:33:20.497794
a1000000-0000-0000-0000-000000000005	\N	Writing & Translation	writing-translation	\N	Jobs related to content writing, copywriting, and translation	5	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000001	a1000000-0000-0000-0000-000000000001	Frontend Development	frontend-development	\N	\N	1	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000002	a1000000-0000-0000-0000-000000000001	Backend Development	backend-development	\N	\N	2	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000003	a1000000-0000-0000-0000-000000000001	Full Stack Development	full-stack-development	\N	\N	3	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000004	a1000000-0000-0000-0000-000000000001	WordPress & CMS	wordpress-cms	\N	\N	4	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000005	a1000000-0000-0000-0000-000000000001	E-commerce Development	ecommerce-development	\N	\N	5	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000006	a1000000-0000-0000-0000-000000000002	UI/UX Design	ui-ux-design	\N	\N	1	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000007	a1000000-0000-0000-0000-000000000002	Logo & Brand Identity	logo-brand-identity	\N	\N	2	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000008	a1000000-0000-0000-0000-000000000002	Illustration	illustration	\N	\N	3	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000009	a1000000-0000-0000-0000-000000000002	Motion Graphics	motion-graphics	\N	\N	4	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000010	a1000000-0000-0000-0000-000000000003	Data Analysis	data-analysis	\N	\N	1	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000011	a1000000-0000-0000-0000-000000000003	Machine Learning	machine-learning	\N	\N	2	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000012	a1000000-0000-0000-0000-000000000003	Data Engineering	data-engineering	\N	\N	3	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000013	a1000000-0000-0000-0000-000000000004	iOS Development	ios-development	\N	\N	1	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000014	a1000000-0000-0000-0000-000000000004	Android Development	android-development	\N	\N	2	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000015	a1000000-0000-0000-0000-000000000004	React Native	react-native	\N	\N	3	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000016	a1000000-0000-0000-0000-000000000004	Flutter	flutter	\N	\N	4	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000017	a1000000-0000-0000-0000-000000000005	Content Writing	content-writing	\N	\N	1	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000018	a1000000-0000-0000-0000-000000000005	Copywriting	copywriting	\N	\N	2	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000019	a1000000-0000-0000-0000-000000000005	Technical Writing	technical-writing	\N	\N	3	2026-05-05 00:33:20.497794
a2000000-0000-0000-0000-000000000020	a1000000-0000-0000-0000-000000000005	Translation	translation	\N	\N	4	2026-05-05 00:33:20.497794
\.


--
-- Data for Name: job_skills; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.job_skills (job_id, skill_id, is_required) FROM stdin;
\.


--
-- Data for Name: jobs; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.jobs (id, client_id, category_id, title, description, job_type, budget_type, budget_fixed, budget_min, budget_max, duration, experience_level, status, proposals_count, created_at, updated_at, visibility, is_urgent, freelancers_needed, subcategory_id) FROM stdin;
\.


--
-- Data for Name: proposals; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.proposals (id, job_id, freelancer_id, cover_letter, bid_type, bid_amount, bid_duration, status, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: skill_categories; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.skill_categories (id, name, slug, icon_url, sort_order, created_at) FROM stdin;
a1000000-0000-0000-0000-000000000002	Design & UI/UX	design-ui-ux	\N	2	2026-04-25 17:54:07.179587
a1000000-0000-0000-0000-000000000003	Marketing & Content	marketing-content	\N	3	2026-04-25 17:54:07.179587
a1000000-0000-0000-0000-000000000004	Data, AI & Machine Learning	data-ai-ml	\N	4	2026-04-25 17:54:07.179587
a1000000-0000-0000-0000-000000000001	Programming & Technology	programming-technology	\N	1	2026-04-25 17:54:07.179587
a1000000-0000-0000-0000-000000000005	Business & Finance	business-finance	\N	5	2026-04-25 17:54:07.179587
\.


--
-- Data for Name: skills; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.skills (id, category_id, name, slug, is_verified, created_at) FROM stdin;
b1000000-0000-0000-0000-000000000001	a1000000-0000-0000-0000-000000000001	HTML/CSS	html-css	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000002	a1000000-0000-0000-0000-000000000001	JavaScript	javascript	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000003	a1000000-0000-0000-0000-000000000001	TypeScript	typescript	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000004	a1000000-0000-0000-0000-000000000001	React	react	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000005	a1000000-0000-0000-0000-000000000001	Vue.js	vuejs	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000006	a1000000-0000-0000-0000-000000000001	Angular	angular	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000007	a1000000-0000-0000-0000-000000000001	Next.js	nextjs	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000008	a1000000-0000-0000-0000-000000000001	Java	java	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000009	a1000000-0000-0000-0000-000000000001	Spring Boot	spring-boot	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000010	a1000000-0000-0000-0000-000000000001	Python	python	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000011	a1000000-0000-0000-0000-000000000001	Node.js	nodejs	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000012	a1000000-0000-0000-0000-000000000001	PHP	php	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000013	a1000000-0000-0000-0000-000000000001	Go	go	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000014	a1000000-0000-0000-0000-000000000001	Rust	rust	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000015	a1000000-0000-0000-0000-000000000001	React Native	react-native	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000016	a1000000-0000-0000-0000-000000000001	Flutter	flutter	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000017	a1000000-0000-0000-0000-000000000001	Swift	swift	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000018	a1000000-0000-0000-0000-000000000001	Kotlin	kotlin	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000019	a1000000-0000-0000-0000-000000000001	PostgreSQL	postgresql	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000020	a1000000-0000-0000-0000-000000000001	MySQL	mysql	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000021	a1000000-0000-0000-0000-000000000001	MongoDB	mongodb	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000022	a1000000-0000-0000-0000-000000000001	Redis	redis	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000023	a1000000-0000-0000-0000-000000000001	Docker	docker	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000024	a1000000-0000-0000-0000-000000000001	Kubernetes	kubernetes	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000025	a1000000-0000-0000-0000-000000000001	AWS	aws	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000026	a1000000-0000-0000-0000-000000000001	GraphQL	graphql	t	2026-04-25 17:54:07.179587
b1000000-0000-0000-0000-000000000027	a1000000-0000-0000-0000-000000000001	REST API	rest-api	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000001	a1000000-0000-0000-0000-000000000002	Figma	figma	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000002	a1000000-0000-0000-0000-000000000002	Adobe XD	adobe-xd	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000003	a1000000-0000-0000-0000-000000000002	Adobe Photoshop	adobe-photoshop	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000004	a1000000-0000-0000-0000-000000000002	Adobe Illustrator	adobe-illustrator	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000005	a1000000-0000-0000-0000-000000000002	UI Design	ui-design	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000006	a1000000-0000-0000-0000-000000000002	UX Research	ux-research	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000007	a1000000-0000-0000-0000-000000000002	Wireframing	wireframing	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000008	a1000000-0000-0000-0000-000000000002	Prototyping	prototyping	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000009	a1000000-0000-0000-0000-000000000002	Logo Design	logo-design	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000010	a1000000-0000-0000-0000-000000000002	Motion Graphics	motion-graphics	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000011	a1000000-0000-0000-0000-000000000002	Video Editing	video-editing	t	2026-04-25 17:54:07.179587
b2000000-0000-0000-0000-000000000012	a1000000-0000-0000-0000-000000000002	Brand Identity	brand-identity	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000001	a1000000-0000-0000-0000-000000000003	SEO	seo	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000002	a1000000-0000-0000-0000-000000000003	Google Ads	google-ads	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000003	a1000000-0000-0000-0000-000000000003	Facebook Ads	facebook-ads	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000004	a1000000-0000-0000-0000-000000000003	Social Media Marketing	social-media-marketing	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000005	a1000000-0000-0000-0000-000000000003	Content Writing	content-writing	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000006	a1000000-0000-0000-0000-000000000003	Copywriting	copywriting	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000007	a1000000-0000-0000-0000-000000000003	Email Marketing	email-marketing	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000008	a1000000-0000-0000-0000-000000000003	TikTok Marketing	tiktok-marketing	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000009	a1000000-0000-0000-0000-000000000003	Affiliate Marketing	affiliate-marketing	t	2026-04-25 17:54:07.179587
b3000000-0000-0000-0000-000000000010	a1000000-0000-0000-0000-000000000003	Translation	translation	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000001	a1000000-0000-0000-0000-000000000004	Machine Learning	machine-learning	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000002	a1000000-0000-0000-0000-000000000004	Deep Learning	deep-learning	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000003	a1000000-0000-0000-0000-000000000004	Data Analysis	data-analysis	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000004	a1000000-0000-0000-0000-000000000004	Data Visualization	data-visualization	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000005	a1000000-0000-0000-0000-000000000004	NLP	nlp	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000006	a1000000-0000-0000-0000-000000000004	Computer Vision	computer-vision	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000007	a1000000-0000-0000-0000-000000000004	TensorFlow	tensorflow	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000008	a1000000-0000-0000-0000-000000000004	PyTorch	pytorch	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000009	a1000000-0000-0000-0000-000000000004	Pandas	pandas	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000010	a1000000-0000-0000-0000-000000000004	Power BI	power-bi	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000011	a1000000-0000-0000-0000-000000000004	Tableau	tableau	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000012	a1000000-0000-0000-0000-000000000004	SQL	sql	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000013	a1000000-0000-0000-0000-000000000004	Apache Spark	apache-spark	t	2026-04-25 17:54:07.179587
b4000000-0000-0000-0000-000000000014	a1000000-0000-0000-0000-000000000004	LLM / Prompt Eng.	llm-prompt-engineering	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000001	a1000000-0000-0000-0000-000000000005	Business Analysis	business-analysis	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000002	a1000000-0000-0000-0000-000000000005	Project Management	project-management	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000003	a1000000-0000-0000-0000-000000000005	Financial Modeling	financial-modeling	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000004	a1000000-0000-0000-0000-000000000005	Accounting	accounting	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000005	a1000000-0000-0000-0000-000000000005	Market Research	market-research	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000006	a1000000-0000-0000-0000-000000000005	Business Planning	business-planning	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000007	a1000000-0000-0000-0000-000000000005	Legal Consulting	legal-consulting	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000008	a1000000-0000-0000-0000-000000000005	Tax Advisory	tax-advisory	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000009	a1000000-0000-0000-0000-000000000005	HR & Recruiting	hr-recruiting	t	2026-04-25 17:54:07.179587
b5000000-0000-0000-0000-000000000010	a1000000-0000-0000-0000-000000000005	Virtual Assistant	virtual-assistant	t	2026-04-25 17:54:07.179587
\.


--
-- Data for Name: user_oauth_providers; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.user_oauth_providers (id, user_id, provider, provider_user_id, access_token, refresh_token, expires_at, created_at, updated_at) FROM stdin;
\.


--
-- Data for Name: user_settings; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.user_settings (user_id, country, timezone, updated_at, language, active_profile_type) FROM stdin;
112feb01-e7a1-4d85-93e3-1f27b713a34f	LonDon	Asia/Ho_Chi_Minh	2026-05-23 19:09:10.76465	vi	CLIENT
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: librework_user
--

COPY public.users (id, email, password, full_name, username, status, created_at, updated_at) FROM stdin;
112feb01-e7a1-4d85-93e3-1f27b713a34f	baoho1503@gmail.com	$2a$10$25o/Ra4S361DMluEmFDOgOmrWNfkByCo1RsMU.mXzh.sNca6T7WN6	Ho Quoc Bao	Juffww	ACTIVE	2026-04-21 20:34:42.350982	2026-04-21 20:34:42.35103
\.


--
-- Name: client_profiles client_profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.client_profiles
    ADD CONSTRAINT client_profiles_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: freelancer_profiles freelancer_profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.freelancer_profiles
    ADD CONSTRAINT freelancer_profiles_pkey PRIMARY KEY (id);


--
-- Name: freelancer_profiles freelancer_profiles_user_id_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.freelancer_profiles
    ADD CONSTRAINT freelancer_profiles_user_id_key UNIQUE (user_id);


--
-- Name: freelancer_skills freelancer_skills_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.freelancer_skills
    ADD CONSTRAINT freelancer_skills_pkey PRIMARY KEY (freelancer_id, skill_id);


--
-- Name: job_attachments job_attachments_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_attachments
    ADD CONSTRAINT job_attachments_pkey PRIMARY KEY (id);


--
-- Name: job_categories job_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_categories
    ADD CONSTRAINT job_categories_pkey PRIMARY KEY (id);


--
-- Name: job_categories job_categories_slug_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_categories
    ADD CONSTRAINT job_categories_slug_key UNIQUE (slug);


--
-- Name: job_skills job_skills_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_skills
    ADD CONSTRAINT job_skills_pkey PRIMARY KEY (job_id, skill_id);


--
-- Name: jobs jobs_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_pkey PRIMARY KEY (id);


--
-- Name: proposals proposals_job_id_freelancer_id_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.proposals
    ADD CONSTRAINT proposals_job_id_freelancer_id_key UNIQUE (job_id, freelancer_id);


--
-- Name: proposals proposals_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.proposals
    ADD CONSTRAINT proposals_pkey PRIMARY KEY (id);


--
-- Name: skill_categories skill_categories_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.skill_categories
    ADD CONSTRAINT skill_categories_pkey PRIMARY KEY (id);


--
-- Name: skill_categories skill_categories_slug_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.skill_categories
    ADD CONSTRAINT skill_categories_slug_key UNIQUE (slug);


--
-- Name: skills skills_name_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_name_key UNIQUE (name);


--
-- Name: skills skills_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_pkey PRIMARY KEY (id);


--
-- Name: skills skills_slug_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_slug_key UNIQUE (slug);


--
-- Name: user_oauth_providers user_oauth_providers_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.user_oauth_providers
    ADD CONSTRAINT user_oauth_providers_pkey PRIMARY KEY (id);


--
-- Name: user_oauth_providers user_oauth_providers_provider_provider_user_id_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.user_oauth_providers
    ADD CONSTRAINT user_oauth_providers_provider_provider_user_id_key UNIQUE (provider, provider_user_id);


--
-- Name: user_settings user_profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.user_settings
    ADD CONSTRAINT user_profiles_pkey PRIMARY KEY (user_id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: idx_client_profiles_user_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_client_profiles_user_id ON public.client_profiles USING btree (user_id);


--
-- Name: idx_job_attachments_entity; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_job_attachments_entity ON public.job_attachments USING btree (entity_type, entity_id);


--
-- Name: idx_job_skills_skill_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_job_skills_skill_id ON public.job_skills USING btree (skill_id);


--
-- Name: idx_jobs_category_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_jobs_category_id ON public.jobs USING btree (category_id);


--
-- Name: idx_jobs_client_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_jobs_client_id ON public.jobs USING btree (client_id);


--
-- Name: idx_jobs_status_created_at; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_jobs_status_created_at ON public.jobs USING btree (status, created_at DESC);


--
-- Name: idx_jobs_subcategory_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_jobs_subcategory_id ON public.jobs USING btree (subcategory_id);


--
-- Name: idx_proposals_freelancer_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_proposals_freelancer_id ON public.proposals USING btree (freelancer_id);


--
-- Name: idx_proposals_job_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_proposals_job_id ON public.proposals USING btree (job_id);


--
-- Name: idx_proposals_status; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_proposals_status ON public.proposals USING btree (status);


--
-- Name: idx_skills_category_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_skills_category_id ON public.skills USING btree (category_id);


--
-- Name: idx_user_oauth_providers_user_id; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_user_oauth_providers_user_id ON public.user_oauth_providers USING btree (user_id);


--
-- Name: idx_users_status; Type: INDEX; Schema: public; Owner: librework_user
--

CREATE INDEX idx_users_status ON public.users USING btree (status);


--
-- Name: client_profiles client_profiles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.client_profiles
    ADD CONSTRAINT client_profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: freelancer_skills fk_freelancer_skills_freelancer; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.freelancer_skills
    ADD CONSTRAINT fk_freelancer_skills_freelancer FOREIGN KEY (freelancer_id) REFERENCES public.freelancer_profiles(id) ON DELETE CASCADE;


--
-- Name: freelancer_skills fk_freelancer_skills_skill; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.freelancer_skills
    ADD CONSTRAINT fk_freelancer_skills_skill FOREIGN KEY (skill_id) REFERENCES public.skills(id) ON DELETE CASCADE;


--
-- Name: freelancer_profiles freelancer_profiles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.freelancer_profiles
    ADD CONSTRAINT freelancer_profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: job_attachments job_attachments_uploaded_by_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_attachments
    ADD CONSTRAINT job_attachments_uploaded_by_fkey FOREIGN KEY (uploaded_by) REFERENCES public.users(id) ON DELETE SET NULL;


--
-- Name: job_categories job_categories_parent_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_categories
    ADD CONSTRAINT job_categories_parent_id_fkey FOREIGN KEY (parent_id) REFERENCES public.job_categories(id) ON DELETE SET NULL;


--
-- Name: job_skills job_skills_job_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_skills
    ADD CONSTRAINT job_skills_job_id_fkey FOREIGN KEY (job_id) REFERENCES public.jobs(id) ON DELETE CASCADE;


--
-- Name: job_skills job_skills_skill_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.job_skills
    ADD CONSTRAINT job_skills_skill_id_fkey FOREIGN KEY (skill_id) REFERENCES public.skills(id) ON DELETE CASCADE;


--
-- Name: jobs jobs_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.job_categories(id) ON DELETE SET NULL;


--
-- Name: jobs jobs_client_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_client_id_fkey FOREIGN KEY (client_id) REFERENCES public.client_profiles(id) ON DELETE RESTRICT;


--
-- Name: jobs jobs_subcategory_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_subcategory_id_fkey FOREIGN KEY (subcategory_id) REFERENCES public.job_categories(id) ON DELETE SET NULL;


--
-- Name: proposals proposals_freelancer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.proposals
    ADD CONSTRAINT proposals_freelancer_id_fkey FOREIGN KEY (freelancer_id) REFERENCES public.freelancer_profiles(id) ON DELETE RESTRICT;


--
-- Name: proposals proposals_job_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.proposals
    ADD CONSTRAINT proposals_job_id_fkey FOREIGN KEY (job_id) REFERENCES public.jobs(id) ON DELETE RESTRICT;


--
-- Name: skills skills_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.skills
    ADD CONSTRAINT skills_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.skill_categories(id) ON DELETE SET NULL;


--
-- Name: user_oauth_providers user_oauth_providers_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.user_oauth_providers
    ADD CONSTRAINT user_oauth_providers_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: user_settings user_profiles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: librework_user
--

ALTER TABLE ONLY public.user_settings
    ADD CONSTRAINT user_profiles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

\unrestrict dGjKbsbxzTXKAAAfNIuMRhRdvPLxVZPwDDCbBCSP4KWbh9LkVobOrqFfKEwOUna

