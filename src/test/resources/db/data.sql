-- test avatar
INSERT INTO avatars (data) VALUES (X'89504E470D0A1A0A0000000D4948445200000050000000500806000000952F1D0000000A49444154789C63000100000500010D0A2DB40000000049454E44AE426082');

-- test user
INSERT INTO users (login, password, first_name, last_name, phone, role, avatar_id)
VALUES ('kodarov@gmail.com', '$2y$10$QHIcFXFrciNttlsG3y5MSOl6YFVEyJWcrWOxBKqXKPC6oSTfbdvkG', 'salavat', 'kodarov', '79616544133', 'USER', 1);

-- test ad image
INSERT INTO ad_image (data) VALUES (X'89504E470D0A1A0A0000000D4948445200000050000000500806000000952F1D0000000A49444154789C63000100000500010D0A2DB40000000049454E44AE426082');

-- test ad
INSERT INTO ads (price, title, description, user_id, ad_image_id)
VALUES (1000, 'Test Ad', 'This is a test ad', 1, 1);

-- test comment
INSERT INTO comments (date_time, text, user_id, ad_id)
VALUES ('2023-12-01 12:00:00', 'This is a test comment', 1, 1);