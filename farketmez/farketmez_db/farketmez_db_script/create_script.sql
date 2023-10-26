CREATE DATABASE farketmez;
USE farketmez;

-- user_types table
CREATE TABLE user_types (
    id INT PRIMARY KEY,
    type VARCHAR(20) NOT NULL
);

-- users table
CREATE TABLE users (
    id INT PRIMARY KEY,
    user_type_id INT,
    token VARCHAR(256),
    username VARCHAR(50),
    password VARCHAR(50),
    name VARCHAR(30),
    surname VARCHAR(30),
    age INT,
    gender INT,
    longitude VARCHAR(100),
    latitude VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (user_type_id) REFERENCES user_types(id)
);

-- event_types table
CREATE TABLE event_types (
    id INT PRIMARY KEY,
    type INT
);

-- locations table
CREATE TABLE locations (
    id INT PRIMARY KEY,
    longitude VARCHAR(100),
    latitude VARCHAR(100)
);

-- events table
CREATE TABLE events (
    id INT PRIMARY KEY,
    event_type_id INT,
    location_id INT,
    creator_id INT,
    is_active CHAR(1),
    title VARCHAR(50),
    description VARCHAR(200),
    date TIMESTAMP,
    average_rating NUMERIC(1, 1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (event_type_id) REFERENCES event_types(id),
    FOREIGN KEY (location_id) REFERENCES locations(id),
    FOREIGN KEY (creator_id) REFERENCES users(id)
);

-- participants table
CREATE TABLE participants (
    id INT PRIMARY KEY,
    user_id INT,
    event_id INT,
    rating NUMERIC(1, 1),
    comment VARCHAR(55),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);

-- interests table
CREATE TABLE interests (
    id INT PRIMARY KEY,
    interest_name VARCHAR(50)
);

-- user_interests table
CREATE TABLE user_interests (
    id INT PRIMARY KEY,
    user_id INT,
    interest_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (interest_id) REFERENCES interests(id)
);

