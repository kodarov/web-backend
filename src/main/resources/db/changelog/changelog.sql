-- liquibase formatted sql

--changeset 1 kodarov_s: roles
CREATE TABLE roles
(
    role VARCHAR(50) PRIMARY KEY
);
INSERT INTO roles (role)
VALUES ('USER'),
       ('ADMIN');

--changeset 2 kodarov_s: users
CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    login      VARCHAR(50) NOT NULL UNIQUE,
    password   VARCHAR(255),
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(50),
    role       VARCHAR(50)
);
--changeset 3 kodarov_s: avatar
CREATE TABLE avatars
(
    id      SERIAL PRIMARY KEY,
    data    BYTEA CHECK (LENGTH(data) <= 5 * 1024 * 1024),
    user_id SERIAL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE -- удалим вместе с user и avatar
);
--changeset 4 kodarov_s: ad_image
CREATE TABLE ad_image
(
    id    SERIAL PRIMARY KEY,
    data  BYTEA CHECK (LENGTH(data) <= 10 * 1024 * 1024)
    --ad_id SERIAL NOT NULL,
    --FOREIGN KEY (ad_id) REFERENCES ads (id) ON DELETE CASCADE -- удалим вместе с ad все его ad_image
);

--changeset 5 kodarov_s: ads
CREATE TABLE ads
(
    id          SERIAL PRIMARY KEY,
    image_uri   VARCHAR(50),
    price       INTEGER,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    user_id     SERIAL       NOT NULL,
    ad_image_id SERIAL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE, -- удалим вместе с user и все ad
    FOREIGN KEY (ad_image_id) REFERENCES ad_image (id)
);
--changeset 6 kodarov_s: comments
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


