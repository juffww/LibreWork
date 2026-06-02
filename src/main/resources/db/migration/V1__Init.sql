CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS public.users (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    email character varying(255) NOT NULL UNIQUE,
    password character varying(255),
    full_name character varying(150) NOT NULL,
    username character varying(50) NOT NULL UNIQUE,
    status character varying(20) DEFAULT 'ACTIVE' NOT NULL,
    avatar_url character varying(255),
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL
);

CREATE TABLE IF NOT EXISTS public.client_profiles (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    user_id uuid NOT NULL,
    company_name character varying(200),
    logo_url character varying(500),
    industry character varying(100),
    total_spent numeric(14,2) DEFAULT 0 NOT NULL,
    payment_verified boolean DEFAULT false NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    description text,
    website_url character varying(500),
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.freelancer_profiles (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    user_id uuid NOT NULL UNIQUE,
    title character varying(200),
    hourly_rate numeric(10,2),
    experience_level character varying(20) DEFAULT 'ENTRY' NOT NULL,
    availability character varying(20) DEFAULT 'FULL_TIME' NOT NULL,
    total_earned numeric(14,2) DEFAULT 0 NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    overview text,
    social_links jsonb,
    CONSTRAINT freelancer_profiles_hourly_rate_check CHECK (hourly_rate >= 0),
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.user_settings (
    user_id uuid NOT NULL PRIMARY KEY,
    active_client_profile_id uuid,
    country character varying(100),
    timezone character varying(100) DEFAULT 'Asia/Ho_Chi_Minh' NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    language character varying(20),
    active_profile_type character varying(20),
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.user_oauth_providers (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    user_id uuid NOT NULL,
    provider character varying(20) NOT NULL,
    provider_user_id character varying(255) NOT NULL,
    access_token text,
    refresh_token text,
    expires_at timestamp without time zone,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    UNIQUE (provider, provider_user_id),
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.skill_categories (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    name character varying(255) NOT NULL,
    slug character varying(255) NOT NULL UNIQUE,
    icon_url character varying(255),
    sort_order integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL
);

CREATE TABLE IF NOT EXISTS public.skills (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    category_id uuid,
    name character varying(255) NOT NULL UNIQUE,
    slug character varying(255) NOT NULL UNIQUE,
    is_verified boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    FOREIGN KEY (category_id) REFERENCES public.skill_categories(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS public.user_skills (
    user_id uuid NOT NULL,
    skill_id uuid NOT NULL,
    proficiency_level character varying(255) DEFAULT 'INTERMEDIATE' NOT NULL,
    years_of_experience integer,
    PRIMARY KEY (user_id, skill_id),
    CONSTRAINT user_skills_years_of_experience_check CHECK (years_of_experience >= 0),
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES public.skills(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.job_categories (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    parent_id uuid,
    name character varying(100) NOT NULL,
    slug character varying(100) NOT NULL UNIQUE,
    icon_url character varying(500),
    description character varying(255),
    sort_order integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES public.job_categories(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS public.jobs (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
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
    experience_level character varying(20) DEFAULT 'INTERMEDIATE' NOT NULL,
    status character varying(20) DEFAULT 'OPEN' NOT NULL,
    proposals_count integer DEFAULT 0 NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    FOREIGN KEY (category_id) REFERENCES public.job_categories(id) ON DELETE SET NULL,
    FOREIGN KEY (client_id) REFERENCES public.client_profiles(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS public.job_skills (
    job_id uuid NOT NULL,
    skill_id uuid NOT NULL,
    is_required boolean DEFAULT true NOT NULL,
    PRIMARY KEY (job_id, skill_id),
    FOREIGN KEY (job_id) REFERENCES public.jobs(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES public.skills(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.proposals (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    job_id uuid NOT NULL,
    freelancer_id uuid NOT NULL,
    cover_letter text NOT NULL,
    bid_type character varying(10) NOT NULL,
    bid_amount numeric(12,2) NOT NULL,
    bid_duration integer NOT NULL,
    status character varying(20) DEFAULT 'PENDING' NOT NULL,
    created_at timestamp without time zone DEFAULT now() NOT NULL,
    updated_at timestamp without time zone DEFAULT now() NOT NULL,
    UNIQUE (job_id, freelancer_id),
    CONSTRAINT proposals_bid_amount_check CHECK (bid_amount > 0),
    CONSTRAINT proposals_bid_duration_check CHECK (bid_duration > 0),
    FOREIGN KEY (freelancer_id) REFERENCES public.freelancer_profiles(id) ON DELETE RESTRICT,
    FOREIGN KEY (job_id) REFERENCES public.jobs(id) ON DELETE RESTRICT
);

CREATE INDEX IF NOT EXISTS idx_client_profiles_user_id ON public.client_profiles USING btree (user_id);
CREATE INDEX IF NOT EXISTS idx_job_skills_skill_id ON public.job_skills USING btree (skill_id);
CREATE INDEX IF NOT EXISTS idx_jobs_category_id ON public.jobs USING btree (category_id);
CREATE INDEX IF NOT EXISTS idx_jobs_client_id ON public.jobs USING btree (client_id);
CREATE INDEX IF NOT EXISTS idx_jobs_status_created_at ON public.jobs USING btree (status, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_proposals_freelancer_id ON public.proposals USING btree (freelancer_id);
CREATE INDEX IF NOT EXISTS idx_proposals_job_id ON public.proposals USING btree (job_id);
CREATE INDEX IF NOT EXISTS idx_proposals_status ON public.proposals USING btree (status);
CREATE INDEX IF NOT EXISTS idx_skills_category_id ON public.skills USING btree (category_id);
CREATE INDEX IF NOT EXISTS idx_user_oauth_providers_user_id ON public.user_oauth_providers USING btree (user_id);
CREATE INDEX IF NOT EXISTS idx_users_status ON public.users USING btree (status);

