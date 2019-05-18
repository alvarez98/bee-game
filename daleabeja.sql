CREATE DATABASE daleabeja;
USE daleabeja;
CREATE TABLE users(
	id_user SERIAL NOT NULL,
    name_user VARCHAR(40) NOT NULL,
    password_user VARCHAR(10) NOT NULL,
    PRIMARY KEY (id_user)
);
CREATE TABLE scores (
    id_score INTEGER AUTO_INCREMENT,
    score_sc INTEGER(10) NOT NULL,
    date_sc DATETIME NOT NULL,
    id_user SERIAL,
    PRIMARY KEY (id_score),
    FOREIGN KEY (id_user)
        REFERENCES users (id_user)
        ON DELETE CASCADE
);

    