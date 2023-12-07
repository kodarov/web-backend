--changeset 1 kodarov_s: roles
CREATE TABLE roles
(
    role VARCHAR(50) NOT NULL PRIMARY KEY
);
INSERT INTO roles (role)
VALUES ('USER'),
       ('ADMIN');

--changeset 2 kodarov_s: avatar
CREATE TABLE avatars
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    data BLOB CHECK (LENGTH(data) <= 5 * 1024 * 1024)
);

--changeset 3 kodarov_s: users
CREATE TABLE users
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    login      VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    phone      VARCHAR(50),
    role       VARCHAR(50)  NOT NULL,
    avatar_id  INT REFERENCES avatars (id)
);

--changeset 4 kodarov_s: ad_image
CREATE TABLE ad_image
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    data BLOB CHECK (LENGTH(data) <= 10 * 1024 * 1024)
);

--changeset 5 kodarov_s: ads
CREATE TABLE ads
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    price       INT,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    user_id     INT REFERENCES users (id),
    ad_image_id INT REFERENCES ad_image (id)
);

--changeset 6 kodarov_s: comments
CREATE TABLE comments
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    date_time TIMESTAMP,
    text      TEXT,
    user_id   INT REFERENCES users(id),
    ad_id     INT REFERENCES ads(id)
);