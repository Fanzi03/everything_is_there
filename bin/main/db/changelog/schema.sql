-- db/changelog/schema.sql

-- liquibase formatted sql

-- changeset fanzi03:create-items-table
CREATE TABLE items(
	id UUID PRIMARY KEY,
	name VARCHAR(55) NOT NULL
);
