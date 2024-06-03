--liquibase formatted sql
--changeset khoale:insert-post-data

INSERT INTO category (name) 
VALUES ('room for rent'), 
       ('house for rent');

INSERT INTO post (title, description, category_id)
VALUES ('Master room for rent', '1 Master room for rent in Westminster. Contact (714) 999-9999.', 1),
       ('2 rooms for rent in Santa Ana', '2 rooms for rent in Santa Ana. Contact (714) 888-8888.', 1),
       ('1 room for rent', '1 spacious room for rent in Garden Grove. Contact (714) 777-7777.', 1);

INSERT INTO post_image (image_link, post_id)
VALUES ('https://res.cloudinary.com/khoale97/image/upload/jbuy/635134_247882_01_front_thumbnail.jpg', 1),
       ('https://res.cloudinary.com/khoale97/image/upload/jbuy/635998_259739_01_front_thumbnail.jpg', 2),
       ('https://res.cloudinary.com/khoale97/image/upload/jbuy/642647_335810_01_front_thumbnail.jpg', 3);

--rollback DELETE from post_image WHERE id IN (1,2,3);
--rollback DELETE from post WHERE id IN (1,2,3);
--rollback DELETE from category WHERE id IN (1,2);