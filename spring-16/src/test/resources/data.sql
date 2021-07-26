insert into GENRES(id, name) values(1, 'genre1');
insert into AUTHORS(id, fio) values(1, 'author1');
insert into BOOKS(id, title, author_id, genre_id) values(1, 'book1', 1, 1);
insert into BOOKS(id, title, author_id, genre_id) values(2, 'book2', 1, 1);
insert into BOOK_COMMENTS(id, book_id, comment) values(1, 2, 'comment1');
insert into BOOK_COMMENTS(id, book_id, comment) values(2, 2, 'comment2');
insert into BOOK_COMMENTS(id, book_id, comment) values(3, 1, 'comment3');
insert into USERS(id, login, password) values(1, 'user', 'pass');