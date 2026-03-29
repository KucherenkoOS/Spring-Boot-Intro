DELETE FROM book_category;
DELETE FROM books;
DELETE FROM categories;
INSERT INTO categories (id, name, description, is_deleted) VALUES (1, 'Fantasy', 'Books about magic', false);
INSERT INTO categories (id, name, description, is_deleted) VALUES (2, 'Sci-Fi', 'Future technology', false);
INSERT INTO books (id, title, author, isbn, price, is_deleted) VALUES (1, 'The Hobbit', 'Tolkien', '9780007440831', 20.00, false);
INSERT INTO book_category (book_id, category_id) VALUES (1, 1);