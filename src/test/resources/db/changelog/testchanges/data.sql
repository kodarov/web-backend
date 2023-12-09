--changeset 7 kodarov_s: avatars-test
INSERT INTO avatars (data) VALUES (E'\\x89504E470D0A1A0A0000000D49484452000000500000005008060000003A9C64'::BYTEA);

--changeset 8 kodarov_s: users-test
INSERT INTO users (login, password, first_name, last_name, phone, role, avatar_id)
VALUES ('kodarov@gmail.com', '$2y$10$QHIcFXFrciNttlsG3y5MSOl6YFVEyJWcrWOxBKqXKPC6oSTfbdvkG', 'salavat', 'kodarov', '79616544133', 'USER', 1);

--changeset 9 kodarov_s: ad_image-test
INSERT INTO ad_image (data) VALUES (E'\\x89504E470D0A1A0A0000000D49484452000000500000005008060000003A9C64'::BYTEA);

--changeset 10 kodarov_s: ads-test
INSERT INTO ads (price, title, description, user_id, ad_image_id)
VALUES (1000, 'Test Ad', 'This is a test ad', 1, 1);

--changeset 11 kodarov_s: comments-test
INSERT INTO comments (date_time, text, user_id, ad_id)
VALUES ('2023-12-01 12:00:00', 'This is a test comment', 1, 1);