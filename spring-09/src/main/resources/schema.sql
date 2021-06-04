DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, FIO VARCHAR(255));

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID BIGINT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255));

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT AUTO_INCREMENT PRIMARY KEY, TITLE VARCHAR(255), AUTHOR_ID BIGINT, GENRE_ID BIGINT);

ALTER TABLE BOOKS ADD FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID);
ALTER TABLE BOOKS ADD FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID);