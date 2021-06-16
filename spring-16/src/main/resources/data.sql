insert into GENRES(id, name) values(1, 'genre1');
insert into AUTHORS(id, fio) values(1, 'author1');
insert into BOOKS(id, title, author_id, genre_id) values(1, 'book1', 1, 1);
insert into BOOK_COMMENTS(id, book_id, comment) values(1, 1, 'comment1');