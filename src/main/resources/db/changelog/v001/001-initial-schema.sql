--liquibase formatted sql
--changeset khoale:create-post-tables

CREATE TABLE category (
    category_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id)
);

CREATE TABLE post (
    post_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    datetime TIMESTAMP NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE post_image (
    post_image_id INT NOT NULL AUTO_INCREMENT,
    image_link VARCHAR(1000) NOT NULL,
    post_id INT NOT NULL,
    PRIMARY KEY (post_image_id),
    FOREIGN KEY (post_id) REFERENCES post(post_id)
);

--rollback DROP TABLE post_image;
--rollback DROP TABLE post;
--rollback DROP TABLE category;