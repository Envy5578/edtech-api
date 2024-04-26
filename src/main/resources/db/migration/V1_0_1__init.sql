CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS tokens_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS users (
                id integer not null,
                email varchar(255),
                firstname varchar(255),
                lastname varchar(255),
                password varchar(255),
                role varchar(255) check (role in ('USER','ADMIN')),
                primary key (id)
);

CREATE TABLE IF NOT EXISTS tokens (
                expired boolean not null,
                id integer not null,
                revoked boolean not null,
                user_id integer,
                token varchar(255) unique,
                primary key (id)
);

ALTER TABLE tokens
    add constraint token_user_id_fkey
    foreign key (user_id)
    references users(id);