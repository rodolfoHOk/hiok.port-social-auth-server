DELETE FROM user_groups;

DELETE FROM users;

ALTER TABLE user_groups
DROP CONSTRAINT user_groups_pkey;

ALTER TABLE user_groups
DROP CONSTRAINT user_groups_user_id_fk;

ALTER TABLE users
DROP CONSTRAINT users_pkey;

ALTER TABLE users
DROP COLUMN id;

ALTER TABLE users
DROP COLUMN provider;

ALTER TABLE users
DROP COLUMN provider_id;

ALTER TABLE users
ADD id uuid NOT NULL;

ALTER TABLE users
ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE user_groups
DROP COLUMN user_id;

ALTER TABLE user_groups
ADD user_id uuid NOT NULL;

ALTER TABLE user_groups
ADD CONSTRAINT user_groups_pkey PRIMARY KEY (user_id, group_id);

ALTER TABLE user_groups
ADD CONSTRAINT user_groups_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id);

CREATE TABLE social_ids (
    provider varchar(30) NOT NULL,
    social_id varchar(255) NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT social_ids_pkey PRIMARY KEY (provider, social_id),
    CONSTRAINT social_ids_user_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);
