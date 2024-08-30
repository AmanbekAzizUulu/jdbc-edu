drop database if exists literature;
create database literature;

drop table if exists books;
drop table if exists genres;
drop table if exists authors;


create table authors (
	author_id 		INT AUTO_INCREMENT PRIMARY KEY,
	first_name 		VARCHAR(100) NOT NULL,
	last_name 		VARCHAR(100) NOT NULL,
	date_of_birth 	DATE,
	date_of_death 	DATE,
	nationality 	VARCHAR(50),
	biography 		TEXT,
	email 			VARCHAR(255),
	website 		VARCHAR(255),
	gender 			ENUM('Male', 'Female'),
	profile_picture VARCHAR(255)
);

CREATE TABLE genres (
	genre_id 		INT AUTO_INCREMENT PRIMARY KEY,
	genre_name 		VARCHAR(50) NOT NULL
);

CREATE TABLE books (
	book_id 		INT AUTO_INCREMENT PRIMARY KEY,
	title 			VARCHAR(250) NOT NULL,
	publication_date DATE,
	author_id 		INT NOT NULL,
	genre_id 		INT NOT NULL,
	FOREIGN KEY (author_id) REFERENCES authors(author_id),
	FOREIGN KEY (genre_id) REFERENCES genres(genre_id)
);
