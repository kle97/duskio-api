CREATE TABLE author
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
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
    PRIMARY KEY (id)
);


CREATE TABLE alternate_name
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    alternate_name   VARCHAR(512) NOT NULL,
    author_id        BIGINT       NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);


CREATE TABLE author_link
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    title            VARCHAR(512),
    url              VARCHAR(512) NOT NULL,
    author_id        BIGINT       NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);


CREATE TABLE work
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    title            VARCHAR(512) NOT NULL,
    ol_key           VARCHAR(255),
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);


CREATE TABLE author_work
(
    author_id        BIGINT       NOT NULL,
    work_id          BIGINT       NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (author_id, work_id),
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE,
    FOREIGN KEY (work_id) REFERENCES work (id) ON DELETE CASCADE
);


CREATE TABLE subject
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    subject_name     VARCHAR(512) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);


CREATE TABLE work_subject
(
    work_id          BIGINT       NOT NULL,
    subject_id       BIGINT       NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (work_id, subject_id),
    FOREIGN KEY (work_id) REFERENCES work (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE CASCADE
);


CREATE TABLE rating
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    score            INT          NOT NULL,
    work_id          BIGINT       NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (work_id) REFERENCES work (id) ON DELETE CASCADE
);


CREATE TABLE publisher
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    publisher_name   VARCHAR(255) NOT NULL,
    created_by       VARCHAR(255) NOT NULL,
    created_at       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(255) NOT NULL,
    last_modified_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision         INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);


CREATE TABLE edition
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
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
    publisher_id        BIGINT,
    work_id             BIGINT,
    created_by          VARCHAR(255) NOT NULL,
    created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_by    VARCHAR(255) NOT NULL,
    last_modified_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    revision            INT          NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (work_id) REFERENCES work (id) ON DELETE SET NULL,
    FOREIGN KEY (publisher_id) REFERENCES publisher (id) ON DELETE SET NULL
);