CREATE TABLE users (
	id bigserial NOT NULL,
	email varchar(255) NOT NULL,
	image_url varchar(255) NULL,
	"name" varchar(255) NOT NULL,
	provider varchar(255) NULL,
	provider_id varchar(255) NULL,
	CONSTRAINT users_email_uk UNIQUE (email),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE roles (
	id bigserial NOT NULL,
	"description" varchar(255) NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE user_roles (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
	CONSTRAINT user_roles_role_id_fk FOREIGN KEY (role_id) REFERENCES roles(id),
	CONSTRAINT user_roles_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
);
