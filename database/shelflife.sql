-- my ShelfLife database
-- I run this in MySQL Workbench before I start my app

CREATE DATABASE IF NOT EXISTS shelflife;
USE shelflife;

DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    author           VARCHAR(150) NOT NULL,
    isbn             VARCHAR(13) UNIQUE,
    genre            VARCHAR(50) NOT NULL,
    format           ENUM('hardcover','paperback','ebook','audiobook') NOT NULL,
    publication_year SMALLINT,
    page_count       INT,
    reading_status   ENUM('want_to_read','reading','finished') NOT NULL DEFAULT 'want_to_read',
    rating           TINYINT,
    date_finished    DATE,
    owned            BOOLEAN DEFAULT TRUE,
    notes            TEXT,
    created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- I added some of my books so the app isn't empty
INSERT INTO books (title, author, isbn, genre, format, publication_year, page_count, reading_status, rating, date_finished, owned, notes)
VALUES
('The Hobbit', 'J.R.R. Tolkien', '9780547928227', 'Fantasy', 'paperback', 1937, 300, 'finished', 5, '2026-03-14', TRUE, 'One of my all time favorites.'),
('Project Hail Mary', 'Andy Weir', '9780593135204', 'Science Fiction', 'hardcover', 2021, 476, 'reading', NULL, NULL, TRUE, 'Really fun so far.'),
('Atomic Habits', 'James Clear', '9780735211292', 'Self-Help', 'ebook', 2018, 320, 'want_to_read', NULL, NULL, FALSE, 'Borrow from the library.');
