DROP DATABASE IF EXISTS IMDB;
CREATE DATABASE IMDB;
USE IMDB; 

DROP TABLE IF EXISTS USER;
CREATE TABLE USER
(
 id VARCHAR(50),
 email VARCHAR(50),
 password VARCHAR(50),
 favMovie VARCHAR(50),
 region VARCHAR(50),
 age INT,
 language VARCHAR(50),
 PRIMARY KEY (id)
);

DROP TABLE IF EXISTS USER_RATING;
CREATE TABLE USER_RATING
(
 id VARCHAR(50),
 tconst VARCHAR(50),
 stars INT,
 PRIMARY KEY (id,tconst),
 FOREIGN KEY (tconst) REFERENCES MOVIE_INFO(tconst)
);

DROP TABLE IF EXISTS  MOVIE_INFO;
CREATE TABLE MOVIE_INFO
(
  tconst VARCHAR(50),
  ordering INT,
  title VARCHAR(128),
  region VARCHAR(50),
  language VARCHAR(50),
  types VARCHAR(50), 
  attributes VARCHAR(50),
  isOriginalTItle boolean,
  PRIMARY KEY (tconst)
);

DROP TABLE IF EXISTS BASICS;
CREATE TABLE BASICS
(
  tconst VARCHAR(50),
  titleType VARCHAR(50),
  primaryTitle VARCHAR(128),
  originalTitle VARCHAR(128),
  isAdult BOOLEAN,
  startYear INT, 
  endYear INT,
  runtimeMinutes INT,
  genres VARCHAR(50),
  FOREIGN KEY (tconst) REFERENCES MOVIE_INFO(tconst)
);

DROP TABLE IF EXISTS CREW;
CREATE TABLE CREW
(
 tconst VARCHAR(50),
 directors VARCHAR(50), -- array of nconst
 writers VARCHAR(50), -- array of nconst
 FOREIGN KEY (tconst) REFERENCES MOVIE_INFO(tconst)
);

DROP TABLE IF EXISTS EPISODE;
CREATE TABLE EPISODE
(
 tconst VARCHAR(50),
 parentTconst VARCHAR(30),
 seasonNumber INT,
 episodeNumber INT,
 FOREIGN KEY (tconst) REFERENCES MOVIE_INFO(tconst)
);


DROP TABLE IF EXISTS PRINCIPALS;
CREATE TABLE PRINCIPALS
(
 tconst VARCHAR(50),
 ordering INT, -- should it be a foreign key? from MOVIE_INFO
 nconst VARCHAR(128),
 category VARCHAR(50),
 job VARCHAR(50),
 characters VARCHAR(50),
 FOREIGN KEY (tconst) REFERENCES MOVIE_INFO(tconst),
 FOREIGN KEY (nconst) REFERENCES NAMESS(nconst)
);

DROP TABLE IF EXISTS RATING;
CREATE TABLE RATING
(
 tconst VARCHAR(50),
 averageRating INT,
 numVotes INT,
 FOREIGN KEY (tconst) REFERENCES MOVIE_INFO(tconst)
);

DROP TABLE IF EXISTS NAMESS;
CREATE TABLE NAMESS
(
 nconst VARCHAR(50),
 primaryName VARCHAR(50),
 birthYear INT,
 deathYear INT,
 primaryProfession VARCHAR(128), 
 knownForTitles VARCHAR(128), 
 PRIMARY KEY (nconst)
);




LOAD DATA LOCAL INFILE '/usr/local/mysql-8.0.12-macos10.13-x86_64/title.akas.tsv' INTO TABLE MOVIE_INFO;
LOAD DATA LOCAL INFILE '/usr/local/mysql-8.0.12-macos10.13-x86_64/title.crew.tsv' INTO TABLE CREW;

