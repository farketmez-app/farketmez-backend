-- Veritabanını oluştur
CREATE DATABASE farketmez;
USE farketmez;

-- user_types tablosunu oluştur
CREATE TABLE user_types (
    id serial PRIMARY KEY,
    type varchar(20)
);

-- users tablosunu oluştur
CREATE TABLE users (
    id serial PRIMARY KEY,
    username varchar(50),
    password varchar(50),
    name_surname varchar(100),
    age integer,
    gender integer,
    longitude varchar(100),
    latitude varchar(100),
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp,
    user_type_id integer REFERENCES user_types(id)
);

-- participants tablosunu oluştur
CREATE TABLE participants (
    id serial PRIMARY KEY,
    application_user_id integer REFERENCES users(id),
    event_id integer
);

-- ratings tablosunu oluştur
CREATE TABLE ratings (
    id serial PRIMARY KEY,
    application_user_id integer REFERENCES users(id),
    event_id integer,
    evaluation_rating numeric(1, 1)
);

-- user_interests tablosunu oluştur
CREATE TABLE user_interests (
    id serial PRIMARY KEY,
    application_user_id integer REFERENCES users(id),
    interest_id integer
);

-- interests tablosunu oluştur
CREATE TABLE interests (
    id serial PRIMARY KEY,
    interest_name varchar(50)
);

-- event_types tablosunu oluştur
CREATE TABLE event_types (
    id serial PRIMARY KEY,
    type integer
);

-- events tablosunu oluştur
CREATE TABLE events (
    id serial PRIMARY KEY,
    event_type_id integer REFERENCES event_types(id),
    location_id integer REFERENCES locations(id),
    is_active char(1),
    title varchar(50),
    description varchar(200),
    date timestamp,
    average_rating numeric(1, 1),
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp
);

-- locations tablosunu oluştur
CREATE TABLE locations (
    id serial PRIMARY KEY,
    longitude varchar(100),
    latitude varchar(100)
);