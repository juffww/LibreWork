ALTER TABLE public.jobs
    ADD COLUMN visibility         varchar(20)  NOT NULL DEFAULT 'PUBLIC',
  ADD COLUMN is_urgent          boolean      NOT NULL DEFAULT false,
  ADD COLUMN freelancers_needed integer      NOT NULL DEFAULT 1
    CONSTRAINT jobs_freelancers_needed_check CHECK (freelancers_needed >= 1),
  ADD COLUMN subcategory_id     uuid
    REFERENCES public.job_categories(id) ON DELETE SET NULL;

CREATE INDEX idx_jobs_subcategory_id ON public.jobs USING btree (subcategory_id);

CREATE TABLE public.job_attachments (
                                        id           uuid      DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
                                        entity_type  varchar(20)  NOT NULL,          -- 'JOB' | 'PROPOSAL'
                                        entity_id    uuid         NOT NULL,
                                        uploaded_by  uuid         REFERENCES public.users(id) ON DELETE SET NULL,
                                        file_url     varchar(500) NOT NULL,
                                        file_name    varchar(255) NOT NULL,
                                        file_size    integer,
                                        uploaded_at  timestamp DEFAULT now() NOT NULL
);

CREATE INDEX idx_job_attachments_entity ON public.job_attachments (entity_type, entity_id);
