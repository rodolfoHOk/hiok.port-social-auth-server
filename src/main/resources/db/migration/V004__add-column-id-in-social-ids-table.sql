ALTER TABLE social_ids
DROP CONSTRAINT social_ids_pkey;

ALTER TABLE social_ids
ADD id bigserial NOT NULL;

ALTER TABLE social_ids
ADD CONSTRAINT social_ids_pkey PRIMARY KEY (id);
