ALTER TABLE jobs DROP COLUMN IF EXISTS job_type;

ALTER TABLE jobs ADD COLUMN IF NOT EXISTS currency VARCHAR(10) NOT NULL DEFAULT 'USD';

ALTER TABLE jobs ADD CONSTRAINT chk_budget_type
    CHECK (
        (budget_type = 'FIXED' AND (
            budget_fixed IS NOT NULL
                OR (budget_min IS NOT NULL AND budget_max IS NOT NULL AND budget_fixed IS NULL)
            ))
            OR
        (budget_type = 'HOURLY' AND budget_min IS NOT NULL AND budget_max IS NOT NULL AND budget_fixed IS NULL)
        );

ALTER TABLE jobs ADD CONSTRAINT chk_budget_hourly_range
    CHECK (budget_min IS NULL OR budget_max IS NULL OR budget_min <= budget_max);