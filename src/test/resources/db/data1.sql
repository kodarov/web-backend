-- Тестовые данные для roles
INSERT INTO roles (role)
VALUES ('USER'),
       ('ADMIN');

-- Тестовые данные для avatars
INSERT INTO avatars (data)
VALUES (E'\\x89504E470D0A1A0A0000000D49484452000000500000005008060000003A9C64'::BYTEA),
       (E'\\x89504E470D0A1A0A0000000D49484452000000500000005008060000003A9C64'::BYTEA);
-- Пример данных в формате BYTEA, можно изменить

-- Тестовые данные для users
INSERT INTO users (login, password, first_name, last_name, phone, role, avatar_id)
VALUES ('user1', 'password1', 'John', 'Doe', '123456789', 'USER', 1),
       ('admin1', 'adminpassword', 'Admin', 'Smith', '987654321', 'ADMIN', 2);

-- Тестовые данные для ad_image
INSERT INTO ad_image (data)
VALUES (E'\\x89504E470D0A1A0A0000000D49484452000000500000005008060000003A9C64'::BYTEA),
       (E'\\x89504E470D0A1A0A0000000D49484452000000500000005008060000003A9C64'::BYTEA);
-- Пример данных в формате BYTEA, можно изменить

-- Тестовые данные для ads
INSERT INTO ads (price, title, description, user_id, ad_image_id)
VALUES (100, 'Ad Title 1', 'Description for Ad 1', 1, 1),
       (200, 'Ad Title 2', 'Description for Ad 2', 2, 2);

-- Тестовые данные для comments
INSERT INTO comments (date_time, text, user_id, ad_id)
VALUES ('2023-12-09 12:00:00', 'Comment 1', 1, 1),
       ('2023-12-10 14:30:00', 'Comment 2', 2, 2);
