-- liquibase formatted sql

--changeset 1 kodarov_s: users
CREATE TYPE USER_ROLE AS ENUM ('USER','ADMIN');
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    login      VARCHAR(50) NOT NULL UNIQUE,
    password   VARCHAR(50),
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(50) UNIQUE,
    role       USER_ROLE   NOT NULL,
    avatar_uri VARCHAR(50)
);
--changeset 2 kodarov_s: ads
CREATE TABLE ads
(
    id          SERIAL PRIMARY KEY,
    image_uri   VARCHAR(50),
    price       INTEGER,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    user_id     SERIAL       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE -- удалим вместе с user и все ad
);
--changeset 3 kodarov_s: comments
CREATE TABLE comments
(
    id        SERIAL PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    text      TEXT      NOT NULL,
    user_id   SERIAL    NOT NULL,
    ad_id     SERIAL    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (ad_id) REFERENCES ads
);

CREATE TABLE avatar
(
    id         SERIAL PRIMARY KEY,
    file_size  INT8,
    media_type VARCHAR(15),
    data       BYTEA CHECK (LENGTH(data) <= 5 * 1024 * 1024),
    user_id    SERIAL NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE -- удалим вместе с user и все avatar
);

CREATE TABLE ad_image
(
    id         SERIAL PRIMARY KEY,
    file_size  INT8,
    media_type VARCHAR(15),
    data       BYTEA CHECK (LENGTH(data) <= 10 * 1024 * 1024),
    ad_id      SERIAL NOT NULL,
    FOREIGN KEY (ad_id) REFERENCES ads (id) ON DELETE CASCADE -- удалим вместе с ad все его ad_image
);
