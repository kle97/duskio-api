CREATE TABLE author
(
    author_id        INT GENERATED ALWAYS AS IDENTITY,
    author_name      VARCHAR(512) NOT NULL,
    birth_date       DATE,
    death_date       DATE,
    author_date      DATE,
    biography        VARCHAR(8192),
    photo            VARCHAR(255),
    ol_key           VARCHAR(255),
    created_by       VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (author_id)
);


CREATE TABLE alternate_name
(
    alternate_name_id INT GENERATED ALWAYS AS IDENTITY,
    alternate_name    VARCHAR(512) NOT NULL,
    author_id         INT          NOT NULL,
    created_by        VARCHAR(255) NOT NULL,
    created_at        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by  VARCHAR(255) NOT NULL,
    last_modified_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (alternate_name_id),
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);


CREATE TABLE author_link
(
    author_link_id   INT GENERATED ALWAYS AS IDENTITY,
    title            VARCHAR(512),
    url              VARCHAR(512),
    author_id        INT          NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (author_link_id),
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);


CREATE TABLE work
(
    work_id          INT GENERATED ALWAYS AS IDENTITY,
    title            VARCHAR(512) NOT NULL,
    ol_key           VARCHAR(255),
    created_by       VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (work_id)
);


CREATE TABLE author_work
(
    book_id   INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES work (work_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);


CREATE TABLE subject
(
    subject_id       INT GENERATED ALWAYS AS IDENTITY,
    subject_name     VARCHAR(512) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (subject_id)
);


CREATE TABLE work_subject
(
    subject_id INT NOT NULL,
    work_id    INT NOT NULL,
    PRIMARY KEY (subject_id, work_id),
    FOREIGN KEY (subject_id) REFERENCES subject (subject_id) ON DELETE CASCADE,
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE CASCADE
);


CREATE TABLE rating
(
    work_id          INT GENERATED ALWAYS AS IDENTITY,
    score            INT          NOT NULL,
    rating_id        INT          NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE CASCADE,
);


CREATE TABLE publisher
(
    publisher_id     INT GENERATED ALWAYS AS IDENTITY,
    publisher_name   VARCHAR(255) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (publisher_id)
);


CREATE TABLE edition
(
    edition_id          INT GENERATED ALWAYS AS IDENTITY,
    title               VARCHAR(512) NOT NULL,
    subtitle            VARCHAR(512),
    description         VARCHAR(8192),
    pagination          VARCHAR(255),
    number_of_pages     INT,
    volumns             VARCHAR(255),
    physical_format     VARCHAR(255),
    physical_dimensions VARCHAR(255),
    weight              VARCHAR(255),
    isbn10              VARCHAR(255),
    isbn13              VARCHAR(255),
    oclc_number         VARCHAR(255),
    lccn_number         VARCHAR(255),
    dewey_number        VARCHAR(255),
    lc_classifications  VARCHAR(255),
    language            VARCHAR(255),
    publish_date        DATE,
    publish_country     VARCHAR(255),
    publish_place       VARCHAR(255),
    cover               VARCHAR(255),
    ol_key              VARCHAR(255),
    grade               INT          NOT NULL,
    publisher_id        INT,
    work_id             INT,
    created_by          VARCHAR(255) NOT NULL,
    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (edition_id),
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE SET NULL,
    FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id) ON DELETE SET NULL
);

