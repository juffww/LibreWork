-- 1. Drop FK cũ (user_id → users)
ALTER TABLE user_skills
DROP CONSTRAINT IF EXISTS user_skills_user_id_fkey;

-- 2. Drop PK cũ
ALTER TABLE user_skills
DROP CONSTRAINT IF EXISTS user_skills_pkey;

-- 3. Rename table
ALTER TABLE user_skills
    RENAME TO freelancer_skills;

-- 4. Rename column user_id → freelancer_id
ALTER TABLE freelancer_skills
    RENAME COLUMN user_id TO freelancer_id;

-- 5. Thêm created_at
ALTER TABLE freelancer_skills
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL;

-- 6. Thêm PK mới
ALTER TABLE freelancer_skills
    ADD CONSTRAINT freelancer_skills_pkey PRIMARY KEY (freelancer_id, skill_id);

-- 7. FK freelancer_id → freelancer_profiles(id)
ALTER TABLE freelancer_skills
    ADD CONSTRAINT fk_freelancer_skills_freelancer
        FOREIGN KEY (freelancer_id) REFERENCES freelancer_profiles(id)
            ON DELETE CASCADE;

-- 8. FK skill_id → skills(id)
ALTER TABLE freelancer_skills
DROP CONSTRAINT IF EXISTS user_skills_skill_id_fkey;

ALTER TABLE freelancer_skills
    ADD CONSTRAINT fk_freelancer_skills_skill
        FOREIGN KEY (skill_id) REFERENCES skills(id)
            ON DELETE CASCADE;

-- 9. Thêm created_at cho client_profiles
ALTER TABLE public.client_profiles
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL;