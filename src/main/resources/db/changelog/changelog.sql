-- liquibase formatted sql

--changeset 1 kodarov_s: roles
CREATE TABLE roles
(
    role VARCHAR(50) PRIMARY KEY
);
INSERT INTO roles (role)
VALUES ('USER'),
       ('ADMIN');

--changeset 2 kodarov_s: avatar
CREATE TABLE avatars
(
    id   SERIAL PRIMARY KEY,
    data BYTEA CHECK (LENGTH(data) <= 5 * 1024 * 1024)
);

--changeset 3 kodarov_s: users
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    login      VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(50),
    role       VARCHAR(50)  NOT NULL,
    avatar_id  INTEGER REFERENCES avatars (id)
);
--changeset 4 kodarov_s: ad_image
CREATE TABLE ad_image
(
    id   SERIAL PRIMARY KEY,
    data BYTEA CHECK (LENGTH(data) <= 10 * 1024 * 1024)
);

--changeset 5 kodarov_s: ads
CREATE TABLE ads
(
    id          SERIAL PRIMARY KEY,
    image_uri   VARCHAR(50),
    price       INTEGER,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    user_id     SERIAL REFERENCES users (id),
    ad_image_id SERIAL REFERENCES ad_image (id)
    --ON DELETE CASCADE
);
--changeset 6 kodarov_s: comments
CREATE TABLE comments
(
    id        SERIAL PRIMARY KEY,
    date_time TIMESTAMP,
    text      TEXT,
    user_id   SERIAL    REFERENCES users(id),
    ad_id     SERIAL    REFERENCES ads(id),
);


