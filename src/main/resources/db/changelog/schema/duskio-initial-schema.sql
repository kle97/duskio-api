CREATE TABLE author
(
    author_id INT NOT NULL,
    author_name VARCHAR(512) NOT NULL,
    birth_date DATE,
    death_date DATE,
    biography VARCHAR(8192),
    photo VARCHAR(255),
    created_at DATE NOT NULL,
    modified_at DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    ol_key VARCHAR(255) NOT NULL,
    date DATE,
    PRIMARY KEY (author_id)
);

CREATE TABLE work
(
    work_id INT NOT NULL,
    ol_key VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    title VARCHAR(512) NOT NULL,
    PRIMARY KEY (work_id)
);

CREATE TABLE author_work
(
    book_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES work(work_id),
    FOREIGN KEY (author_id) REFERENCES author(author_id)
);

CREATE TABLE alternate_name
(
    alternate_name_id INT NOT NULL,
    alternate_name VARCHAR(512) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (alternate_name_id),
    FOREIGN KEY (author_id) REFERENCES author(author_id)
);

CREATE TABLE publisher
(
    publisher_id INT NOT NULL,
    publisher_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    PRIMARY KEY (publisher_id)
);

CREATE TABLE author_link
(
    author_link_id INT NOT NULL,
    title VARCHAR(512) NOT NULL,
    url VARCHAR(512),
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (author_link_id),
    FOREIGN KEY (author_id) REFERENCES author(author_id)
);

CREATE TABLE rating
(
    created_by VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    rating_id INT NOT NULL,
    score INT NOT NULL,
    work_id INT NOT NULL,
    PRIMARY KEY (rating_id),
    FOREIGN KEY (work_id) REFERENCES work(work_id)
);

CREATE TABLE subject
(
    created_by VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    subject_id INT NOT NULL,
    subject_name VARCHAR(512) NOT NULL,
    PRIMARY KEY (subject_id)
);

CREATE TABLE work_subject
(
    subject_id INT NOT NULL,
    work_id INT NOT NULL,
    PRIMARY KEY (subject_id, work_id),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
    FOREIGN KEY (work_id) REFERENCES work(work_id)
);

CREATE TABLE edition
(
    edition_id INT NOT NULL,
    ol_key VARCHAR(255) NOT NULL,
    subtitle VARCHAR(512),
    title VARCHAR(512) NOT NULL,
    number_of_pages INT,
    weight VARCHAR(255),
    isbn10 VARCHAR(255),
    isbn13 VARCHAR(255),
    publish_date DATE,
    created_by VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL,
    modified_by VARCHAR(255) NOT NULL,
    modified_at DATE NOT NULL,
    grade INT NOT NULL,
    pagination VARCHAR(255),
    physical_format VARCHAR(255),
    physical_dimensions VARCHAR(255),
    publish_place VARCHAR(255),
    oclc_number VARCHAR(255),
    lccn_number VARCHAR(255),
    dewey_number VARCHAR(255),
    lc_classifications VARCHAR(255),
    language VARCHAR(255),
    publish_country VARCHAR(255),
    volumns VARCHAR(255),
    cover VARCHAR(255),
    description VARCHAR(8192),
    work_id INT,
    publisher_id INT,
    PRIMARY KEY (edition_id),
    FOREIGN KEY (work_id) REFERENCES work(work_id),
    FOREIGN KEY (publisher_id) REFERENCES publisher(publisher_id)
);