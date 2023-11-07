CREATE TABLE settings
(
    id SERIAL PRIMARY KEY,
    key VARCHAR(50) NOT NULL UNIQUE,
    value VARCHAR(50),
	create_date timestamptz NULL,
	update_date timestamptz NULL,
);