INSERT INTO categories (id, name, is_deleted) VALUES (1, 'Sci-Fi', false);
INSERT INTO categories (id, name, is_deleted) VALUES (2, 'Fantasy', false);
INSERT INTO books (id, title, author, isbn, price, is_deleted)
VALUES (1, 'Title 1', 'Author 1', '9234329234', 12.99, false);
INSERT INTO books (id, title, author, isbn, price, is_deleted)
VALUES (2, 'Title 2', 'Author 2', '92343292342', 14.99, false);
INSERT INTO book_category (book_id, category_id) VALUES (1, 1);
INSERT INTO book_category (book_id, category_id) VALUES (2, 2);