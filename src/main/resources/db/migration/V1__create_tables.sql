CREATE TABLE users
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date_created TIMESTAMP WITHOUT TIME ZONE,
    date_updated TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
    password     VARCHAR(255)                            NOT NULL,
    email        VARCHAR(100)                            NOT NULL,
    first_name   VARCHAR(50)                             NOT NULL,
    last_name    VARCHAR(50)                             NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE articles
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    date_created   TIMESTAMP WITHOUT TIME ZONE,
    date_updated   TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp,
    title          VARCHAR(255),
    content        TEXT,
    date_published DATE,
    status         VARCHAR(255) check (status in ('CREATED', 'PUBLISHED', 'UNPUBLISHED')),
    CONSTRAINT pk_articles PRIMARY KEY (id)
);

create table articles_authors
(
    articles_id BIGINT NOT NULL,
    authors     VARCHAR(100)
);

create table articles_publishers
(
    articles_id BIGINT NOT NULL,
    publishers  VARCHAR(100)
);