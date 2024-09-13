DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE indexname = 'idx_disciplines_name'
        ) THEN
            CREATE INDEX idx_disciplines_name
                ON disciplines (name);
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE indexname = 'idx_groups_name'
        ) THEN
            CREATE INDEX idx_groups_name
                ON groups (name);
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE indexname = 'idx_specialities_name'
        ) THEN
            CREATE INDEX idx_specialities_name
                ON specialities (name);
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE indexname = 'idx_specialities_code'
        ) THEN
            CREATE INDEX idx_specialities_code
                ON specialities (code);
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE indexname = 'idx_users_email'
        ) THEN
            CREATE INDEX idx_users_email
                ON users (email);
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_indexes
            WHERE indexname = 'idx_users_phonenumber'
        ) THEN
            CREATE INDEX idx_users_phoneNumber
                ON users (phone_number);
        END IF;
    END $$;