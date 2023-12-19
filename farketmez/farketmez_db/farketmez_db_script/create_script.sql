-- Drop tables
drop table if exists public.participants cascade;
drop table if exists public.events cascade;
drop table if exists public.event_types cascade;
drop table if exists public.locations cascade;
drop table if exists public.user_interests cascade;
drop table if exists public.users cascade;
drop table if exists public.user_types cascade;
drop table if exists public.interests cascade;
drop table if exists public.settings cascade;

-- user_types table
CREATE TABLE user_types (
    id int PRIMARY KEY,
    type VARCHAR(20) NOT NULL
);

-- users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_type_id INT,
    mail VARCHAR(50),
    username VARCHAR(50),
    password VARCHAR(256),
    name VARCHAR(30),
    lastname VARCHAR(30),
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
    id SERIAL PRIMARY KEY,
    type VARCHAR(20)
);

-- locations table
CREATE TABLE locations (
    id SERIAL PRIMARY KEY,
    longitude DOUBLE PRECISION,
    latitude DOUBLE PRECISION
);

-- events table
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    event_type_id INT,
    location_id INT,
    creator_id INT NOT NULL,
    is_active BOOLEAN NOT NULL,
	is_private BOOLEAN NOT NULL,
    title VARCHAR(50) NOT NULL,
	price DECIMAL(18, 2) NOT NULL,
	place VARCHAR(50) NOT NULL,
    description VARCHAR(200) NOT NULL,
    date TIMESTAMP NOT NULL,
    average_rating NUMERIC(2, 1) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (event_type_id) REFERENCES event_types(id),
    FOREIGN KEY (location_id) REFERENCES locations(id)
);

-- participants table
CREATE TABLE participants (
    id SERIAL PRIMARY KEY,
    user_id INT,
    event_id INT,
    rating NUMERIC(2, 1),
    comment VARCHAR(55),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);

-- interests table
CREATE TABLE interests (
    id SERIAL PRIMARY KEY,
    interest_name VARCHAR(50)
);

-- user_interests table
CREATE TABLE user_interests (
    id SERIAL PRIMARY KEY,
    user_id INT,
    interest_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (interest_id) REFERENCES interests(id)
);

CREATE TABLE settings
(
    id SERIAL PRIMARY KEY,
    key VARCHAR(50) NOT NULL UNIQUE,
    value VARCHAR(50),
    create_date timestamptz NULL,
    update_date timestamptz NULL
);