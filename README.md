CREATE SCHEMA cp AUTHORIZATION postgres;

CREATE TABLE cp.users (
	id bigserial NOT NULL,
	"password" varchar(120) NULL,
	username varchar(20) NULL,
	CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE cp.userdetail (
	id bigserial NOT NULL,
	address varchar(150) NULL,
	mobileno varchar(120) NULL,
	username varchar(20) NULL,
	CONSTRAINT usersdetail_pkey PRIMARY KEY (id)
);


INSERT INTO cp.users
(id, "password", username)
VALUES(3, 'Powar', 'Powar');

INSERT INTO cp.users
(id, "password", username)
VALUES(4, 'Ashish', 'Ashish');

