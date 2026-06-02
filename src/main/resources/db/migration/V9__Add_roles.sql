CREATE TABLE IF NOT EXISTS public.roles (
    id uuid DEFAULT gen_random_uuid() NOT NULL PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE,
    description varchar(255),
    created_at timestamp without time zone DEFAULT now() NOT NULL
);

CREATE TABLE IF NOT EXISTS public.user_roles (
                                             user_id uuid NOT NULL,
                                             role_id uuid NOT NULL,
                                             PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE
);

INSERT INTO public.roles (name, description)
VALUES
    ('GUEST', 'Anonymous user'),
    ('USER', 'Authenticated user'),
    ('ADMIN', 'System administrator')
    ON CONFLICT (name) DO NOTHING;