-- liquibase formatted sql

-- changeset fanzi03:add-tag
CREATE TABLE items(
	id UUID PRIMARY KEY,
	name VARCHAR(55) NOT NULL,
	primary_tag VARCHAR(55)
);
