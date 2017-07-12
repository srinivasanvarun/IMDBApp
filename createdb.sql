CREATE TABLE movies (movieid INTEGER PRIMARY KEY, title VARCHAR2(200), imdbID INTEGER, spanishTitle VARCHAR2(100), imdbPictureURL VARCHAR2(200), year INTEGER, rtmovieID VARCHAR2(150), rtAllCriticsRating FLOAT, rtAllCriticsNumReviews INTEGER, rtAllCriticsNumFresh INTEGER, rtAllCriticsNumRotten INTEGER, rtAllCriticsScore INTEGER, rtTopCriticsRating FLOAT, rtTopCriticsNumReviews INTEGER, rtTopCriticsNumFresh INTEGER, rtTopCriticsNumRotten INTEGER, rtTopCriticsScore INTEGER, rtAudienceRating FLOAT, rtAudienceNumRatings INTEGER, rtAudienceScore INTEGER, rtPictureURL VARCHAR2(200));
CREATE TABLE tags (tagid INTEGER, tagtext VARCHAR2(100));
CREATE TABLE movie_countries (movieid INTEGER, country VARCHAR2(100));
CREATE TABLE movie_genres (movieid INTEGER, genre VARCHAR2(50));
CREATE TABLE movie_tags (movieid INTEGER, tagid INTEGER, tagweight INTEGER);
CREATE TABLE movie_locations (movieid INTEGER, country VARCHAR2(50), state VARCHAR2(50), city VARCHAR2(100), street VARCHAR2(200));
CREATE TABLE movie_actors (movieid INTEGER, actorid VARCHAR2(100), actorname VARCHAR2(100), actorrating INTEGER);
CREATE TABLE movie_directors (movieid INTEGER, directorid VARCHAR2(100), directorname VARCHAR2(100));
CREATE TABLE user_taggedmovies (userid INTEGER, movieid INTEGER, tagid INTEGER);

CREATE INDEX genre_index ON movie_genres (genre);

CREATE INDEX country_index ON movie_countries (country);

CREATE INDEX actor_index ON movie_actors (actorname);

CREATE INDEX director_index ON movie_directors (directorname);

CREATE INDEX usertags_index on user_taggedmovies (tagid);