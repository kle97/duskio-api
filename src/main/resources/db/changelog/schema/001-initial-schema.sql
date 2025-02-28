CREATE TABLE author
(
    author_id        INT          NOT NULL AUTO_INCREMENT,
    author_name      VARCHAR(512) NOT NULL,
    birth_date       VARCHAR(255),
    death_date       VARCHAR(255),
    author_date      VARCHAR(255),
    biography        VARCHAR(8192),
    photo            VARCHAR(255),
    ol_key           VARCHAR(255),
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (author_id)
);


CREATE TABLE alternate_name
(
    alternate_name_id INT          NOT NULL AUTO_INCREMENT,
    alternate_name    VARCHAR(512) NOT NULL,
    author_id         INT          NOT NULL,
    created_by        VARCHAR(255) NOT NULL,
    created_at        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by  VARCHAR(255) NOT NULL,
    last_modified_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision          INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (alternate_name_id),
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);


CREATE TABLE author_link
(
    author_link_id   INT          NOT NULL AUTO_INCREMENT,
    title            VARCHAR(512),
    url              VARCHAR(512),
    author_id        INT          NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (author_link_id),
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE
);


CREATE TABLE work
(
    work_id          INT          NOT NULL AUTO_INCREMENT,
    title            VARCHAR(512) NOT NULL,
    ol_key           VARCHAR(255),
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (work_id)
);


CREATE TABLE author_work
(
    author_id        INT          NOT NULL,
    work_id          INT          NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (author_id, work_id),
    FOREIGN KEY (author_id) REFERENCES author (author_id) ON DELETE CASCADE,
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE CASCADE
);


CREATE TABLE subject
(
    subject_id       INT          NOT NULL AUTO_INCREMENT,
    subject_name     VARCHAR(512) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (subject_id)
);


CREATE TABLE work_subject
(
    work_id          INT          NOT NULL,
    subject_id       INT          NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (work_id, subject_id),
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject (subject_id) ON DELETE CASCADE
);


CREATE TABLE rating
(
    rating_id        INT          NOT NULL AUTO_INCREMENT,
    score            INT          NOT NULL,
    work_id          INT          NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE CASCADE
);


CREATE TABLE publisher
(
    publisher_id     INT          NOT NULL AUTO_INCREMENT,
    publisher_name   VARCHAR(255) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (publisher_id)
);


CREATE TABLE edition
(
    edition_id          INT          NOT NULL AUTO_INCREMENT,
    title               VARCHAR(512) NOT NULL,
    subtitle            VARCHAR(512),
    description         VARCHAR(8192),
    pagination          VARCHAR(255),
    number_of_pages     INT,
    volumns             VARCHAR(255),
    physical_format     VARCHAR(255),
    physical_dimensions VARCHAR(255),
    weight              VARCHAR(255),
    isbn_10             VARCHAR(255),
    isbn_13             VARCHAR(255),
    oclc_number         VARCHAR(255),
    lccn_number         VARCHAR(255),
    dewey_number        VARCHAR(255),
    lc_classifications  VARCHAR(255),
    language            VARCHAR(255),
    publish_date        VARCHAR(255),
    publish_country     VARCHAR(255),
    publish_place       VARCHAR(255),
    cover               VARCHAR(255),
    ol_key              VARCHAR(255),
    grade               INT          NOT NULL,
    publisher_id        INT,
    work_id             INT,
    created_by          VARCHAR(255) NOT NULL,
    created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision            INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (edition_id),
    FOREIGN KEY (work_id) REFERENCES work (work_id) ON DELETE SET NULL,
    FOREIGN KEY (publisher_id) REFERENCES publisher (publisher_id) ON DELETE SET NULL
);