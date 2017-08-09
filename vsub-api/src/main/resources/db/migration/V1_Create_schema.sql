-- * cc   : constraint check
-- * ucc  : unique constraint check
-- * fk   : foreign key

CREATE TABLE IF NOT EXISTS public.user
(
  id            SERIAL PRIMARY KEY      NOT NULL,
  username      VARCHAR(255)            NOT NULL,
  firstname     VARCHAR(255),
  lastname      VARCHAR(255),
  password      VARCHAR(255),
  email         VARCHAR(255),
  date          TIMESTAMP DEFAULT NOW() NOT NULL,
  role          INT                     NOT NULL,
  enabled       BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE INDEX IF NOT EXISTS user_date_idx
  ON public.user (date);
CREATE UNIQUE INDEX IF NOT EXISTS user_username_uidx
  ON public.user (username);
CREATE UNIQUE INDEX IF NOT EXISTS user_email_uidx
  ON public.user (email);

INSERT INTO public."user" (username, password, is_admin, role, enabled) VALUES
  ('root', '3c7a96ef888de079e3a83d99789f5a23e2c29c96ff41a4c6d38417d7e0aa85e4', true, 0, true);